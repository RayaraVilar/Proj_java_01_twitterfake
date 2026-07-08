package br.com.twitterfake.controller;

import br.com.twitterfake.dto.CriarPostRequest;
import br.com.twitterfake.model.Post;
import br.com.twitterfake.service.PostService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Post criarPost(@Valid @RequestBody CriarPostRequest request) {
        return postService.criarPost(request);
    }

    @GetMapping
    public List<Post> listarPosts() {
        return postService.listarPosts();
    }

    @GetMapping("/{id}")
    public Post buscarPostPorId(@PathVariable Long id) {
        return postService.buscarPorId(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarPost(@PathVariable Long id) {
        postService.deletarPost(id);
    }

    @PutMapping("/{id}")
    public Post atualizarPost(@PathVariable Long id, @Valid @RequestBody CriarPostRequest request) {
    return postService.atualizarPost(id, request);
    }
}