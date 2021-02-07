package jpabook.jpashop.dto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class PatchMemberRequest {
    private String name;
    private Address address;

    @AllArgsConstructor
    static class Address {
        private String city;
        private String street;
        private String zipcode;
    }
}
