package com.epam.esm.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GiftCertificate {
    private Integer id;
    private String name;
    private String description;
    private Integer price;
    private Integer duration;
    private String create_date;
    private String last_update_date;
    List<Tag> tags = new ArrayList<>();

    public GiftCertificate(Integer id, String name, String description, Integer price, Integer duration, String create_date, String last_update_date) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.create_date = create_date;
        this.last_update_date = last_update_date;
    }
}
