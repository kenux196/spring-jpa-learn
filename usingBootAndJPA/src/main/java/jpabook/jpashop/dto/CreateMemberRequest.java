package jpabook.jpashop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Getter
@NoArgsConstructor // Serializable 시 필수.
@AllArgsConstructor
public class CreateMemberRequest implements Serializable {
    @NotEmpty
    private String name;

    @NotEmpty
    private String city;

    @NotEmpty
    private String street;

    @NotEmpty
    private String zipcode;
}
