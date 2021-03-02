
package hellojpa.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Hello {

    @Id
    private String id;
}