package com.github.inc0grepoz.http;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.github.inc0grepoz.commons.util.json.mapper.JsonMapper;
import com.github.inc0grepoz.http.addon.AddonLoader;
import com.github.inc0grepoz.http.config.Config;
import com.github.inc0grepoz.http.request.Request;
import com.github.inc0grepoz.http.response.Response;
import com.github.inc0grepoz.http.servlet.Servlet;
import com.github.inc0grepoz.http.servlet.ServletManager;

public class HttpServer
{

    private final Config config;

    private final Logger logger = Logger.getLogger("INFO");

    private final JsonMapper jsonMapper = new JsonMapper();
    private final ExecutorService executorService = Executors.newCachedThreadPool();
    private final ServletManager servletManager = new ServletManager(this);
    private final AddonLoader addonLoader = new AddonLoader(this);

    // The server is running as long, as this value is true
    private boolean running;

    public HttpServer(File configFile)
    {
        try
        {
            String configFileContent = Files.readAllLines(configFile.toPath())
                    .stream().collect(Collectors.joining());
            config = jsonMapper.deserialize(configFileContent, Config.class);
        }
        catch (Throwable t)
        {
            throw new RuntimeException("Failed to read the configuration file");
        }

        if (config.getCertificate().isEnabled())
        {
            try
            {
                config.getCertificate().init();
            }
            catch (Throwable t)
            {
                System.err.println("Failed to initialize a certificate");
            }
        }
    }

    private void loop(ServerSocket serverSocket)
    {
        Socket clientSocket;

        try
        {
            clientSocket = serverSocket.accept();

            if (clientSocket == null)
            {
                return; // no socket to handle
            }
        }
        catch (IOException e)
        {
            System.err.println(e.getMessage());
            return;
        }

        // If new sockets connect, the request is handled
        // and the connection is terminated
        executorService.execute(() -> {
            try
            {
                InputStream in = clientSocket.getInputStream();
                OutputStream out = clientSocket.getOutputStream();

                Request request = Request.read(in);
                Servlet resource = servletManager.findPrefix(request.getPath());

                String host = clientSocket.getInetAddress().getHostAddress() + ":" + clientSocket.getPort();
                System.out.println("Handling a request from " + host + " " + request.toString());

                if (resource != null) // method call
                {
                    resource.generate(request).write(out);
                }
                else // default page
                {
                    Response.notFound().write(out);
                }

                // Connection is terminated
                out.close();
                in.close(); // input is received
                clientSocket.close();

                System.out.println("Closed the socket for " + host);
            }
            catch (Throwable t)
            {
                System.err.println(t.getClass().getName() + ": " + t.getMessage());
            }
        });
    }

    public boolean isRunning()
    {
        return running;
    }

    public void start() throws IOException
    {
        if (running)
        {
            throw new IllegalStateException("Already started");
        }

        running = true;

        // Initializing the server socket
        ServerSocket serverSocket;
        if (config.getCertificate().isEnabled())
        {
            serverSocket = config.getCertificate().getServerSocketFactory()
                    .createServerSocket(config.getPort());
        }
        else
        {
            serverSocket = new ServerSocket(config.getPort());
        }

        // Handling new connections in a different thread
        executorService.execute(() -> {
            while (running)
            {
                loop(serverSocket);
            }

            // Shutting down the server
            try
            {
                serverSocket.close();
            }
            catch (Throwable t)
            {
                System.err.println(t.getClass().getName() + ": " + t.getMessage());
            }
        });
    }

    public void stop()
    {
        running = false;
    }

    public Config getConfig()
    {
        return config;
    }

    public Logger getLogger()
    {
        return logger;
    }

    public JsonMapper getJsonMapper()
    {
        return jsonMapper;
    }

    public ExecutorService getThreadPool()
    {
        return executorService;
    }

    public ServletManager getServletManager()
    {
        return servletManager;
    }

    public AddonLoader getAddonLoader()
    {
        return addonLoader;
    }

}
