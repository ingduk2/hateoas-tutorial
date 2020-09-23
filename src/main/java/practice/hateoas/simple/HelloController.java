package practice.hateoas.simple;

import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping("/api")
public class HelloController {

    @GetMapping("/hello")
    public EntityModel<Information> hello(){
        Information information = new Information(1L, "1", "11");
        EntityModel<Information> entityModel = EntityModel.of(information);
        entityModel.add(linkTo(HelloController.class).slash("hello").withSelfRel());
        return entityModel;
    }

    @GetMapping("/hellos")
    public EntityModel<Information> hellos(){
        Information information = new Information(1L, "1", "11");
        List<Information> informationList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            informationList.add(new Information((long)i , "name" + i , "desc" + i));
        }

        EntityModel<Information> entityModel = EntityModel.of(information);
        entityModel.add(linkTo(HelloController.class).slash("hello").withSelfRel());
        return entityModel;
    }
}
