package com.example.shopapp.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "order_details")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "order_id")
    @ManyToOne
    private Order order;

    @JoinColumn(name = "product_id")
    @ManyToOne
    private Product product;

    @Column(name = "price")
    private Float price;

    @Column(name = "number_of_product")
    private  int numberOfProduct;

    @Column(name = "total_money")
    private Float totalMoney;

    @Column(name = "color")
    private String color;



}
