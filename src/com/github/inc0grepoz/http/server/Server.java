package com.github.inc0grepoz.http.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

import com.github.inc0grepoz.commons.util.json.mapper.JsonMapper;
import com.github.inc0grepoz.http.server.context.Context;
import com.github.inc0grepoz.http.server.context.ContextManager;
import com.github.inc0grepoz.http.server.request.Request;
import com.github.inc0grepoz.http.server.response.Response;

public class Server
{

    private final int port;
    private final Logger logger;

    private final JsonMapper jsonMapper = new JsonMapper();
    private final ExecutorService executorService = Executors.newCachedThreadPool();
    private final ContextManager contextManager = new ContextManager(this);

    // The server is running as long, as this value is true
    private boolean running;

    public Server(Logger logger, int port)
    {
        this.logger = logger;
        this.port = port;
    }

    private void loop(ServerSocket serverSocket)
    {
        Socket clientSocket;

        try
        {
            clientSocket = serverSocket.accept();

            if (clientSocket == null)
            {
                return;
            }
        }
        catch (IOException e)
        {
            logger.warning(e.getMessage());
            return;
        }

        // If new sockets connect, the request is handled
        // and the connection is terminated
        executorService.execute(() -> {
            try
            {
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

                Request request = Request.read(in);
                Context resource = contextManager.find(request.getPath());

                String host = clientSocket.getInetAddress().getHostAddress() + ":" + clientSocket.getPort();
                System.out.println("Handling a request from " + host + " " + request.toString());

                if (resource != null) // method call
                {
                    Map<String, String> args = request.resolveArguments();
                    resource.generate(request.getType(), args, out).write(out);
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
                System.err.println(t.getMessage());
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
        ServerSocket serverSocket = new ServerSocket(port);

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
            catch (Throwable e)
            {
                logger.warning(e.getMessage());
            }
        });

        // Starting a watch service
        executorService.execute(() -> {
            try
            {
                WatchService watchService = FileSystems.getDefault().newWatchService();
                Path dir = Paths.get(System.getProperty("user.dir"));

                dir.register(watchService,
                        StandardWatchEventKinds.ENTRY_CREATE, 
                        StandardWatchEventKinds.ENTRY_DELETE, 
                        StandardWatchEventKinds.ENTRY_MODIFY);

                WatchKey key;

                while ((key = watchService.take()) != null)
                {
                    for (WatchEvent<?> event : key.pollEvents())
                    {
                        System.out.println("Event kind:" + event.kind() + ". File affected: " + event.context() + ".");
                    }
                    key.reset();
                }
            }
            catch (IOException | InterruptedException e)
            {
                e.printStackTrace();
            }
        });
    }

    public void stop()
    {
        running = false;
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

    public ContextManager getContextManager()
    {
        return contextManager;
    }

}
