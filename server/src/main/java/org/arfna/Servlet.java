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
            response.getWriter().println(GsonHelper.getGsonWithPrettyPrint().toJson(apiResponse));
        }
    }

    public String[] generateEndpoint(String uri) {
        uri = uri.replace(ENDPOINT, "");
        return uri.split(URI_SEPARATOR);
    }

    private Optional<Subscriber> getSubscriberCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            List<Cookie> subscriberCookie = Arrays.stream(cookies).filter(c -> c.getName().equals(ECookieKeys.SUBSCRIBER.getCookieName())).collect(Collectors.toList());
            if (subscriberCookie.size() == 1)
                return CacheHelper.getAsSubscriber(subscriberCookie.get(0).getValue());
        }
        return Optional.empty();
    }

    private void addCookies(ApiResponse apiResponse, HttpServletResponse response) {
        if (apiResponse.getResponse() != null && apiResponse.getResponse().getDataToPersist() != null ) {
            List<String> subscriberKeys = apiResponse.getResponse().getDataToPersist().stream().map(CacheHelper::addValue).collect(Collectors.toList());
            subscriberKeys.forEach(k -> {
                Cookie c = new Cookie(ECookieKeys.SUBSCRIBER.getCookieName(), k);
                c.setMaxAge(4 * 60 * 60); // 4 hours in seconds
                c.setHttpOnly(true);
                response.addCookie(c);
            });
        }
    }

}
