/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.izeno.demo.dtos;

/**
 *
 * @author cw
 */
public enum Response {

    SUCCESS("00", "Request succeed."),
    FAILED("01", "Request failed."),
    DUPLICATE("98", "Request failed. Duplicate request."),
    EXCEPTION("99", "Exception."),
    RC_TOO_MANY_REQUEST("429","Too Many Request.");

    private final String code;
    private final String desc;

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    private Response(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
