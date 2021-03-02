package hellojpa.domain;

/**
 * <pre>
 * 서비스 명   : hellojpa
 * 패키지 명   : hellojpa.domain
 * 클래스 명   : Board
 * 설명       : 일대일 식별관계 예제 클래스
 *
 * ====================================================================================
 *
 * </pre>
 * @date 2021-03-02
 * @author skyun
 * @version 1.0.0
 **/

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * 일대일 식별 관계는 조금 특별하다.
 * 일대일 식별 관계는 자식 테이블의 기본 키 값으로 부모 테이블의 기본 키 값만 사용.
 * 따라서, 부모 테이블의 기본 키가 복합 키가 아니면 자식 테이블의 기본 키는 복합키로 구성하지 않아도 된다. **
 */

// 부모

@Setter
@Getter
@NoArgsConstructor
@Entity
public class Board {
    @Id
    @GeneratedValue
    @Column(name = "board_id")
    private Long id;

    private String title;

    @OneToOne(mappedBy = "board")
    private BoardDetail boardDetail;
}