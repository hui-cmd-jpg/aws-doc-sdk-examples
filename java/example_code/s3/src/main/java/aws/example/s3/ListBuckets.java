// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: Apache-2.0
package aws.example.s3;

import com.obs.services.ObsClient;
import com.obs.services.model.S3Bucket;

import java.util.List;

/**
 * List your Amazon S3 buckets.
 * 
 * This code expects that you have AWS credentials set up per:
 * http://docs.aws.amazon.com/java-sdk/latest/developer-guide/setup-credentials.html
 */
public class ListBuckets {
    public static void main(String[] args) {
        final ObsClient obsClient = new ObsClient("accessKey", "secretKey", "https://obs.region.myhuaweicloud.com");
        try {
            List<S3Bucket> buckets = obsClient.listBuckets();
            System.out.println("Your Amazon S3 buckets are:");
            for (S3Bucket b : buckets) {
                System.out.println("* " + b.getBucketName());
            }
        } finally {
            try {
                obsClient.close();
            } catch (Exception e) {
                // ignore
            }
        }
    }
}
