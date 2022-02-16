package Suara.Suara.ServiceInterface;

import Suara.Suara.DTO.SongDTO;
import Suara.Suara.Model.Song;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface ISongService {
    List<Song> getAllSongs();

    Song getSongById(Long id);

    Song createSong(SongDTO songDTO, Authentication token);

    boolean deleteSong(Long id, Authentication token);

    boolean updateSong(SongDTO songDTO, Long id, Authentication token);
}
