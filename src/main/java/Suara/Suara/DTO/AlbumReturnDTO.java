package Suara.Suara.DTO;

import Suara.Suara.Model.Song;
import lombok.*;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class AlbumReturnDTO {
    private Long id;
    private String name;
    private String ownerName;
    private List<Song> songs;
}
