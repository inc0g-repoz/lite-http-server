package com.github.inc0grepoz.http.server.resource;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Map;

import com.github.inc0grepoz.http.server.response.Response;
import com.github.inc0grepoz.http.server.response.ResponseBuilder;
import com.github.inc0grepoz.http.server.response.ResponseContentType;
import com.github.inc0grepoz.http.server.response.ResponseStatusCode;

public class ResourceFile implements Resource {

    public static ResourceFile from(File file) {
        try (
            InputStreamReader reader = new InputStreamReader
            (new FileInputStream(file), StandardCharsets.UTF_8)
        ) {
            System.out.println(Files.probeContentType(file.toPath()));
            return new ResourceFile(reader);
        } catch (IOException e) {
            throw new IllegalArgumentException("File \"" + file.getName() + "\" not found", e);
        }
    }

    public static ResourceFile from(InputStream in) {
        try (
            InputStreamReader reader = new InputStreamReader
            (in, StandardCharsets.UTF_8)
        ) {
            return new ResourceFile(reader);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private final String content;

    public ResourceFile(InputStreamReader reader) throws IOException {
        StringBuilder builder = new StringBuilder();

        // Reading the file|resource character by character
        for (int i; (i = reader.read()) != -1;) {
            builder.append((char) i);
        }

        content = builder.toString();
    }

    @Override
    public Response handle(Map<String, String> args, BufferedWriter out) {
        ResponseBuilder builder = Response.builder();
        builder.code(ResponseStatusCode.OK);
        builder.contentType(ResponseContentType.TEXT_HTML.toString());
        builder.appendContent(content);
        return builder.build();
    }

}
