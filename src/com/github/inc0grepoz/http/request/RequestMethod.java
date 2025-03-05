package com.github.inc0grepoz.http.request;

/**
 * Represents a set of request methods to indicate the purpose of
 * the request and what is expected if the request is successful.
 * 
 * @author inc0g-repoz
 */
public enum RequestMethod
{

    /** 
     * Requests a representation of the specified resource.
     * Requests using GET should only retrieve data and should
     * not contain a request.
     */
    GET,
    /**
     * Asks for a response identical to a GET request, but without
     * a response body.
     */
    HEAD,
    /**
     * Submits an entity to the specified resource,
     * often causing a change in state or side effects on the server.
     */
    POST,
    /**
     * Replaces all current representations of the target resource
     * with the request content.
     */
    PUT,
    /**
     * Deletes the specified resource.
     */
    DELETE,
    /**
     * Establishes a tunnel to the server identified by the target
     * resource.
     */
    CONNECT,
    /**
     * Describes the communication options for the target resource.
     */
    OPTIONS,
    /**
     * Performs a message loop-back test along the path to the target
     * resource.
     */
    TRACE,
    /**
     * Applies partial modifications to a resource.
     */
    PATH;

}
