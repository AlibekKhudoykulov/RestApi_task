package com.epam.esm.Configuration;

import com.epam.esm.Entity.Tag;
import com.epam.esm.Payload.ApiResponse;
import com.epam.esm.Payload.GiftCertificateDTO;
import com.epam.esm.Service.GiftCertificateService;
import com.epam.esm.Service.TagService;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        List<Integer> tag=new ArrayList<>();
        tag.add(1);
        tag.add(4);

        GiftCertificateDTO giftCertificateDTO=new GiftCertificateDTO("Elbek","gift",100000,
                30);

        Tag tag1=new Tag("qalesan");
        TagService tagService=new TagService();
        ApiResponse allTags = tagService.addTag(tag1);
        System.out.println(allTags);


    }

}
