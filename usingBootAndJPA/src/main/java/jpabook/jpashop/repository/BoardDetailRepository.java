package jpabook.jpashop.repository;

import jpabook.jpashop.domain.BoardDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class BoardDetailRepository {

    private final EntityManager em;

    public void save(BoardDetail boardDetail) {
        em.persist(boardDetail);
    }
}
