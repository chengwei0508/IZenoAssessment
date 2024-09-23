/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.izeno.demo.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.math.BigDecimal;

/**
 *
 * @author cw
 */
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class TransactionsDTO {

    private String trxnId; // transaction id
    private BigDecimal trxnAmt; // transaction amount
    private String trxnDt; // transction date
    private String pymTyp;
    private String rrn;
    private String stan;

    public String getTrxnId() {
        return trxnId;
    }

    public void setTrxnId(String trxnId) {
        this.trxnId = trxnId;
    }

    public BigDecimal getTrxnAmt() {
        return trxnAmt;
    }

    public void setTrxnAmt(BigDecimal trxnAmt) {
        this.trxnAmt = trxnAmt;
    }

    public String getTrxnDt() {
        return trxnDt;
    }

    public void setTrxnDt(String trxnDt) {
        this.trxnDt = trxnDt;
    }

    public String getPymTyp() {
        return pymTyp;
    }

    public void setPymTyp(String pymTyp) {
        this.pymTyp = pymTyp;
    }

    public String getRrn() {
        return rrn;
    }

    public void setRrn(String rrn) {
        this.rrn = rrn;
    }

    public String getStan() {
        return stan;
    }

    public void setStan(String stan) {
        this.stan = stan;
    }

    public TransactionsDTO(String trxnId, BigDecimal trxnAmt, String trxnDt, String pymTyp, String rrn, String stan) {
        this.trxnId = trxnId;
        this.trxnAmt = trxnAmt;
        this.trxnDt = trxnDt;
        this.pymTyp = pymTyp;
        this.rrn = rrn;
        this.stan = stan;
    }
}
