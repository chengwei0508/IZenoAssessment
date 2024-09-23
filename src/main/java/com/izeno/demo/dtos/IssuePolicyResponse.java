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
@Schema(description = "Response after issuing an insurance policy")
@Getter
@Setter
public class IssuePolicyResponse {

    @Schema(description = "Status of the policy issuance", example = "ACCEPTED")
    private String status;

    @Schema(description = "Detailed message about the policy issuance", example = "Policy issuance is in progress.")
    private String message;

    @Schema(description = "Unique identifier for the job", example = "job-1627896543000")
    private String jobId;
}
