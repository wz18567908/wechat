package com.example.demo.component;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.example.demo.exception.AuthorizationException;
import com.example.demo.utils.StringUtil;

@Component
public class RSAGeneral {

    private static final String RSA_ALIAS = "appdemo";
    private static final String RSA_PASSWORD = "appdemo";
    private static final String RSA_CERT_TYPE = "X.509";
    private static final String RSA_STORE_TYPE = "JCEKS";
    private static final String RSA_CERT_FILE_EXTENSION = ".cert";
    private static final String RSA_STORE_FILE_EXTENSION = ".store";

    private Logger logger = Logger.getLogger(RSAGeneral.class);

    public PrivateKey getPrivateKey() throws AuthorizationException {
        char[] password = RSA_PASSWORD.toCharArray();
        InputStream inputStream = null;
        try {
            String storePath = "/rsa/" + RSA_ALIAS + RSA_STORE_FILE_EXTENSION;
            KeyStore keyStore = KeyStore.getInstance(RSA_STORE_TYPE);
            inputStream = this.getClass().getResourceAsStream(storePath);
            keyStore.load(inputStream, password);
            return (PrivateKey) keyStore.getKey(RSA_ALIAS, password);
        } catch (KeyStoreException | NoSuchAlgorithmException | CertificateException | IOException
                | UnrecoverableKeyException e) {
            logger.error(StringUtil.formatErrorLogger(e, "getPrivateKey"));
            throw new AuthorizationException(e);
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                logger.error(StringUtil.formatErrorLogger(e, "getPrivateKey"));
            }
        }
    }

    public PublicKey getPublicKey() throws AuthorizationException {
        String certPath = "/rsa/" + RSA_ALIAS + RSA_CERT_FILE_EXTENSION;
        InputStream inputStream = null;
        try {
            CertificateFactory factory = CertificateFactory.getInstance(RSA_CERT_TYPE);
            inputStream = this.getClass().getResourceAsStream(certPath);
            Certificate certificate = factory.generateCertificate(inputStream);
            return certificate.getPublicKey();
        } catch (CertificateException e) {
            logger.error(StringUtil.formatErrorLogger(e, "getPublicKey"));
            throw new AuthorizationException(e);
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                logger.error(StringUtil.formatErrorLogger(e, "getPublicKey"));
            }
        }
    }
}
