package org.listingbotv1.listingbot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class TaskQueueService {

    private static final String QUEUE_NAME = "scraperQueue";

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public void enqueueScraperTask(String task) {
        System.out.println("Enqueueing task: " + task);
        redisTemplate.opsForList().rightPush(QUEUE_NAME, task);
    }

    public String dequeueScraperTask() {
        String task = redisTemplate.opsForList().leftPop(QUEUE_NAME);
        System.out.println("Dequeueing task:" + task);
        return task;
    }

    public RedisTemplate<String, String> getRedisTemplate(){
        return this.redisTemplate;
    }
}
