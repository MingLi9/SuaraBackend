package Suara.Suara.DAL;

import Suara.Suara.DALInterface.ISongDAL;
import Suara.Suara.Model.Song;
import Suara.Suara.Repository.SongRepo;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("SongDAL")
public class SongDAL implements ISongDAL {
    private final SongRepo songRepo;

    public SongDAL(@Qualifier("SongRepo") SongRepo songRepo) {
        this.songRepo = songRepo;
    }

    public List<Song> getAllSongs() {
        return songRepo.findAll();
    }

    public Song getSongById(Long id) {
        Optional<Song> value = songRepo.findById(id);

        return value.orElse(null);
    }

    public Song createSong(Song song) {
        return songRepo.save(song);
    }

    public boolean updateSong(Song song) {
        songRepo.save(song);
        return true;
    }

    public void deleteSong(Long id) {
        songRepo.deleteById(id);
    }
}
