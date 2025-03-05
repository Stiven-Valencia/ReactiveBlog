package SEGUIMIENTO.SEGUIMIENTO.controller;

import SEGUIMIENTO.SEGUIMIENTO.model.Post;

import SEGUIMIENTO.SEGUIMIENTO.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Locale;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostRepository postRepository;
    private final MessageSource messageSource;

    @Autowired
    public PostController(PostRepository postRepository, MessageSource messageSource) {
        this.postRepository = postRepository;
        this.messageSource = messageSource;
    }

    // Endpoint existente: Listar todos los posts
    @GetMapping
    public Flux<Post> getAllPosts() {
        return postRepository.findAll();
    }

    // Endpoint existente: Crear un post
    @PostMapping
    public Mono<ResponseEntity<String>> createPost(@RequestBody Post post, Locale locale) {
        return postRepository.save(post)
                .map(savedPost -> ResponseEntity.ok(
                        messageSource.getMessage("post.created", null, locale)
                ));
    }

    // Endpoint existente: Saludo público
    @GetMapping("/public/welcome")
    public Mono<String> welcome(@RequestParam String name, Locale locale) {
        return Mono.just(
                messageSource.getMessage("greeting", new Object[]{name}, locale)
        );
    }

    //  Obtener un post por ID
    @GetMapping("/{id}")
    public Mono<ResponseEntity<Post>> getPostById(@PathVariable Long id) {
        return postRepository.findById(id)
                .map(post -> ResponseEntity.ok(post))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    //  Actualizar un post
    @PutMapping("/{id}")
    public Mono<ResponseEntity<String>> updatePost(@PathVariable Long id, @RequestBody Post updatedPost, Locale locale) {
        return postRepository.findById(id)
                .flatMap(existingPost -> {
                    existingPost.setTitle(updatedPost.getTitle());
                    existingPost.setContent(updatedPost.getContent());
                    existingPost.setAuthor(updatedPost.getAuthor());
                    return postRepository.save(existingPost);
                })
                .map(savedPost -> ResponseEntity.ok(
                        messageSource.getMessage("post.updated", null, "Post updated successfully", locale)
                ))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    //  Eliminar un post
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<String>> deletePost(@PathVariable Long id, Locale locale) {
        return postRepository.findById(id)
                .flatMap(post -> postRepository.delete(post)
                        .then(Mono.just(ResponseEntity.ok(
                                messageSource.getMessage("post.deleted", null, "Post deleted successfully", locale)
                        ))))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}