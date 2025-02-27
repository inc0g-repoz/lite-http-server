package com.github.inc0grepoz.http.server.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.stream.Collectors;

public class Request
{

    public static Request read(BufferedReader in) throws IOException
    {
        Queue<String> raw = new LinkedList<>();
        String line;

        while ((line = in.readLine()) != null)
        {
            if (line.isEmpty()) break;
//          System.out.println(line);
            raw.add(line);
        }

        return new Request(raw);
    }

    private final RequestType type;
    private final String path, queryString;

    private Request(Queue<String> raw)
    {
        System.out.println(raw.stream().collect(Collectors.joining(", ", "Raw: ", "")));
        String[] context = raw.poll().split(" ");
        type = RequestType.valueOf(context[0]);

        String[] target = context[1].split("\\?", 2);
        path = target[0];
        queryString = target.length == 1 ? null : target[1];
    }

    public RequestType getType()
    {
        return type;
    }

    public String getPath()
    {
        return path;
    }

    public String getQueryString()
    {
        return queryString;
    }

    public boolean hasArguments()
    {
        return queryString != null;
    }

    public Map<String, String> resolveArguments()
    {
        if (!hasArguments() || queryString.length() < 2)
        {
            return Collections.emptyMap();
        }

        // left&right
        String[] argVal = queryString.split("&");
        if (argVal.length == 0 || argVal[0].isEmpty())
        {
            return Collections.emptyMap();
        }

        Map<String, String> args = new HashMap<>();

        // left=right
        for (String kvp: argVal)
        {
            String[] kv = kvp.split("=", 2);
            args.put(kv[0], kv[1]);
        }

        return args;
    }

}
