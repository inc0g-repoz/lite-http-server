package com.github.inc0grepoz.http.server.response;

/**
 * A set of response status code defined by
 * <a href="https://www.rfc-editor.org/info/rfc9110">RFC 9110</a>.
 * 
 * @author inc0g-repoz
 */
public enum ResponseStatusCode
{

    /**
     * Indicates that a request has succeeded. The response is
     * cacheable by default.
     */
    OK(200, "OK"),
    /**
     * Indicates that the HTTP request has led to the creation of a
     * resource. This status code is commonly sent as the result of a
     * {@link com.github.inc0grepoz.http.server.request.RequestType#POST POST}
     * request.
     */
    CREATED(201, "Created"),
    /**
     * Indicates that a request has succeeded, but the client doesn't
     * need to navigate away from its current page. The response is
     * cacheable by default, and an ETag header is included in such
     * cases.
     */
    NO_CONTENT(204, "204 No Content"),
    /**
     * Indicates that the requested resource has been permanently
     * moved to the {@code URL} in the {@code Location} header.
     */
    MOVED_PERMANENTLY(301, "Moved Permanently"),
    /**
     * Indicates that the resource requested has been temporarily
     * moved to the {@code URL} in the {@code Location} header.
     */
    TEMPORARY_REDIRECT(307, "Temporary Redirect"),
    /**
     * Indicates that the requested resource has been permanently
     * moved to the {@code URL} given by the {@code Location} header.
     */
    PERMANENT_REDIRECT(308, "Permanent Redirect"),
    /**
     * Indicates that the server would not process the request due
     * to something the server considered to be a client error.
     */
    BAD_REQUEST(400, "Bad Request"),
    /**
     * Indicates that the server understood the request, but refused
     * to process it.
     */
    FORBIDDEN(403, "Forbidden"),
    /**
     * Indicates that the server cannot find the requested resource.
     */
    NOT_FOUND(404, "Not Found"),
    /**
     * Indicates that the server would like to shut down this unused
     * connection. This status code is sent on an idle connection by
     * some servers, even without any previous request by the client.
     */
    REQUEST_TIMEOUT(408, "Request Timeout");

    private final String string;

    ResponseStatusCode(int code, String message)
    {
        string = code + " " + message;
    }

    @Override
    public String toString()
    {
        return string;
    }

}
