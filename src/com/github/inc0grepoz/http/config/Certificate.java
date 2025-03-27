package com.github.inc0grepoz.http.config;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.security.KeyStore;
import java.util.Enumeration;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocketFactory;

public class Certificate
{

    private boolean enabled;
    private String keyPassword, path;

    transient SSLServerSocketFactory sslssFactory;

    // JSON-mapper constructor
    private Certificate() {}

    public boolean isEnabled()
    {
        return enabled;
    }

    public String getPath()
    {
        return path;
    }

    public SSLServerSocketFactory getServerSocketFactory()
    {
        return sslssFactory;
    }

    public void init() throws Throwable
    {
        init(new FileInputStream(path));
    }

    private void init(InputStream in) throws Throwable
    {
        System.out.println("Configuring an SSL certificate");
        char[] pw = keyPassword.toCharArray();

        // Load the keystore
        KeyStore ks = KeyStore.getInstance(getKeyStoreType(path));
        try (InputStream keystoreFile = in)
        {
            ks.load(keystoreFile, pw);
        }
        printKeyAliases(ks, System.out);

        // Set up KeyManagerFactory
        KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
        kmf.init(ks, pw);

        // Set up SSLContext
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(kmf.getKeyManagers(), null, null);

        // Create SSLServerSocketFactory
        sslssFactory = sslContext.getServerSocketFactory();
        System.out.println("Initialized an SSL server socket factory");
    }

    private String getKeyStoreType(String filename)
    {
        int dotIdx = filename.lastIndexOf('.');
        String ext = filename.substring(dotIdx);

        switch (ext)
        {
        case ".jks":
            return "JKS";
        case ".p12":
            return "PKCS12";
        default:
            throw new IllegalArgumentException("Unknown keystore type: " + ext);
        }
    }

    private void printKeyAliases(KeyStore keyStore, PrintStream out) throws Throwable
    {
        Enumeration<String> aliases = keyStore.aliases();

        while (aliases.hasMoreElements())
        {
            out.println("Key alias found: " + aliases.nextElement());
        }
    }

}
