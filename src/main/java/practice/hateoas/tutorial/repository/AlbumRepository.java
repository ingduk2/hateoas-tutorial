package practice.hateoas.tutorial.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import practice.hateoas.tutorial.Entity.AlbumEntity;

public interface AlbumRepository extends JpaRepository<AlbumEntity, Long> {
}
