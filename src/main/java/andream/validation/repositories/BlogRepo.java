package andream.validation.repositories;

import andream.validation.entities.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BlogRepo extends JpaRepository<Blog, UUID> {
}

