package practice.hateoas.tutorial.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;
import practice.hateoas.tutorial.Entity.ActorEntity;
import practice.hateoas.tutorial.Entity.AlbumEntity;
import practice.hateoas.tutorial.controller.WebController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
//collection embeded name
@Relation(collectionRelation = "actors", itemRelation = "actor")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ActorModel extends RepresentationModel<ActorModel> {
    private Long id;
    private String firstName;
    private String lastName;
    private String birthDate;

    private List<AlbumModel> albums;
//    private List<String> actorStrings = new ArrayList<>();

    public void setActorData(ActorEntity actorEntity){
        this.id = actorEntity.getId();
        this.firstName = actorEntity.getFirstName();
        this.lastName = actorEntity.getLastName();
        this.birthDate = actorEntity.getBirthDate();
        this.albums = toAlbumModel(actorEntity.getAlbums());
    }


    private List<AlbumModel> toAlbumModel(List<AlbumEntity> albums) {
        if(albums.isEmpty()){
            return Collections.emptyList();
        }

        return albums.stream()
                .map(album -> AlbumModel.builder()
                .id(album.getId())
                .title(album.getTitle())
                .description(album.getDescription())
                .releaseDate(album.getReleaseDate())
//                        .albumStrings(new ArrayList<>())
                .build()
                .add(linkTo(
                        methodOn(WebController.class)
                        .getAlbumById(album.getId())

                ).withSelfRel())
                ).collect(Collectors.toList());
    }
}
