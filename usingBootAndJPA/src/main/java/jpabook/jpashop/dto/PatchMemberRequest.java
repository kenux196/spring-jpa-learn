package jpabook.jpashop.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PatchMemberRequest {
    private String name;

    @JsonProperty(value = "address")
    private AddressDto addressDto;
}
