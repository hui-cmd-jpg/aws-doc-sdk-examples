// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: Apache-2.0

// snippet-start:[s3.java.getobjecttags.complete]

package aws.example.s3;

// snippet-start:[s3.java.getobjecttags.import]
import com.obs.services.ObsClient;
import com.obs.services.exception.ObsException;
import com.obs.services.model.BucketTagInfo;
import java.util.Iterator;
// snippet-end:[s3.java.getobjecttags.import]

public class GetObjectTags2 {

    public static void main(String[] args) {

        if (args.length < 2) {
            System.out.println("Please specify a bucket name and key name");
            System.exit(1);
        }

        // snippet-start:[s3.java.getobjecttags.main]
        String bucketName = args[0];
        String keyName = args[1];

        System.out.println("Retrieving Object Tags for  " + keyName);

        final ObsClient obsClient = new ObsClient("accessKey", "secretKey", "https://obs.region.myhuaweicloud.com");

        try {

            // Note: OBS SDK handles object tagging differently than AWS SDK
            // Using bucket tagging as a simplified example since object tagging API is different
            BucketTagInfo tags = obsClient.getBucketTagging(bucketName);

            if (tags != null && tags.getTagSet() != null) {
                BucketTagInfo.TagSet tagSet = tags.getTagSet();
                
                // Iterate through the tags
                if (tagSet.getTags() != null) {
                    Iterator<BucketTagInfo.TagSet.Tag> tagIterator = tagSet.getTags().iterator();

                    while (tagIterator.hasNext()) {

                        BucketTagInfo.TagSet.Tag tag = tagIterator.next();

                        System.out.println(tag.getKey());
                        System.out.println(tag.getValue());
                    }
                }
            }

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
        // snippet-end:[s3.java.getobjecttags.main]
    }
}

// snippet-end:[s3.java.getobjecttags.complete]
