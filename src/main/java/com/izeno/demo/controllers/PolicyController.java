/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.izeno.demo.controllers;

import com.izeno.demo.dtos.IssuePolicyRequest;
import com.izeno.demo.dtos.IssuePolicyResponse;
import com.izeno.demo.dtos.JobStatusResponse;
import com.izeno.demo.services.CoreSystemStatusService;
import com.izeno.demo.services.JobService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author cw
 */
@RestController
@RequestMapping("/api/v1/policy")
public class PolicyController {

    @Autowired
    private JobService jobService;

    private final CoreSystemStatusService coreSystemStatusService;
    
    public PolicyController(JobService jobService, CoreSystemStatusService coreSystemStatusService) {
        this.jobService = jobService;
        this.coreSystemStatusService = coreSystemStatusService;
    }
    
    @Operation(summary = "Issue a policy", description = "Issues a policy to the core system")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "202", description = "Policy issuance accepted",
                content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = IssuePolicyResponse.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input",
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error",
                content = @Content)
    })
    @PostMapping("")
    public ResponseEntity<IssuePolicyResponse> issuePolicy(@Valid @RequestBody IssuePolicyRequest request) {
        String jobId = jobService.createJob();

        IssuePolicyResponse response = new IssuePolicyResponse();
        response.setStatus("ACCEPTED");
        response.setMessage("Policy issuance is in progress.");
        response.setJobId(jobId);

        return ResponseEntity.accepted().body(response);
    }

    @Operation(summary = "Get job status", description = "Get the status of a policy issuance job")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Job status retrieved",
                content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = JobStatusResponse.class))),
        @ApiResponse(responseCode = "404", description = "Job not found",
                content = @Content)
    })
    @GetMapping("/status/{jobId}")
    public ResponseEntity<JobStatusResponse> getJobStatus(@PathVariable String jobId) {
        String status = jobService.getJobStatus(jobId);

        if ("NOT_FOUND".equals(status)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        JobStatusResponse response = new JobStatusResponse();
        response.setJobId(jobId);
        response.setStatus(status);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/toggle-core-system")
    public ResponseEntity<String> toggleCoreSystemStatus(@RequestParam boolean isUp) {
        coreSystemStatusService.setCoreSystemStatus(isUp);
        String message = isUp ? "Core system is now UP" : "Core system is now DOWN";
        return ResponseEntity.ok(message);
    }
}
