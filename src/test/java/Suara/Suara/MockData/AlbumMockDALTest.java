package Suara.Suara.MockData;

import Suara.Suara.Model.Album;
import Suara.Suara.Model.Song;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AlbumMockDALTest {

    private final AlbumMockDAL albumMockDAL = new AlbumMockDAL();

    @Test
    void getAllAlbums() {
        //arrange
        int expectedSize = 1;

        //act
        List<Album> result = albumMockDAL.getAllAlbum();
        int actualSize = result.size();

        //assert
        assertEquals(expectedSize, actualSize);
    }

    @Test
    void getAllAlbumByPartName() {
        //arrange
        int expectedSize = 1;

        //act
        List<Album> result = albumMockDAL.getAllAlbumByPartName("ang");
        int actualSize = result.size();

        //assert
        assertEquals(expectedSize, actualSize);
    }

    @Test
    void getAllAlbumByPartNameNullValue() {
        //arrange
        int expectedSize = 0;

        //act
        List<Album> result = albumMockDAL.getAllAlbumByPartName(null);
        int actualSize = result.size();

        //assert
        assertEquals(expectedSize, actualSize);
    }

    @Test
    void getAllAlbumByPartNameEmptyValue() {
        //arrange
        int expectedSize = 1;

        //act
        List<Album> result = albumMockDAL.getAllAlbumByPartName("");
        int actualSize = result.size();

        //assert
        assertEquals(expectedSize, actualSize);
    }

    @Test
    void getAlbumById() {
        //arrange
        String expectedName = "Gange";

        //act
        Album result = albumMockDAL.getAlbumById(1L);
        String actualName = result.getName();

        //assert
        assertEquals(expectedName, actualName);
    }

    @Test
    void getAlbumByIdNullValue() {
        //arrange
        Album expectedValue = null;

        //act
        Album actualValue = albumMockDAL.getAlbumById(null);

        //assert
        assertEquals(expectedValue, actualValue);
    }

    @Test
    void getAlbumByIdToHighValue() {
        //arrange
        Album expectedValue = null;

        //act
        Album actualValue = albumMockDAL.getAlbumById(200L);

        //assert
        assertEquals(expectedValue, actualValue);
    }

    @Test
    void getAlbumByIdNegativeValue() {
        //arrange
        Album expectedValue = null;

        //act
        Album actualValue = albumMockDAL.getAlbumById(-1L);

        //assert
        assertEquals(expectedValue, actualValue);
    }

    @Test
    void createAlbum() {
        //arrange
        List<Song> songs = new ArrayList<>();
        songs.add(new Song(1L, "Kaas", 1L, "C/kaas.pm3"));
        songs.add(new Song(4L, "frietje met, zonder mayo", 2L, "C/frietjeV2.pm3"));
        songs.add(new Song(6L, "10,50 en een natte krentebol", 2L, "C/1050.pm3"));
        songs.add(new Song(7L, "You shall not pass", 3L, "C/LOTR.pm3"));


        Album albumToAdd = new Album(2L, "Gange2", 1L, songs);
        Long expectedId = 2L;
        String expectedName = "Gange2";
        Long expectedOwnerId = 1L;
        int expectedSongListSize = 4;

        //act
        Album result = albumMockDAL.createAlbum(albumToAdd);
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
    void createAlbumNullValue() {
        //arrange
        List<Song> songs = new ArrayList<>();
        songs.add(new Song(1L, "Kaas", 1L, "C/kaas.pm3"));
        songs.add(new Song(4L, "frietje met, zonder mayo", 2L, "C/frietjeV2.pm3"));
        songs.add(new Song(6L, "10,50 en een natte krentebol", 2L, "C/1050.pm3"));
        songs.add(new Song(7L, "You shall not pass", 3L, "C/LOTR.pm3"));


        Album albumToAdd = new Album(null, "Gange2", 1L, songs);
        Long expectedId = 2L;
        String expectedName = "Gange2";
        Long expectedOwnerId = 1L;
        int expectedSongListSize = 4;

        //act
        Album result = albumMockDAL.createAlbum(albumToAdd);
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
    void createAlbumNegativeValue() {
        //arrange
        List<Song> songs = new ArrayList<>();
        songs.add(new Song(1L, "Kaas", 1L, "C/kaas.pm3"));
        songs.add(new Song(4L, "frietje met, zonder mayo", 2L, "C/frietjeV2.pm3"));
        songs.add(new Song(6L, "10,50 en een natte krentebol", 2L, "C/1050.pm3"));
        songs.add(new Song(7L, "You shall not pass", 3L, "C/LOTR.pm3"));


        Album albumToAdd = new Album(-2L, "Gange2", 1L, songs);
        Long expectedId = 2L;
        String expectedName = "Gange2";
        Long expectedOwnerId = 1L;
        int expectedSongListSize = 4;

        //act
        Album result = albumMockDAL.createAlbum(albumToAdd);
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
    void deleteAlbum() {
        //arrange
        int expectedSize = 0;

        //act
        albumMockDAL.deleteAlbum(1L);
        List<Album> result = albumMockDAL.getAllAlbum();
        int actualSize = result.size();

        //assert
        assertEquals(expectedSize, actualSize);
    }

    @Test
    void deleteAlbumNullValue() {
        //arrange
        int expectedSize = 1;

        //act
        albumMockDAL.deleteAlbum(null);
        List<Album> result = albumMockDAL.getAllAlbum();
        int actualSize = result.size();

        //assert
        assertEquals(expectedSize, actualSize);
    }

    @Test
    void deleteAlbumNegativeValue() {
        //arrange
        int expectedSize = 1;

        //act
        albumMockDAL.deleteAlbum(-1L);
        List<Album> result = albumMockDAL.getAllAlbum();
        int actualSize = result.size();

        //assert
        assertEquals(expectedSize, actualSize);
    }

    @Test
    void deleteAlbumNToHighegativeValue() {
        //arrange
        int expectedSize = 1;

        //act
        albumMockDAL.deleteAlbum(200L);
        List<Album> result = albumMockDAL.getAllAlbum();
        int actualSize = result.size();

        //assert
        assertEquals(expectedSize, actualSize);
    }
}