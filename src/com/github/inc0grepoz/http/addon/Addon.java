package com.github.inc0grepoz.http.addon;

import com.github.inc0grepoz.http.HttpServer;

public abstract class Addon
{

    protected abstract void initialize(HttpServer server);

    protected abstract void deinitialize(HttpServer server);

}
