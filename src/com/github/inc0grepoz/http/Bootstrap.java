package com.github.inc0grepoz.http;

import java.io.File;
import java.util.Map;
import java.util.StringJoiner;
import java.util.logging.Logger;

import com.github.inc0grepoz.http.response.Response;
import com.github.inc0grepoz.http.response.ResponseBuilder;
import com.github.inc0grepoz.http.response.ResponseContentType;
import com.github.inc0grepoz.http.response.ResponseStatusCode;

public class Bootstrap
{

    public static void main(String[] args) throws Throwable
    {
        HttpServer server = new HttpServer(Logger.getLogger("INFO"), 80);

        server.getServletManager().register("/api/test", (request) -> {
            ResponseBuilder builder = Response.builder();
            StringJoiner joiner = new StringJoiner("\n");
            Map<String, String> map = request.resolveParameters();

            if (map.isEmpty())
            {
                joiner.add("<title>REST-API</title><p>No arguments specified.</p>");
                builder.code(ResponseStatusCode.BAD_REQUEST);
                builder.contentType(ResponseContentType.TEXT_HTML);
            }
            else
            {
                joiner.add(server.getJsonMapper().serialize(map));
                builder.code(ResponseStatusCode.OK);
                builder.contentType(ResponseContentType.APP_JSON);
            }

            builder.content(joiner.toString());
            return builder.build();
        });

        server.getServletManager().register("/redirect", (request) -> {
            return Response.redirect("https://google.com/");
        });

        server.getServletManager().load(new File("resources.json"));
        server.start();
    }

}
