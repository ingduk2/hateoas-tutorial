package practice.hateoas.tutorial.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import practice.hateoas.tutorial.Entity.ActorEntity;
import practice.hateoas.tutorial.Entity.AlbumEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Transactional
@Commit
class TutorialRepositoryTest {

    @Autowired
    ActorRepository actorRepository;
    @Autowired
    AlbumRepository albumRepository;
    @PersistenceContext
    EntityManager em;

    @Test
    public void setData() {
        AlbumEntity albumEntity = AlbumEntity.builder()
                .title("title")
                .description("desc")
                .releaseDate("release")
                .build();

        ActorEntity actorEntity = ActorEntity.builder()
                .firstName("first")
                .lastName("last")
                .birthDate("1234")
                .build();

        actorEntity.addAlbum(albumEntity);

        actorRepository.save(actorEntity);
        albumRepository.save(albumEntity);

        em.flush();
        em.clear();

        System.out.println("-=-=-=-=-=-=-=-=-=-");
        List<ActorEntity> actorEntities = actorRepository.findAll();
        System.out.println("-=-=-=-=-=-=-=-=-=-");
        for (ActorEntity actorEntity1 : actorEntities) {
            actorEntity1.getAlbums().get(0).getDescription();
        }



        System.out.println("-=-=-=-=-=-=-=-=-=-");
        List<AlbumEntity> albumEntities = albumRepository.findAll();
        System.out.println("-=-=-=-=-=-=-=-=-=-");
        for (AlbumEntity albumEntity1 : albumEntities) {
            albumEntity1.getActors().get(0).getFirstName();
        }

        assertThat(actorEntities.size()).isEqualTo(1);
        assertThat(albumEntities.size()).isEqualTo(1);
        assertThat(actorEntities.get(0).getFirstName()).isEqualTo("first");
    }


}