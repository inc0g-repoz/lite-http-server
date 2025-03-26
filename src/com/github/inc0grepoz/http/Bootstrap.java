package com.github.inc0grepoz.http;

import java.io.File;
import java.util.logging.Logger;

import com.github.inc0grepoz.http.addon.AddonLoader;
import com.github.inc0grepoz.http.servlet.ServletManager;

public class Bootstrap
{

    public static void main(String[] args) throws Throwable
    {
        HttpServer server = new HttpServer(Logger.getLogger("INFO"), 80);

        AddonLoader loader = server.getAddonLoader();
        loader.loadAddonsFromDirectory(new File("addons"));

        ServletManager manager = server.getServletManager();
        manager.load(new File("resources.json"));

        server.start();
    }

}
