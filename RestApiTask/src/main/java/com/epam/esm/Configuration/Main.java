package com.epam.esm.Configuration;

import com.epam.esm.Payload.ApiResponse;
import com.epam.esm.Service.GiftCertificateService;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {

//        List<Integer> tag=new ArrayList<>();
//        tag.add(1);
//        tag.add(4);
//
//        GiftCertificateDTO giftCertificateDTO=new GiftCertificateDTO("Elbek","gift",100000,
//                30);
//
//        Tag tag1=new Tag("qalesan");
//        TagService tagService=new TagService();
//        ApiResponse allTags = tagService.addTag(tag1);
//        System.out.println(allTags);

        GiftCertificateService giftCertificateService=new GiftCertificateService();
        Integer addedCertificateId = giftCertificateService.getAddedCertificateId("iphone 10");
        ApiResponse giftById = giftCertificateService.getGiftById(addedCertificateId);



    }

}
