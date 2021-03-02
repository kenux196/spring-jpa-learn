package hellojpa.domain;

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
public class Child {

    @Id
    private String id;

    private String name;

    // 부모 테이블의 기본 키 컬럼이 복합 키이므로, 자식 테이블의 외래 키도 복합 키이다.
    // 따라서, 외래 키 매핑 시 여러 컬럼을 매핑해야 하므로, @JoinColumns 사용
    // 각각의 외래 키 컬럼을 @JoinColumn 으로 매핑한다.
    // @JoinColumn 의 name 속성가 referencedColumnName 속성의 값이 같으면 referencedColumnName 생략 가능
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "parent_id1", referencedColumnName = "parent_id1"),
            @JoinColumn(name = "parent_id2", referencedColumnName = "parent_id2")
    })
    private Parent parent;
}