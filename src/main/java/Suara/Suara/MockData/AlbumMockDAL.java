package Suara.Suara.MockData;

import Suara.Suara.DALInterface.IAlbumDAL;
import Suara.Suara.Model.Album;
import Suara.Suara.Model.Song;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AlbumMockDAL implements IAlbumDAL {
    private List<Album> albums = new ArrayList<>();

    public AlbumMockDAL() {
        List<Song> songs = new ArrayList<>();
        songs.add(new Song(1L, "Kaas", 1L, "C/kaas.pm3"));
        songs.add(new Song(2L, "Kaas v2", 1L, "C/kaasv2.pm3"));
        songs.add(new Song(3L, "frietje met", 2L, "C/frietje.pm3"));
        songs.add(new Song(4L, "frietje met, zonder mayo", 2L, "C/frietjeV2.pm3"));
        songs.add(new Song(5L, "2 gehaktballen", 2L, "C/gehaktbal.pm3"));
        songs.add(new Song(6L, "10,50 en een natte krentebol", 2L, "C/1050.pm3"));
        songs.add(new Song(7L, "You shall not pass", 3L, "C/LOTR.pm3"));

        albums.add(new Album(1L, "Gange", 1L, songs));
    }

    public List<Album> getAllAlbum() {
        return albums;
    }

    public List<Album> getAllAlbumByPartName(String chars) {
        if (chars == null) return Collections.emptyList();

        List<Album> returnAlbums = new ArrayList<>();
        for (Album album : albums) {
            if (album.getName().contains(chars)) {
                returnAlbums.add(album);
            }
        }
        return returnAlbums;
    }

    public Album getAlbumById(Long id) {
        for (Album album : albums) {
            if (album.getId().equals(id)) {
                return album;
            }
        }
        return null;
    }

    public Album createAlbum(Album album) {
        Long newId = albums.get(albums.size() - 1).getId() + 1;
        album.setId(newId);
        albums.add(album);
        return album;
    }

    public void deleteAlbum(Long id) {
        int index = 0;
        for (Album album : albums) {
            if (album.getId().equals(id)) {
                break;
            }
            index++;
        }
        if (index == albums.size()) {
            return;
        }
        albums.remove(index);
    }
}
