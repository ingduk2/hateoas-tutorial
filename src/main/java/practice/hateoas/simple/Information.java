package practice.hateoas.simple;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class Information {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String desc;

    public Information(Long id, String name, String desc) {
        this.id = id;
        this.name = name;
        this.desc = desc;
    }

}
