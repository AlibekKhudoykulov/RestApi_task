package com.epam.esm.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class  GiftCertificateDTO {
    private String name;
    private String description;
    private Double price;
    private Integer duration;
    private List<Integer> tags;

    public GiftCertificateDTO(String name, String description, Double price, Integer duration) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
    }
}
