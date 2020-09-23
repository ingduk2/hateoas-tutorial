package practice.hateoas.tutorial.Entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
@Entity
@ToString(exclude = "actors")
@Table(name="album")
public class AlbumEntity {
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String description;
    private String releaseDate;

    @Builder
    public AlbumEntity(String title, String description, String releaseDate) {
        this.title = title;
        this.description = description;
        this.releaseDate = releaseDate;
    }

    @ManyToMany(mappedBy = "albums" )
    private List<ActorEntity> actors = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public List<ActorEntity> getActors() {
        return actors;
    }
}
