package com.github.inc0grepoz.http.servlet;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.github.inc0grepoz.commons.util.json.mapper.JsonException;
import com.github.inc0grepoz.http.HttpServer;

public class ServletManager
{

    private final HttpServer server;
    private final Map<String, Servlet> contexts = new ConcurrentHashMap<>();

    public ServletManager(HttpServer server)
    {
        this.server = server;
    }

    public Servlet findPrefix(String prefix)
    {
        Servlet servlet;

        while (!prefix.isEmpty())
        {
            if ((servlet = findExact(prefix)) != null)
            {
                return servlet;
            }

            prefix = prefix.substring(0, prefix.lastIndexOf('/'));
        }

        return null;
    }

    public Servlet findExact(String path)
    {
        return contexts.get(path);
    }

    public Servlet unregister(String path)
    {
        return contexts.remove(path);
    }

    public Servlet register(String path, Servlet resource)
    {
        contexts.put(path, resource);
        return resource;
    }

    public Servlet register(String path, File file)
    {
        Servlet context = ServletFile.from(file);
        contexts.put(path, context);
        return context;
    }

    public void load() throws JsonException, IOException
    {
        Map<String, String> resources = server.getConfig().getResources();
        resources.forEach((route, filePath) -> register(route, new File(filePath)));
    }

}
