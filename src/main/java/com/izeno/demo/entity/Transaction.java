/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.izeno.demo.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 *
 * @author cw
 */
@Entity
@Table(name = "t_transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "trxn_id")
    private String trxnId;

    @Column(name = "trxn_amt")
    private BigDecimal trxnAmt; // transaction amount

    @Column(name = "trxn_date")
    private Timestamp trxnDt; // transaction date

    @Column(name = "pym_typ")
    private String pymTyp; // payment Type 

    @Column(name = "stan")
    private String stan; // STAN no

    @Column(name = "RRN")
    private String rrn; // reference No

    @ManyToOne(cascade = CascadeType.ALL)
    private Person person;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Timestamp getTrxnDt() {
        return trxnDt;
    }

    public void setTrxnDt(Timestamp trxnDt) {
        this.trxnDt = trxnDt;
    }

    public String getPymTyp() {
        return pymTyp;
    }

    public void setPymTyp(String pymTyp) {
        this.pymTyp = pymTyp;
    }

    public String getStan() {
        return stan;
    }

    public void setStan(String stan) {
        this.stan = stan;
    }

    public String getRrn() {
        return rrn;
    }

    public void setRrn(String rrn) {
        this.rrn = rrn;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
