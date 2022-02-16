package Suara.Suara.MockData;

import Suara.Suara.Model.Song;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SongMockDataTest {

    private final SongMockData songMockData = new SongMockData();

    @Test
    void getAllSongs() {
        //arrange
        int expectedSize = 7;

        //act
        int actualSize = songMockData.getAllSongs().size();

        //assert
        assertEquals(expectedSize, actualSize);
    }

    @Test
    void getSongByIdHappy() {
        //arrange
        String expectedTitle = "Kaas";

        //act
        Song result = songMockData.getSongById(1L);
        String actualTitle = result.getTitle();

        //assert
        assertEquals(expectedTitle, actualTitle);
    }

    @Test
    void getSongByIdNegativeId() {
        //arrange
        Song expectedResult = null;

        //act
        Song actualResult = songMockData.getSongById(-1L);

        //assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void getSongByIdNullValue() {
        //arrange
        Song expectedResult = null;

        //act
        Song actualResult = songMockData.getSongById(null);

        //assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void createSong(){
        //arrange
        Song songToAdd = new Song(1L,"Yesss",1l,"C/yesss.pm3");

        String expectedTitle = songToAdd.getTitle();
        Long expectedId = 8L;

        //act
        Song resultSong = songMockData.createSong(songToAdd);
        String actualTitle = resultSong.getTitle();
        Long actualId = resultSong.getId();

        //assert
        assertEquals(expectedTitle, actualTitle);
        assertEquals(expectedId, actualId);
    }

    @Test
    void createSongNullValue(){
        //arrange
        Song songToAdd = new Song(null,"Yesss",1L,"C/yesss.pm3");
        String expectedTitle = songToAdd.getTitle();
        Long expectedId = 8L;

        //act
        Song resultSong = songMockData.createSong(songToAdd);
        String actualTitle = resultSong.getTitle();
        Long actualId = resultSong.getId();

        //assert
        assertEquals(expectedTitle, actualTitle);
        assertEquals(expectedId, actualId);
    }

    @Test
    void createSongNegativeValue(){
        //arrange
        Song songToAdd = new Song(null,"Yesss",1L,"C/yesss.pm3");
        String expectedTitle = songToAdd.getTitle();
        Long expectedId = 8L;

        //act
        Song resultSong = songMockData.createSong(songToAdd);
        String actualTitle = resultSong.getTitle();
        Long actualId = resultSong.getId();

        //assert
        assertEquals(expectedTitle, actualTitle);
        assertEquals(expectedId, actualId);
    }

    @Test
    void updateSong(){
        //arrange
        Song songToAdd = new Song(1L,"Yesss",1L,"C/yesss.pm3");
        String expectedTitle = songToAdd.getTitle();
        String expectedFile = songToAdd.getFile();

        //act
        songMockData.updateSong(songToAdd);
        Song resultSong = songMockData.getSongById(1L);
        String actualTitle = resultSong.getTitle();
        String actualFile = resultSong.getFile();

        //assert
        assertEquals(expectedTitle, actualTitle);
        assertEquals(expectedFile, actualFile);
    }

    @Test
    void updateSongNullValue(){
        //arrange
        Song songToAdd = new Song(null,"Yesss",1L,"C/yesss.pm3");
        String expectedTitle = "Kaas";
        String expectedFile = "C/kaas.pm3";

        //act
        songMockData.updateSong(songToAdd);
        Song resultSong = songMockData.getSongById(1L);
        String actualTitle = resultSong.getTitle();
        String actualFile = resultSong.getFile();

        //assert
        assertEquals(expectedTitle, actualTitle);
        assertEquals(expectedFile, actualFile);
    }

    @Test
    void updateSongNegativeValue(){
        //arrange
        Song songToAdd = new Song(-1L,"Yesss",1L,"C/yesss.pm3");
        String expectedTitle = "Kaas";
        String expectedFile = "C/kaas.pm3";

        //act
        songMockData.updateSong(songToAdd);
        Song resultSong = songMockData.getSongById(1L);
        String actualTitle = resultSong.getTitle();
        String actualFile = resultSong.getFile();

        //assert
        assertEquals(expectedTitle, actualTitle);
        assertEquals(expectedFile, actualFile);
    }

    @Test
    void deleteSong(){
        //arrange
        int expectedSize = 6;

        //act
        songMockData.deleteSong(1L);
        List<Song> result = songMockData.getAllSongs();
        int actualSize = result.size();

        //assert
        assertEquals(expectedSize, actualSize);
    }

    @Test
    void deleteSongNullValue(){
        //arrange
        int expectedSize = 7;

        //act
        songMockData.deleteSong(null);
        List<Song> result = songMockData.getAllSongs();
        int actualSize = result.size();

        //assert
        assertEquals(expectedSize, actualSize);
    }

    @Test
    void deleteSongNegativeValue(){
        //arrange
        int expectedSize = 7;

        //act
        songMockData.deleteSong(-1L);
        List<Song> result = songMockData.getAllSongs();
        int actualSize = result.size();

        //assert
        assertEquals(expectedSize, actualSize);
    }

}