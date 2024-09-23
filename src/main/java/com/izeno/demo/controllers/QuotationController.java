/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.izeno.demo.controllers;

import com.izeno.demo.dtos.QuotationResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author cw
 */
@RestController
@RequestMapping("/api/v1/quotations")
public class QuotationController {

    @Operation(summary = "Get quotation", description = "Retrieves pricing information for a specified insurance product")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Quotation retrieved successfully",
                content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = QuotationResponse.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input",
                content = @Content),
        @ApiResponse(responseCode = "404", description = "Product not found",
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error",
                content = @Content)
    })
    @GetMapping("")
    public ResponseEntity<QuotationResponse> getQuotation(
            @Parameter(description = "Product code for which to retrieve quotation", required = true)
            @RequestParam String productCode) {

        // Simulate retrieval of pricing information
        QuotationResponse response = new QuotationResponse();
        response.setProductCode(productCode);
        response.setPremium(500.00);

        return ResponseEntity.ok(response);
    }
}
