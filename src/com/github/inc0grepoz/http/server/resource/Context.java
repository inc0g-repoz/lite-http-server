package com.github.inc0grepoz.http.server.resource;

import java.io.BufferedWriter;
import java.util.Map;

import com.github.inc0grepoz.http.server.request.RequestType;
import com.github.inc0grepoz.http.server.response.Response;

@FunctionalInterface
public interface Context
{

    Response handle(RequestType type, Map<String, String> args, BufferedWriter out);

}
