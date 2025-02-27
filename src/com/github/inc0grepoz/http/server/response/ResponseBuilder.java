package com.github.inc0grepoz.http.server.response;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import com.github.inc0grepoz.commons.util.Preconditions;

public class ResponseBuilder
{

    private ResponseStatusCode code;
    private String contentType, contentEncoding;
    private int contentLength;
    private InputStream content;

    public ResponseBuilder code(ResponseStatusCode code)
    {
        this.code = code;
        return this;
    }

    public ResponseBuilder contentType(String contentType)
    {
        this.contentType = contentType;
        return this;
    }

    public ResponseBuilder contentType(ResponseContentType contentType)
    {
        this.contentType = contentType.toString();
        return this;
    }

    public ResponseBuilder contentEncoding(String contentEncoding)
    {
        this.contentEncoding = contentEncoding;
        return this;
    }

    public ResponseBuilder contentLength(int contentLength)
    {
        this.contentLength = contentLength;
        return this;
    }

    public ResponseBuilder content(String content)
    {
        this.content = new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8));
        this.contentLength = content.length();
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
        Preconditions.checkNotNull(contentType);
        Preconditions.checkNotNull(content);
        Preconditions.checkArgument(contentLength != 0);
        return new Response(code, contentType, contentEncoding, contentLength, content);
    }

}
