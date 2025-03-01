package com.github.inc0grepoz.http.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.StringJoiner;
import java.util.regex.Pattern;

public class Request
{

    private static final Pattern PATTERN_QUERY = Pattern.compile("\\?");

    public static Request read(InputStream in) throws IOException
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        Queue<String> raw = new LinkedList<>();
        String line;

        while ((line = reader.readLine()) != null)
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
        String[] context = raw.poll().split(" "); // gets optimized
        type = RequestType.valueOf(context[0]);

        String[] target = PATTERN_QUERY.split(context[1], 2); // doesn't
        path = target[0];
        queryString = target.length == 1 ? null : target[1];
    }

    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(", ", "{", "}");

        joiner.add("\"path\": \"" + path + "\"");
        joiner.add("\"method\": \"" + type + "\"");

        if (queryString != null)
        {
            joiner.add("\"queryString\": \"" + queryString + "\"");
        }

        return joiner.toString();
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
