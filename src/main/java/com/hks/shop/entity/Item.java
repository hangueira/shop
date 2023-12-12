package com.hks.shop.entity;

import com.hks.shop.constant.ItemSellStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Table(name = "item")
@Getter
@Setter
@ToString
@SequenceGenerator(
        name = "ITEM_SEQ_GENERATOR",
        sequenceName = "ITEM_SEQ",
        initialValue = 1,
        allocationSize = 1)
public class Item {


    @Id
    @Column(name = "item_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ITEM_SEQ_GENERATOR")
    private Long id;

    @Column(nullable = false, length = 50)
    private String itemNm;

    @Column(name = "price", nullable = false)
    private int price;

    @Column(nullable = false)
    private int stockNumber;

    @Lob
    @Column(nullable = false)
    private String itemDetail;

    @Enumerated(EnumType.STRING)
    private ItemSellStatus itemSellStatus;

    private LocalDateTime regTime;

    private LocalDateTime updateTime;
}
