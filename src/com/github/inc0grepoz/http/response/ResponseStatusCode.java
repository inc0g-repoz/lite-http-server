package com.github.inc0grepoz.http.response;

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
     * Indicates that a request has been accepted for processing, but
     * processing has not been completed or may not have started. The
     * actual processing of the request is not guaranteed; a task or
     * action may fail or be disallowed when a server tries to process it.
     */
    ACCEPTED(202, "Accepted"),
    /**
     * Indicates that the request was successful, but a transforming
     * proxy has modified the headers or enclosed content from the origin
     * server's 200 (OK) response.
     */
    NON_AUTHORIZED_INFORMATION(203, "Non-Authoritative Information"),
    /**
     * Indicates that a request has succeeded, but the client doesn't
     * need to navigate away from its current page. The response is
     * cacheable by default, and an ETag header is included in such
     * cases.
     */
    NO_CONTENT(204, "No Content"),
    /**
     * Indicates that the request has been successfully processed and
     * the client should reset the document view.
     */
    RESET_CONTENT(205, "Reset Content"),
    /**
     * Sent in response to a range request. The response body contains
     * the requested ranges of data as specified in the {@code Range}
     * header of the request.
     */
    PARTIAL_CONTENT(206, "Partial Content"),
    /**
     * Indicates a mixture of responses. This response is used exclusively
     * in the context of Web Distributed Authoring and Versioning (WebDAV).
     */
    MULTI_STATUS(207, "Multi-Status"),
    /**
     * Used in a multi-status response to save space and avoid conflicts.
     * This response is used exclusively in the context of Web Distributed
     * Authoring and Versioning (WebDAV).
     */
    ALREADY_REPORTED(208, "Already Reported"),
    /**
     * Indicates that the server is returning a delta in response
     * to a {@code GET} request. It is used in the context of HTTP
     * delta encodings.
     */
    IM_USED(226, "IM Used"),
    /**
     * Indicates that the request has more than one possible response.
     * The user-agent or the user should choose one of them.
     */
    MULTIPLE_CHOICES(300, "Multiple Choices"),
    /**
     * Indicates that the requested resource has been permanently
     * moved to the {@code URL} in the {@code Location} header.
     */
    MOVED_PERMANENTLY(301, "Moved Permanently"),
    /**
     * Indicates that the requested resource has been temporarily
     * moved to the {@code URL} in the {@code Location} header.
     */
    FOUND(302, "Found"),
    /**
     * Indicates that the browser should redirect to the {@code URL}
     * in the {@code Location} header instead of rendering the
     * requested resource.
     */
    SEE_OTHER(303, "See Other"),
    /**
     * Indicates that there is no need to retransmit the requested resources.
     */
    NOT_MODIFIED(304, "Not Modified"),
    /**
     * Defined in a previous version of the HTTP specification to indicate
     * that a requested response must be accessed by a proxy. It has been
     * deprecated due to security concerns regarding in-band configuration
     * of a proxy.
     * 
     * @deprecated Was subject to security concerns.
     */
    @Deprecated
    USE_PROXY(305, "Use Proxy"),
    /**
     * This response code is no longer used; but is reserved. It was used
     * in a previous version of the {@code HTTP/1.1} specification.
     * 
     * @deprecated No longer used.
     */
    @Deprecated
    UNUSED(306, "unused"),
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
     * Indicates that a request was not successful, because it lacks
     * valid authentication credentials for the requested resource.
     * This status code is sent with an HTTP {@code WWW-Authenticate}
     * response header that contains information on the authentication
     * scheme the server expects the client to include to make the
     * request successfully.
     */
    UNAUTHORIZED(401, "Unauthorized"),
    /**
     * Non-standard response status code reserved for future use.
     * 
     * @deprecated Non-standard.
     */
    @Deprecated
    PAYMENT_REQUIRED(402, "Payment Required"),
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
     * Indicates that the server knows the request method, but the
     * target resource doesn't support this method. The server must
     * generate an {@code Allow} header in the response with a list
     * of methods that the target resource currently supports.
     */
    METHOD_NOT_ALLOWED(405, "Method Not Allowed"),
    /**
     * Indicates that the server could not produce a response matching
     * the list of acceptable values defined in the request's proactive
     * content negotiation headers and that the server was unwilling to
     * supply a default representation.
     */
    NOT_ACCEPTABLE(406, "Not Acceptable"),
    /**
     * Indicates that the request did not succeed, because it lacks valid
     * authentication credentials for the proxy server that sits between
     * the client and the server with access to the requested resource.
     */
    PROXY_AUTHENTIFICATION_REQUIRED(407, "Proxy Authentication Required"),
    /**
     * Indicates that the server would like to shut down this unused
     * connection. This status code is sent on an idle connection by
     * some servers, even without any previous request by the client.
     */
    REQUEST_TIMEOUT(408, "Request Timeout"),
    /**
     * Indicates a request conflict with the current state of the
     * target resource.
     */
    CONFLICT(409, "Conflict"),
    /**
     * Indicates that the target resource is no longer available at the
     * origin server and that this condition is likely to be permanent.
     * The response is cacheable by default.
     */
    GONE(410, "Gone"),
    /**
     * Indicates that the server refused to accept the request without a
     * defined {@code Content-Length} header.
     */
    LENGTH_REQUIRED(411, "Length Required"),
    /**
     * Indicates that access to the target resource was denied. This
     * happens with conditional requests on methods other than {@code GET}
     * or {@code HEAD} when the condition defined by the
     * {@code If-Unmodified-Since} or {@code If-Match} headers is not
     * fulfilled. In that case, the request (usually an upload or a
     * modification of a resource) cannot be made and this error response
     * is sent back.
     */
    PRECONDITION_FAILED(412, "Precondition Failed"),
    /**
     * Indicates that the request entity was larger than limits defined by
     * server. The server might close the connection or return a
     * {@code Retry-After} header field.
     */
    CONTENT_TOO_LARGE(413, "Content Too Large"),
    /**
     * Indicates that a {@code URI} requested by the client was longer than
     * the server is willing to interpret.
     */
    URI_TOO_LONG(414, "URI Too Long"),
    /**
     * Indicates that the server refused to accept the request, because the
     * message content format is not supported.
     */
    UNSUPPORTED_MEDIA_TYPE(415, "Unsupported Media Type"),
    /**
     * Indicates that a server could not serve the requested ranges. The
     * most likely reason for this response is that the document doesn't
     * contain such ranges, or that the {@code Range} header value, though
     * syntactically correct, doesn't make sense.
     */
    RANGE_NOT_SATISFIABLE(416, "Range Not Satisfiable"),
    /**
     * Indicates that the expectation given in the request's {@code Expect}
     * header could not be met.
     */
    EXPECTATION_FAILED(417, "Expectation Failed"),
    /**
     * Indicates that the server refuses to brew coffee, because it is,
     * permanently, a teapot.
     */
    IM_A_TEAPOT(418, "I'm a teapot"),
    /**
     * Indicates that the request was directed to a server that is not able
     * to produce a response. This can be sent by a server that is not
     * configured to produce responses for the combination of scheme and
     * authority that are included in the request {@code URI}.
     */
    MISDIRECTED_REQUEST(421, "Misdirected Request"),
    /**
     * Indicates that the server understood the content type of the request
     * content, and the syntax of the request content was correct, but it
     * was unable to process the contained instructions.
     */
    UNPROCESSABLE_CONTENT(422, "Unprocessable Content"),
    /**
     * Indicates that a resource is locked, meaning it can't be accessed.
     * Its response body should contain information in WebDAV's XML format.
     */
    LOCKED(423, "Locked"),
    /**
     * Indicates that the method could not be performed on the resource,
     * because the requested action depended on another action, and that
     * action failed.
     */
    FAILED_DEPENDENCY(424, "Failed Dependency"),
    /**
     * Indicates that the server was unwilling to risk processing a request
     * that might be replayed to avoid potential replay attacks.
     */
    TOO_EARLY(425, "Too Early"),
    /**
     * Indicates that the server refused to perform the request using the
     * current protocol but might be willing to do so after the client
     * upgrades to a different protocol.
     */
    UPGRADE_REQUIRED(426, "Upgrade Required"),
    /**
     * Indicates that the server requires the request to be conditional.
     */
    PRECONDITION_REQUIRED(428, "Precondition Required"),
    /**
     * Indicates the client has sent too many requests in a given amount
     * of time. This mechanism of asking the client to slow down the rate
     * of requests is commonly called <strong>rate limiting</strong>.
     */
    TOO_MANY_REQUESTS(429, "Too Many Requests"),
    /**
     * Indicates that the server refuses to process the request, because
     * the request's HTTP headers are too long. The request may be
     * resubmitted after reducing the size of the request headers.
     */
    REQUEST_HEADER_FIELDS_TOO_LARGE(431, "Request Header Fields Too Large"),
    /**
     * Indicates that the user requested a resource that is not available
     * due to legal reasons, such as a web page for which a legal action
     * has been issued.
     */
    UNAVAILABLE_FOR_LEGAL_REASONS(451, "Unavailable For Legal Reasons"),
    /**
     * Indicates that the server encountered an unexpected condition that
     * prevented it from fulfilling the request.
     */
    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
    /**
     * Means that the server does not support the functionality required
     * to fulfill the request.
     */
    NOT_IMPLEMENTED(501, "Not Implemented"),
    /**
     * Indicates that a server was acting as a gateway or proxy and that
     * it received an invalid response from the upstream server.
     */
    BAD_GATEWAY(502, "Bad Gateway"),
    /**
     * Indicates that the server is not ready to handle the request.
     */
    SERVICE_UNAVAILABLE(503, "Service Unavailable"),
    /**
     * Indicates that the server, while acting as a gateway or proxy, did
     * not get a response in time from the upstream server in order to
     * complete the request.
     */
    GATEWAY_TIMEOUT(504, "Gateway Timeout"),
    /**
     * Indicates that the HTTP version used in the request is not supported
     * by the server.
     */
    HTTP_VERSION_NOT_SUPPORTED(505, "HTTP Version Not Supported"),
    /**
     * Returned during content negotiation when there is recursive loop in
     * the process of selecting a resource.
     */
    VARIANT_ALSO_NEGOTIATES(506, "Variant Also Negotiates"),
    /**
     * Indicates that an action could not be performed, because the server
     * does not have enough available storage to successfully complete the
     * request.
     */
    INSUFFICIENT_STORAGE(507, "Insufficient Storage"),
    /**
     * Indicates that the entire operation failed, because it encountered
     * an infinite loop while processing a request with {@code Depth: infinity}.
     */
    LOOP_DETECTED(508, "Loop Detected"),
    /**
     * Sent when the client request declares an HTTP Extension that should be
     * used to process the request, but the extension is not supported.
     */
    NOT_EXTENDED(510, "Not Extended"),
    /**
     * Indicates that the client needs to authenticate to gain network access.
     * This status is not generated by origin servers, but by intercepting
     * proxies that control access to a network.
     */
    NETWORK_AUTHENTICATION_REQUIRED(511, "Network Authentication Required");

    private final int code;
    private final String message, string;

    ResponseStatusCode(int code, String message)
    {
        string = (this.code = code) + " " + (this.message = message);
    }

    /**
     * Returns a {@code String} representation of this {@code ResponseStatusCode}
     * that can be used in responses. Contains of the code and the message
     * separated by a space.
     * 
     * @return a {@code String} representation
     */
    @Override
    public String toString()
    {
        return string;
    }

    /**
     * Returns the {@code int} value of this code.
     * @return the {@code int} value
     */
    public int getCode()
    {
        return code;
    }

    /**
     * Returns the message.
     * 
     * @return the message
     */
    public String getMessage()
    {
        return message;
    }

}
