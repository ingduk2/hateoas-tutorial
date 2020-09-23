package practice.hateoas.tutorial.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.test.web.servlet.MockMvc;
import practice.hateoas.tutorial.Entity.ActorEntity;
import practice.hateoas.tutorial.Entity.AlbumEntity;
import practice.hateoas.tutorial.dto.assembler.ActorModelAssembler;
import practice.hateoas.tutorial.dto.assembler.AlbumModelAssembler;
import practice.hateoas.tutorial.repository.ActorRepository;
import practice.hateoas.tutorial.repository.AlbumRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WebController.class)
class WebControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private AlbumRepository albumRepository;
    @MockBean
    private ActorRepository actorRepository;
    @MockBean
    private ActorModelAssembler actorModelAssembler;
    @MockBean
    private AlbumModelAssembler albumModelAssembler;
    @MockBean
    private PagedResourcesAssembler<AlbumEntity> albumEntityPagedResourcesAssembler;
    @MockBean
    private PagedResourcesAssembler<ActorEntity> actorEntityPagedResourcesAssembler;


    @Test
    public void mainTest() throws Exception {
        mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value("main"))
                .andExpect(jsonPath("$._links.self.href").value("http://localhost"));
    }
}