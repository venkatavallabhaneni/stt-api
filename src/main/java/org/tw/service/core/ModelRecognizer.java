package org.tw.service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;

import nonapi.io.github.classgraph.utils.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vosk.Model;
import org.vosk.Recognizer;

import java.io.File;


public class ModelRecognizer {

    private static Logger logger = LoggerFactory.getLogger(ModelRecognizer.class);

    public static Recognizer getRecognizer(Languages language) {

        Recognizer recognizer = null;
        try (Model modelEnIn = new Model("models/vosk-model-small-en-in-0.4")) {
            recognizer = new Recognizer(modelEnIn, 16000);
        } catch (Exception e) {
            logger.error("ERROR", e);
        }

        return recognizer;
    }

   /* public void getFile(){

        AWSCredentials credentials = new BasicAWSCredentials(
                "<AWS accesskey>",
                "<AWS secretkey>"
        );

        AmazonS3 s3client = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.US_EAST_2)
                .build();

        S3Object s3object = s3client.ge
        S3ObjectInputStream inputStream = s3object.getObjectContent();
        FileUtils.copyInputStreamToFile(inputStream, new File("/Users/user/Desktop/hello.txt"));
    }*/

}
