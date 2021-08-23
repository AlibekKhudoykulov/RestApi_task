package com.epam.esm.service;

import com.epam.esm.payload.ApiResponse;
import com.epam.esm.payload.GiftCertificateDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

@Transactional
class GiftCertificateServiceTest {
    GiftCertificateServiceTest() {
    }

    @Test
    void getAllGiftCertificates() throws SQLException, ClassNotFoundException {
        GiftCertificateService giftCertificateService = new GiftCertificateService();
        ApiResponse allGiftCertificates = giftCertificateService.getAllGiftCertificates();
        Object object = allGiftCertificates.getObject();
        ArrayList<Object> allGifts = new ArrayList(Arrays.asList(object));
        Assertions.assertTrue(allGifts.size() > 0);
    }

    @Test
    void getGiftById() throws SQLException, ClassNotFoundException {
        GiftCertificateService giftCertificateService = new GiftCertificateService();
        ApiResponse giftById = giftCertificateService.getGiftById(1);
        Assertions.assertNotNull(giftById.getObject());
    }

    @Test
    void addGiftCertificate() throws SQLException, ClassNotFoundException {
        GiftCertificateService giftCertificateService = new GiftCertificateService();
        GiftCertificateDTO giftCertificateDTO = new GiftCertificateDTO("iphoneeqeasd", "xs", 500.0, 60,null);
        ApiResponse apiResponse = giftCertificateService.addGiftCertificate(giftCertificateDTO);
        Assertions.assertTrue(apiResponse.isSuccess());
    }

    @Test
    void updateGiftCertificate() throws SQLException, ClassNotFoundException, NoSuchFieldException {
        GiftCertificateService giftCertificateService = new GiftCertificateService();
        giftCertificateService.addGiftCertificate(new GiftCertificateDTO("iphone 10", "xs", 500.0, 60));
        Integer addedCertificateId = giftCertificateService.getAddedCertificateId("iphone 10");
        giftCertificateService.updateGiftCertificate(addedCertificateId, new GiftCertificateDTO("iphone 15", "xs", 1000.0, 60));
        ApiResponse getAddedObject = giftCertificateService.getGiftById(addedCertificateId);
        Assertions.assertNotNull(getAddedObject.getObject());
    }

    @Test
    void deleteGiftCertificate() throws SQLException, ClassNotFoundException {
        int deletedObjectId=60;

        GiftCertificateService giftCertificateService = new GiftCertificateService();
        giftCertificateService.deleteGiftCertificate(deletedObjectId);
        ApiResponse checkDeletingRow = giftCertificateService.getGiftById(deletedObjectId);
        Assertions.assertNull(checkDeletingRow.getObject());
    }
}
