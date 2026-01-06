// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: Apache-2.0
package aws.example.s3;

import com.obs.services.ObsClient;
import com.obs.services.exception.ObsException;

/**
 * Copy an object from one Amazon S3 bucket to another.
 * 
 * This code expects that you have AWS credentials set up per:
 * http://docs.aws.amazon.com/java-sdk/latest/developer-guide/setup-credentials.html
 */
public class CopyObject {
    public static void main(String[] args) {
        final String USAGE = "\n" +
                "To run this example, supply the name (key) of an S3 object, the bucket name\n" +
                "that it's contained within, and the bucket to copy it to.\n" +
                "\n" +
                "Ex: CopyObject <objectname> <frombucket> <tobucket>\n";

        if (args.length < 3) {
            System.out.println(USAGE);
            System.exit(1);
        }

        String object_key = args[0];
        String from_bucket = args[1];
        String to_bucket = args[2];

        System.out.format("Copying object %s from bucket %s to %s\n",
                object_key, from_bucket, to_bucket);
        final ObsClient obsClient = new ObsClient("accessKey", "secretKey", "https://obs.region.myhuaweicloud.com");
        try {
            obsClient.copyObject(from_bucket, object_key, to_bucket, object_key);
        } catch (ObsException e) {
            System.err.println(e.getErrorMessage());
            System.exit(1);
        } finally {
            try {
                obsClient.close();
            } catch (Exception e) {
                // ignore
            }
        }
        System.out.println("Done!");
    }
}
