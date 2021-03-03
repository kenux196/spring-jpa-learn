package jpabook.jpashop.repository;

import jpabook.jpashop.domain.BoardDetail;
import jpabook.jpashop.domain.Board;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class BoardRepositoryTest {

    @Autowired
    BoardRepository boardRepository;
    @Autowired
    BoardDetailRepository boardDetailRepository;

    @Test
    public void test1() {

        Board board = new Board();
        board.setTitle("JPA OneToOne 식별관계 테스트");
        boardRepository.save(board);

        BoardDetail boardDetail = new BoardDetail();
        boardDetail.setContent("이해하기....~~~~~~~~");
        boardDetail.setBoardId(board.getBoardId());
        boardDetail.setBoard(board);
        boardDetailRepository.save(boardDetail);
    }
}