package jpabook.jpashop.domain.item;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "A")
public class Album extends Item {
    private String artist;
    private String etc;
}
