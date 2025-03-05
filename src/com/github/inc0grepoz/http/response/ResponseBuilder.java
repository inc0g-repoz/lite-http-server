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
    private Map<String, String> header = new HashMap<>();
    private InputStream content;

    public ResponseBuilder code(ResponseStatusCode code)
    {
        this.code = code;
        return this;
    }

    public ResponseBuilder field(String key, String value)
    {
        header.compute(key, (k, v) -> value);
        return this;
    }

    public ResponseBuilder field(String key, int value)
    {
        return field(key, Integer.toString(value));
    }

    public ResponseBuilder contentType(String contentType)
    {
        return field("Content-Type", contentType);
    }

    public ResponseBuilder contentType(ResponseContentType contentType)
    {
        return field("Content-Type", contentType.toString());
    }

    public ResponseBuilder contentEncoding(String contentEncoding)
    {
        return field("Content-Encoding", contentEncoding);
    }

    public ResponseBuilder contentLength(int contentLength)
    {
        return field("Content-Length", contentLength);
    }

    public ResponseBuilder location(String location)
    {
        return field("Location", location);
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
        Preconditions.checkArgument(!header.isEmpty(), "No headers specified");
        return new Response(code, header, content);
    }

}
