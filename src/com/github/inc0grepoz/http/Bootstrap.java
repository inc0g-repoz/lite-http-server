package com.github.inc0grepoz.http;

import java.util.logging.Logger;

import com.github.inc0grepoz.http.server.Server;
import com.github.inc0grepoz.http.server.response.Response;
import com.github.inc0grepoz.http.server.response.ResponseBuilder;
import com.github.inc0grepoz.http.server.response.ResponseStatusCode;

public class Bootstrap
{

    public static void main(String[] args) throws Throwable
    {
        Server server = new Server(Logger.getLogger("INFO"), 80);

        server.putResource("/api/test", (map, out) -> {
            ResponseBuilder builder = Response.builder();
            builder.appendContent("<title>REST-API</title>");

            if (map.isEmpty())
            {
                builder.code(ResponseStatusCode.BAD_REQUEST);
                builder.appendContent("<p>No arguments specified.</p>");
            }
            else
            {
                builder.code(ResponseStatusCode.OK);
                map.forEach((k, v) -> {
                    builder.appendContent("<p>" + k + "=" + v + "</p>");
                });
            }

            return builder.build();
        });

        server.loadResourcesFromFiles();
        server.start();
    }

}
