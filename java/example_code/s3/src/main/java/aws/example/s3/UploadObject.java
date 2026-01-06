package aws.example.s3;// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: Apache-2.0

// snippet-start:[s3.java.upload_object.complete]

import com.obs.services.ObsClient;
import com.obs.services.exception.ObsException;
import com.obs.services.model.ObjectMetadata;
import com.obs.services.model.PutObjectRequest;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class UploadObject {

    public static void main(String[] args) throws IOException {
        String bucketName = "*** Bucket name ***";
        String stringObjKeyName = "*** String object key name ***";
        String fileObjKeyName = "*** File object key name ***";
        String fileName = "*** Path to file to upload ***";

        ObsClient obsClient = new ObsClient("accessKey", "secretKey", "https://obs.region.myhuaweicloud.com");
        try {
            // This code expects that you have credentials set up

            // Upload a text string as a new object.
            String content = "Uploaded String Object";
            ByteArrayInputStream inputStream = new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8));
            obsClient.putObject(bucketName, stringObjKeyName, inputStream);

            // Upload a file as a new object with ContentType and title specified.
            PutObjectRequest request = new PutObjectRequest(bucketName, fileObjKeyName, new File(fileName));
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType("plain/text");
            metadata.addUserMetadata("title", "someTitle");
            request.setMetadata(metadata);
            obsClient.putObject(request);
        } catch (ObsException e) {
            // The call was transmitted successfully, but OBS couldn't process
            // it, so it returned an error response.
            e.printStackTrace();
        } finally {
            try {
                obsClient.close();
            } catch (Exception e) {
                // ignore
            }
        }
    }
}

// snippet-end:[s3.java.upload_object.complete]
