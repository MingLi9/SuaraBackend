package Suara.Suara.DALInterface;

import Suara.Suara.Model.Playlist;

import java.util.List;

public interface IPlaylistDAL {
    List<Playlist> getAllPlaylist();

    List<Playlist> getAllPlaylistByPartName(String chars);

    Playlist getPlaylistById(Long id);

    Playlist createPlaylist(Playlist playlist);

    boolean updatePlaylist(Playlist playlist);

    void deletePlaylist(Long id);
}
