package pers.project.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
@Table(name = "book_info")
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//@NoArgsConstructor
public class BookDO implements Serializable {
    private final static long serialVersionUID = 1L;
    @Id
    @GeneratedValue(generator = "jpa-uuid")
    @Column(length = 32)
    String id;
    @Column(name = "book_name", length = 32)
    String name;
    @Column(name = "book_author", length = 32)
    String author;
}
