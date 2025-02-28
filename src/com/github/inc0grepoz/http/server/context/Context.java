package com.github.inc0grepoz.http.server.context;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Map;

import com.github.inc0grepoz.http.server.request.RequestType;
import com.github.inc0grepoz.http.server.response.Response;

@FunctionalInterface
public interface Context
{

    Response generate(RequestType type, Map<String, String> args,
            BufferedWriter out) throws IOException;

}
