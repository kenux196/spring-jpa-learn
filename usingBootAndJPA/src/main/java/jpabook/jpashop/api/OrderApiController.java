package jpabook.jpashop.api;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.order.query.OrderQueryDto;
import jpabook.jpashop.repository.order.query.OrderQueryRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderRepository orderRepository;
    private final OrderQueryRepository orderQueryRepository;

    /**
     * V1. 엔티티 직접 노출
     * 양방향 연관관계에 무한루프 걸리지 않게 한곳에 @JsonIgnore 추가해야 함.
     * 엔티티 직접 노출은 좋지 않다.
     */
    @GetMapping("/api/v1/orders")
    public List<Order> ordersV1() {
        List<Order> orders = orderRepository.findAll();
        // 프록시 객체 강제로 터치해서 영속 컨텍스트에 로딩해 두기. 즉 Lazy 강제 초기화
        for (Order order : orders) {
            order.getMember().getName();
            order.getDelivery().getAddress();
            List<OrderItem> orderItems = order.getOrderItems();
            orderItems.stream().forEach(orderItem -> orderItem.getItem().getName());
        }
        return orders;
    }

    /**
     * V2. Entity -> DTO
     * Lazy Loading 으로 인해 많은 쿼리가 날아간다.
     * DTO 내부에도 DTO 로 구성. DTO 가 엔티티 가지고 돌아다니면 안된단.
     */
    @GetMapping("/api/v2/orders")
    public List<OrderDto> ordersV2() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream().map(OrderDto::new).collect(toList());
    }

    /**
     * V3. Entity -> DTO + Fetch Join 최적화
     * 쿼리는 1회로 모든 데이터를 조회 가능.
     * 그러나, XToMany 연관 관계를 fetch join 하게 되어서, 결과 데이터는 뻥튀기 되어서 나옴.
     * 결과적으로 페이징 처리를 할 수 없게 된다.
     * XToOne 관계의 경우에는 페치 조인을 해도 결과가 부풀려지지 않는데,
     * ToMany 관계인 경우는 결과 뻥튀기 됨. 따라서, 페이징 처리를 위해서는 또 다른 방법이 필요하다.
     * 페이징을 하지 않는 경우라면, 페치 조인을 해도 되지만, 그렇다 해도 데이터 양이 많은 경우라면,
     * 다음 단계의 페치징 처리 방법을 이용하면 도움이 된다.
     */
    @GetMapping("/api/v3/orders")
    public List<OrderDto> ordersV3() {
        List<Order> orders = orderRepository.findAllWithItem();
        return orders.stream().map(OrderDto::new).collect(toList());
    }

    /**
     * V3.1 Entity -> DTO + Fetch Join + Paging
     * ToOne 관계만 우선 페치 조인으로 최적화
     * 컬렉션(ToMany) 관계는 hibernate.default_batch_fetch_size, @BatchSize 로 최적화
     */
    @GetMapping("/api/v3.1/orders")
    public List<OrderDto> ordersV3_page(@RequestParam(value = "offset", defaultValue = "0") int offset,
                                        @RequestParam(value = "limit", defaultValue = "100") int limit) {
        List<Order> orders = orderRepository.findAllWithMemberDelivery(offset, limit);
        return orders.stream().map(OrderDto::new).collect(toList());
    }

    /**
     * V4. JPA에서 DTO 직접 조회
     *
     */
    @GetMapping("/api/v4/orders")
    public List<OrderQueryDto> ordersV4() {
        return orderQueryRepository.findOrderQueryDtos();
    }


    @Data
    static class OrderDto {
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;
        private List<OrderItemDto> orderItems;

        public OrderDto(Order order) {
            orderId = order.getId();
            name = order.getMember().getName();
            orderDate = order.getOrderDate();
            orderStatus = order.getStatus();
            address = order.getDelivery().getAddress();
            orderItems = order.getOrderItems().stream()
                    .map(OrderItemDto::new)
                    .collect(toList());
        }
    }

    @Data
    static class OrderItemDto {

        private String itemName; // 상품명
        private int orderPrice; // 주문 가격
        private int count; // 주문 수량

        public OrderItemDto(OrderItem orderItem) {
            itemName = orderItem.getItem().getName();
            orderPrice = orderItem.getOrderPrice();
            count = orderItem.getCount();
        }
    }


}
