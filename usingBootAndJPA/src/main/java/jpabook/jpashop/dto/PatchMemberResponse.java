package jpabook.jpashop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jpabook.jpashop.domain.Member;
import lombok.Data;

@Data
public class PatchMemberResponse {
    private Long id;
    private String name;
    @JsonProperty(value = "address")
    private AddressDto addressDto;

    public static PatchMemberResponse create(Member member) {
        PatchMemberResponse response = new PatchMemberResponse();
        response.setId(member.getId());
        response.setName(member.getName());
        response.setAddressDto(AddressDto.createAddressDto(member.getAddress()));
        return response;
    }
}
