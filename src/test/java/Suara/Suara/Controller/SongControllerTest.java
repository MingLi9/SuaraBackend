package Suara.Suara.Controller;

import Suara.Suara.DALInterface.IRoleDAL;
import Suara.Suara.DALInterface.ISongDAL;
import Suara.Suara.DALInterface.ISuaraUserDAL;
import Suara.Suara.DTO.SongDTO;
import Suara.Suara.MockData.RoleMockDAL;
import Suara.Suara.MockData.SongMockData;
import Suara.Suara.MockData.SuaraUserMockDAL;
import Suara.Suara.Model.Song;
import Suara.Suara.Service.RoleService;
import Suara.Suara.Service.SongService;
import Suara.Suara.Service.SuaraUserService;
import Suara.Suara.ServiceInterface.IRoleService;
import Suara.Suara.ServiceInterface.ISongService;
import Suara.Suara.ServiceInterface.ISuaraUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.net.URI;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SongControllerTest {
    private Authentication token;
    private SongController songController;

    @BeforeEach
    void setUp() {
        token = new Authentication() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return null;
            }

            @Override
            public Object getCredentials() {
                return null;
            }

            @Override
            public Object getDetails() {
                return null;
            }

            @Override
            public Object getPrincipal() {
                return null;
            }

            @Override
            public boolean isAuthenticated() {
                return false;
            }

            @Override
            public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

            }

            @Override
            public String getName() {
                return "Ming";
            }
        };

        IRoleDAL iRoleDAL = new RoleMockDAL();
        IRoleService iRoleService = new RoleService(iRoleDAL);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        ISuaraUserDAL iSuaraUserDAL = new SuaraUserMockDAL();
        ISuaraUserService iSuaraUserService = new SuaraUserService(iSuaraUserDAL, iRoleService, passwordEncoder);

        ISongDAL songDAL = new SongMockData();
        ISongService songService = new SongService(songDAL, iSuaraUserService);
        songController = new SongController(songService);
    }

    @Test
    void getAllSongs() {
        //arrange
        HttpStatus expectedStatus = HttpStatus.OK;
        int expectedSize = 7;

        //act
        ResponseEntity<List<Song>> responseEntity = songController.getAllSongs();
        HttpStatus actualStatus = responseEntity.getStatusCode();
        List<Song> songList = responseEntity.getBody();
        assert songList != null;
        int actualSize = songList.size();

        //assert
        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedSize, actualSize);
    }

    @Test
    void getSongByID() {
        //arrange
        Song expectedSong = new Song(1L, "Kaas", 1L, "C/kaas.pm3");
        HttpStatus expectedStatus = HttpStatus.OK;
        String expectedTitle = expectedSong.getTitle();
        Long expectedCreatorId = expectedSong.getCreatorId();
        String expectedFile = expectedSong.getFile();

        //act
        ResponseEntity<Song> responseEntity = songController.getSongByID(1L);
        HttpStatus actualStatus = responseEntity.getStatusCode();
        Song actualSong = responseEntity.getBody();
        assert actualSong != null;
        String actualTitle = actualSong.getTitle();
        Long actualCreatorId = actualSong.getCreatorId();
        String actualFile = actualSong.getFile();

        //assert
        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedTitle, actualTitle);
        assertEquals(expectedCreatorId, actualCreatorId);
        assertEquals(expectedFile, actualFile);
    }

    @Test
    void createSong() {
        //arrange
        Song expectedSong = new Song(8L, "testSong", 1L, "C/testy.pm3");
        HttpStatus expectedStatus = HttpStatus.CREATED;
        Long expectedId = expectedSong.getId();
        String expectedTitle = expectedSong.getTitle();
        Long expectedCreatorId = expectedSong.getCreatorId();
        String expectedFile = expectedSong.getFile();

        SongDTO songToAdd = new SongDTO("testSong", "C/testy.pm3");

        //act
        ResponseEntity<?> responseEntity = songController.createSong(songToAdd, token);
        HttpStatus actualStatus = responseEntity.getStatusCode();

        ResponseEntity<Song> responseEntity2 = songController.getSongByID(8L);
        Song actualSong = responseEntity2.getBody();
        assert actualSong != null;

        Long actualId = actualSong.getId();
        String actualTitle = actualSong.getTitle();
        Long actualCreatorId = actualSong.getCreatorId();
        String actualFile = actualSong.getFile();

        //assert
        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedId, actualId);
        assertEquals(expectedTitle, actualTitle);
        assertEquals(expectedCreatorId, actualCreatorId);
        assertEquals(expectedFile, actualFile);
    }

    @Test
    void createSongNullValueTitle() {
        //arrange
        HttpStatus expectedStatus = HttpStatus.CONFLICT;

        SongDTO songToAdd = new SongDTO(null, "C/testy.pm3");

        //act
        ResponseEntity<?> responseEntity = songController.createSong(songToAdd, token);
        HttpStatus actualStatus = responseEntity.getStatusCode();

        //assert
        assertEquals(expectedStatus, actualStatus);
    }

    @Test
    void createSongEmptyValueTitle() {
        //arrange
        HttpStatus expectedStatus = HttpStatus.CONFLICT;

        SongDTO songToAdd = new SongDTO("", "C/testy.pm3");

        //act
        ResponseEntity<?> responseEntity = songController.createSong(songToAdd, token);
        HttpStatus actualStatus = responseEntity.getStatusCode();

        //assert
        assertEquals(expectedStatus, actualStatus);
    }

    @Test
    void createSongNullValueFile() {
        //arrange
        HttpStatus expectedStatus = HttpStatus.CONFLICT;

        SongDTO songToAdd = new SongDTO("testSong", null);

        //act
        ResponseEntity<?> responseEntity = songController.createSong(songToAdd, token);
        HttpStatus actualStatus = responseEntity.getStatusCode();

        //assert
        assertEquals(expectedStatus, actualStatus);
    }

    @Test
    void createSongEmptyValueFile() {
        //arrange
        HttpStatus expectedStatus = HttpStatus.CONFLICT;

        SongDTO songToAdd = new SongDTO("testSong", "");

        //act
        ResponseEntity<?> responseEntity = songController.createSong(songToAdd, token);
        HttpStatus actualStatus = responseEntity.getStatusCode();

        //assert
        assertEquals(expectedStatus, actualStatus);
    }

    @Test
    void updateSong() {
        //arrange
        HttpStatus expectedStatus = HttpStatus.NO_CONTENT;
        SongDTO songDTO = new SongDTO("update", "song");
        Long expectedCreatorId = 1L;
        String expectedTitle = songDTO.getTitle();
        String expectedFile = songDTO.getFile();

        //act
        ResponseEntity<?> responseEntity = songController.updateSong(songDTO, expectedCreatorId, token);
        HttpStatus actualStatus = responseEntity.getStatusCode();

        ResponseEntity<Song> responseEntity2 = songController.getSongByID(expectedCreatorId);
        Song result = responseEntity2.getBody();
        assert result != null;
        Long actualCreatorId = result.getCreatorId();
        String actualTitle = result.getTitle();
        String actualFile = result.getFile();

        //assert
        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedCreatorId, actualCreatorId);
        assertEquals(expectedTitle, actualTitle);
        assertEquals(expectedFile, actualFile);
    }

    @Test
    void updateSongNullValueTitle() {
        //arrange
        HttpStatus expectedStatus = HttpStatus.NOT_FOUND;
        SongDTO songDTO = new SongDTO(null, "song");
        Long creatorId = 1L;

        //act
        ResponseEntity<?> responseEntity = songController.updateSong(songDTO, creatorId, token);
        HttpStatus actualStatus = responseEntity.getStatusCode();

        //assert
        assertEquals(expectedStatus, actualStatus);
    }

    @Test
    void updateSongEmptyValueTitle() {
        //arrange
        HttpStatus expectedStatus = HttpStatus.NOT_FOUND;
        SongDTO songDTO = new SongDTO("", "song");
        Long creatorId = 1L;

        //act
        ResponseEntity<?> responseEntity = songController.updateSong(songDTO, creatorId, token);
        HttpStatus actualStatus = responseEntity.getStatusCode();

        //assert
        assertEquals(expectedStatus, actualStatus);
    }

    @Test
    void updateSongNullValueFile() {
        //arrange
        HttpStatus expectedStatus = HttpStatus.NOT_FOUND;
        SongDTO songDTO = new SongDTO("Update", null);
        Long creatorId = 1L;

        //act
        ResponseEntity<?> responseEntity = songController.updateSong(songDTO, creatorId, token);
        HttpStatus actualStatus = responseEntity.getStatusCode();

        //assert
        assertEquals(expectedStatus, actualStatus);
    }

    @Test
    void updateSongEmptyValueFile() {
        //arrange
        HttpStatus expectedStatus = HttpStatus.NOT_FOUND;
        SongDTO songDTO = new SongDTO("Update", "");
        Long creatorId = 1L;

        //act
        ResponseEntity<?> responseEntity = songController.updateSong(songDTO, creatorId, token);
        HttpStatus actualStatus = responseEntity.getStatusCode();

        //assert
        assertEquals(expectedStatus, actualStatus);
    }

    @Test
    void deleteSong() {
        //arrange
        HttpStatus expectedStatus = HttpStatus.OK;

        //act
        ResponseEntity<?> responseEntity = songController.deleteSong(1L, token);
        HttpStatus actualStatus = responseEntity.getStatusCode();

        //assert
        assertEquals(expectedStatus, actualStatus);
    }

    @Test
    void deleteSongNullValueId() {
        //arrange
        HttpStatus expectedStatus = HttpStatus.NOT_FOUND;

        //act
        ResponseEntity<?> responseEntity = songController.deleteSong(null, token);
        HttpStatus actualStatus = responseEntity.getStatusCode();

        //assert
        assertEquals(expectedStatus, actualStatus);
    }

    @Test
    void deleteSongNegativeValueId() {
        //arrange
        HttpStatus expectedStatus = HttpStatus.NOT_FOUND;

        //act
        ResponseEntity<?> responseEntity = songController.deleteSong(-1L, token);
        HttpStatus actualStatus = responseEntity.getStatusCode();

        //assert
        assertEquals(expectedStatus, actualStatus);
    }

    @Test
    void deleteSongToHighValueId() {
        //arrange
        HttpStatus expectedStatus = HttpStatus.NOT_FOUND;

        //act
        ResponseEntity<?> responseEntity = songController.deleteSong(100L, token);
        HttpStatus actualStatus = responseEntity.getStatusCode();

        //assert
        assertEquals(expectedStatus, actualStatus);
    }

    @Test
    void deleteSongZeroValueId() {
        //arrange
        HttpStatus expectedStatus = HttpStatus.NOT_FOUND;

        //act
        ResponseEntity<?> responseEntity = songController.deleteSong(0L, token);
        HttpStatus actualStatus = responseEntity.getStatusCode();

        //assert
        assertEquals(expectedStatus, actualStatus);
    }

    @Test
    void deleteSongNullValueToken() {
        //arrange
        HttpStatus expectedStatus = HttpStatus.NOT_FOUND;

        //act
        ResponseEntity<?> responseEntity = songController.deleteSong(-1L, null);
        HttpStatus actualStatus = responseEntity.getStatusCode();

        //assert
        assertEquals(expectedStatus, actualStatus);
    }
}