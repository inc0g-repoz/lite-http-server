package com.github.inc0grepoz.http;

import java.io.File;
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

        server.getContextManager().register("/api/test", (type, map) -> {
            ResponseBuilder builder = Response.builder();
            StringJoiner joiner = new StringJoiner("\n");

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

        server.getContextManager().register("/redirect", (type, map) -> {
            return Response.redirect("https://google.com/");
        });

        server.getContextManager().load(new File("resources.json"));
        server.start();
    }

}
