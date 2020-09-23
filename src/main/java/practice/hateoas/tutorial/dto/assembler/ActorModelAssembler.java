package practice.hateoas.tutorial.dto.assembler;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;
import practice.hateoas.tutorial.Entity.ActorEntity;
import practice.hateoas.tutorial.controller.WebController;
import practice.hateoas.tutorial.dto.ActorModel;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ActorModelAssembler extends RepresentationModelAssemblerSupport<ActorEntity, ActorModel> {


    public ActorModelAssembler() {
        super(WebController.class, ActorModel.class);
    }

    /**
     * Entity 를 Model(Dto) 로 변환한다. 그러면서 link 를 add
     */
    @Override
    public ActorModel toModel(ActorEntity entity) {
        ActorModel actorModel = new ActorModel();

        actorModel.add(linkTo(
                methodOn(WebController.class)
                .getActorById(entity.getId())
        ).withSelfRel());

        actorModel.setActorData(entity);
        return actorModel;
    }

    /**
     *
     * toCollectionModel 안에서 toModel 사용해 하나씩 entity -> model 로 바꾼다.
     */
    @Override
    public CollectionModel<ActorModel> toCollectionModel(Iterable<? extends ActorEntity> entities) {
        return super.toCollectionModel(entities).add(linkTo(methodOn(WebController.class).getAllActors()).withSelfRel());
    }
}
