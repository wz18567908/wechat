package com.example.demo.utils;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

import com.example.demo.exception.DemoException;

final public class DesEncrypter {
    private static DesEncrypter desEncrypter = null;
    private Cipher ecipher = null;
    private Cipher dcipher = null;

    private Logger logger = Logger.getLogger(DesEncrypter.class);
    public DesEncrypter(String passPhrase) throws DemoException {
        try {
            KeySpec keySpec = new DESKeySpec(passPhrase.getBytes());
            SecretKey key = SecretKeyFactory.getInstance("DES").generateSecret(keySpec);

            ecipher = Cipher.getInstance("DES");
            dcipher = Cipher.getInstance("DES");

            ecipher.init(Cipher.ENCRYPT_MODE, key);
            dcipher.init(Cipher.DECRYPT_MODE, key);
        } catch (InvalidKeySpecException | NoSuchAlgorithmException | InvalidKeyException | NoSuchPaddingException e) {
            logger.error(StringUtil.formatErrorLogger(e, "DesEncrypter", passPhrase));
            throw new DemoException(e);
        }
    }

    public String encrypt(String str) throws DemoException {
        byte[] utf8 = null;
        byte[] enc = null;
        try {
            utf8 = str.getBytes("UTF8");
            enc = ecipher.doFinal(utf8);
        } catch (UnsupportedEncodingException | IllegalBlockSizeException | BadPaddingException e) {
            logger.error(StringUtil.formatErrorLogger(e, "encrypt", str));
            throw new DemoException(e);
        }
        return new String(Base64.encodeBase64(enc));
    }

    public String decrypt(String str) throws DemoException {
        byte[] dec = null;
        byte[] utf8 = null;
        dec = Base64.decodeBase64(str);
        try {
            utf8 = dcipher.doFinal(dec);
            return new String(utf8, "UTF8");
        } catch (IllegalBlockSizeException | BadPaddingException | UnsupportedEncodingException e) {
            logger.error(StringUtil.formatErrorLogger(e, "decrypt", str));
            throw new DemoException(e);
        }
    }

    public synchronized static DesEncrypter getInstance() throws DemoException {
        if (desEncrypter == null) {
            desEncrypter = new DesEncrypter(".-'/W2@ce03=fky#vw6&H");
        }
        return desEncrypter;
    }

    public static void main(String[] args) throws DemoException {
        String passwd = args[0];
        String operateType = args[1];
        try {
            if (operateType.equals("dec")) {
                System.out.println(DesEncrypter.getInstance().decrypt(passwd));
            } else if (operateType.equals("enc")) {
                System.out.println(DesEncrypter.getInstance().encrypt(passwd));
            }
        } catch (DemoException e) {
            throw new DemoException(e.getMessage());
        }
    }
}
