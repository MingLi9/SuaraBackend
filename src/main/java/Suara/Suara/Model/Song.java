package Suara.Suara.Model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private Long creatorId;
    private String file;

    public Song(String title, Long creatorId, String file) {
        this.title = title;
        this.creatorId = creatorId;
        this.file = file;
    }
}
