/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.izeno.demo.controllers;

import com.google.gson.Gson;
import com.izeno.demo.dtos.BaseDTO;
import com.izeno.demo.services.PersonAPIService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author cw
 */
@RestController
@RequestMapping(value = "/api/v1/person")
public class PersonAPIController {

    @Autowired
    PersonAPIService personAPIServices;

    Gson mGson = new Gson();

    @GetMapping(value = "/")
    public String getPage() {
        return "Hello World.";
    }

    @GetMapping("/getPerson/{personId}")
    public @ResponseBody
    String getPerson(@PathVariable(value = "personId") Long personId, HttpServletRequest request) {
        ResponseEntity<BaseDTO> response = personAPIServices.getPerson(personId, request);
        BaseDTO responseDTO = response.getBody();
        return mGson.toJson(responseDTO);
    }

    @GetMapping("/retrieve3rdPartyPosts")
    public ResponseEntity<BaseDTO> retrieve3rdPartyPosts(HttpServletRequest request) throws Exception {
        return personAPIServices.retrieve3rdPartyPosts(request);
    }

    @GetMapping("/getTransaction/{personId}/page/{page}")
    public @ResponseBody
    String getTransactionByPerson(@PathVariable(value = "personId") Long personId,
            @PathVariable(value = "page") int page, HttpServletRequest request) {
        ResponseEntity<BaseDTO> response = personAPIServices.getTransactionByPerson(personId, page, request);
        BaseDTO responseDTO = response.getBody();
        return mGson.toJson(responseDTO);
    }

    @Transactional
    @PostMapping(value = "/createPerson")
    public @ResponseBody
    String createPerson(@RequestBody BaseDTO requestDTO) {
        ResponseEntity<BaseDTO> response = personAPIServices.createPerson(requestDTO);
        BaseDTO responseDTO = response.getBody();
        return mGson.toJson(responseDTO);
    }

    @PostMapping("/createTransaction")
    public @ResponseBody
    String createTransaction(@RequestBody BaseDTO requestDTO) {
        ResponseEntity<BaseDTO> response = personAPIServices.createTransaction(requestDTO);
        BaseDTO responseDTO = response.getBody();
        return mGson.toJson(responseDTO);
    }

    @PutMapping("/updatePerson")
    public @ResponseBody
    String updatePerson(@RequestBody BaseDTO requestDTO) {
        ResponseEntity<BaseDTO> response = personAPIServices.updatePerson(requestDTO);
        BaseDTO responseDTO = response.getBody();
        return mGson.toJson(responseDTO);
    }
}
