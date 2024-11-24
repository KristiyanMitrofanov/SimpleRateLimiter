package com.example.token_bucket_rate_limiter;

public class TokenBucket {

    public static int NUMBER_OF_REQUESTS = 10;
    public static int WINDOW_SIZE_FOR_RATE_LIMIT_IN_MILLISECONDS = 10000;
    public static int MAX_BUCKET_SIZE = 10;

    private int numberOfTokenAvailable;
    private int maxBucketSize;
    private long lastRefillTime;

    public TokenBucket(int maxBucketSize) {
        this.maxBucketSize = maxBucketSize;
        this.numberOfTokenAvailable = maxBucketSize;
        this.lastRefillTime = System.currentTimeMillis();
        this.refill();
    }

    public synchronized boolean tryConsume() {
        refill();
        if (this.numberOfTokenAvailable > 0) {
            this.numberOfTokenAvailable--;
            return true;
        }
        return false;
    }


    private void refill() {
        long currentTime = System.currentTimeMillis();
        long timeSinceLastRefill = currentTime - this.lastRefillTime;

        if (timeSinceLastRefill > WINDOW_SIZE_FOR_RATE_LIMIT_IN_MILLISECONDS) {
            int tokensToAdd = (int) (timeSinceLastRefill * NUMBER_OF_REQUESTS / WINDOW_SIZE_FOR_RATE_LIMIT_IN_MILLISECONDS);
            this.numberOfTokenAvailable = Math.min(maxBucketSize, this.numberOfTokenAvailable + tokensToAdd);
            this.lastRefillTime = System.currentTimeMillis();
        }
    }
}
