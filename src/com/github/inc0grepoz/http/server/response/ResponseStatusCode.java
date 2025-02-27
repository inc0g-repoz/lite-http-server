package com.github.inc0grepoz.http.server.response;

/**
 * A set of response status code defined by RFC 9110.
 * 
 * @author inc0g-repoz
 */
public enum ResponseStatusCode
{

    /**
     * Indicates that a request has succeeded. The
     * response is cacheable by default.
     */
    OK(200, "OK"),
    /**
     * Indicates that the HTTP request has led to the
     * creation of a resource. This status code is
     * commonly sent as the result of a POST request.
     */
    CREATED(201, "Created"),
    /**
     * Indicates that the server would not process the
     * request due to something the server considered
     * to be a client error.
     */
    BAD_REQUEST(400, "Bad Request"),
    /**
     * Indicates that the server understood the request,
     * but refused to process it.
     */
    FORBIDDEN(403, "Forbidden"),
    /**
     * Indicates that the server cannot find the
     * requested resource.
     */
    NOT_FOUND(404, "Not Found"),
    /**
     * Indicates that the server would like to shut down
     * this unused connection. This status code is sent
     * on an idle connection by some servers, even without
     * any previous request by the client.
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
