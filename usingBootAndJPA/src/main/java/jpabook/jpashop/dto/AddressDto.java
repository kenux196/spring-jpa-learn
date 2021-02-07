package jpabook.jpashop.dto;

import jpabook.jpashop.domain.Address;
import lombok.Data;

@Data
public class AddressDto {
    private String city;
    private String street;
    private String zipcode;

    public static AddressDto createAddressDto(Address address) {
        AddressDto addr = new AddressDto();
        addr.setCity(address.getCity());
        addr.setStreet(address.getStreet());
        addr.setZipcode(address.getZipcode());
        return addr;
    }
}
