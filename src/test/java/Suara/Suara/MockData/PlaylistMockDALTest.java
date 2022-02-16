package Suara.Suara.MockData;

import Suara.Suara.Model.Playlist;
import Suara.Suara.Model.Song;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlaylistMockDALTest {

    private final PlaylistMockDAL playlistMockDAL = new PlaylistMockDAL();

    @Test
    void getAllPlaylists() {
        //arrange
        int expectedSize = 1;

        //act
        List<Playlist> result = playlistMockDAL.getAllPlaylist();
        int actualSize = result.size();

        //assert
        assertEquals(expectedSize, actualSize);
    }

    @Test
    void getAllPlaylistByPartName() {
        //arrange
        int expectedSize = 1;

        //act
        List<Playlist> result = playlistMockDAL.getAllPlaylistByPartName("ang");
        int actualSize = result.size();

        //assert
        assertEquals(expectedSize, actualSize);
    }

    @Test
    void getAllPlaylistByPartNameNullValue() {
        //arrange
        int expectedSize = 0;

        //act
        List<Playlist> result = playlistMockDAL.getAllPlaylistByPartName(null);
        int actualSize = result.size();

        //assert
        assertEquals(expectedSize, actualSize);
    }

    @Test
    void getAllPlaylistByPartNameEmptyValue() {
        //arrange
        int expectedSize = 1;

        //act
        List<Playlist> result = playlistMockDAL.getAllPlaylistByPartName("");
        int actualSize = result.size();

        //assert
        assertEquals(expectedSize, actualSize);
    }

    @Test
    void getPlaylistById() {
        //arrange
        String expectedName = "Gange";

        //act
        Playlist result = playlistMockDAL.getPlaylistById(1L);
        String actualName = result.getName();

        //assert
        assertEquals(expectedName, actualName);
    }

    @Test
    void getPlaylistByIdNullValue() {
        //arrange
        Playlist expectedValue = null;

        //act
        Playlist actualValue = playlistMockDAL.getPlaylistById(null);

        //assert
        assertEquals(expectedValue, actualValue);
    }

    @Test
    void getPlaylistByIdToHighValue() {
        //arrange
        Playlist expectedValue = null;

        //act
        Playlist actualValue = playlistMockDAL.getPlaylistById(200L);

        //assert
        assertEquals(expectedValue, actualValue);
    }

    @Test
    void getPlaylistByIdNegativeValue() {
        //arrange
        Playlist expectedValue = null;

        //act
        Playlist actualValue = playlistMockDAL.getPlaylistById(-1L);

        //assert
        assertEquals(expectedValue, actualValue);
    }

    @Test
    void createPlaylist() {
        //arrange
        List<Song> songs = new ArrayList<>();
        songs.add(new Song(1L, "Kaas", 1L, "C/kaas.pm3"));
        songs.add(new Song(4L, "frietje met, zonder mayo", 2L, "C/frietjeV2.pm3"));
        songs.add(new Song(6L, "10,50 en een natte krentebol", 2L, "C/1050.pm3"));
        songs.add(new Song(7L, "You shall not pass", 3L, "C/LOTR.pm3"));


        Playlist playlistToAdd = new Playlist(2L, "Gange2", 1L, songs);
        Long expectedId = 2L;
        String expectedName = "Gange2";
        Long expectedOwnerId = 1L;
        int expectedSongListSize = 4;

        //act
        Playlist result = playlistMockDAL.createPlaylist(playlistToAdd);
        Long actualId = result.getId();
        String actualName = result.getName();
        Long actualOwnerId = result.getOwnerId();
        int actualSongListSize = result.getSongs().size();

        //assert
        assertEquals(expectedId, actualId);
        assertEquals(expectedName, actualName);
        assertEquals(expectedOwnerId, actualOwnerId);
        assertEquals(expectedSongListSize, actualSongListSize);
    }

    @Test
    void createPlaylistNullValue() {
        //arrange
        List<Song> songs = new ArrayList<>();
        songs.add(new Song(1L, "Kaas", 1L, "C/kaas.pm3"));
        songs.add(new Song(4L, "frietje met, zonder mayo", 2L, "C/frietjeV2.pm3"));
        songs.add(new Song(6L, "10,50 en een natte krentebol", 2L, "C/1050.pm3"));
        songs.add(new Song(7L, "You shall not pass", 3L, "C/LOTR.pm3"));


        Playlist playlistToAdd = new Playlist(null, "Gange2", 1L, songs);
        Long expectedId = 2L;
        String expectedName = "Gange2";
        Long expectedOwnerId = 1L;
        int expectedSongListSize = 4;

        //act
        Playlist result = playlistMockDAL.createPlaylist(playlistToAdd);
        Long actualId = result.getId();
        String actualName = result.getName();
        Long actualOwnerId = result.getOwnerId();
        int actualSongListSize = result.getSongs().size();

        //assert
        assertEquals(expectedId, actualId);
        assertEquals(expectedName, actualName);
        assertEquals(expectedOwnerId, actualOwnerId);
        assertEquals(expectedSongListSize, actualSongListSize);
    }

    @Test
    void createPlaylistNegativeValue() {
        //arrange
        List<Song> songs = new ArrayList<>();
        songs.add(new Song(1L, "Kaas", 1L, "C/kaas.pm3"));
        songs.add(new Song(4L, "frietje met, zonder mayo", 2L, "C/frietjeV2.pm3"));
        songs.add(new Song(6L, "10,50 en een natte krentebol", 2L, "C/1050.pm3"));
        songs.add(new Song(7L, "You shall not pass", 3L, "C/LOTR.pm3"));


        Playlist playlistToAdd = new Playlist(-2L, "Gange2", 1L, songs);
        Long expectedId = 2L;
        String expectedName = "Gange2";
        Long expectedOwnerId = 1L;
        int expectedSongListSize = 4;

        //act
        Playlist result = playlistMockDAL.createPlaylist(playlistToAdd);
        Long actualId = result.getId();
        String actualName = result.getName();
        Long actualOwnerId = result.getOwnerId();
        int actualSongListSize = result.getSongs().size();

        //assert
        assertEquals(expectedId, actualId);
        assertEquals(expectedName, actualName);
        assertEquals(expectedOwnerId, actualOwnerId);
        assertEquals(expectedSongListSize, actualSongListSize);
    }

    @Test
    void updatePlaylist() {
        //arrange
        List<Song> songs = new ArrayList<>();
        songs.add(new Song(1L, "Kaas", 1L, "C/kaas.pm3"));
        songs.add(new Song(4L, "frietje met, zonder mayo", 2L, "C/frietjeV2.pm3"));
        songs.add(new Song(6L, "10,50 en een natte krentebol", 2L, "C/1050.pm3"));
        songs.add(new Song(7L, "You shall not pass", 3L, "C/LOTR.pm3"));


        Playlist playlistToAdd = new Playlist(1L, "Gange2", 2L, songs);
        Long expectedId = 1L;
        String expectedName = "Gange2";
        Long expectedOwnerId = 2L;
        int expectedSongListSize = 4;

        //act
        boolean actualValue = playlistMockDAL.updatePlaylist(playlistToAdd);
        Playlist result = playlistMockDAL.getPlaylistById(1L);
        Long actualId = result.getId();
        String actualName = result.getName();
        Long actualOwnerId = result.getOwnerId();
        int actualSongListSize = result.getSongs().size();

        //assert
        assertTrue(actualValue);
        assertEquals(expectedId, actualId);
        assertEquals(expectedName, actualName);
        assertEquals(expectedOwnerId, actualOwnerId);
        assertEquals(expectedSongListSize, actualSongListSize);
    }

    @Test
    void updatePlaylistNullValue() {
        //arrange
        List<Song> songs = new ArrayList<>();
        songs.add(new Song(1L, "Kaas", 1L, "C/kaas.pm3"));
        songs.add(new Song(4L, "frietje met, zonder mayo", 2L, "C/frietjeV2.pm3"));
        songs.add(new Song(6L, "10,50 en een natte krentebol", 2L, "C/1050.pm3"));
        songs.add(new Song(7L, "You shall not pass", 3L, "C/LOTR.pm3"));


        Playlist playlistToAdd = new Playlist(null, "Gange2", 2L, songs);
        int expectedPlaylistsSize = 1;
        Long expectedId = 1L;
        String expectedName = "Gange";
        Long expectedOwnerId = 1L;
        int expectedSongListSize = 7;

        //act
        boolean actualValue = playlistMockDAL.updatePlaylist(playlistToAdd);
        List<Playlist> result = playlistMockDAL.getAllPlaylist();
        int actualPlaylistsSize = result.size();
        Long actualId = result.get(0).getId();
        String actualName = result.get(0).getName();
        Long actualOwnerId = result.get(0).getOwnerId();
        int actualSongListSize = result.get(0).getSongs().size();

        //assert
        assertFalse(actualValue);
        assertEquals(expectedPlaylistsSize, actualPlaylistsSize);
        assertEquals(expectedId, actualId);
        assertEquals(expectedName, actualName);
        assertEquals(expectedOwnerId, actualOwnerId);
        assertEquals(expectedSongListSize, actualSongListSize);
    }

    @Test
    void updatePlaylistNegativeValue() {
        //arrange
        List<Song> songs = new ArrayList<>();

        songs.add(new Song(1L, "Kaas", 1L, "C/kaas.pm3"));
        songs.add(new Song(4L, "frietje met, zonder mayo", 2L, "C/frietjeV2.pm3"));
        songs.add(new Song(6L, "10,50 en een natte krentebol", 2L, "C/1050.pm3"));
        songs.add(new Song(7L, "You shall not pass", 3L, "C/LOTR.pm3"));

        Playlist playlistToAdd = new Playlist(-1L, "Gange2", 2L, songs);
        int expectedPlaylistsSize = 1;
        Long expectedId = 1L;
        String expectedName = "Gange";
        Long expectedOwnerId = 1L;
        int expectedSongListSize = 7;

        //act
        boolean actualValue = playlistMockDAL.updatePlaylist(playlistToAdd);
        List<Playlist> result = playlistMockDAL.getAllPlaylist();
        int actualPlaylistsSize = result.size();
        Long actualId = result.get(0).getId();
        String actualName = result.get(0).getName();
        Long actualOwnerId = result.get(0).getOwnerId();
        int actualSongListSize = result.get(0).getSongs().size();

        //assert
        assertFalse(actualValue);
        assertEquals(expectedPlaylistsSize, actualPlaylistsSize);
        assertEquals(expectedId, actualId);
        assertEquals(expectedName, actualName);
        assertEquals(expectedOwnerId, actualOwnerId);
        assertEquals(expectedSongListSize, actualSongListSize);
    }

    @Test
    void deletePlaylist() {
        //arrange
        int expectedSize = 0;

        //act
        playlistMockDAL.deletePlaylist(1L);
        List<Playlist> result = playlistMockDAL.getAllPlaylist();
        int actualSize = result.size();

        //assert
        assertEquals(expectedSize, actualSize);
    }

    @Test
    void deletePlaylistNullValue() {
        //arrange
        int expectedSize = 1;

        //act
        playlistMockDAL.deletePlaylist(null);
        List<Playlist> result = playlistMockDAL.getAllPlaylist();
        int actualSize = result.size();

        //assert
        assertEquals(expectedSize, actualSize);
    }

    @Test
    void deletePlaylistNegativeValue() {
        //arrange
        int expectedSize = 1;

        //act
        playlistMockDAL.deletePlaylist(-1L);
        List<Playlist> result = playlistMockDAL.getAllPlaylist();
        int actualSize = result.size();

        //assert
        assertEquals(expectedSize, actualSize);
    }

    @Test
    void deletePlaylistNToHighegativeValue() {
        //arrange
        int expectedSize = 1;

        //act
        playlistMockDAL.deletePlaylist(200L);
        List<Playlist> result = playlistMockDAL.getAllPlaylist();
        int actualSize = result.size();

        //assert
        assertEquals(expectedSize, actualSize);
    }
}