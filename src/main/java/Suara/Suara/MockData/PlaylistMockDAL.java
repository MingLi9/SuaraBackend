package Suara.Suara.MockData;

import Suara.Suara.DALInterface.IPlaylistDAL;
import Suara.Suara.Model.Playlist;
import Suara.Suara.Model.Song;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PlaylistMockDAL implements IPlaylistDAL {

    private List<Playlist> playlists = new ArrayList<>();

    public PlaylistMockDAL() {
        List<Song> songs = new ArrayList<>();
        songs.add(new Song(1L, "Kaas", 1L, "C/kaas.pm3"));
        songs.add(new Song(2L, "Kaas v2", 1L, "C/kaasv2.pm3"));
        songs.add(new Song(3L, "frietje met", 2L, "C/frietje.pm3"));
        songs.add(new Song(4L, "frietje met, zonder mayo", 2L, "C/frietjeV2.pm3"));
        songs.add(new Song(5L, "2 gehaktballen", 2L, "C/gehaktbal.pm3"));
        songs.add(new Song(6L, "10,50 en een natte krentebol", 2L, "C/1050.pm3"));
        songs.add(new Song(7L, "You shall not pass", 3L, "C/LOTR.pm3"));

        playlists.add(new Playlist(1L, "Gange", 1L, songs));
    }

    public List<Playlist> getAllPlaylist() {
        return playlists;
    }

    public List<Playlist> getAllPlaylistByPartName(String chars) {
        if (chars == null) return Collections.emptyList();

        List<Playlist> returnPlaylists = new ArrayList<>();
        for (Playlist playlist : playlists) {
            if (playlist.getName().contains(chars)) {
                returnPlaylists.add(playlist);
            }
        }
        return returnPlaylists;
    }

    public Playlist getPlaylistById(Long id) {
        for (Playlist playlist : playlists) {
            if (playlist.getId().equals(id)) {
                return playlist;
            }
        }
        return null;
    }

    public Playlist createPlaylist(Playlist playlist) {
        Long newId = playlists.get(playlists.size() - 1).getId() + 1;
        playlist.setId(newId);
        playlists.add(playlist);
        return playlist;
    }

    public boolean updatePlaylist(Playlist playlist) {
        int index = 0;
        for (Playlist pl : playlists) {
            if (pl.getId().equals(playlist.getId())) {
                break;
            }
            index++;
        }
        if (index == playlists.size()) {
            return false;
        }
        playlists.set(index, playlist);
        return true;
    }

    public void deletePlaylist(Long id) {
        int index = 0;
        for (Playlist pl : playlists) {
            if (pl.getId().equals(id)) {
                break;
            }
            index++;
        }
        if (index == playlists.size()) {
            return;
        }
        playlists.remove(index);
    }
}
