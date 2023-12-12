package com.hks.shop.repository;

import com.hks.shop.constant.ItemSellStatus;
import com.hks.shop.entity.Item;
import com.hks.shop.entity.QItem;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.thymeleaf.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class ItemRepositoryTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    ItemRepository itemRepository;

    public void createItemList() {
        for (int i = 1; i <= 10; i++) {
            Item item = new Item();
            item.setItemNm("테스트 상품" + i);
            item.setPrice(10000 + i);
            item.setItemDetail("테스트 상품 상세 설명" + i);
            item.setItemSellStatus(ItemSellStatus.SELL);
            item.setStockNumber(100);
            item.setRegTime(LocalDateTime.now());
            item.setUpdateTime(LocalDateTime.now());
            itemRepository.save(item);
        }
    }

    public void createItemList2() {
        for (int i = 1; i <= 5; i++) {
            Item item = new Item();
            item.setItemNm("테스트 상품" + i);
            item.setPrice(10000 + i);
            item.setItemDetail("테스트 상품 상세 설명" + i);
            item.setItemSellStatus(ItemSellStatus.SELL);
            item.setStockNumber(100);
            item.setRegTime(LocalDateTime.now());
            item.setUpdateTime(LocalDateTime.now());
            itemRepository.save(item);
        }
        for (int i = 6; i <= 10; i++) {
            Item item = new Item();
            item.setItemNm("테스트 상품" + i);
            item.setPrice(10000 + i);
            item.setItemDetail("테스트 상품 상세 설명" + i);
            item.setItemSellStatus(ItemSellStatus.SELL);
            item.setStockNumber(100);
            item.setRegTime(LocalDateTime.now());
            item.setUpdateTime(LocalDateTime.now());
            itemRepository.save(item);
        }
    }



    @Test
    @DisplayName("상품 저장 테스트")
    public void createItemTest() {
        Item item = new Item();
        item.setItemNm("테스트 상품");
        item.setPrice(1000);
        item.setItemDetail("테스트 상품 상세 설명");
        item.setItemSellStatus(ItemSellStatus.SELL);
        item.setStockNumber(100);
        item.setRegTime(LocalDateTime.now());
        item.setUpdateTime(LocalDateTime.now());
        Item savedItem = itemRepository.save(item);
        assertThat(savedItem.getItemNm()).isEqualTo("테스트 상품");
    }



    @Test
    @DisplayName("상품명 조회 테스트")
    public void findByItemNmTest() {
        this.createItemList();
        List<Item> itemList = itemRepository.findByItemNm("테스트 상품1");
        Item firstItem = itemList.getFirst();
        assertThat(firstItem.getPrice()).isEqualTo(10001);
    }

    @Test
    @DisplayName("상품명, 상품상세설명 or 테스트")
    public void findByItemNmOrItemDetailTest() {
        this.createItemList();
        List<Item> findItemList = itemRepository.findByItemNmOrItemDetail("테스트 상품1", "테스트 상품 상세 설명5");
        findItemList.forEach(System.out::println);
    }

    @Test
    @DisplayName("가격 LessThan 테스트")
    public void findByLessThanTest() {
        this.createItemList();
        List<Item> findItemList = itemRepository.findByPriceLessThan(10005);
        findItemList.forEach(System.out::println);
    }

    @Test
    @DisplayName("가격 내림차순 테스트")
    public void findByLessThanOrderByPriceDescTest() {
        this.createItemList();
        List<Item> findItemList = itemRepository.findByPriceLessThanOrderByPriceDesc(10005);
        findItemList.forEach(System.out::println);
    }

    @Test
    @DisplayName("@Query를 이용한 상품 조회 테스트")
    public void findByItemDetailTest() {
        this.createItemList();
        List<Item> findItemList = itemRepository.findByItemDetail("테스트 상품 상세 설명");
        findItemList.forEach(System.out::println);
    }

    @Test
    @DisplayName("@Query를 이용한 상품 조회 테스트")
    public void findByItemDetailByNativeTest() {
        this.createItemList();
        List<Item> findItemList = itemRepository.findByItemDetailByNative("테스트 상품 상세 설명");
        findItemList.forEach(System.out::println);
    }

    @Test
    @DisplayName("Querydsl 조회 테스트")
    public void queryDslTest() {
        this.createItemList();
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QItem qItem = QItem.item;
        JPAQuery<Item> query = queryFactory
                .selectFrom(qItem)
                .where(qItem.itemSellStatus.eq(ItemSellStatus.SELL))
                .where(qItem.itemDetail.like("%" + "테스트 상품 상세 설명" + "%"))
                .orderBy(qItem.price.desc());

        List<Item> findItemList = query.fetch();
        findItemList.forEach(System.out::println);
    }

    @Test
    @DisplayName("상품 Querydsl 조회 테스트 2")
    public void queryDslTest2() {
        this.createItemList2();

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QItem qItem = QItem.item;

        String itemDetail = "테스트 상품 상세 셜명";
        int price = 10003;
        String itemSellStat = "SELL";

        booleanBuilder.and(qItem.itemDetail.like("%" + "테스트 상품 상세 설명" + "%"));
        booleanBuilder.and(qItem.price.gt(price));

        if (StringUtils.equals(itemSellStat, ItemSellStatus.SELL)) {
            booleanBuilder.and(qItem.itemSellStatus.eq(ItemSellStatus.SELL));
        }

        Pageable pageable = PageRequest.of(0, 5);
        Page<Item> itemPagingResult = itemRepository.findAll(booleanBuilder, pageable);

        System.out.println("total elements : " + itemPagingResult.getTotalElements());

        List<Item> resultItemList = itemPagingResult.getContent();
        resultItemList.forEach(System.out::println);
    }


}
