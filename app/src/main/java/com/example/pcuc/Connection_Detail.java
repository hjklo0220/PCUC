package com.example.pcuc;

import android.util.Log;

import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.CipherSuite;
import okhttp3.ConnectionSpec;
import okhttp3.OkHttpClient;
import okhttp3.TlsVersion;


class Connection_Detail {

    private static final int DEFAULT_CONNECTION_TIMEOUT = 1;
    private static final int DEFAULT_READ_TIMEOUT = 1;
    private static final ConnectionSpec spec = new ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
            .tlsVersions(TlsVersion.TLS_1_0, TlsVersion.TLS_1_1, TlsVersion.TLS_1_2).cipherSuites(
                    CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA, CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA,
                    CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA256, CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA256,
                    CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256, CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256,

                    CipherSuite.TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA, CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA,
                    CipherSuite.TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA384, CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA384,
                    CipherSuite.TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384, CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_256_GCM_SHA384,

                    CipherSuite.TLS_RSA_WITH_AES_128_CBC_SHA, CipherSuite.TLS_RSA_WITH_AES_256_CBC_SHA,
                    CipherSuite.TLS_RSA_WITH_AES_128_CBC_SHA256, CipherSuite.TLS_RSA_WITH_AES_256_CBC_SHA256,
                    CipherSuite.TLS_RSA_WITH_AES_128_GCM_SHA256, CipherSuite.TLS_RSA_WITH_AES_256_GCM_SHA384,

                    CipherSuite.TLS_ECDHE_RSA_WITH_3DES_EDE_CBC_SHA
            )
            .supportsTlsExtensions(true).build();
    static OkHttpClient.Builder configureClient(final OkHttpClient.Builder builder) throws NoSuchAlgorithmException, KeyManagementException {
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, new TrustManager[]{new SNX509TrustManager()}, new SecureRandom());
        final HostnameVerifier hostnameVerifier = new HostnameVerifier() {
            @Override
            public boolean verify(final String hostname, final SSLSession session) {
                //return hostname.contains(".plocs.kr")||hostname.contains(".google.com");
                //HostnameVerifier hv = HttpsURLConnection.getDefaultHostnameVerifier();
                //return hv.verify("*.plocs.kr",session);
                return true;
            }};
        return builder.sslSocketFactory(sslContext.getSocketFactory(), new SNX509TrustManager()).hostnameVerifier(hostnameVerifier)
                .connectTimeout(DEFAULT_CONNECTION_TIMEOUT, TimeUnit.SECONDS).readTimeout(DEFAULT_READ_TIMEOUT,TimeUnit.SECONDS)
                .connectionSpecs(Collections.singletonList(spec));
    }

    public static Map<String, String> getHeader() {
        Map<String, String> header = new HashMap<>();
        // Accept
        header.put("Accept", "*/*");
        // Accept-Charset
        header.put("Accept-Charset", "utf-8");
        // Accept-Charset
        header.put("Accept-Encoding", "gzip");
        // Accept-Language
        header.put("Accept-Language", Locale.getDefault().toString());
        // Content-Type
        header.put("Content-Type", "application/x-www-form-urlencoded");
        return header;
    }

    private static final class SNX509TrustManager implements X509TrustManager {
        public static final X509Certificate[] X_509_CERTIFICATES = {};
        private X509TrustManager standardTrustManager;
        private SNX509TrustManager() {
            try {
                TrustManagerFactory tmf = TrustManagerFactory.getInstance("X509");
                tmf.init((KeyStore) null);
                TrustManager[] trustManagers = tmf.getTrustManagers();
                if (trustManagers == null || trustManagers.length == 0) {
                    throw new NoSuchAlgorithmException("no trust manager found.");
                }
                standardTrustManager = (X509TrustManager) trustManagers[0];
            } catch (Exception e) {
                Log.d(getClass().getName(), "failed to initialize the standard trust manager: " + e.getMessage());
                standardTrustManager = null;
            }
        }
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }
        @Override
        public void checkServerTrusted(final X509Certificate[] certificates, final String authType) throws CertificateException {
            if (certificates == null) {
                throw new IllegalArgumentException("there were no certificates.");
            }
            if (certificates.length == 1) {
                // if certificates have only one, check the validity simply
                certificates[0].checkValidity();
            } else if (standardTrustManager != null) {
                standardTrustManager.checkServerTrusted(certificates, authType);
            } else {
                throw new CertificateException("there were one more certificates but no trust manager found.");
            }
        }


        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return X_509_CERTIFICATES;
        }
    }
}
