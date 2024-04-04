package com.demo.shoppingapp.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Products {

    private int productId;
    private String productName;
    private Double price;
    private String productImage;
    private int inventory;

}
