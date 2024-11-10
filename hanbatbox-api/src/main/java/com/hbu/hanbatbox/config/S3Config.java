package com.hbu.hanbatbox.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.transfer.s3.S3TransferManager;

@Configuration
public class S3Config {

  @Value("${spring.cloud.aws.credentials.access-key}")
  private String accessKey;
  @Value("${spring.cloud.aws.credentials.secret-key}")
  private String secretKey;
  @Value("${spring.cloud.aws.region.static}")
  private String region;

  @Bean
  public S3Client s3Client() {
    return S3Client.builder().region(Region.of(region)).credentialsProvider(
            StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKey, secretKey)))
        .accelerate(true).build();
  }

  @Bean
  public S3AsyncClient s3AsyncClient() {
    return S3AsyncClient.builder().multipartEnabled(true).region(Region.of(region))
        .credentialsProvider(
            StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKey, secretKey)))
        .build();
  }

  @Bean
  public S3TransferManager S3TransferManager(S3AsyncClient s3AsyncClient) {
    return S3TransferManager.builder().s3Client(s3AsyncClient).build();
  }
}
