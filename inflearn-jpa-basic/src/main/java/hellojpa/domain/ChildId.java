package hellojpa.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * <pre>
 * 서비스 명   : hellojpa
 * 패키지 명   : hellojpa.domain
 * 클래스 명   : ChildId
 * 설명       : 자식 ID
 *
 * ====================================================================================
 *
 * </pre>
 * @date 2021-03-02
 * @author skyun
 * @version 1.0.0
 **/

@Embeddable
public class ChildId implements Serializable {

//    private String parent; // Child.parent 매핑
//    private String childId; // Child.childId 매핑

    private String parentId; // @MapsId("parent") 로 매핑

    @Column(name = "child_id")
    private String id;

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}