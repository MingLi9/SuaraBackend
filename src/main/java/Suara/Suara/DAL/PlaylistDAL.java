package Suara.Suara.DAL;

import Suara.Suara.DALInterface.IPlaylistDAL;
import Suara.Suara.Model.Playlist;
import Suara.Suara.Repository.PlaylistRepo;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("PlaylistDAL")
public class PlaylistDAL implements IPlaylistDAL {
    private final PlaylistRepo playlistRepo;

    public PlaylistDAL(@Qualifier("PlaylistRepo") PlaylistRepo playlistRepo) {
        this.playlistRepo = playlistRepo;
    }

    public List<Playlist> getAllPlaylist() {
        return playlistRepo.findAll();
    }

    public List<Playlist> getAllPlaylistByPartName(String chars) {
        return playlistRepo.findPlaylistByNameContaining(chars);
    }

    public Playlist getPlaylistById(Long id) {
        return playlistRepo.getById(id);
    }

    public Playlist createPlaylist(Playlist playlist) {
        return playlistRepo.save(playlist);
    }

    public boolean updatePlaylist(Playlist playlist) {
        playlistRepo.save(playlist);
        return true;
    }

    public void deletePlaylist(Long id) {
        playlistRepo.deleteById(id);
    }
}
