package pers.project.domain;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
@Table(name = "book_info")
public class BookDO implements Serializable {
    private final static long serialVersionUID = 1L;
    @Id
    @GeneratedValue(generator = "jpa-uuid")
    @Column(length = 32)
    String id;
    @Column(name = "book_name", length = 32, nullable = false)
    String name;
    @Column(name = "book_author", length = 32)
    String author;
}
