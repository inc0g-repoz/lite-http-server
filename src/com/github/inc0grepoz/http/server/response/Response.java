package com.github.inc0grepoz.http.server.response;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class Response
{

    public static ResponseBuilder builder()
    {
        return new ResponseBuilder();
    }

    private final ResponseStatusCode code;
    private final String contentType;
    private final int contentLength;
    private final InputStream content;

    Response(ResponseStatusCode code, String contentType, int contentLength, InputStream content)
    {
        this.code = code;
        this.contentType = contentType;
        this.contentLength = contentLength;
        this.content = content;
    }

    public void write(BufferedWriter out)
    {
        try
        {
            // Code
            out.write("HTTP/1.1 " + code.toString() + "\r\n");

            // Header
            out.write("Date: Fri, 31 Dec 1999 23:59:59 GMT\r\n");
            out.write("Server: Apache/0.8.4\r\n");
            out.write("Content-Type: " + contentType + "\r\n");
            out.write("Content-Length: " + contentLength + "\r\n");
            out.write("Expires: Sat, 01 Jan 2000 00:59:59 GMT\r\n");
            out.write("Last-modified: Fri, 09 Aug 1996 14:21:40 GMT\r\n");

            // A line-breaker before content
            out.write("\r\n");

            // Content
            try (
                InputStreamReader reader = new InputStreamReader
                (content, StandardCharsets.UTF_8)
            ) {
                for (int i; (i = reader.read()) != -1;) {
                    out.write((char) i);
                }
            } catch (IOException e) {
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

}
