package hellojpa.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

/**
 * <pre>
 * 서비스 명   : hellojpa
 * 패키지 명   : hellojpa.domain
 * 클래스 명   : GrandChildId
 * 설명       : 손자 ID
 *
 * ====================================================================================
 *
 * </pre>
 * @date 2021-03-02
 * @author skyun
 * @version 1.0.0
 **/

@Embeddable
public class GrandChildId implements Serializable {

//    private ChildId child; // GrandChild.child 매핑
//    private String id; // GrandChild.id 매핑

    private ChildId childId; // @MapsId("childId")로 매핑

    @Column(name = "grand_child_id")
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