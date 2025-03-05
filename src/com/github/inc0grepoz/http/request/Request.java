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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Request
{

    private static final Pattern PATTERN_HOST = Pattern.compile("(?:(.+)\\.)?"
            + "(localhost|\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3})"
            + "(?:\\:(\\d{1,5}))?");

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

    private final RequestMethod type;
    private final String path, parameters, domain, subdomain;
    private final Map<String, String> header = new HashMap<String, String>();

    private Request(Queue<String> raw)
    {
        // Reading the request line: <Method> <Request-URI> <HTTP-Version>
        String line = raw.poll();
        String[] requestLine = line.split(" "); // gets optimized

        int pathEnd = requestLine[1].indexOf('?');
        parameters = pathEnd == -1 ? null : requestLine[1].substring(pathEnd + 1);
        path = pathEnd == -1 ? requestLine[1] : requestLine[1].substring(0, pathEnd);

        // Reading the method
        type = RequestMethod.valueOf(requestLine[0]);

        // Reading headers
        int pos;
        while (!raw.isEmpty())
        {
            line = raw.poll();
            pos = line.indexOf(':');

            header.put(line.substring(0, pos), line.substring(pos + 2));
        }

        Matcher matcher = PATTERN_HOST.matcher(getField("Host"));
        domain = matcher.find() ? matcher.group(2) : null;
        subdomain = matcher.group(1);
    }

    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(", ", "{", "}");

        joiner.add("\"path\": \"" + path + "\"");
        joiner.add("\"method\": \"" + type + "\"");
        joiner.add("\"domain\" : \"" + domain + "\"");

        if (subdomain != null)
        {
            joiner.add("\"subdomain\": \"" + subdomain + "\"");
        }

        if (parameters != null)
        {
            joiner.add("\"query\": \"" + parameters + "\"");
        }

        return joiner.toString();
    }

    public RequestMethod getType()
    {
        return type;
    }

    public String getPath()
    {
        return path;
    }

    public String getRawParameters()
    {
        return parameters;
    }

    public boolean hasParameters()
    {
        return parameters != null;
    }

    public String getField(String field)
    {
        return header.get(field);
    }

    public String getDomain()
    {
        return domain;
    }

    public String getSubdomain()
    {
        return subdomain;
    }

    public Map<String, String> resolveParameters()
    {
        if (!hasParameters() || parameters.length() < 2)
        {
            return Collections.emptyMap();
        }

        // left&right
        String[] argVal = parameters.split("&");
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
