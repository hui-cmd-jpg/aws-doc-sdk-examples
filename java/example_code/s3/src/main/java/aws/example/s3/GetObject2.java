package aws.example.s3;// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: Apache-2.0

// snippet-start:[s3.java.get_object.complete]

import com.obs.services.ObsClient;
import com.obs.services.exception.ObsException;
import com.obs.services.model.GetObjectRequest;
import com.obs.services.model.ObsObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class GetObject2 {

    public static void main(String[] args) throws IOException {
        String bucketName = "*** Bucket name ***";
        String key = "*** Object key ***";

        ObsObject fullObject = null, objectPortion = null, headerOverrideObject = null;
        ObsClient obsClient = new ObsClient("accessKey", "secretKey", "https://obs.region.myhuaweicloud.com");
        try {
            // Get an object and print its contents.
            System.out.println("Downloading an object");
            fullObject = obsClient.getObject(new GetObjectRequest(bucketName, key));
            System.out.println("Content-Type: " + fullObject.getMetadata().getContentType());
            System.out.println("Content: ");
            displayTextInputStream(fullObject.getObjectContent());

            // Get a range of bytes from an object and print the bytes.
            GetObjectRequest rangeObjectRequest = new GetObjectRequest(bucketName, key);
            rangeObjectRequest.setRangeStart(0L);
            rangeObjectRequest.setRangeEnd(9L);
            objectPortion = obsClient.getObject(rangeObjectRequest);
            System.out.println("Printing bytes retrieved.");
            displayTextInputStream(objectPortion.getObjectContent());

            // Get an entire object, overriding the specified response headers, and print
            // the object's content.
            // Note: OBS SDK handles response header overrides differently than AWS SDK
            GetObjectRequest getObjectRequestHeaderOverride = new GetObjectRequest(bucketName, key);
            headerOverrideObject = obsClient.getObject(getObjectRequestHeaderOverride);
            displayTextInputStream(headerOverrideObject.getObjectContent());
        } catch (ObsException e) {
            // The call was transmitted successfully, but OBS couldn't process
            // it, so it returned an error response.
            e.printStackTrace();
        } finally {
            // To ensure that the network connection doesn't remain open, close any open
            // input streams.
            if (fullObject != null) {
                try {
                    fullObject.getObjectContent().close();
                } catch (IOException e) {
                    // ignore
                }
            }
            if (objectPortion != null) {
                try {
                    objectPortion.getObjectContent().close();
                } catch (IOException e) {
                    // ignore
                }
            }
            if (headerOverrideObject != null) {
                try {
                    headerOverrideObject.getObjectContent().close();
                } catch (IOException e) {
                    // ignore
                }
            }
            try {
                obsClient.close();
            } catch (Exception e) {
                // ignore
            }
        }
    }

    private static void displayTextInputStream(InputStream input) throws IOException {
        // Read the text input stream one line at a time and display each line.
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        String line = null;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
        System.out.println();
    }
}

// snippet-end:[s3.java.get_object.complete]
