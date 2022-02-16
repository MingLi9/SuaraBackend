package Suara.Suara.Controller;

import Suara.Suara.DALInterface.IPlaylistDAL;
import Suara.Suara.DALInterface.IRoleDAL;
import Suara.Suara.DALInterface.ISongDAL;
import Suara.Suara.DALInterface.ISuaraUserDAL;
import Suara.Suara.DTO.PlaylistDTO;
import Suara.Suara.DTO.PlaylistReturnDTO;
import Suara.Suara.MockData.PlaylistMockDAL;
import Suara.Suara.MockData.RoleMockDAL;
import Suara.Suara.MockData.SongMockData;
import Suara.Suara.MockData.SuaraUserMockDAL;
import Suara.Suara.Model.Playlist;
import Suara.Suara.Model.Song;
import Suara.Suara.Service.PlaylistService;
import Suara.Suara.Service.RoleService;
import Suara.Suara.Service.SongService;
import Suara.Suara.Service.SuaraUserService;
import Suara.Suara.ServiceInterface.IPlaylistService;
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class PlaylistControllerTest {
    private Authentication token;
    private PlaylistController playlistController;

    @BeforeEach
    void setup() {
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

        IPlaylistDAL iPlaylistDAL = new PlaylistMockDAL();

        ISuaraUserDAL iSuaraUserDAL = new SuaraUserMockDAL();

        IRoleDAL iRoleDAL = new RoleMockDAL();
        IRoleService iRoleService = new RoleService(iRoleDAL);

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        ISuaraUserService iSuaraUserService = new SuaraUserService(iSuaraUserDAL, iRoleService, passwordEncoder);

        ISongDAL isongDAL = new SongMockData();
        ISongService isongService = new SongService(isongDAL, iSuaraUserService);

        IPlaylistService iPlaylistService = new PlaylistService(iPlaylistDAL, iSuaraUserService, isongService);
        playlistController = new PlaylistController(iPlaylistService);
    }

    @Test
    void getAllPlaylist() {
        //arrange
        HttpStatus expectedStatusCode = HttpStatus.OK;
        int expectedBodyPlaylists_listLength = 1;

        //act
        ResponseEntity<?> responseEntity = playlistController.getAllPlaylist();
        HttpStatus resultStatusCode = responseEntity.getStatusCode();
        List<Playlist> resultBody = (List<Playlist>) responseEntity.getBody();
        int resultBodyPlaylists_listLength = resultBody.size();

        //assert
        assertEquals(expectedStatusCode, resultStatusCode);
        assertEquals(expectedBodyPlaylists_listLength, resultBodyPlaylists_listLength);
    }

    @Test
    void getAllPlaylistByPartNameHappyFullName() {
        //arrange
        HttpStatus expectedStatusCode = HttpStatus.OK;
        int expectedBodyPlaylists_listLength = 1;

        //act
        ResponseEntity<?> responseEntity = playlistController.getAllPlaylistByPartName("Gange");
        HttpStatus resultStatusCode = responseEntity.getStatusCode();
        List<Playlist> resultBody = (List<Playlist>) responseEntity.getBody();
        int resultBodyPlaylists_listLength = resultBody.size();

        //assert
        assertEquals(expectedStatusCode, resultStatusCode);
        assertEquals(expectedBodyPlaylists_listLength, resultBodyPlaylists_listLength);
    }

    @Test
    void getAllPlaylistByPartNameHappy1Char() {
        //arrange
        HttpStatus expectedStatusCode = HttpStatus.OK;
        int expectedBodyPlaylists_listLength = 1;

        //act
        ResponseEntity<?> responseEntity = playlistController.getAllPlaylistByPartName("g");
        HttpStatus resultStatusCode = responseEntity.getStatusCode();
        List<Playlist> resultBody = (List<Playlist>) responseEntity.getBody();
        int resultBodyPlaylists_listLength = resultBody.size();

        //assert
        assertEquals(expectedStatusCode, resultStatusCode);
        assertEquals(expectedBodyPlaylists_listLength, resultBodyPlaylists_listLength);
    }

    @Test
    void getAllPlaylistByPartNameHappyEmptyString() {
        //arrange
        HttpStatus expectedStatusCode = HttpStatus.OK;
        int expectedBodyPlaylists_listLength = 1;

        //act
        ResponseEntity<?> responseEntity = playlistController.getAllPlaylistByPartName("");
        HttpStatus resultStatusCode = responseEntity.getStatusCode();
        List<Playlist> resultBody = (List<Playlist>) responseEntity.getBody();
        int resultBodyPlaylists_listLength = resultBody.size();

        //assert
        assertEquals(expectedStatusCode, resultStatusCode);
        assertEquals(expectedBodyPlaylists_listLength, resultBodyPlaylists_listLength);
    }

    @Test
    void getAllPlaylistByPartNameFailNonexisting() {
        //arrange
        HttpStatus expectedStatusCode = HttpStatus.NOT_FOUND;

        //act
        ResponseEntity<?> responseEntity = playlistController.getAllPlaylistByPartName("K");
        HttpStatus resultStatusCode = responseEntity.getStatusCode();

        //assert
        assertEquals(expectedStatusCode, resultStatusCode);
    }

    @Test
    void getPlaylistById() {
        //arrange
        HttpStatus expectedStatusCode = HttpStatus.OK;

        List<Song> songs = new ArrayList<>();
        songs.add(new Song(1L, "Kaas", 1L, "C/kaas.pm3"));
        songs.add(new Song(2L, "Kaas v2", 1L, "C/kaasv2.pm3"));
        songs.add(new Song(3L, "frietje met", 2L, "C/frietje.pm3"));
        songs.add(new Song(4L, "frietje met, zonder mayo", 2L, "C/frietjeV2.pm3"));
        songs.add(new Song(5L, "2 gehaktballen", 2L, "C/gehaktbal.pm3"));
        songs.add(new Song(6L, "10,50 en een natte krentebol", 2L, "C/1050.pm3"));
        songs.add(new Song(7L, "You shall not pass", 3L, "C/LOTR.pm3"));

        PlaylistReturnDTO expectedBody = new PlaylistReturnDTO(1L, "Gange", "Ming", songs);
        Long expectedId = expectedBody.getId();
        String expectedName = expectedBody.getName();
        String expectedOwnerName = expectedBody.getOwnerName();
        int expectedSongListSize = expectedBody.getSongs().size();

        //act
        ResponseEntity<?> responseEntity = playlistController.getPlaylistById(1L);
        HttpStatus resultStatusCode = responseEntity.getStatusCode();
        PlaylistReturnDTO actualBody = (PlaylistReturnDTO) responseEntity.getBody();
        assert actualBody != null;
        Long actualId = actualBody.getId();
        String actualName = actualBody.getName();
        String actualOwnerName = actualBody.getOwnerName();
        int actualSongListSize = actualBody.getSongs().size();

        //assert
        assertEquals(expectedStatusCode, resultStatusCode);
        assertEquals(expectedId, actualId);
        assertEquals(expectedName, actualName);
        assertEquals(expectedOwnerName, actualOwnerName);
        assertEquals(expectedSongListSize, actualSongListSize);
    }

    @Test
    void getPlaylistByIdNullValue() {
        //arrange
        HttpStatus expectedStatusCode = HttpStatus.NOT_FOUND;
        PlaylistReturnDTO expectedBody = null;

        //act
        ResponseEntity<?> responseEntity = playlistController.getPlaylistById(null);
        HttpStatus resultStatusCode = responseEntity.getStatusCode();
        PlaylistReturnDTO actualBody = (PlaylistReturnDTO) responseEntity.getBody();
        //assert
        assertEquals(expectedStatusCode, resultStatusCode);
        assertEquals(expectedBody, actualBody);
    }

    @Test
    void getPlaylistByIdNegativeValue() {
        //arrange
        HttpStatus expectedStatusCode = HttpStatus.NOT_FOUND;
        PlaylistReturnDTO expectedBody = null;

        //act
        ResponseEntity<?> responseEntity = playlistController.getPlaylistById(-1L);
        HttpStatus resultStatusCode = responseEntity.getStatusCode();
        PlaylistReturnDTO actualBody = (PlaylistReturnDTO) responseEntity.getBody();
        //assert
        assertEquals(expectedStatusCode, resultStatusCode);
        assertEquals(expectedBody, actualBody);
    }

    @Test
    void getPlaylistByIdToHighValue() {
        //arrange
        HttpStatus expectedStatusCode = HttpStatus.NOT_FOUND;
        PlaylistReturnDTO expectedBody = null;

        //act
        ResponseEntity<?> responseEntity = playlistController.getPlaylistById(200L);
        HttpStatus resultStatusCode = responseEntity.getStatusCode();
        PlaylistReturnDTO actualBody = (PlaylistReturnDTO) responseEntity.getBody();
        //assert
        assertEquals(expectedStatusCode, resultStatusCode);
        assertEquals(expectedBody, actualBody);
    }

    @Test
    void createPlaylist() {
        //arrange
        HttpStatus expectedStatusCode = HttpStatus.CREATED;

        List<Long> songIds = new ArrayList<>();
        songIds.add(1L);
        songIds.add(3L);
        songIds.add(4L);
        PlaylistDTO playlistToAdd = new PlaylistDTO("Frietzaak", songIds);

        List<Song> songs = new ArrayList<>();
        songs.add(new Song(1L, "Kaas", 1L, "C/kaas.pm3"));
        songs.add(new Song(3L, "frietje met", 2L, "C/frietje.pm3"));
        songs.add(new Song(4L, "frietje met, zonder mayo", 2L, "C/frietjeV2.pm3"));

        Long expectedId = 2L;
        String expectedName = "Frietzaak";
        String expectedOwnerName = "Ming";
        int expectedSongListSize = songs.size();

        //act
        ResponseEntity<?> responseEntity = playlistController.createPlaylist(playlistToAdd, token);
        HttpStatus resultStatusCode = responseEntity.getStatusCode();

        ResponseEntity<?> responseEntity2 = playlistController.getPlaylistById(2L);
        PlaylistReturnDTO actualBody2 = (PlaylistReturnDTO) responseEntity2.getBody();
        assert actualBody2 != null;

        Long actualId = actualBody2.getId();
        String actualName = actualBody2.getName();
        String actualOwnerName = actualBody2.getOwnerName();
        int actualSongListSize = actualBody2.getSongs().size();

        //assert
        assertEquals(expectedStatusCode, resultStatusCode);
        assertEquals(expectedId, actualId);
        assertEquals(expectedName, actualName);
        assertEquals(expectedOwnerName, actualOwnerName);
        assertEquals(expectedSongListSize, actualSongListSize);
    }

    @Test
    void createPlaylistEmptyList() {
        //arrange
        HttpStatus expectedStatusCode = HttpStatus.CONFLICT;
        URI expectedBody = URI.create("fail");

        List<Long> songIds = new ArrayList<>();
        PlaylistDTO playlistToAdd = new PlaylistDTO("Frietzaak", songIds);

        //act
        ResponseEntity<URI> responseEntity = playlistController.createPlaylist(playlistToAdd, token);
        HttpStatus resultStatusCode = responseEntity.getStatusCode();
        URI actualBody = responseEntity.getBody();

        //assert
        assertEquals(expectedStatusCode, resultStatusCode);
        assertEquals(expectedBody, actualBody);
    }

    @Test
    void createPlaylistEmptyName() {
        //arrange
        HttpStatus expectedStatusCode = HttpStatus.CONFLICT;
        URI expectedBody = URI.create("fail");

        List<Long> songIds = new ArrayList<>();
        songIds.add(1L);
        songIds.add(3L);
        songIds.add(4L);
        PlaylistDTO playlistToAdd = new PlaylistDTO("", songIds);

        //act
        ResponseEntity<URI> responseEntity = playlistController.createPlaylist(playlistToAdd, token);
        HttpStatus resultStatusCode = responseEntity.getStatusCode();
        URI actualBody = responseEntity.getBody();

        //assert
        assertEquals(expectedStatusCode, resultStatusCode);
        assertEquals(expectedBody, actualBody);
    }

    @Test
    void createPlaylistNullValueName() {
        //arrange
        HttpStatus expectedStatusCode = HttpStatus.CONFLICT;
        URI expectedBody = URI.create("fail");

        List<Long> songIds = new ArrayList<>();
        songIds.add(1L);
        songIds.add(3L);
        songIds.add(4L);
        PlaylistDTO playlistToAdd = new PlaylistDTO(null, songIds);

        //act
        ResponseEntity<URI> responseEntity = playlistController.createPlaylist(playlistToAdd, token);
        HttpStatus resultStatusCode = responseEntity.getStatusCode();
        URI actualBody = responseEntity.getBody();

        //assert
        assertEquals(expectedStatusCode, resultStatusCode);
        assertEquals(expectedBody, actualBody);
    }

    @Test
    void createPlaylistNullValueSongIds() {
        //arrange
        HttpStatus expectedStatusCode = HttpStatus.CONFLICT;
        URI expectedBody = URI.create("fail");

        List<Long> songIds = null;
        PlaylistDTO playlistToAdd = new PlaylistDTO("Frietzaak", songIds);

        //act
        ResponseEntity<URI> responseEntity = playlistController.createPlaylist(playlistToAdd, token);
        HttpStatus resultStatusCode = responseEntity.getStatusCode();
        URI actualBody = responseEntity.getBody();

        //assert
        assertEquals(expectedStatusCode, resultStatusCode);
        assertEquals(expectedBody, actualBody);
    }

    @Test
    void updatePlaylist() {
        //arrange
        HttpStatus expectedStatusCode = HttpStatus.NO_CONTENT;
        String expectedName = "JohnsPlaylist";
        String expectedOwnerName = "Ming";
        int expectedSongListSize = 4;

        List<Long> songIds = new ArrayList<>();
        songIds.add(1L);
        songIds.add(3L);
        songIds.add(4L);
        songIds.add(6L);
        PlaylistDTO playlistToAdd = new PlaylistDTO("JohnsPlaylist", songIds);

        //act
        ResponseEntity<?> responseEntity = playlistController.updatePlaylist(playlistToAdd, 1L, token);
        HttpStatus actualStatusCode = responseEntity.getStatusCode();
        String actualBody = (String) responseEntity.getBody();

        ResponseEntity<?> responseEntity2 = playlistController.getPlaylistById(1L);
        PlaylistReturnDTO actualBody2 = (PlaylistReturnDTO) responseEntity2.getBody();
        assert actualBody2 != null;

        String actualName = actualBody2.getName();
        String actualOwnerName = actualBody2.getOwnerName();
        int actualSongListSize = actualBody2.getSongs().size();

        //assert
        assertEquals(expectedStatusCode, actualStatusCode);
        assertNull(actualBody);
        assertEquals(expectedName, actualName);
        assertEquals(expectedOwnerName, actualOwnerName);
        assertEquals(expectedSongListSize, actualSongListSize);
    }

    @Test
    void updatePlaylistNullValueName() {
        //arrange
        HttpStatus expectedStatusCode = HttpStatus.NOT_FOUND;
        String expectedBody = null;
        String expectedName = "Gange";
        String expectedOwnerName = "Ming";
        int expectedSongListSize = 7;

        List<Long> songIds = new ArrayList<>();
        songIds.add(1L);
        songIds.add(3L);
        songIds.add(4L);
        songIds.add(6L);
        PlaylistDTO playlistToAdd = new PlaylistDTO(null, songIds);

        //act
        ResponseEntity<?> responseEntity = playlistController.updatePlaylist(playlistToAdd, 1L, token);
        HttpStatus actualStatusCode = responseEntity.getStatusCode();
        String actualBody = (String) responseEntity.getBody();

        ResponseEntity<?> responseEntity2 = playlistController.getPlaylistById(1L);
        PlaylistReturnDTO actualBody2 = (PlaylistReturnDTO) responseEntity2.getBody();
        assert actualBody2 != null;

        String actualName = actualBody2.getName();
        String actualOwnerName = actualBody2.getOwnerName();
        int actualSongListSize = actualBody2.getSongs().size();

        //assert
        assertEquals(expectedStatusCode, actualStatusCode);
        assertEquals(expectedBody, actualBody);
        assertEquals(expectedName, actualName);
        assertEquals(expectedOwnerName, actualOwnerName);
        assertEquals(expectedSongListSize, actualSongListSize);
    }

    @Test
    void updatePlaylistEmptyValueName() {
        //arrange
        HttpStatus expectedStatusCode = HttpStatus.NOT_FOUND;
        String expectedBody = null;
        String expectedName = "Gange";
        String expectedOwnerName = "Ming";
        int expectedSongListSize = 7;

        List<Long> songIds = new ArrayList<>();
        songIds.add(1L);
        songIds.add(3L);
        songIds.add(4L);
        songIds.add(6L);
        PlaylistDTO playlistToAdd = new PlaylistDTO("", songIds);

        //act
        ResponseEntity<?> responseEntity = playlistController.updatePlaylist(playlistToAdd, 1L, token);
        HttpStatus actualStatusCode = responseEntity.getStatusCode();
        String actualBody = (String) responseEntity.getBody();

        ResponseEntity<?> responseEntity2 = playlistController.getPlaylistById(1L);
        PlaylistReturnDTO actualBody2 = (PlaylistReturnDTO) responseEntity2.getBody();
        assert actualBody2 != null;

        String actualName = actualBody2.getName();
        String actualOwnerName = actualBody2.getOwnerName();
        int actualSongListSize = actualBody2.getSongs().size();

        //assert
        assertEquals(expectedStatusCode, actualStatusCode);
        assertEquals(expectedBody, actualBody);
        assertEquals(expectedName, actualName);
        assertEquals(expectedOwnerName, actualOwnerName);
        assertEquals(expectedSongListSize, actualSongListSize);
    }

    @Test
    void updatePlaylistNullValueSongIds() {
        //arrange
        HttpStatus expectedStatusCode = HttpStatus.NOT_FOUND;
        String expectedBody = null;
        String expectedName = "Gange";
        String expectedOwnerName = "Ming";
        int expectedSongListSize = 7;

        PlaylistDTO playlistToAdd = new PlaylistDTO("Frietzaak", null);

        //act
        ResponseEntity<?> responseEntity = playlistController.updatePlaylist(playlistToAdd, 1L, token);
        HttpStatus actualStatusCode = responseEntity.getStatusCode();
        String actualBody = (String) responseEntity.getBody();

        ResponseEntity<?> responseEntity2 = playlistController.getPlaylistById(1L);
        PlaylistReturnDTO actualBody2 = (PlaylistReturnDTO) responseEntity2.getBody();
        assert actualBody2 != null;

        String actualName = actualBody2.getName();
        String actualOwnerName = actualBody2.getOwnerName();
        int actualSongListSize = actualBody2.getSongs().size();

        //assert
        assertEquals(expectedStatusCode, actualStatusCode);
        assertEquals(expectedBody, actualBody);
        assertEquals(expectedName, actualName);
        assertEquals(expectedOwnerName, actualOwnerName);
        assertEquals(expectedSongListSize, actualSongListSize);
    }

    @Test
    void updatePlaylistEmptyValueSongIds() {
        //arrange
        HttpStatus expectedStatusCode = HttpStatus.NOT_FOUND;
        String expectedBody = null;
        String expectedName = "Gange";
        String expectedOwnerName = "Ming";
        int expectedSongListSize = 7;

        List<Long> songIds = new ArrayList<>();
        PlaylistDTO playlistToAdd = new PlaylistDTO("Frietzaak", songIds);

        //act
        ResponseEntity<?> responseEntity = playlistController.updatePlaylist(playlistToAdd, 1L, token);
        HttpStatus actualStatusCode = responseEntity.getStatusCode();
        String actualBody = (String) responseEntity.getBody();

        ResponseEntity<?> responseEntity2 = playlistController.getPlaylistById(1L);
        PlaylistReturnDTO actualBody2 = (PlaylistReturnDTO) responseEntity2.getBody();
        assert actualBody2 != null;

        String actualName = actualBody2.getName();
        String actualOwnerName = actualBody2.getOwnerName();
        int actualSongListSize = actualBody2.getSongs().size();

        //assert
        assertEquals(expectedStatusCode, actualStatusCode);
        assertEquals(expectedBody, actualBody);
        assertEquals(expectedName, actualName);
        assertEquals(expectedOwnerName, actualOwnerName);
        assertEquals(expectedSongListSize, actualSongListSize);
    }

    @Test
    void updatePlaylistNullValuePlaylistDTO() {
        //arrange
        HttpStatus expectedStatusCode = HttpStatus.NOT_FOUND;
        String expectedBody = null;
        String expectedName = "Gange";
        String expectedOwnerName = "Ming";
        int expectedSongListSize = 7;

        //act
        ResponseEntity<?> responseEntity = playlistController.updatePlaylist(null, 1L, token);
        HttpStatus actualStatusCode = responseEntity.getStatusCode();
        String actualBody = (String) responseEntity.getBody();

        ResponseEntity<?> responseEntity2 = playlistController.getPlaylistById(1L);
        PlaylistReturnDTO actualBody2 = (PlaylistReturnDTO) responseEntity2.getBody();
        assert actualBody2 != null;

        String actualName = actualBody2.getName();
        String actualOwnerName = actualBody2.getOwnerName();
        int actualSongListSize = actualBody2.getSongs().size();

        //assert
        assertEquals(expectedStatusCode, actualStatusCode);
        assertEquals(expectedBody, actualBody);
        assertEquals(expectedName, actualName);
        assertEquals(expectedOwnerName, actualOwnerName);
        assertEquals(expectedSongListSize, actualSongListSize);
    }

    @Test
    void updatePlaylistNullValueId() {
        //arrange
        HttpStatus expectedStatusCode = HttpStatus.NOT_FOUND;
        String expectedBody = null;
        String expectedName = "Gange";
        String expectedOwnerName = "Ming";
        int expectedSongListSize = 7;

        List<Long> songIds = new ArrayList<>();
        songIds.add(1L);
        songIds.add(3L);
        songIds.add(4L);
        songIds.add(6L);
        PlaylistDTO playlistToAdd = new PlaylistDTO("Frietzaak", songIds);

        //act
        ResponseEntity<?> responseEntity = playlistController.updatePlaylist(playlistToAdd, null, token);
        HttpStatus actualStatusCode = responseEntity.getStatusCode();
        String actualBody = (String) responseEntity.getBody();

        ResponseEntity<?> responseEntity2 = playlistController.getPlaylistById(1L);
        PlaylistReturnDTO actualBody2 = (PlaylistReturnDTO) responseEntity2.getBody();
        assert actualBody2 != null;

        String actualName = actualBody2.getName();
        String actualOwnerName = actualBody2.getOwnerName();
        int actualSongListSize = actualBody2.getSongs().size();

        //assert
        assertEquals(expectedStatusCode, actualStatusCode);
        assertEquals(expectedBody, actualBody);
        assertEquals(expectedName, actualName);
        assertEquals(expectedOwnerName, actualOwnerName);
        assertEquals(expectedSongListSize, actualSongListSize);
    }

    @Test
    void updatePlaylistNegativeValueId() {
        //arrange
        HttpStatus expectedStatusCode = HttpStatus.NOT_FOUND;
        String expectedBody = null;
        String expectedName = "Gange";
        String expectedOwnerName = "Ming";
        int expectedSongListSize = 7;

        List<Long> songIds = new ArrayList<>();
        songIds.add(1L);
        songIds.add(3L);
        songIds.add(4L);
        songIds.add(6L);
        PlaylistDTO playlistToAdd = new PlaylistDTO("Frietzaak", songIds);

        //act
        ResponseEntity<?> responseEntity = playlistController.updatePlaylist(playlistToAdd, -1L, token);
        HttpStatus actualStatusCode = responseEntity.getStatusCode();
        String actualBody = (String) responseEntity.getBody();

        ResponseEntity<?> responseEntity2 = playlistController.getPlaylistById(1L);
        PlaylistReturnDTO actualBody2 = (PlaylistReturnDTO) responseEntity2.getBody();
        assert actualBody2 != null;

        String actualName = actualBody2.getName();
        String actualOwnerName = actualBody2.getOwnerName();
        int actualSongListSize = actualBody2.getSongs().size();

        //assert
        assertEquals(expectedStatusCode, actualStatusCode);
        assertEquals(expectedBody, actualBody);
        assertEquals(expectedName, actualName);
        assertEquals(expectedOwnerName, actualOwnerName);
        assertEquals(expectedSongListSize, actualSongListSize);
    }

    @Test
    void updatePlaylistToHighValueId() {
        //arrange
        HttpStatus expectedStatusCode = HttpStatus.NOT_FOUND;
        String expectedBody = null;
        String expectedName = "Gange";
        String expectedOwnerName = "Ming";
        int expectedSongListSize = 7;

        List<Long> songIds = new ArrayList<>();
        songIds.add(1L);
        songIds.add(3L);
        songIds.add(4L);
        songIds.add(6L);
        PlaylistDTO playlistToAdd = new PlaylistDTO("Frietzaak", songIds);

        //act
        ResponseEntity<?> responseEntity = playlistController.updatePlaylist(playlistToAdd, 200L, token);
        HttpStatus actualStatusCode = responseEntity.getStatusCode();
        String actualBody = (String) responseEntity.getBody();

        ResponseEntity<?> responseEntity2 = playlistController.getPlaylistById(1L);
        PlaylistReturnDTO actualBody2 = (PlaylistReturnDTO) responseEntity2.getBody();
        assert actualBody2 != null;

        String actualName = actualBody2.getName();
        String actualOwnerName = actualBody2.getOwnerName();
        int actualSongListSize = actualBody2.getSongs().size();

        //assert
        assertEquals(expectedStatusCode, actualStatusCode);
        assertEquals(expectedBody, actualBody);
        assertEquals(expectedName, actualName);
        assertEquals(expectedOwnerName, actualOwnerName);
        assertEquals(expectedSongListSize, actualSongListSize);
    }

    @Test
    void deletePlaylist() {
        //arrange
        HttpStatus expectedStatusCode = HttpStatus.OK;
        HttpStatus expectedStatusCodeGet = HttpStatus.NOT_FOUND;

        //act
        ResponseEntity<?> responseEntity = playlistController.deletePlaylist(1L, token);
        HttpStatus actualStatusCode = responseEntity.getStatusCode();

        ResponseEntity<?> responseEntity2 = playlistController.getPlaylistById(1L);
        HttpStatus actualStatusCodeGet = responseEntity2.getStatusCode();

        //assert
        assertEquals(expectedStatusCode, actualStatusCode);
        assertEquals(expectedStatusCodeGet, actualStatusCodeGet);
    }

    @Test
    void deletePlaylistNullValueId() {
        //arrange
        HttpStatus expectedStatusCode = HttpStatus.NOT_FOUND;
        HttpStatus expectedStatusCodeGet = HttpStatus.OK;

        //act
        ResponseEntity<?> responseEntity = playlistController.deletePlaylist(null, token);
        HttpStatus actualStatusCode = responseEntity.getStatusCode();

        ResponseEntity<?> responseEntity2 = playlistController.getPlaylistById(1L);
        HttpStatus actualStatusCodeGet = responseEntity2.getStatusCode();

        //assert
        assertEquals(expectedStatusCode, actualStatusCode);
        assertEquals(expectedStatusCodeGet, actualStatusCodeGet);
    }

    @Test
    void deletePlaylistNegativeValueId() {
        //arrange
        HttpStatus expectedStatusCode = HttpStatus.NOT_FOUND;
        HttpStatus expectedStatusCodeGet = HttpStatus.OK;

        //act
        ResponseEntity<?> responseEntity = playlistController.deletePlaylist(-1L, token);
        HttpStatus actualStatusCode = responseEntity.getStatusCode();

        ResponseEntity<?> responseEntity2 = playlistController.getPlaylistById(1L);
        HttpStatus actualStatusCodeGet = responseEntity2.getStatusCode();

        //assert
        assertEquals(expectedStatusCode, actualStatusCode);
        assertEquals(expectedStatusCodeGet, actualStatusCodeGet);
    }

    @Test
    void deletePlaylistToHighValueId() {
        //arrange
        HttpStatus expectedStatusCode = HttpStatus.NOT_FOUND;
        HttpStatus expectedStatusCodeGet = HttpStatus.OK;

        //act
        ResponseEntity<?> responseEntity = playlistController.deletePlaylist(200L, token);
        HttpStatus actualStatusCode = responseEntity.getStatusCode();

        ResponseEntity<?> responseEntity2 = playlistController.getPlaylistById(1L);
        HttpStatus actualStatusCodeGet = responseEntity2.getStatusCode();

        //assert
        assertEquals(expectedStatusCode, actualStatusCode);
        assertEquals(expectedStatusCodeGet, actualStatusCodeGet);
    }
}
