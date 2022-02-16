package Suara.Suara.Model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.FetchType.EAGER;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private Long ownerId;
    @ManyToMany(fetch = EAGER)
    private List<Song> songs;

    public Album(String name, Long ownerId, List<Song> songs) {
        this.name = name;
        this.ownerId = ownerId;
        this.songs = songs;
    }
}
