// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: Apache-2.0
package aws.example.s3;

import com.obs.services.ObsClient;
import com.obs.services.exception.ObsException;
import com.obs.services.model.ObsObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Get an object within an Amazon S3 bucket.
 * 
 * This code expects that you have AWS credentials set up per:
 * http://docs.aws.amazon.com/java-sdk/latest/developer-guide/setup-credentials.html
 */
public class GetObject {
    public static void main(String[] args) {
        final String USAGE = "\n" +
                "To run this example, supply the name of an S3 bucket and object to\n" +
                "download from it.\n" +
                "\n" +
                "Ex: GetObject <bucketname> <filename>\n";

        if (args.length < 2) {
            System.out.println(USAGE);
            System.exit(1);
        }

        String bucket_name = args[0];
        String key_name = args[1];

        System.out.format("Downloading %s from S3 bucket %s...\n", key_name, bucket_name);
        final ObsClient obsClient = new ObsClient("accessKey", "secretKey", "https://obs.region.myhuaweicloud.com");
        try {
            ObsObject o = obsClient.getObject(bucket_name, key_name);
            InputStream obsInputStream = o.getObjectContent();
            FileOutputStream fos = new FileOutputStream(new File(key_name));
            byte[] read_buf = new byte[1024];
            int read_len = 0;
            while ((read_len = obsInputStream.read(read_buf)) > 0) {
                fos.write(read_buf, 0, read_len);
            }
            obsInputStream.close();
            fos.close();
        } catch (ObsException e) {
            System.err.println(e.getErrorMessage());
            System.exit(1);
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        } catch (IOException e) {
            System.err.println(e.getMessage());
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
