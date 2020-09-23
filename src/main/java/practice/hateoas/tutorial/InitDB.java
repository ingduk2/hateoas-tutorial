package practice.hateoas.tutorial;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import practice.hateoas.tutorial.Entity.ActorEntity;
import practice.hateoas.tutorial.Entity.AlbumEntity;
import practice.hateoas.tutorial.repository.ActorRepository;
import practice.hateoas.tutorial.repository.AlbumRepository;

import javax.annotation.PostConstruct;

@Profile("local")
@Component
@RequiredArgsConstructor
public class InitDB {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.initData();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService{
        private final AlbumRepository albumRepository;
        private final ActorRepository actorRepository;

        public void initData() {

            ActorEntity actorEntity1 = ActorEntity.builder()
                    .firstName("first1")
                    .lastName("last1")
                    .birthDate("birth1")
                    .build();

            for(int i=1; i<=100; i++){
                AlbumEntity albumEntity1 = AlbumEntity.builder()
                        .title("title" +i)
                        .description("desc" +i)
                        .releaseDate("release" +i)
                        .build();
                actorEntity1.addAlbum(albumEntity1);
            }

            //Cascade
            actorRepository.save(actorEntity1);

//            AlbumEntity albumEntity1 = AlbumEntity.builder()
//                    .title("title1")
//                    .description("desc1")
//                    .releaseDate("release1")
//                    .build();
//
//
//            AlbumEntity albumEntity2 = AlbumEntity.builder()
//                    .title("title2")
//                    .description("desc2")
//                    .releaseDate("release2")
//                    .build();


//            actorEntity1.addAlbum(albumEntity1);
//            actorEntity1.addAlbum(albumEntity2);

//            albumRepository.save(albumEntity1);
//            albumRepository.save(albumEntity2);
        }
    }

}
