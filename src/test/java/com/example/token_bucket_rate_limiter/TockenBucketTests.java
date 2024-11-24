package com.example.token_bucket_rate_limiter;

import org.junit.jupiter.api.Test;

public class TockenBucketTests {

    @Test
    public void testBucketAlgorithm() {
        TokenBucket tokenBucket = new TokenBucket(TokenBucket.MAX_BUCKET_SIZE);

        int numberOfConsumed = 0;

        long startTime = System.currentTimeMillis();

        while ((System.currentTimeMillis() - startTime) < 10 * 1000) {
            boolean consumeSuccess = tokenBucket.tryConsume();
            System.out.println("try consume = " + consumeSuccess);
            if (consumeSuccess) {
                numberOfConsumed++;
            }

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        long endTime = System.currentTimeMillis();
        long timeInMS = endTime - startTime;

        System.out.println("№ of consumed requests = " + numberOfConsumed);
        System.out.println("time taken = " + timeInMS + " ms");
        System.out.println("№ of requests per window = " + (numberOfConsumed * TokenBucket.WINDOW_SIZE_FOR_RATE_LIMIT_IN_MILLISECONDS) / timeInMS);
        System.out.println("№ of requests per window expected = " + TokenBucket.NUMBER_OF_REQUESTS);

    }
}
