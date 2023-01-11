package com.tasanah.springsecurity.config;

import com.auth0.jwt.algorithms.Algorithm;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.util.StringUtils;

@Slf4j
@Configuration
public class KeyAlgorithmConfig {

  @Value("${setting.jwt.jks.filePath}")
  private String filePath;

  @Value("${setting.jwt.jks.filePassword}")
  private String filePassword;

  @Value("${setting.jwt.jks.keyName}")
  private String keyName;

  @Value("${setting.jwt.jks.keyPassword}")
  private String keyPassword;

  @Value("${setting.jwt.jks.keyAlgorithm}")
  private String keyAlgorithm;

  @Autowired
  private ApplicationContext context;

  @Bean
  @Profile({ "production" })
  public Algorithm getKeyAlgorithm() {
    Algorithm keyAlgorithm = null;

    // Load jks file stream
    try (InputStream jskFileStream = new FileInputStream(filePath)) {
      KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
      keyStore.load(jskFileStream, StringUtils.hasText(filePassword) ? null : filePassword.toCharArray());

      // Get public key
      RSAPublicKey publicKey = (RSAPublicKey) keyStore.getCertificate(keyName).getPublicKey();

      // Get private key
      RSAPrivateKey privateKey = (RSAPrivateKey) keyStore.getKey(
        keyName,
        StringUtils.hasText(keyPassword) ? null : keyPassword.toCharArray()
      );

      // Return algorithm instance
      keyAlgorithm = Algorithm.RSA512(publicKey, privateKey);
    } catch (Exception unableToObtainKeyException) {
      log.error("Application is unable to obtain key", unableToObtainKeyException);
      ((ConfigurableApplicationContext) context).close();
    }

    return keyAlgorithm;
  }

  @Bean
  @Profile({ "local" })
  public Algorithm getKeyAlgorithmLocal() {
    return Algorithm.HMAC512(keyAlgorithm);
  }
}
