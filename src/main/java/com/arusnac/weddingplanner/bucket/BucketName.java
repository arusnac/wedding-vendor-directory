package com.arusnac.weddingplanner.bucket;

public enum BucketName {
    PROFILE_IMAGE("wedding-vendor-bucket");
    private final String bucketName;

    BucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getBucketName() {
        return bucketName;
    }
}
