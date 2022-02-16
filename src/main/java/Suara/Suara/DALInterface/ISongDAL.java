package Suara.Suara.DALInterface;

import Suara.Suara.Model.Song;

import java.util.List;

public interface ISongDAL {
    List<Song> getAllSongs();
    Song getSongById(Long id);
    Song createSong(Song song);
    boolean updateSong(Song song);
    void deleteSong(Long id);
}
