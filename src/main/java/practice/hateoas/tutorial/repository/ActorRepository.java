package practice.hateoas.tutorial.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import practice.hateoas.tutorial.Entity.ActorEntity;

public interface ActorRepository extends JpaRepository<ActorEntity, Long> {
}
