/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.izeno.demo.services;

import com.izeno.demo.dtos.BaseDTO;
import com.izeno.demo.dtos.Response;
import com.izeno.demo.dtos.TransactionsDTO;
import com.izeno.demo.entity.Person;
import com.izeno.demo.entity.Transaction;
import jakarta.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import com.izeno.demo.repos.PersonRepository;
import com.izeno.demo.repos.TransactionRepository;
import com.izeno.demo.utils.Util;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author cw
 */
@Service
public class PersonAPIService {

    @Autowired
    PersonRepository personRepository;

    @Autowired
    TransactionRepository transactionRepository;

    public final static int PAGE_SIZE = 10;

    public final static String PAYMENT_TYPE = "CASH";

    @Value("${third.party.posts.url}")
    private String thirdPartyPostsUrl;

    @Transactional
    public ResponseEntity<BaseDTO> createPerson(BaseDTO dto) {
        try {
            List<Person> personList = personRepository.findByEmail(dto.getEmail());
            if (personList.isEmpty()) {
                Person person = new Person();
                BeanUtils.copyProperties(dto, person);
                personRepository.save(person);

                dto.setPersonId(person.getId());
                dto.setResponse(Response.SUCCESS.getCode());
                dto.setDesc(Response.SUCCESS.getDesc());
            } else {
                dto.setResponse(Response.DUPLICATE.getCode());
                dto.setDesc(Response.DUPLICATE.getDesc());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            dto.setResponse(Response.EXCEPTION.getCode());
            dto.setDesc(Response.EXCEPTION.getDesc());
        }

        return ResponseEntity.ok().body(dto);
    }

    @Transactional
    public ResponseEntity<BaseDTO> getPerson(Long personId, HttpServletRequest request) {
        BaseDTO dto = new BaseDTO();
        try {
            Optional<Person> personO = personRepository.findById(personId);
            if (personO.isEmpty()) {
                dto.setResponse(Response.FAILED.getCode());
                dto.setDesc(Response.FAILED.getDesc());
            } else {
                Person person = personO.get();
                dto.setResponse(Response.SUCCESS.getCode());
                dto.setDesc(Response.SUCCESS.getDesc());
                BeanUtils.copyProperties(person, dto);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            dto.setResponse(Response.EXCEPTION.getCode());
            dto.setDesc(Response.EXCEPTION.getDesc());
        }

        return ResponseEntity.ok().body(dto);
    }

    @Transactional
    public ResponseEntity<BaseDTO> updatePerson(BaseDTO dto) {
        try {
            Optional<Person> personT = personRepository.findById(dto.getPersonId());
            if (personT.isPresent()) {
                Person person = personT.get();

                if (StringUtils.hasText(dto.getName())) {
                    person.setName(dto.getName());
                }
                if (StringUtils.hasText(dto.getEmail())) {
                    person.setEmail(dto.getEmail());
                }
                if (StringUtils.hasText(dto.getPhoneNo())) {
                    person.setPhoneNo(dto.getPhoneNo());
                }
                if (StringUtils.hasText(dto.getCity())) {
                    person.setCity(dto.getCity());
                }

                personRepository.save(person);
                dto.setResponse(Response.SUCCESS.getCode());
                dto.setDesc(Response.SUCCESS.getDesc());
            } else {
                // result not found
                dto = new BaseDTO();
                dto.setResponse(Response.FAILED.getCode());
                dto.setDesc(Response.FAILED.getDesc());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            dto.setResponse(Response.EXCEPTION.getCode());
            dto.setDesc(Response.EXCEPTION.getDesc());
        }

        return ResponseEntity.ok().body(dto);
    }

    @Transactional
    public ResponseEntity<BaseDTO> createTransaction(BaseDTO dto) {
        try {
            Optional<Person> personT = personRepository.findById(dto.getPersonId());
            if (personT.isPresent()) {
                Person person = personT.get();
                Transaction trxn = new Transaction();
                trxn.setTrxnId(UUID.randomUUID().toString());
                trxn.setTrxnDt(new Timestamp(System.currentTimeMillis()));
                trxn.setTrxnAmt(dto.getTrxnAmt());
                trxn.setPymTyp(PAYMENT_TYPE);
                trxn.setStan(Util.getTimeStamp().substring(9, 15));
                trxn.setRrn(Util.getTimeStamp().substring(0, 15));
                trxn.setPerson(person);
                transactionRepository.save(trxn);
                dto.setResponse(Response.SUCCESS.getCode());
                dto.setDesc(Response.SUCCESS.getDesc());
            } else {
                // result not found
                dto = new BaseDTO();
                dto.setResponse(Response.FAILED.getCode());
                dto.setDesc(Response.FAILED.getDesc());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            dto.setResponse(Response.EXCEPTION.getCode());
            dto.setDesc(Response.EXCEPTION.getDesc());
        }
        return ResponseEntity.ok().body(dto);
    }

    @Transactional
    public ResponseEntity<BaseDTO> getTransactionByPerson(Long personId, int page, HttpServletRequest request) {
        BaseDTO dto = new BaseDTO();
        try {
            Optional<Person> personT = personRepository.findById(personId);
            if (personT.isPresent()) {
                Pageable pageable = PageRequest.of(page, PAGE_SIZE);
                List<Transaction> transactionList = transactionRepository.findByPerson(personT.get(), pageable);
                List<TransactionsDTO> transactionDTOList = transactionList.stream()
                        .map(obj -> new TransactionsDTO(
                                obj.getTrxnId(), 
                                obj.getTrxnAmt(), 
                                Util.parseDateTime(obj.getTrxnDt()),
                                obj.getPymTyp(),
                                obj.getRrn(),
                                obj.getStan()
                        ))
                        .collect(Collectors.toList());
                dto.setTrxnList(transactionDTOList);
                dto.setResponse(Response.SUCCESS.getCode());
                dto.setDesc(Response.SUCCESS.getDesc());
            } else {
                // result not found
                dto = new BaseDTO();
                dto.setResponse(Response.FAILED.getCode());
                dto.setDesc(Response.FAILED.getDesc());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            dto.setResponse(Response.EXCEPTION.getCode());
            dto.setDesc(Response.EXCEPTION.getDesc());
        }

        return ResponseEntity.ok().body(dto);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<BaseDTO> retrieve3rdPartyPosts(HttpServletRequest request) throws Exception {

        BaseDTO dto = new BaseDTO();
        RestTemplate rs = new RestTemplate();
        ResponseEntity<String> response = rs.exchange(thirdPartyPostsUrl, HttpMethod.GET, null, String.class);
        String responseStr = response.getBody();

        if (StringUtils.hasText(responseStr)) {
            dto.setThirdPartyPosts(responseStr);
            dto.setResponse(Response.SUCCESS.getCode());
            dto.setDesc(Response.SUCCESS.getDesc());
        } else {
            // result not found
            dto.setResponse(Response.FAILED.getCode());
            dto.setDesc(Response.FAILED.getDesc());
        }

        return ResponseEntity.ok().body(dto);
    }
}
