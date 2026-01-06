// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: Apache-2.0
package aws.example.s3;

import com.obs.services.ObsClient;
import com.obs.services.exception.ObsException;

/**
 * Delete an object from an Amazon S3 bucket.
 * 
 * This code expects that you have AWS credentials set up per:
 * http://docs.aws.amazon.com/java-sdk/latest/developer-guide/setup-credentials.html
 * 
 * ++ Warning ++ This code will actually delete the object that you specify!
 */
public class DeleteObject {
    public static void main(String[] args) {
        final String USAGE = "\n" +
                "To run this example, supply the name of an S3 bucket and object\n" +
                "name (key) to delete.\n" +
                "\n" +
                "Ex: DeleteObject <bucketname> <objectname>\n";

        if (args.length < 2) {
            System.out.println(USAGE);
            System.exit(1);
        }

        String bucket_name = args[0];
        String object_key = args[1];

        System.out.format("Deleting object %s from S3 bucket: %s\n", object_key,
                bucket_name);
        final ObsClient obsClient = new ObsClient("accessKey", "secretKey", "https://obs.region.myhuaweicloud.com");
        try {
            obsClient.deleteObject(bucket_name, object_key);
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
