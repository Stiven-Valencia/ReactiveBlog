package SEGUIMIENTO.SEGUIMIENTO;

import SEGUIMIENTO.SEGUIMIENTO.model.Post;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

@SpringBootTest
@AutoConfigureWebTestClient
public class PostControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void testGetAllPostsReactive() {
        webTestClient.get()
                .uri("/api/posts")
                .header("Authorization", "Basic dXNlcjpwYXNzd29yZA==")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Post.class)
                .hasSize(2) // Verifica que devuelva 2 posts (según schema.sql)
                .consumeWith(response -> {
                    List<Post> posts = response.getResponseBody();
                    assert posts != null;
                    assert posts.get(0).getTitle().equals("Primer Post");
                });
    }
}