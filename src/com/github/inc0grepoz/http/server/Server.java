package com.github.inc0grepoz.http.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.github.inc0grepoz.commons.util.json.mapper.JsonException;
import com.github.inc0grepoz.commons.util.json.mapper.JsonMapper;
import com.github.inc0grepoz.http.server.request.Request;
import com.github.inc0grepoz.http.server.resource.Resource;
import com.github.inc0grepoz.http.server.resource.ResourceFile;
import com.github.inc0grepoz.http.server.response.Response;
import com.github.inc0grepoz.http.server.response.ResponseBuilder;
import com.github.inc0grepoz.http.server.response.ResponseContentType;
import com.github.inc0grepoz.http.server.response.ResponseStatusCode;

@SuppressWarnings("unchecked")
public class Server
{

    public static final Pattern PATTERN_WHITESPACES = Pattern.compile("\\s+");

    private final int port;
    private final Logger logger;

    private final ExecutorService executorService = Executors.newCachedThreadPool();

    private final Map<String, Resource> resources = new HashMap<>();
    private final JsonMapper jsonMapper = new JsonMapper();

    // The server is running as long, as this value is true
    private boolean running;

    public Server(Logger logger, int port)
    {
        this.logger = logger;
        this.port = port;
    }

    private void loop(ServerSocket serverSocket)
    {
        // If new sockets connect, the request is handled
        // and the connection is terminated
        try (Socket clientSocket = serverSocket.accept())
        {
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

            Request request = Request.read(in);
            Resource resource = resources.get(request.getPath());

            if (resource != null) // method call
            {
                Map<String, String> args = request.resolveArguments();
                resource.handle(args, out).write(out);
            }
            else // default page
            {
                ResponseBuilder builder = Response.builder();
                builder.code(ResponseStatusCode.NOT_FOUND);
                builder.contentType(ResponseContentType.TEXT_HTML.toString());
                builder.appendContent("<title>404 Not Found</title>");
                builder.appendContent("<p>Unknown resource.</p>");
                builder.build().write(out);
            }

            System.out.print("Request from ");
            System.out.print(clientSocket.getInetAddress().getHostName() + ":" + clientSocket.getPort() + " ");
            System.out.print("{");
            System.out.print("\"path\": \"" + request.getPath() + "\", ");
            System.out.print("\"method\": \"" + request.getType() + "\", ");
            System.out.print("\"queryString\": \"" + request.getQueryString() + "\"");
            System.out.println("}");

            // Connection is terminated
            out.close();
            in.close(); // input is received
            clientSocket.close();
        }
        catch (Throwable e)
        {
            logger.warning(e.getMessage());
            e.printStackTrace();
        }
    }

    public void loadResourcesFromFiles() throws JsonException, IOException
    {
        // Validating the resources file
        File file = new File("resources.json");
        if (!file.exists())
        {
            throw new IOException("File resources.json not found");
        }

        // Loading resources
        Path path = FileSystems.getDefault().getPath("resources.json");
        String lines = Files.readAllLines(path).stream().collect(Collectors.joining());
        Map<String, String> resources = jsonMapper.deserialize(lines, HashMap.class, String.class, String.class);
        resources.forEach((route, filePath) -> {
            this.resources.put(route, ResourceFile.from(new File(filePath)));
        });
    }

    public void removeResource(String path)
    {
        resources.remove(path);
    }

    public void putResource(String path, Resource resource)
    {
        resources.put(path, resource);
    }

    public void putResource(String path, File file)
    {
        resources.put(path, ResourceFile.from(file));
    }

    public boolean isRunning()
    {
        return running;
    }

    public void start() throws IOException
    {
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

}
