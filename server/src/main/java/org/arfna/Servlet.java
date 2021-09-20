package org.arfna;

import org.arfna.api.ApiResponse;
import org.arfna.service.ServiceClient;
import org.arfna.util.gson.GsonHelper;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
            ApiResponse apiResponse = client.execute(request.getInputStream(), endpoint[1]);
            response.getWriter().println(GsonHelper.getGsonWithPrettyPrint().toJson(apiResponse));
        }
    }

    public String[] generateEndpoint(String uri) {
        uri = uri.replace(ENDPOINT, "");
        return uri.split(URI_SEPARATOR);
    }


}
