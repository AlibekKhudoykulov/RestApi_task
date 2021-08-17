package com.epam.esm.Controller;

import com.epam.esm.Payload.ApiResponse;
import com.epam.esm.Payload.GiftCertificateDTO;
import com.epam.esm.Service.GiftCertificateService;
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
        return ResponseEntity.status(allGiftCertificates.isSuccess() ? 200 : 404).body(allGiftCertificates);
    }

    @GetMapping("/{id]")
    public HttpEntity<?> getById(@PathVariable Integer id) throws SQLException, ClassNotFoundException {
        ApiResponse giftById = giftCertificateService.getGiftById(id);
        return ResponseEntity.status(giftById.isSuccess()?200:404).body(giftById);
    }

    @PostMapping
    public HttpEntity<?> addGift(@RequestBody GiftCertificateDTO giftCertificateDTO) throws SQLException, ClassNotFoundException {
        ApiResponse apiResponse = giftCertificateService.addGiftCertificate(giftCertificateDTO);
        return ResponseEntity.status(apiResponse.isSuccess()?201:409).body(apiResponse);
    }
    @PutMapping("/{id}")
    public HttpEntity<?> updateGiftById(@PathVariable Integer id,@RequestBody GiftCertificateDTO giftCertificateDTO) throws SQLException, ClassNotFoundException {
        ApiResponse apiResponse = giftCertificateService.updateGiftCertificate(id, giftCertificateDTO);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }
    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteGiftById(@PathVariable Integer id) throws SQLException, ClassNotFoundException {
        ApiResponse apiResponse = giftCertificateService.deleteGiftCertificate(id);
        return ResponseEntity.status(apiResponse.isSuccess()?200:404).body(apiResponse);
    }
}
