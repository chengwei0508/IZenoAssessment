/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.izeno.demo.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author cw
 */
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Schema(description = "Request to issue a new insurance policy")
public class IssuePolicyRequest {

    @Schema(description = "Name of the policyholder", example = "John Doe")
    @NotNull
    private String policyHolderName;

    @Schema(description = "Email address of the policyholder", example = "john.doe@example.com")
    @NotNull
    @Email
    private String policyHolderEmail;

    @Schema(description = "Type of coverage", example = "FULL")
    @NotNull
    private String coverageType;
}
