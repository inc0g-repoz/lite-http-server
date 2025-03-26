package com.github.inc0grepoz.http.config;

import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.cert.X509Certificate;
import java.util.Enumeration;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class Certificate
{

    private boolean enabled;
    private String path;

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

    public void init() throws Throwable
    {
        init(new FileInputStream(path));
    }

    @SuppressWarnings("unused")
    private void initFromJar() throws Throwable
    {
        init(getClass().getClassLoader().getResourceAsStream("MY_CERT.p12"));
    }

    private void init(InputStream in) throws Throwable
    {
        HttpsURLConnection.setDefaultHostnameVerifier(
            new HostnameVerifier()
            {

                @Override
                public boolean verify(String hostname, SSLSession sslSession)
                {
                    return true;
                }

            }
        );

        TrustManager[] trustAllCerts = new TrustManager[] {
            new X509TrustManager() {

                @Override
                public X509Certificate[] getAcceptedIssuers()
                {
                    return new X509Certificate[0];
                }

                @Override
                public void checkClientTrusted(X509Certificate[] certs, String authType) {}

                @Override
                public void checkServerTrusted(X509Certificate[] certs, String authType) {}

            }
        };

        SSLContext sc_ssl = SSLContext.getInstance("SSL");
        sc_ssl.init(null, trustAllCerts, new java.security.SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc_ssl.getSocketFactory());

        char[] keyPassword =  "MY_PASSWORD".toCharArray();
        KeyStore keystore = KeyStore.getInstance("PKCS12");
        keystore.load(in, keyPassword);

        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        keyManagerFactory.init(keystore, keyPassword);      
        KeyManager keyManagers[] = keyManagerFactory.getKeyManagers();

        Enumeration<String> aliases = keystore.aliases();
        String keyAlias = "";
        while (aliases.hasMoreElements())
        {
            keyAlias = (String) aliases.nextElement();
            System.out.println("Key found: " + keyAlias);
        }

        SSLContext sc_tls = SSLContext.getInstance("TLS"); 
        sc_tls.init(keyManagers, null, null);

        SSLServerSocketFactory sslContextFactory = (SSLServerSocketFactory) sc_tls.getServerSocketFactory();
        SSLServerSocket ssl = (SSLServerSocket) sslContextFactory.createServerSocket(32567);     
        ssl.setEnabledProtocols(new String[] { "TLSv1", "TLSv1.1", "TLSv1.2", "SSLv3" });
        ssl.setEnabledCipherSuites(sslContextFactory.getSupportedCipherSuites());
    }

}
