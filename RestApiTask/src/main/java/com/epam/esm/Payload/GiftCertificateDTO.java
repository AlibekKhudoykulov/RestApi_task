package com.epam.esm.Payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@Component
public class GiftCertificateDTO {
    private String name;
    private String description;
    private Integer price;
    private Integer duration;
    List<Integer> tags = new ArrayList<>();

    public GiftCertificateDTO(String name, String description, Integer price, Integer duration) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
    }

}
