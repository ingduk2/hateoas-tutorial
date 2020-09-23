package practice.hateoas.tutorial.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.*;
import org.springframework.hateoas.Link;
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
@Relation(collectionRelation = "albums")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AlbumModel extends RepresentationModel<AlbumModel> {
    private Long id;
    private String title;
    private String description;
    private String releaseDate;


    private List<ActorModel> actors;
//    private List<String> albumStrings = new ArrayList<>();

    public void setAlbumData(AlbumEntity albumEntity) {
        this.id = albumEntity.getId();
        this.title = albumEntity.getTitle();
        this.description = albumEntity.getDescription();
        this.releaseDate = albumEntity.getReleaseDate();
        this.actors =toActorModel(albumEntity.getActors());
    }

    private List<ActorModel> toActorModel(List<ActorEntity> actors){
        if(actors.isEmpty()){
            return Collections.emptyList();
        }

        return actors.stream()
                .map(actor -> ActorModel.builder()
                .id(actor.getId())
                .firstName(actor.getFirstName())
                        .lastName(actor.getLastName())
                        .birthDate(actor.getBirthDate())
//                        .actorStrings(new ArrayList<>())
                        .build()
                        .add(linkTo(
                                methodOn(WebController.class)
                                .getActorById(actor.getId())
                        ).withSelfRel())
                ).collect(Collectors.toList());
    }
}
