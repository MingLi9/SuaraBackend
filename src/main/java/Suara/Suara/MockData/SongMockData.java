package Suara.Suara.MockData;

import Suara.Suara.DALInterface.ISongDAL;
import Suara.Suara.Model.Song;

import java.util.ArrayList;
import java.util.List;

public class SongMockData implements ISongDAL {
    private List<Song> songs = new ArrayList<>();

    public SongMockData() {
        songs.add(new Song(1L,"Kaas",1L,"C/kaas.pm3"));
        songs.add(new Song(2L,"Kaas v2",1L,"C/kaasv2.pm3"));
        songs.add(new Song(3L,"frietje met",2L,"C/frietje.pm3"));
        songs.add(new Song(4L,"frietje met, zonder mayo",2L,"C/frietjeV2.pm3"));
        songs.add(new Song(5L,"2 gehaktballen",2L,"C/gehaktbal.pm3"));
        songs.add(new Song(6L,"10,50 en een natte krentebol",2L,"C/1050.pm3"));
        songs.add(new Song(7L,"You shall not pass",3L,"C/LOTR.pm3"));
    }

    public List<Song> getAllSongs() {
        return songs;
    }

    public Song getSongById(Long id) {
        for (Song song : songs) {
            if (song.getId().equals(id))
                return song;
        }
        return null;
    }

    public Song createSong(Song song) {
        Long newId = songs.get(songs.size()-1).getId() +1;
        song.setId(newId);
        songs.add(song);
        return song;
    }

    public boolean updateSong(Song newSong) {
        int indexer = 0;
        for (Song song : songs) {
            if (song.getId().equals(newSong.getId()))
                break;
            indexer++;
        }
        if(indexer == songs.size()){
            return false;
        }
        songs.set(indexer, newSong);
        return true;
    }

    public void deleteSong(Long id) {
        int indexer = 0;
        for (Song song : songs) {
            if (song.getId().equals(id))
                break;
            indexer++;
        }
        if(indexer == songs.size()){
            return;
        }
        songs.remove(indexer);
    }
}
