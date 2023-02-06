package org.arfna.database.s3;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.S3ObjectSummary;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Prior to using this class, please ensure that your aws credentials have been set on this machine
 * This should be in your root directory, in the .aws/credentials file
 */
public class S3Util {

    private static final String BUCKET_NAME = "arfna-images";

    private static AmazonS3 S3_CLIENT;

    public List<String> getAllFileNames() {
        ListObjectsV2Result result = getS3Client().listObjectsV2(BUCKET_NAME);
        List<S3ObjectSummary> objects = result.getObjectSummaries();
        return objects.stream().map(S3ObjectSummary::getKey).collect(Collectors.toList());
    }

    public Set<String> getAllFilesInPath(String path) {
        ListObjectsV2Result result = getS3Client().listObjectsV2(BUCKET_NAME, path);
        List<S3ObjectSummary> objects = result.getObjectSummaries();
        return objects.stream()
                .map(S3ObjectSummary::getKey)
                .map(x -> x.replaceAll(path, ""))
                .collect(Collectors.toSet());
    }

    public boolean isFileInS3(String path) {
        ListObjectsV2Result result = getS3Client().listObjectsV2(BUCKET_NAME, path);
        List<S3ObjectSummary> objects = result.getObjectSummaries();
        return !objects.isEmpty();
    }

    public void uploadFile(String localFile, String pathToUpload) {
        getS3Client().putObject(BUCKET_NAME, pathToUpload, new File(localFile));
    }

    /**
     * Downloads a file and returns its bytes
     */
    public byte[] downloadFile(String s3Path) throws IOException {
        S3Object obj = getS3Client().getObject(BUCKET_NAME, s3Path);
        S3ObjectInputStream s3is = obj.getObjectContent();
        return s3is.readAllBytes();
    }

    public void deleteFile(String pathToDelete) {
        getS3Client().deleteObject(BUCKET_NAME, pathToDelete);
    }

    private static AmazonS3 getS3Client() {
        if (S3_CLIENT == null) {
            S3_CLIENT = AmazonS3ClientBuilder.standard()
                    .withRegion(Regions.US_EAST_1)
                    .build();
        }
        return S3_CLIENT;
    }

    public static void main(String[] args) {
        S3Util util = new S3Util();
        util.getAllFileNames().forEach(System.out::println);
        System.out.println();
        util.getAllFilesInPath("thumbnails/").forEach(System.out::println);
        System.out.println();
        System.out.println(util.isFileInS3("thumbnails/tiger.png"));
        System.out.println(util.isFileInS3("thumbnails/bat.png"));
        util.uploadFile(System.getProperty("user.home") + File.separator + String.join(File.separator, "Downloads", "swarm.png"),
                "test/swarm.png");
        System.out.println();
        util.getAllFileNames().forEach(System.out::println);
        System.out.println();
        util.deleteFile("test/swarm.png");
    }

}
