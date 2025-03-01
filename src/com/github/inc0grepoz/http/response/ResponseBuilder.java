package com.github.inc0grepoz.http.response;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import com.github.inc0grepoz.commons.util.Preconditions;

public class ResponseBuilder
{

    private ResponseStatusCode code;
    private Map<String, String> headers = new HashMap<>();
    private InputStream content;

    public ResponseBuilder code(ResponseStatusCode code)
    {
        this.code = code;
        return this;
    }

    public ResponseBuilder header(String key, String value)
    {
        headers.compute(key, (k, v) -> value);
        return this;
    }

    public ResponseBuilder header(String key, int value)
    {
        return header(key, Integer.toString(value));
    }

    public ResponseBuilder contentType(String contentType)
    {
        return header("Content-Type", contentType);
    }

    public ResponseBuilder contentType(ResponseContentType contentType)
    {
        return header("Content-Type", contentType.toString());
    }

    public ResponseBuilder contentEncoding(String contentEncoding)
    {
        return header("Content-Encoding", contentEncoding);
    }

    public ResponseBuilder contentLength(int contentLength)
    {
        return header("Content-Length", contentLength);
    }

    public ResponseBuilder location(String location)
    {
        return header("Location", location);
    }

    public ResponseBuilder content(String content)
    {
        this.content = new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8));
        contentLength(content.length());
        return this;
    }

    public ResponseBuilder content(InputStream content)
    {
        this.content = content;
        return this;
    }

    public Response build()
    {
        Preconditions.checkNotNull(code);
        Preconditions.checkArgument(!headers.isEmpty(), "No headers specified");
        return new Response(code, headers, content);
    }

}
