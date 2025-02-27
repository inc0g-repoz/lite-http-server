package com.github.inc0grepoz.http.server.response;

import java.util.Objects;

public class ResponseBuilder
{

    private ResponseStatusCode code;
    private String contentType;
    private StringBuilder content = new StringBuilder();

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

    public ResponseBuilder appendContent(String string)
    {
        content.append(string);
        return this;
    }

    public ResponseBuilder appendContent(char ch)
    {
        content.append(ch);
        return this;
    }

    public Response build()
    {
        return new Response(Objects.requireNonNull(code),
                Objects.requireNonNull(contentType).toString(),
                Objects.requireNonNull(content.toString()));
    }

}
