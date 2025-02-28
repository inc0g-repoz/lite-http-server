package com.github.inc0grepoz.http.server.context;

import java.io.File;
import java.io.IOException;
import java.net.URLConnection;
import java.util.Map;

import com.github.inc0grepoz.http.server.request.RequestType;
import com.github.inc0grepoz.http.server.response.Response;
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
    public Response generate(RequestType type, Map<String, String> args) throws IOException
    {
        URLConnection connection = file.toURI().toURL().openConnection();
        args.forEach((k, v) -> connection.addRequestProperty(k, v));

        return Response.builder()
                .code(ResponseStatusCode.OK)
                .contentType(connection.getContentType())
                .contentEncoding(connection.getContentEncoding())
                .contentLength(connection.getContentLength())
                .content(connection.getInputStream())
                .build();
    }

}
