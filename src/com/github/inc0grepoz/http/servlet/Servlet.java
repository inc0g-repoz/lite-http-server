package com.github.inc0grepoz.http.servlet;

import java.io.IOException;

import com.github.inc0grepoz.http.request.Request;
import com.github.inc0grepoz.http.response.Response;

@FunctionalInterface
public interface Servlet
{

    Response generate(Request request) throws IOException;

}
