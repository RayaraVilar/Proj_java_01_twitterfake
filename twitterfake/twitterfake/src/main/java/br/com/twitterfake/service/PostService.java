package br.com.twitterfake.service;

import br.com.twitterfake.dto.CriarPostRequest;
import br.com.twitterfake.model.Post;
import br.com.twitterfake.repository.PostRepository;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Post criarPost(CriarPostRequest request) {
        Post post = new Post(request.autor(), request.conteudo());
        return postRepository.save(post);
    }

    public List<Post> listarPosts() {
        return postRepository.findAll(Sort.by(Sort.Direction.DESC, "criadoEm"));
    }

    public Post buscarPorId(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post não encontrado"));
    }

    public void deletarPost(Long id) {
        if (!postRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post não encontrado");
        }

        postRepository.deleteById(id);
    }

    public Post atualizarPost(Long id, CriarPostRequest request) {
    Post post = buscarPorId(id);

    post.setAutor(request.autor());
    post.setConteudo(request.conteudo());

    return postRepository.save(post);
    }
}