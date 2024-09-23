/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.izeno.demo.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.izeno.demo.entity.Person;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author cw
 */
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class BaseDTO {

    private String response;  
    private String desc;
    private String name;
    private String email;
    private String phoneNo;
    private String city;
    private BigDecimal trxnAmt; // transaction Amount
    private Long personId;
    private String thirdPartyPosts;
    private Person person;
    private List<TransactionsDTO> trxnList; // transaction list

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public BigDecimal getTrxnAmt() {
        return trxnAmt;
    }

    public void setTrxnAmt(BigDecimal trxnAmt) {
        this.trxnAmt = trxnAmt;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public String getThirdPartyPosts() {
        return thirdPartyPosts;
    }

    public void setThirdPartyPosts(String thirdPartyPosts) {
        this.thirdPartyPosts = thirdPartyPosts;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public List<TransactionsDTO> getTrxnList() {
        return trxnList;
    }

    public void setTrxnList(List<TransactionsDTO> trxnList) {
        this.trxnList = trxnList;
    }
}
