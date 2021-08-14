package com.epam.esm.Configuration;

import com.epam.esm.Payload.ApiResponse;
import com.epam.esm.Service.GiftCertificateService;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        GiftCertificateService giftCertificateService=new GiftCertificateService();
        ApiResponse allGiftCertificates = giftCertificateService.getGiftById(1);
        System.out.println(allGiftCertificates);
    }

}
