package com.github.inc0grepoz.http.servlet;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;

import com.github.inc0grepoz.http.request.Request;
import com.github.inc0grepoz.http.response.Response;
import com.github.inc0grepoz.http.response.ResponseStatusCode;

public class ServletFile implements Servlet
{

    public static ServletFile from(File file)
    {
        return new ServletFile(file);
    }

    private final File file;

    public ServletFile(File file)
    {
        this.file = file;
    }

    @Override
    public Response generate(Request request) throws IOException
    {
        if (!file.exists())
        {
            return Response.gone();
        }

        URLConnection connection = file.toURI().toURL().openConnection();
        request.resolveParameters().forEach((k, v) -> connection.addRequestProperty(k, v));

        InputStream input;

        if (file.getName().endsWith(".php")) // executing PHP
        {
            String command = "php \"" + file.getPath() + "\" " + request.getRawParameters();
            Process process = Runtime.getRuntime().exec(command);
            input = process.getInputStream();
        }
        else // writing content from the URL connection
        {
            input = connection.getInputStream();
        }

        return Response.builder()
                .code(ResponseStatusCode.OK)
                .contentType(connection.getContentType())
                .contentEncoding(connection.getContentEncoding())
                .contentLength(connection.getContentLength())
                .content(input)
                .build();
    }

}
