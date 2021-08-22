package com.epam.esm.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GiftCertificate {
    private Integer id;
    private String name;
    private String description;
    private double price;
    private Integer duration;
    private String createDate;
    private String lastUpdateDate;
    private List<Tag> tagList;

    public GiftCertificate(Integer id, String name, String description, double price, Integer duration, String createDate, String lastUpdateDate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.createDate = createDate;
        this.lastUpdateDate = lastUpdateDate;
    }
}
