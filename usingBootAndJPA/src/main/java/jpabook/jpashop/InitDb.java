package jpabook.jpashop;

import jpabook.jpashop.domain.*;
import jpabook.jpashop.domain.item.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit1();
        initService.dbInit2();
    }


    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {
        private final EntityManager em;

        public void dbInit1() {
            Member member = createMember("kenux", "대구", "대실역남로", "12345");
            em.persist(member);

            Book book1 = createBook("SpringBoot 2", 10000, 100);
            em.persist(book1);

            Book book2 = createBook("JPA 2", 13000, 300);
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 10000, 10);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 13000, 10);
            Order order = Order.createOrder(member, createDelivery(member), orderItem1, orderItem2);
            em.persist(order);
        }

        public void dbInit2() {
            Member member = createMember("dragon", "서울", "세종대로", "39392");
            em.persist(member);

            Book book1 = createBook("Spring Book 1", 20000, 30);
            em.persist(book1);
            Book book2 = createBook("Spring book 2", 24000, 50);
            em.persist(book2);

            Delivery delivery = createDelivery(member);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 20000, 3);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 24000, 4);
            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
            em.persist(order);
        }

        private Member createMember(String dragon, String city, String street, String zipcode) {
            Member member = new Member();
            member.setName(dragon);
            member.setAddress(new Address(city, street, zipcode));
            return member;
        }

        private Book createBook(String s, int price, int stockQuantity) {
            Book book1 = new Book();
            book1.setName(s);
            book1.setPrice(price);
            book1.setStockQuantity(stockQuantity);
            return book1;
        }

        private Delivery createDelivery(Member member) {
            Delivery delivery = new Delivery();
            delivery.setAddress(member.getAddress());
            return delivery;
        }
    }
}
