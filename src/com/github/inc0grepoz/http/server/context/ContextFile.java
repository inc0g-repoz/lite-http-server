package com.github.inc0grepoz.http.server.context;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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
    public Response generate(RequestType type, Map<String, String> args,
            BufferedWriter out) throws IOException
    {
        URLConnection connection = file.toURI().toURL().openConnection();
        args.forEach((k, v) -> connection.addRequestProperty(k, v));

        ResponseBuilder builder = Response.builder();
        builder.code(ResponseStatusCode.OK);
        builder.contentType(connection.getContentType());
        builder.contentEncoding(connection.getContentEncoding());
        builder.contentLength(connection.getContentLength());
        builder.content(new FileInputStream(file));

        return builder.build();
    }

}
