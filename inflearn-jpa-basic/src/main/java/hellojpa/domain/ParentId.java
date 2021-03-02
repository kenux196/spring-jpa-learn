package hellojpa.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * 식별자 클래스
 *
 * @IdClass를 사용할 때, 식별자 클래스는 다음 조건을 만조해야 한다.
 *
 * 1. 식별자 클래스의 속성명과 엔티티에서 사용하는 식별자의 속성명이 같아야 한다.
 * 2. Serializable 인터페이스를 구현해야 한다.
 * 3. equals, hashCode 를 구현해야 한다.
 * 4. 기본 생성자가 있어야 한다.
 * 5. 식별자 클래스는 public 이어야 한다.
 */

/**
 * 식별자 클래스
 *
 * @EmbddedId 를 사용할 때, 식별자 클래스는 다음 조건을 만조해야 한다.
 *
 * 1. @Embeddable 어노테이션을 붙여야 한다.
 * 2. Serializable 인터페이스를 구현해야 한다.
 * 3. equals, hashCode 구현
 * 4. 기본 생성자 있어야 함.
 * 5. 식별자 클래스는 public
 */

/**
 * 복합키에는 @GenerateValue 사용 불가.
 */

@Embeddable
public class ParentId implements Serializable {

    @Column(name = "parent_id1")
    private String id1; // Parent.id1 매핑
    @Column(name = "parent_id2")
    private String id2; // Parent.id2 매핑

    public ParentId() {

    }

    public ParentId(String id1, String id2) {
        this.id1 = id1;
        this.id2 = id2;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}