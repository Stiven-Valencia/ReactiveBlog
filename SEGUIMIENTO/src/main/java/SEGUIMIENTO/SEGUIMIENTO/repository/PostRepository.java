package SEGUIMIENTO.SEGUIMIENTO.repository;//package SEGUIMIENTO.SEGUIMIENTO.post;

import SEGUIMIENTO.SEGUIMIENTO.model.Post;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

public interface PostRepository extends R2dbcRepository<Post, Long> {
    // consulta personalizada reactiva (opcional)
    Flux<Post> findByAuthor(String author);
}