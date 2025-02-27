package com.github.inc0grepoz.http.server.response;

import java.io.BufferedWriter;
import java.io.IOException;

public class Response
{

    public static ResponseBuilder builder()
    {
        return new ResponseBuilder();
    }

    private final ResponseStatusCode code;
    private final String contentType, content;

    Response(ResponseStatusCode code, String contentType, String content)
    {
        this.code = code;
        this.contentType = contentType;
        this.content = content;
    }

    public void write(BufferedWriter out) throws IOException
    {
        // Code
        out.write("HTTP/1.1 " + code.toString() + "\r\n");

        // Header
        out.write("Date: Fri, 31 Dec 1999 23:59:59 GMT\r\n");
        out.write("Server: Apache/0.8.4\r\n");
        out.write("Content-Type: " + contentType + "\r\n");
        out.write("Content-Length: " + content.length() + "\r\n");
        out.write("Expires: Sat, 01 Jan 2000 00:59:59 GMT\r\n");
        out.write("Last-modified: Fri, 09 Aug 1996 14:21:40 GMT\r\n");

        // A line-breaker before content
        out.write("\r\n");

        // Content
        out.write(content);
    }

}
