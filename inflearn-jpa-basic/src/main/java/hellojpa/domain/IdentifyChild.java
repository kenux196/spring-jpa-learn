package hellojpa.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
* <pre>
* 서비스 명   : hellojpa
* 패키지 명   : hellojpa.domain
* 클래스 명   : IdentifyChild
* 설명       : 자식
*
* ====================================================================================
*
* </pre>
* @date        2021-03-02
* @author      skyun
* @version     1.0.0
**/

@Setter
@Getter
@NoArgsConstructor
@Entity
//@IdClass(ChildId.class)
public class IdentifyChild {

//    @Id
//    @ManyToOne
//    @JoinColumn(name = "parent_id")
//    public IdentifyParent parent;

//    @Id
//    @Column(name = "child_id")
//    private String childId;

    @EmbeddedId
    private ChildId id;

    /**
     * @MapsId : 외래 키와 매핑한 연관관게를 기본 키에도 매핑하겠다는 뜻.
     * 속성 값은 @EmbeddedId를 사용한 식별자 클래스의 기본 키 필드를 지정
     */
    @MapsId("parentId") // ChildId.parentId 매핑
    @ManyToOne
    @JoinColumn(name = "parent_id")
    public IdentifyParent parent;

    private String name;
}