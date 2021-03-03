package hellojpa.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * <pre>
 * 서비스 명   : hellojpa
 * 패키지 명   : hellojpa.domain
 * 클래스 명   : Board2
 * 설명       : @SecondaryTable 예제 : 한 엔티티에 여러 테이블을 매핑할 수 있다.
 *
 * ====================================================================================
 *
 * </pre>
 *
 * @author skyun
 * @version 1.0.0
 * @date 2021-03-03
 **/
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "board2")
@SecondaryTable(name = "board_detail2",
        pkJoinColumns = @PrimaryKeyJoinColumn(name = "board_detail_id")
)
public class Board2 {

    @Id
    @GeneratedValue
    @Column(name = "board_id")
    private Long id;

    private String title;

    @Column(table = "board_detail2")
    private String content;
}