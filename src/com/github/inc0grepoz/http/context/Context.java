package com.github.inc0grepoz.http.context;

import java.io.IOException;
import java.util.Map;

import com.github.inc0grepoz.http.request.RequestType;
import com.github.inc0grepoz.http.response.Response;

@FunctionalInterface
public interface Context
{

    Response generate(RequestType type, Map<String, String> args) throws IOException;

}
