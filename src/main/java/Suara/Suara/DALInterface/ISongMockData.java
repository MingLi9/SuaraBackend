package Suara.Suara.DALInterface;

import Suara.Suara.Model.Song;

import java.util.List;

public interface ISongMockData {
    List<Song> getAllSongs();
    Song getSongById(long id);
    Song save(Song song);
    Song updateSong(Song newSong);
    void deleteSong(long id);
}
