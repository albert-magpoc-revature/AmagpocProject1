package com.revature.util;

import java.net.URL;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.HttpMethod;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

public class S3Util {
	public static final Region REGION = Region.US_EAST_2;
	public static final String BUCKET_NAME = "amagpocproject1";
	public static final Regions CLIENT_REGIONS = Regions.US_EAST_2;
	
	private static Logger log = LogManager.getLogger(S3Util.class);
	
	private static S3Util instance = null;
	private S3Client s3 = null;
	
	private S3Util() {
		s3 = S3Client.builder().region(REGION).build();
	}
	
	public static synchronized S3Util getinstance() {
		if(instance == null) {
			instance = new S3Util();
		}
		return instance;
	}
	
	public void uploadToBucket(String key, RequestBody requestBody) {
		log.trace("Uploading a file");
		s3.putObject(PutObjectRequest.builder()
				.bucket(BUCKET_NAME)
				.key(key)
				.build(),
				requestBody);
		log.trace("finished uploading");
	}
	
	public String getObject(String key) {
        try {
            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                    .withRegion(CLIENT_REGIONS)
                    .withCredentials(new ProfileCredentialsProvider())
                    .build();

            // Set the presigned URL to expire after one hour.
            java.util.Date expiration = new java.util.Date();
            long expTimeMillis = expiration.getTime();
            expTimeMillis += 1000 * 60 * 60;
            expiration.setTime(expTimeMillis);

            // Generate the presigned URL.
            System.out.println("Generating pre-signed URL.");
            GeneratePresignedUrlRequest generatePresignedUrlRequest =
                    new GeneratePresignedUrlRequest(BUCKET_NAME, key)
                            .withMethod(HttpMethod.GET)
                            .withExpiration(expiration);
            URL url = s3Client.generatePresignedUrl(generatePresignedUrlRequest);

            return url.toString();
        } catch (AmazonServiceException e) {
            // The call was transmitted successfully, but Amazon S3 couldn't process 
            // it, so it returned an error response.
            e.printStackTrace();
        } catch (SdkClientException e) {
            // Amazon S3 couldn't be contacted for a response, or the client
            // couldn't parse the response from Amazon S3.
            e.printStackTrace();
        }
        throw new RuntimeException("Link generation went wrong");
    }
}
