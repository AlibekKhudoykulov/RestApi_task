package com.epam.esm.Controller;

import com.epam.esm.Payload.ApiResponse;
import com.epam.esm.Service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.sql.SQLException;

@Controller
public class GiftCertificateController {
    @Autowired
    GiftCertificateService giftCertificateService;

    @GetMapping("/all")
    public HttpEntity<?> getAll() throws SQLException, ClassNotFoundException {
        ApiResponse allGiftCertificates = giftCertificateService.getAllGiftCertificates();
        return ResponseEntity.status(allGiftCertificates.isSuccess() ? 200 : 404).body(allGiftCertificates);
    }
}
