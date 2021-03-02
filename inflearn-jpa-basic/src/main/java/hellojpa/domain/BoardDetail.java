package hellojpa.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * <pre>
 * 서비스 명   : hellojpa
 * 패키지 명   : hellojpa.domain
 * 클래스 명   : BoardDetail
 * 설명       : 일대일 식별 관계 자식 엔티티
 *
 * ====================================================================================
 *
 * </pre>
 * @date 2021-03-02
 * @author skyun
 * @version 1.0.0
 **/

@Setter
@Getter
@NoArgsConstructor
@Entity
public class BoardDetail {

    @Id
    private Long boardId;

    @MapsId // BoardDetail.boardId 매핑
    @OneToOne
    @JoinColumn(name = "board_id")
    private Board board;

    private String content;
}