package jpabook.jpashop.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PatchMemberResponse {
    private Long id;
    private String name;
//    private Address address;
}
