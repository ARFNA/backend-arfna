package org.arfna;

import org.arfna.api.ApiResponse;
import org.arfna.database.entity.Subscriber;
import org.arfna.service.CacheHelper;
import org.arfna.service.ServiceClient;
import org.arfna.util.gson.GsonHelper;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author roshnee
 * Note: Figure out why annotations are not working
 */
public class Servlet extends HttpServlet {

    private static final String ENDPOINT = "/arfna/api";
    private static final String URI_SEPARATOR = "/";

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/json");
        response.getWriter().println("{" +
                "status: 200," +
                "response: \"Hello world\"" +
                "}");
    }

    /**
     * This will be the primary API - it will allow us accept a json payload and send a json response easily
     * This also allows for versioning to be done easily via a json
     */
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String[] endpoint = generateEndpoint(request.getRequestURI());
        response.setContentType("text/json");
        if (endpoint.length < 2) {
            response.getWriter().println("{" +
                    "status: 200," +
                    "response: \"Hello world who posts\"" +
                    "}");
        } else {
            ServiceClient client = new ServiceClient();
            Optional<Subscriber> subscriberCookie = getSubscriberCookie(request);
            ApiResponse apiResponse = client.execute(request.getInputStream(), endpoint[1], subscriberCookie);
            addCookies(apiResponse, response);
            removeCookies(apiResponse, request, response);
            response.setStatus(apiResponse.getStatus().getCode());
            response.addHeader("Access-Control-Allow-Headers", "Content-Type");
            response.addHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
            response.addHeader("Access-Control-Allow-Origin", "*");
            response.getWriter().println(GsonHelper.getGsonWithPrettyPrint().toJson(apiResponse));
        }
    }

    public String[] generateEndpoint(String uri) {
        uri = uri.replace(ENDPOINT, "");
        return uri.split(URI_SEPARATOR);
    }

    private Optional<Subscriber> getSubscriberCookie(HttpServletRequest request) {
        Optional<String> subscriberKey = getSubscriberCookieKey(request);
        if (subscriberKey.isPresent()) {
            return CacheHelper.getAsSubscriber(subscriberKey.get());
        }
        return Optional.empty();
    }

    private Optional<String> getSubscriberCookieKey(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            List<Cookie> subscriberCookie = Arrays.stream(cookies).filter(c -> c.getName().equals(ECookieKeys.SUBSCRIBER.getCookieName())).collect(Collectors.toList());
            if (subscriberCookie.size() == 1)
                return Optional.of(subscriberCookie.get(0).getValue());
        }
        return Optional.empty();
    }

    private void addCookies(ApiResponse apiResponse, HttpServletResponse response) {
        if (apiResponse.getResponse() != null && apiResponse.getResponse().getDataToPersist() != null ) {
            List<String> subscriberKeys = apiResponse.getResponse().getDataToPersist().stream().map(CacheHelper::addValue).collect(Collectors.toList());
            subscriberKeys.forEach(k -> {
                Cookie c = generateCookie(k);
                response.addCookie(c);
            });
        }
    }

    private Cookie generateCookie(String k) {
        Cookie c = new Cookie(ECookieKeys.SUBSCRIBER.getCookieName(), k);
        c.setMaxAge(4 * 60 * 60); // 4 hours in seconds
        c.setHttpOnly(true);
        return c;
    }

    private void removeCookies(ApiResponse apiResponse, HttpServletRequest request, HttpServletResponse response) {
        if (apiResponse.getResponse() != null && apiResponse.getResponse().getDataToRevoke() != null &&
                !apiResponse.getResponse().getDataToRevoke().isEmpty()) {
            // for now it ignores the whole response and just removes all the subscriber cookies
            Optional<String> cookieKey = getSubscriberCookieKey(request);
            if (cookieKey.isPresent()) {
                CacheHelper.forceExpiration(cookieKey.get());
                Cookie c = generateCookie(cookieKey.get());
                c.setMaxAge(0);
                response.addCookie(c);
            }
        }
    }

}
