package com.github.inc0grepoz.http.config;

import java.util.Collections;
import java.util.Map;

public class Config
{

    private int port;
    private Map<String, String> resources = Collections.emptyMap();
    private Certificate certificate;

    // JSON-mapper constructor
    private Config() {}

    public int getPort()
    {
        return port;
    }

    public Map<String, String> getResources()
    {
        return resources;
    }

    public Certificate getCertificate()
    {
        return certificate;
    }

}
