package practice.hateoas.tutorial.Entity;

import lombok.*;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@ToString(exclude = "albums")
@Entity
@Table(name = "actor")
public class ActorEntity {
    @Id
    @GeneratedValue
    private Long id;
    private String firstName;
    private String lastName;
    private String birthDate;

    @Builder
    public ActorEntity(String firstName, String lastName, String birthDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
    }

    @ManyToMany(fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    @JoinTable(
            name="actor_album",
            joinColumns = @JoinColumn(name = "actor_id"),
            inverseJoinColumns = @JoinColumn(name="album_id")
    )
    private List<AlbumEntity> albums = new ArrayList<>();

    public void addAlbum(AlbumEntity album){
        albums.add(album);
        album.getActors().add(this);
    }
}
