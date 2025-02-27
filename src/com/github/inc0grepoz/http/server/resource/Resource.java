package com.github.inc0grepoz.http.server.resource;

import java.io.BufferedWriter;
import java.util.Map;

import com.github.inc0grepoz.http.server.response.Response;

@FunctionalInterface
public interface Resource
{

    Response handle(Map<String, String> args, BufferedWriter out);

}
