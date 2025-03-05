package com.github.inc0grepoz.http.servlet;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import com.github.inc0grepoz.commons.util.json.mapper.JsonException;
import com.github.inc0grepoz.http.HttpServer;

@SuppressWarnings("unchecked")
public class ServletManager
{

    private final HttpServer server;
    private final Map<String, Servlet> contexts = new ConcurrentHashMap<>();

    public ServletManager(HttpServer server)
    {
        this.server = server;
    }

    public Servlet find(String path)
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

    public void load(File file) throws JsonException, IOException
    {
        // Validating the resources file
        if (!file.exists())
        {
            throw new IOException("File resources.json not found");
        }

        // Loading the resources.json file
//      Path path = FileSystems.getDefault().getPath("resources.json");
        String lines = Files.readAllLines(file.toPath()).stream().collect(Collectors.joining());

        // Registering contexts declared in the file above
        Map<String, String> resources = server.getJsonMapper().deserialize(lines, HashMap.class, String.class, String.class);
        resources.forEach((route, filePath) -> register(route, new File(filePath)));
    }

}
