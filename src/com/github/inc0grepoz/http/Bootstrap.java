package com.github.inc0grepoz.http;

import java.util.StringJoiner;
import java.util.logging.Logger;

import com.github.inc0grepoz.http.server.Server;
import com.github.inc0grepoz.http.server.response.Response;
import com.github.inc0grepoz.http.server.response.ResponseBuilder;
import com.github.inc0grepoz.http.server.response.ResponseContentType;
import com.github.inc0grepoz.http.server.response.ResponseStatusCode;

public class Bootstrap
{

    public static void main(String[] args) throws Throwable
    {
        Server server = new Server(Logger.getLogger("INFO"), 80);

        server.putResource("/api/test", (map, out) -> {
            ResponseBuilder builder = Response.builder();
            builder.contentType(ResponseContentType.TEXT_HTML);

            StringJoiner joiner = new StringJoiner("\n");
            joiner.add("<title>REST-API</title>");

            if (map.isEmpty())
            {
                joiner.add("<p>No arguments specified.</p>");
                builder.code(ResponseStatusCode.BAD_REQUEST);
            }
            else
            {
                map.forEach((k, v) -> {
                    joiner.add("<p>" + k + "=" + v + "</p>");
                });

                builder.code(ResponseStatusCode.OK);
            }

            builder.content(joiner.toString());
            return builder.build();
        });

        server.loadResourcesFromFiles();
        server.start();
    }

}
