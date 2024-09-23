/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.izeno.demo.services;

import java.util.concurrent.ConcurrentHashMap;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 *
 * @author cw
 */
@Service
public class JobService {

    private final ConcurrentHashMap<String, String> jobStatusMap = new ConcurrentHashMap<>();
    private final CoreSystemStatusService coreSystemStatusService;

    public JobService(CoreSystemStatusService coreSystemStatusService) {
        this.coreSystemStatusService = coreSystemStatusService;
    }
    
    public String createJob() {
        if (!coreSystemStatusService.isCoreSystemUp()) {
            throw new RuntimeException("Core system is down");
        }
        String jobId = generateJobId();
        jobStatusMap.put(jobId, "IN_PROGRESS");
        processJob(jobId); // Trigger async processing
        return jobId;
    }

    @Async
    public void processJob(String jobId) {
        new Thread(() -> {
            try {
                // Simulate a long-running process (e.g., policy creation)
                Thread.sleep(120000); // 2 minutes delay
                if (coreSystemStatusService.isCoreSystemUp()) {
                    jobStatusMap.put(jobId, "COMPLETED");
                } else {
                    jobStatusMap.put(jobId, "FAILED");
                }
            } catch (InterruptedException e) {
                jobStatusMap.put(jobId, "FAILED");
                Thread.currentThread().interrupt();
            }
        }).start();
    }

    public String getJobStatus(String jobId) {
        return jobStatusMap.getOrDefault(jobId, "NOT_FOUND");
    }

    private String generateJobId() {
        return "job-" + System.currentTimeMillis();
    }
}
