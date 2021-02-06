package jpabook.jpashop.domain;

import jpabook.jpashop.domain.item.Item;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "order_item")
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {

    @Id
    @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item; // 주문상품

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order; // 주문

    private int orderPrice; // 주문 가격 -> 아이템에 가격이 있지만, 할인 등의 행사가 적용을 위해서 따로 주문 시 가격 추가함.
    private int count; // 주문 수량

    // 생성 메서드
    public static OrderItem createOrderItem(Item item, int orderPrice, int count) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        item.removeStock(count); // 아이템 재고 수량을 주문 수량만큼 뺀다.
        return orderItem;
    }

    // 비지니스 로직
    /**
     * 주문 취소 처리
     */
    public void cancel() {
        getItem().addStock(getCount()); // 아이템 재고 수량을 주문 취소 수량만큼 복구한다.
    }

    // 조회 로직
    /**
     * 주문 상품 전체 가격 조회
     * @return 상품 가격 * 주문 수량
     */
    public int getTotalPrice() {
        return getOrderPrice() * getCount();
    }
}
