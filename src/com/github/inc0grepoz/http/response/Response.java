package com.github.inc0grepoz.http.response;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.Map.Entry;

public class Response
{

    public static ResponseBuilder builder()
    {
        return new ResponseBuilder();
    }

    public static Response redirect(String url)
    {
        return builder()
                .code(ResponseStatusCode.SEE_OTHER)
                .location(url)
                .build();
    }

    public static Response notFound()
    {
        return builder()
                .code(ResponseStatusCode.NOT_FOUND)
                .contentType(ResponseContentType.TEXT_HTML)
                .content("<html><head><title>404 Not Found</title></head><body bgcolor=\"white\">"
                        + "<center><h1>404 Not Found</h1></center><hr>"
                        + "<center>nginx/0.8.54</center></body></html>")
                .build();
    }

    public static Response gone()
    {
        return builder()
                .code(ResponseStatusCode.GONE)
                .contentType(ResponseContentType.TEXT_HTML)
                .content("<html><head><title>Gone</title></head><body bgcolor=\"white\">"
                        + "<center><h1>Gone</h1></center><hr>"
                        + "<center>nginx/0.8.54</center></body></html>")
                .build();
    }

    private final ResponseStatusCode code;
    private final Map<String, String> headers;
    private final InputStream content;

    Response(ResponseStatusCode code, Map<String, String> headers, InputStream content)
    {
        this.code = code;
        this.headers = headers;
        this.content = content;
    }

    public void write(OutputStream out) throws IOException
    {
        // Code
        out.write(("HTTP/1.1 " + code.toString() + "\r\n").getBytes());

        // Headers
        out.write(("Date: Fri, 31 Dec 1999 23:59:59 GMT\r\n").getBytes());
        out.write(("Server: Apache/0.8.4\r\n").getBytes());

        for (Entry<String, String> entry: headers.entrySet())
        {
            out.write((entry.getKey() + ": " + entry.getValue() + "\r\n").getBytes());
            System.out.println("Sending header " + entry.getKey() + ": " + entry.getValue());
        }

        out.write(("Expires: Sat, 01 Jan 2000 00:59:59 GMT\r\n").getBytes());
        out.write(("Last-modified: Fri, 09 Aug 1996 14:21:40 GMT\r\n").getBytes());

        if (content == null)
        {
            return; // likely a no content or redirect
        }

        // A line-breaker before content
        out.write(("\r\n").getBytes());

        // Content (StandardCharsets.UTF_8 breaks it)
        byte[] buffer = new byte[4096];
        while (content.read(buffer) != -1)
        {
            out.write(buffer);
        }
    }

}
