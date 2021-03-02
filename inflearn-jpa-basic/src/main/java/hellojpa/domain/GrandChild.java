package hellojpa.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * <pre>
 * 서비스 명   : hellojpa
 * 패키지 명   : hellojpa.domain
 * 클래스 명   : GrandChild
 * 설명       : 손자
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
//@IdClass(GrandChildId.class)
public class GrandChild {

    // 식별 관계는 기본 키와 외래 키를 같이 매핑해야 한다.
    // 따라서, @Id, @ManyToOne 을 같이 사용.
    // @Id : 기본키 매핑하면서, @ManyToOne 과 @JoinColumn 으로 외래 키를 같이 매핑
//    @Id
//    @ManyToOne
//    @JoinColumns({
//            @JoinColumn(name = "parent_id"),
//            @JoinColumn(name = "child_id")
//    })
//    private Child child;
//
//    @Id
//    @Column(name = "grand_child_id")
//    private String id;

    @EmbeddedId
    private GrandChildId id;

    @MapsId("childId") // GrandChildId.childId 매핑
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "parent_id"),
            @JoinColumn(name = "child_id")
    })
    private IdentifyChild child;


    private String name;
}