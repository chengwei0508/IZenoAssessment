/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.izeno.demo.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author cw
 */
@Schema(description = "Response object containing the status of a job")
@Getter
@Setter
public class JobStatusResponse {
    
    @Schema(description = "Unique identifier for the job", example = "job-1627896543000")
    private String jobId;
    
    @Schema(description = "Current status of the job", example = "COMPLETED", allowableValues = {"IN_PROGRESS", "COMPLETED", "FAILED", "NOT_FOUND"})
    private String status;
}
