package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class OrderDto {
    private String itemCode;
    private String description;
    private double price;
    private int qty;
    private double totalPrice;
}
