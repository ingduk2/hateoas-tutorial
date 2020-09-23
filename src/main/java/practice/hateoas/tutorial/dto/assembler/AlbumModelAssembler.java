package practice.hateoas.tutorial.dto.assembler;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;
import practice.hateoas.tutorial.Entity.AlbumEntity;
import practice.hateoas.tutorial.controller.WebController;
import practice.hateoas.tutorial.dto.AlbumModel;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class AlbumModelAssembler extends RepresentationModelAssemblerSupport<AlbumEntity, AlbumModel> {

    public AlbumModelAssembler() {
        super(WebController.class, AlbumModel.class);
    }

    /**
     * Entity 를 Model(Dto) 로 변환한다. 그러면서 link 를 add
     */
    @Override
    public AlbumModel toModel(AlbumEntity entity) {
//        AlbumModel albumModel = instantiateModel(entity);
        AlbumModel albumModel = new AlbumModel();

        albumModel.add(
                linkTo(methodOn(WebController.class)
                .getAlbumById(entity.getId()))
                .withSelfRel()
        );

        albumModel.setAlbumData(entity);
        return albumModel;
    }

    /**
     *
     * toCollectionModel 안에서 toModel 사용해 하나씩 entity -> model 로 바꾼다.
     */
    @Override
    public CollectionModel<AlbumModel> toCollectionModel(Iterable<? extends AlbumEntity> entities) {
        return super.toCollectionModel(entities).add(linkTo(methodOn(WebController.class).getAllAlbums()).withSelfRel());
    }


}
