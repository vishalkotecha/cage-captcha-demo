package com.example.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;

@SpringBootApplication
public class CaptchaDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(CaptchaDemoApplication.class, args);
	}

	@Value("${aws.access.key.id}")
	private String awsKeyId;

	@Value("${aws.access.key.secret}")
	private String awsKeySecret;

	@Value("${aws.region}")
	private String awsRegion;

	@Value("${aws.s3.audio.bucket}")
	private String awsS3AudioBucket;

	@Bean(name = "awsKeyId")
	public String getAWSKeyId() {
		return awsKeyId;
	}

	@Bean(name = "awsKeySecret")
	public String getAWSKeySecret() {
		return awsKeySecret;
	}

	@Bean(name = "awsRegion")
	public Region getAWSPollyRegion() {
		return Region.getRegion(Regions.fromName(awsRegion));
	}

	@Bean(name = "awsCredentialsProvider")
	public AWSCredentialsProvider getAWSCredentials() {
		BasicAWSCredentials awsCredentials = new BasicAWSCredentials(this.awsKeyId, this.awsKeySecret);
		return new AWSStaticCredentialsProvider(awsCredentials);
	}

	@Bean(name = "awsS3AudioBucket")
	public String getAWSS3AudioBucket() {
		return awsS3AudioBucket;
	}

}
