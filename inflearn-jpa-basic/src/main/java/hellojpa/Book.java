package hellojpa;

import javax.persistence.Entity;

@Entity
public class Book extends Item {

//    private Long id;

    private String author;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
