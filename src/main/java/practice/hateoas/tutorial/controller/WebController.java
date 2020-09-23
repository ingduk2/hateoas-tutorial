package practice.hateoas.tutorial.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import practice.hateoas.tutorial.Entity.ActorEntity;
import practice.hateoas.tutorial.Entity.AlbumEntity;
import practice.hateoas.tutorial.dto.ActorModel;
import practice.hateoas.tutorial.dto.AlbumModel;
import practice.hateoas.tutorial.dto.assembler.ActorModelAssembler;
import practice.hateoas.tutorial.dto.assembler.AlbumModelAssembler;
import practice.hateoas.tutorial.repository.ActorRepository;
import practice.hateoas.tutorial.repository.AlbumRepository;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Slf4j
@RestController
@RequiredArgsConstructor
public class WebController {

    private final AlbumRepository albumRepository;
    private final ActorRepository actorRepository;
    private final ActorModelAssembler actorModelAssembler;
    private final AlbumModelAssembler albumModelAssembler;
    private final PagedResourcesAssembler<AlbumEntity> albumEntityPagedResourcesAssembler;
    private final PagedResourcesAssembler<ActorEntity> actorEntityPagedResourcesAssembler;

    @GetMapping("/")
    public ResponseEntity<EntityModel<String>> main() {
        EntityModel<String> entityModel = EntityModel.of("main");
        entityModel.add(linkTo(WebController.class).withSelfRel());
        entityModel.add(linkTo(methodOn(WebController.class).getAllActors()).withRel("actors"));
        entityModel.add(linkTo(methodOn(WebController.class).getAllAlbums()).withRel("albums"));
        return new ResponseEntity<>(entityModel , HttpStatus.OK);
    }

    @GetMapping("/api/actors")
    public ResponseEntity<CollectionModel<ActorModel>> getAllActors() {
        List<ActorEntity> actorEntities = actorRepository.findAll();
        log.info("===================");
        return new ResponseEntity<>(actorModelAssembler.toCollectionModel(actorEntities),
                HttpStatus.OK);
    }

    @GetMapping("/api/actors/{id}")
    public ResponseEntity<ActorModel> getActorById(@PathVariable("id") Long id) {
        return actorRepository.findById(id)
                .map(actorModelAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @GetMapping("/api/albums")
    public ResponseEntity<CollectionModel<AlbumModel>> getAllAlbums() {
        List<AlbumEntity> albumEntities = albumRepository.findAll();
        log.info("===================");
        return new ResponseEntity<>(
                albumModelAssembler.toCollectionModel(albumEntities),
                HttpStatus.OK
        );
    }

    @GetMapping("/api/albums/{id}")
    public ResponseEntity<AlbumModel> getAlbumById(@PathVariable("id") Long id) {
        return albumRepository.findById(id)
                .map(albumModelAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/api/albums-list")
    public ResponseEntity<PagedModel<AlbumModel>> getAllAlbums(Pageable pageable) {
        Page<AlbumEntity> albumEntities = albumRepository.findAll(pageable);
        PagedModel<AlbumModel> collModel = albumEntityPagedResourcesAssembler
                .toModel(albumEntities, albumModelAssembler);

        return new ResponseEntity<>(collModel, HttpStatus.OK);
    }


}
