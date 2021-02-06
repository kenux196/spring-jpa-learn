package jpabook.jpashop.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 값 타입은 변경이 불가능해야 한다. 따라서 protected로 생성 제약.
@AllArgsConstructor
public class Address {

    private String city;
    private String street;
    private String zipcode;
}