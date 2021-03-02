/**
 * Project: B.IOT Cloud
 * <p>
 * Copyright (c) 2017 DMC R&D Center, SAMSUNG ELECTRONICS Co.,LTD.
 * (Maetan dong)129, Samsung-ro Yeongtong-gu, Suwon-si. Gyeonggi-do 443-742, Korea
 * All right reserved.
 * <p>
 * This software is the confidential and proprietary information of Samsung Electronics, Inc.
 * ("Confidential Information"). You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement you entered
 * into with Samsung Electronics.
 */
package hellojpa;

import hellojpa.domain.Board;
import hellojpa.domain.BoardDetail;
import hellojpa.domain.Parent;
import hellojpa.domain.ParentId;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 * <pre>
 * 서비스 명   : hellojpa
 * 패키지 명   : hellojpa
 * 클래스 명   : OneToOneItentifyTest
 * 설명       :
 *
 * ====================================================================================
 *
 * </pre>
 * @date 2021-03-02
 * @author skyun
 * @version 1.0.0
 **/
public class OneToOneIdentifyTest {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hellojpa");

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        try {
            Board board = new Board();
            board.setTitle("제목");
            em.persist(board);

            BoardDetail boardDetail = new BoardDetail();
            boardDetail.setContent("내용");
            boardDetail.setBoard(board);
            em.persist(boardDetail);

            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}