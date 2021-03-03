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
 * 클래스 명   : ChildT
 * 설명       :
 *
 * ====================================================================================
 *
 * </pre>
 * @date 2021-03-03
 * @author skyun
 * @version 1.0.0
 **/

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "child_t")
public class ChildT {

    @Id
    @GeneratedValue
    @Column(name = "child_id")
    private Long id;

    private String name;

//    // 일대일 조인 테이블 양방향
//    @OneToOne(mappedBy = "child")
//    private ParentT parent;

//    // 다대일 조인 테이블 양방향
//    @ManyToOne(optional = false)
//    @JoinTable(name = "parent_child_t",
//            joinColumns = @JoinColumn(name = "child_id"),
//            inverseJoinColumns = @JoinColumn(name = "parent_id")
//    )
//    private ParentT parent;

}