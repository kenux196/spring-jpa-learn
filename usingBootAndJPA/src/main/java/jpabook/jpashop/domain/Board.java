package jpabook.jpashop.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "board")
public class Board {

    @Id
    @GeneratedValue
    @Column(name = "board_id")
    private Long boardId;

    private String title;

    @OneToOne(mappedBy = "board")
    private BoardDetail boardDetail;
}
