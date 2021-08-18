package com.epam.esm.Service;

import com.epam.esm.Payload.ApiResponse;
import com.epam.esm.Payload.GiftCertificateDTO;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;


@Transactional
class GiftCertificateServiceTest {


    @Test
    void getAllGiftCertificates() throws SQLException, ClassNotFoundException {
        GiftCertificateService giftCertificateService = new GiftCertificateService();
        ApiResponse allGiftCertificates = giftCertificateService.getAllGiftCertificates();
        Object object = allGiftCertificates.getObject();
        ArrayList<Object> allGifts = new ArrayList(Arrays.asList(object));
        assertTrue(allGifts.size()>0);
    }

    @Test
    void getGiftById() throws SQLException, ClassNotFoundException {
        GiftCertificateService giftCertificateService = new GiftCertificateService();
        ApiResponse giftById = giftCertificateService.getGiftById(123);

        assertNull(giftById.getObject());

    }

    @Test
    void addGiftCertificate() throws SQLException, ClassNotFoundException {
        GiftCertificateService giftCertificateService = new GiftCertificateService();
        GiftCertificateDTO giftCertificateDTO=new GiftCertificateDTO("iphone 21","xs",500,60);
        ApiResponse apiResponse = giftCertificateService.addGiftCertificate(giftCertificateDTO);

        assertTrue(apiResponse.isSuccess());

    }



    @Test
    void updateGiftCertificate() throws SQLException, ClassNotFoundException {
        GiftCertificateService giftCertificateService = new GiftCertificateService();
        giftCertificateService.addGiftCertificate(new GiftCertificateDTO("iphone 11", "xs", 500, 60));
        Integer addedCertificateId = giftCertificateService.getAddedCertificateId("iphone 11");

        giftCertificateService.updateGiftCertificate(addedCertificateId, new GiftCertificateDTO("iphone 15", "xs", 1000, 60));
        ApiResponse getAddedObject = giftCertificateService.getGiftById(addedCertificateId);


        assertNotNull(getAddedObject.getObject());

    }

    @Test
    void deleteGiftCertificate() throws SQLException, ClassNotFoundException {
        int id=12;

        GiftCertificateService giftCertificateService = new GiftCertificateService();
        giftCertificateService.deleteGiftCertificate(id);

        ApiResponse checkDeletingRow = giftCertificateService.getGiftById(id);
        assertFalse(checkDeletingRow.isSuccess());
        assertNull(checkDeletingRow.getObject());
    }
}