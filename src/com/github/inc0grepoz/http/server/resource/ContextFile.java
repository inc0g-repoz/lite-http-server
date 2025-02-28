package com.github.inc0grepoz.http.server.resource;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.net.URLConnection;
import java.util.Map;

import com.github.inc0grepoz.http.server.request.RequestType;
import com.github.inc0grepoz.http.server.response.Response;
import com.github.inc0grepoz.http.server.response.ResponseBuilder;
import com.github.inc0grepoz.http.server.response.ResponseStatusCode;

public class ContextFile implements Context
{

    public static ContextFile from(File file)
    {
        return new ContextFile(file);
    }

    private final File file;

    public ContextFile(File file)
    {
        this.file = file;
    }

    @Override
    public Response handle(RequestType type, Map<String, String> args, BufferedWriter out)
    {
        URLConnection connection;
        try
        {
             connection = file.toURI().toURL().openConnection();
//           connection.setReadTimeout(10_000);
             args.forEach((k, v) -> connection.addRequestProperty(k, v));
        }
        catch (Throwable t)
        {
            throw new IllegalArgumentException("File \"" + file.getName() + "\" not found", t);
        }

        ResponseBuilder builder = Response.builder();
        builder.code(ResponseStatusCode.OK);
        builder.contentType(connection.getContentType());
        builder.contentEncoding(connection.getContentEncoding());
        builder.contentLength(connection.getContentLength());

        try
        {
            builder.content(new FileInputStream(file));
        }
        catch (Throwable t)
        {
            throw new RuntimeException();
        }

        return builder.build();
    }

}
