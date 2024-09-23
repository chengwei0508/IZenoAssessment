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
@Setter
@Getter
public class QuotationResponse {

    @Schema(description = "The unique code representing the insurance product.")
    private String productCode;

    @Schema(description = "The calculated premium for the insurance product.")
    private double premium;

}
