package hellojpa.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
* <pre>
* 서비스 명   : hellojpa
* 패키지 명   : hellojpa.domain
* 클래스 명   : IdentifyParent
* 설명       : 부모
*
* ====================================================================================
*
* </pre>
* @date        2021-03-02
* @author      skyun
* @version     1.0.0
**/

@Getter
@Setter
@NoArgsConstructor
@Entity
public class IdentifyParent {

    @Id
    @Column(name = "parent_id")
    private String id;

    private String name;

}