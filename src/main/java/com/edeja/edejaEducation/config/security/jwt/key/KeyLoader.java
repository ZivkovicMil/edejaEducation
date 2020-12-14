package com.edeja.edejaEducation.config.security.jwt.key;

import com.edeja.edejaEducation.config.security.jwt.key.exception.UnableToLoadKeyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;

@Service
public class KeyLoader {

  private static final Logger log = LoggerFactory.getLogger(KeyLoader.class);

  @Value("${jwt.keyStoreType}")
  private String keyStoreType;

  @Value("${jwt.keyStoreFile}")
  private String keyStoreFile;

  @Value("${jwt.keyStorePassword}")
  private String keyStorePassword;

  @Value("${jwt.keyAlias}")
  private String keyAlias;

  @Autowired
  private ApplicationContext appContext;

  // bertConfigDir is actually the $CATALINA_HOME / tomcat home
  @Value("file:#{systemProperties['bertConfigDir']}")
  private String tomcatHome;

  private volatile Key jwtKey;

  public Key loadJwtKey() throws UnableToLoadKeyException {
    /*
     * cache key
     *
     * see http://www.cs.umd.edu/~pugh/java/memoryModel/DoubleCheckedLocking.html see
     * https://en.wikipedia.org/wiki/Double-checked_locking
     */
    Key result = jwtKey;
    if (result == null) {
      synchronized (this) {
        result = jwtKey;
        if (result == null) {
          log.debug("Loading JKS key store from file system.");
          jwtKey = loadJwtKeyFromFileSystem();
          result = loadJwtKeyFromFileSystem();
        }
      }
    }

    return result;
  }

  private Key loadJwtKeyFromFileSystem() throws UnableToLoadKeyException {
    InputStream jksResourceStream = null;

    try {
      KeyStore keyStore = KeyStore.getInstance(keyStoreType);
      String jksPath = tomcatHome + "/" + keyStoreFile;
      Resource jksResource = appContext.getResource(jksPath);
      jksResourceStream = jksResource.getInputStream();
      keyStore.load(jksResourceStream, keyStorePassword.toCharArray());
      Key key = keyStore.getKey(keyAlias, keyStorePassword.toCharArray());
      return key;
    } catch (KeyStoreException e) {
      log.error("Unable to load keystore!", e);
      throw new UnableToLoadKeyException("Unable to load keystore!", e);
    } catch (IOException e) {
      log.error("Unable to load JKS for REST JWT!", e);
      throw new UnableToLoadKeyException("Unable to load JKS for REST JWT!", e);
    } catch (CertificateException e) {
      log.error("Unable to load keystore!", e);
      throw new UnableToLoadKeyException("Unable to load keystore!", e);
    } catch (NoSuchAlgorithmException e) {
      log.error("Unable to load keystore / key from keystore!", e);
      throw new UnableToLoadKeyException("Unable to load keystore / key from keystore!", e);
    } catch (UnrecoverableKeyException e) {
      log.error("Unable to load key from keystore!", e);
      throw new UnableToLoadKeyException("Unable to load key from keystore!", e);
    } finally {
      if (jksResourceStream != null) {
        try {
          jksResourceStream.close();
        } catch (IOException e) {
          log.warn("Unable to close JKS resource stream!", e);
        }
      }
    }
  }

}
