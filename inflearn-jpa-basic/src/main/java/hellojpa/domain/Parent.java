package hellojpa.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * 복합 키: 비식별 관계 매핑
 * 1. @IdClass 사용하는 방법 : 데이터베이스 중심
 * 2. @EmbeddedId 사용하는 방법 : 조금 더 객체지향적인 방법
 */

@Getter
@Setter
@NoArgsConstructor
@Entity
//@IdClass(ParentId.class)
public class Parent {

//    // @IdClass 사용 방법
//    @Id
//    @Column(name = "parent_id1")
//    private String id1;
//
//    @Id
//    @Column(name = "parent_id2")
//    private String id2; // 실행 시점에 매핑 예외 발생 -> @IdClass 사용하여 처리

    // @EmbeddedId 사용 방법
    @EmbeddedId
    private ParentId id;

    private String name;
}