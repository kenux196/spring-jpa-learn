package jpabook.jpashop.service;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.OrderRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderServiceTest {

    @Autowired EntityManager em;
    @Autowired OrderService orderService;
    @Autowired OrderRepository orderRepository;

    @Test
    public void 상품주문_테스트() throws Exception {
        // given
        Member member = createMember();
        Item item = createBook("JPA Book", 30000, 10);
        int count = 2;

        // when
        Long orderId = orderService.order(member.getId(), item.getId(), 2);

        // then
        Order getOrder = orderRepository.findOne(orderId);
        assertEquals("상품 주문시 상태는 ORDER", OrderStatus.ORDER, getOrder.getStatus());
        assertEquals("주문한 상품 종류 수가 정확해야 한다.", 1, getOrder.getOrderItems().size());
        assertEquals("주문 가격은 가격 * 수량이다.", count * item.getPrice(), getOrder.getTotalPrice());
        assertEquals("주문한 수량만큼 재고가 줄어야 한다.", 8, item.getStockQuantity());
    }

    @Test(expected = NotEnoughStockException.class)
    public void 재고수량초과_예외_테스트() throws Exception {

        // given
        Member member = createMember();
        Item item = createBook("JPA BOOk", 10000, 10);
        int orderCount = 11; // 재고보다 많은 수량

        // when
        orderService.order(member.getId(), item.getId(), orderCount);

        // then
        fail("재고 수량 부족 예외가 발생해야 한다.");

    }

    @Test
    public void 상품취소_테스트() throws Exception {

        // given
        Member member = createMember();
        Item item = createBook("JPA BOOK", 10000, 10);
        int orderCount = 2;
        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        // when
        orderService.cancel(orderId);

        // then
        Order order = orderRepository.findOne(orderId);

        assertEquals("주문 취소시 상태는 CANCEL 이다", OrderStatus.CANCEL, order.getStatus());
        assertEquals("주문 취소된 상품은 재고가 원복되어야 한다.", 10, item.getStockQuantity());

    }

    private Book createBook(String name, int price, int stockQuantity) {
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        em.persist(book);
        return book;
    }

    private Member createMember() {
        Member member = new Member();
        member.setName("kim");
        member.setAddress(new Address("대구", "대실역남로 35", "12345"));
        em.persist(member);
        return member;
    }

}