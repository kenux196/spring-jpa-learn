package hellojpa.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * 서비스 명   : hellojpa
 * 패키지 명   : hellojpa.domain
 * 클래스 명   : ParentT
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
@Entity
@Table(name = "parent_t")
public class ParentT {

    @Id
    @GeneratedValue
    @Column(name = "parent_id")
    private Long id;
    private String name;

//    // 일대일 조인 테이블
//    @OneToOne
//    @JoinTable(name = "parent_child_t", // 매핑할 테이블 이름
//            joinColumns = @JoinColumn(name = "parent_id"), // 현재 엔티티를 참조하는 외래 키
//            inverseJoinColumns = @JoinColumn(name = "child_id") // 반대방향 엔티티를 참조하는 외래 키
//    )
//    private ChildT child;


//    // 일대다 조인 테이블
//    @OneToMany
//    @JoinTable(name = "parent_child_t",
//            joinColumns = @JoinColumn(name = "parent_id"), // 현재 엔티티를 참조하는 외래 키
//            inverseJoinColumns = @JoinColumn(name = "child_id") // 반대방향 엔티티를 참조하는 외래 키
//    )
//    private List<ChildT> child = new ArrayList<>();

//    // 다대일 조인 테이블
//    @OneToMany(mappedBy = "parent")
//    private List<ChildT> child = new ArrayList<>();

    // 다대다 조인 테이블
    @ManyToMany
    @JoinTable(name = "parent_child_t",
            joinColumns = @JoinColumn(name = "parent_id"),
            inverseJoinColumns = @JoinColumn(name = "child_id")
    )
    private List<ChildT> child = new ArrayList<>();
}