package com.epam.esm.controller;


import com.epam.esm.exceptionHandling.exceptions.NoContentException;
import com.epam.esm.exceptionHandling.exceptions.NotFoundException;
import com.epam.esm.payload.GiftCertificateDTO;
import com.epam.esm.payload.response.ApiResponse;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
@RequestMapping("/api/gift")
public class GiftCertificateController {

    @Autowired
    GiftCertificateService giftCertificateService;

    @GetMapping("/all")
    public HttpEntity<?> getAll() throws SQLException, ClassNotFoundException {
        ApiResponse allGiftCertificates = giftCertificateService.getAllGiftCertificates();
        if (allGiftCertificates.isSuccess()){
            return ResponseEntity.status(200).body(allGiftCertificates);
        }else throw new NoContentException();
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getById(@PathVariable Integer id) throws SQLException, ClassNotFoundException {
        ApiResponse giftById = giftCertificateService.getGiftById(id);
        if (giftById.isSuccess()){
            return ResponseEntity.status(200).body(giftById);
        }else throw new NotFoundException(id);
    }

    @PostMapping
    public HttpEntity<?> addGift(@RequestBody GiftCertificateDTO giftCertificateDTO) throws SQLException, ClassNotFoundException {
        ApiResponse apiResponse = giftCertificateService.addGiftCertificate(giftCertificateDTO);
        if (apiResponse.isSuccess()){
            return ResponseEntity.status(201).body(apiResponse);
        }else throw new NoContentException();
    }

    @PutMapping("/{id}")
    public HttpEntity<?> updateGiftById(@PathVariable Integer id,@RequestBody GiftCertificateDTO giftCertificateDTO) throws SQLException, ClassNotFoundException {
        ApiResponse apiResponse = giftCertificateService.updateGiftCertificate(id, giftCertificateDTO);

        if (apiResponse.isSuccess()){
            return ResponseEntity.status(201).body(apiResponse);
        }else throw new NotFoundException(id);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteGiftById(@PathVariable Integer id) throws SQLException, ClassNotFoundException {
        ApiResponse apiResponse = giftCertificateService.deleteGiftCertificate(id);
        if (apiResponse.isSuccess()){
            return ResponseEntity.status(200).body(apiResponse);
        }else throw new NotFoundException(id);
    }
}
