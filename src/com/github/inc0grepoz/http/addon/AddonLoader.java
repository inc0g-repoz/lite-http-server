package com.github.inc0grepoz.http.addon;

import java.io.File;
import java.io.FileFilter;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.github.inc0grepoz.http.HttpServer;

@SuppressWarnings("unchecked")
public class AddonLoader
{

    private static final FileFilter FILTER = file -> file.getName().endsWith(".jar");

    private final HttpServer server;
    private final Collection<Addon> addons = new ArrayList<>();

    public AddonLoader(HttpServer server)
    {
        this.server = server;
    }

    public void loadAddonsFromDirectory(File directory)
    {
        if (!directory.isDirectory())
        {
            throw new IllegalArgumentException(directory.getName() + " directory is not created");
        }

        Addon addon;

        for (File file: directory.listFiles(FILTER))
        {
            addons.add(addon = loadAddon(file));
            addon.initialize(server);
        }

        System.out.println("Finished loading addons (total of " + addons.size() + ")");
    }

    private Addon loadAddon(File file)
    {
        if (!FILTER.accept(file))
        {
            new IllegalArgumentException(file.getName() + " is not a valid addon");
        }

        System.out.println("Loading an addon " + file.getName());

        try
        {
            URL[] urls = new URL[] { file.toURI().toURL() };
            ClassLoader classLoader = this.getClass().getClassLoader();
            URLClassLoader child = new URLClassLoader(urls, classLoader);

            // Reading addon meta-properties
            Map<String, String> info = readMetaInfo(child);
            String main = Objects.requireNonNull(info.get("mainClass"), "mainClass is not specified in addon.json");

            // Creating an addon instance
            Class<?> classToLoad = Class.forName(main, true, child);
            return (Addon) classToLoad.newInstance();
        }
        catch (Throwable t)
        {
            throw new IllegalStateException(t);
        }
    }

    private Map<String, String> readMetaInfo(URLClassLoader ucl)
    {
        StringBuilder builder = new StringBuilder();
        byte[] buffer = new byte[1024];

        try (InputStream in = ucl.findResource("addon.json").openStream())
        {
            while (in.read(buffer) != -1)
            {
                builder.append(new String(buffer));
            }
        }
        catch (Throwable t)
        {
            throw new IllegalStateException(t);
        }

        return server.getJsonMapper().deserialize(builder.toString(), HashMap.class, String.class, String.class);
    }

}
