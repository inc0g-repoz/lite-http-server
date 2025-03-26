package com.github.inc0grepoz.http;

import java.io.File;

public class Bootstrap
{

    public static void main(String[] args) throws Throwable
    {
        File configFile   = new File("config.json");
        File addonsFolder = new File("addons");

        HttpServer server = new HttpServer(configFile);

        server.getAddonLoader().loadAddonsFromDirectory(addonsFolder);
        server.getServletManager().load();
        server.start();
    }

}
