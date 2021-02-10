package jpabook.jpashop.repository.order.query;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderQueryRepository {

    private final EntityManager em;


    /**
     * 컬렉션은 별도로 조회
     * Query: 1번, 컬렉션 N번
     * 단건 조회에서 많이 사용하는 방식 ****
     */
    public List<OrderQueryDto> findOrderQueryDtos() {
        // 루트 조회(ToOne 관계를 모두 한번에 조회)
        List<OrderQueryDto> result = findOrders();

        // 루프를 돌면서 컬렉션 추가 (추가 쿼리 설정)
        result.forEach(order -> {
            List<OrderItemQueryDto> orderItems = findOrderItems(order.getOrderId());
            order.setOrderItems(orderItems);
        });

        return result;
    }

    /**
     * 1:N 관계(컬렉션)을 제외환 나머지를 한번에 조회
     */
    private List<OrderQueryDto> findOrders() {
        return em.createQuery(
                "select new jpabook.jpashop.repository.order.query.OrderQueryDto(o.id, m.name, o.orderDate, o.status, d.address)" +
                        " from Order o " +
                        " join o.member m " +
                        " join o.delivery d", OrderQueryDto.class)
                .getResultList();
    }

    /**
     * 1:N 관계인 OrderItems 조회
     */
    private List<OrderItemQueryDto> findOrderItems(Long orderId) {
        return em.createQuery(
                "select new jpabook.jpashop.repository.order.query.OrderItemQueryDto(oi.order.id, i.name, oi.orderPrice, oi.count)" +
                        " from OrderItem oi " +
                        " join oi.item i " +
                        " where oi.order.id = :orderId", OrderItemQueryDto.class)
                .setParameter("orderId", orderId)
                .getResultList();
    }
}
