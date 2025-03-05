package SEGUIMIENTO.SEGUIMIENTO.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("posts")
public class Post {
    @Id
    private Long id;
    private String title;
    private String content;
    private String author;
}