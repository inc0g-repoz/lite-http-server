package com.github.inc0grepoz.http.server.response;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Map.Entry;

public class Response
{

    public static final Response RESPONSE_404 = builder()
            .code(ResponseStatusCode.NOT_FOUND)
            .contentType(ResponseContentType.TEXT_HTML)
            .content("<html>\r\n"
                    + "<head><title>404 Not Found</title></head>\r\n"
                    + "<body bgcolor=\"white\">\r\n"
                    + "<center><h1>404 Not Found</h1></center>\r\n"
                    + "<hr><center>nginx/0.8.54</center>\r\n"
                    + "</body>\r\n"
                    + "</html>")
            .build();

    public static ResponseBuilder builder()
    {
        return new ResponseBuilder();
    }

    public static Response redirect(String url)
    {
        return builder()
                .code(ResponseStatusCode.TEMPORARY_REDIRECT)
                .header("Location", url)
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

    public void write(BufferedWriter out)
    {
        try
        {
            // Code
            out.write("HTTP/1.1 " + code.toString() + "\r\n");

            // Headers
            out.write("Date: Fri, 31 Dec 1999 23:59:59 GMT\r\n");
            out.write("Server: Apache/0.8.4\r\n");

            for (Entry<String, String> entry: headers.entrySet())
            {
                out.write(entry.getKey() + ": " + entry.getValue() + "\r\n");
            }

            out.write("Expires: Sat, 01 Jan 2000 00:59:59 GMT\r\n");
            out.write("Last-modified: Fri, 09 Aug 1996 14:21:40 GMT\r\n");

            if (content == null)
            {
                return; // likely a no content or redirect
            }

            // A line-breaker before content
            out.write("\r\n");

            // Content
            try (
                InputStreamReader reader = new InputStreamReader
                (content, StandardCharsets.UTF_8)
            ) {
                while (!reader.ready());
                for (int i; (i = reader.read()) != -1;) {
                    out.write((char) i);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

}
