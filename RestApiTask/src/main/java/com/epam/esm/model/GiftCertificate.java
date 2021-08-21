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
}
