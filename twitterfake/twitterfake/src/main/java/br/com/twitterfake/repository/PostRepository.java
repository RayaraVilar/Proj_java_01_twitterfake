package br.com.twitterfake.repository;

import br.com.twitterfake.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}