package Suara.Suara.Controller;

import Suara.Suara.DALInterface.IAlbumDAL;
import Suara.Suara.DALInterface.IRoleDAL;
import Suara.Suara.DALInterface.ISongDAL;
import Suara.Suara.DALInterface.ISuaraUserDAL;
import Suara.Suara.DTO.AlbumDTO;
import Suara.Suara.DTO.AlbumReturnDTO;
import Suara.Suara.MockData.AlbumMockDAL;
import Suara.Suara.MockData.RoleMockDAL;
import Suara.Suara.MockData.SongMockData;
import Suara.Suara.MockData.SuaraUserMockDAL;
import Suara.Suara.Model.Album;
import Suara.Suara.Model.Song;
import Suara.Suara.Service.AlbumService;
import Suara.Suara.Service.RoleService;
import Suara.Suara.Service.SongService;
import Suara.Suara.Service.SuaraUserService;
import Suara.Suara.ServiceInterface.IAlbumService;
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

class AlbumControllerTest {
    private Authentication token;
    private AlbumController albumController;

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

        IAlbumDAL iAlbumDAL = new AlbumMockDAL();

        ISuaraUserDAL iSuaraUserDAL = new SuaraUserMockDAL();

        IRoleDAL iRoleDAL = new RoleMockDAL();
        IRoleService iRoleService = new RoleService(iRoleDAL);

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        ISuaraUserService iSuaraUserService = new SuaraUserService(iSuaraUserDAL, iRoleService, passwordEncoder);

        ISongDAL isongDAL = new SongMockData();
        ISongService isongService = new SongService(isongDAL, iSuaraUserService);

        IAlbumService iAlbumService = new AlbumService(iAlbumDAL, iSuaraUserService, isongService);
        albumController = new AlbumController(iAlbumService);
    }

    @Test
    void getAllAlbum() {
        //arrange
        HttpStatus expectedStatusCode = HttpStatus.OK;
        int expectedBodyAlbums_listLength = 1;

        //act
        ResponseEntity<?> responseEntity = albumController.getAllAlbum();
        HttpStatus resultStatusCode = responseEntity.getStatusCode();
        List<Album> resultBody = (List<Album>) responseEntity.getBody();
        int resultBodyAlbums_listLength = resultBody.size();

        //assert
        assertEquals(expectedStatusCode, resultStatusCode);
        assertEquals(expectedBodyAlbums_listLength, resultBodyAlbums_listLength);
    }

    @Test
    void getAllAlbumByPartNameHappyFullName() {
        //arrange
        HttpStatus expectedStatusCode = HttpStatus.OK;
        int expectedBodyAlbums_listLength = 1;

        //act
        ResponseEntity<?> responseEntity = albumController.getAllAlbumByPartName("Gange");
        HttpStatus resultStatusCode = responseEntity.getStatusCode();
        List<Album> resultBody = (List<Album>) responseEntity.getBody();
        int resultBodyAlbums_listLength = resultBody.size();

        //assert
        assertEquals(expectedStatusCode, resultStatusCode);
        assertEquals(expectedBodyAlbums_listLength, resultBodyAlbums_listLength);
    }

    @Test
    void getAllAlbumByPartNameHappy1Char() {
        //arrange
        HttpStatus expectedStatusCode = HttpStatus.OK;
        int expectedBodyAlbums_listLength = 1;

        //act
        ResponseEntity<?> responseEntity = albumController.getAllAlbumByPartName("g");
        HttpStatus resultStatusCode = responseEntity.getStatusCode();
        List<Album> resultBody = (List<Album>) responseEntity.getBody();
        int resultBodyAlbums_listLength = resultBody.size();

        //assert
        assertEquals(expectedStatusCode, resultStatusCode);
        assertEquals(expectedBodyAlbums_listLength, resultBodyAlbums_listLength);
    }

    @Test
    void getAllAlbumByPartNameHappyEmptyString() {
        //arrange
        HttpStatus expectedStatusCode = HttpStatus.OK;
        int expectedBodyAlbums_listLength = 1;

        //act
        ResponseEntity<?> responseEntity = albumController.getAllAlbumByPartName("");
        HttpStatus resultStatusCode = responseEntity.getStatusCode();
        List<Album> resultBody = (List<Album>) responseEntity.getBody();
        int resultBodyAlbums_listLength = resultBody.size();

        //assert
        assertEquals(expectedStatusCode, resultStatusCode);
        assertEquals(expectedBodyAlbums_listLength, resultBodyAlbums_listLength);
    }

    @Test
    void getAllAlbumByPartNameFailNonexisting() {
        //arrange
        HttpStatus expectedStatusCode = HttpStatus.NOT_FOUND;

        //act
        ResponseEntity<?> responseEntity = albumController.getAllAlbumByPartName("K");
        HttpStatus resultStatusCode = responseEntity.getStatusCode();

        //assert
        assertEquals(expectedStatusCode, resultStatusCode);
    }

    @Test
    void getAlbumById() {
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

        AlbumReturnDTO expectedBody = new AlbumReturnDTO(1L, "Gange", "Ming", songs);
        Long expectedId = expectedBody.getId();
        String expectedName = expectedBody.getName();
        String expectedOwnerName = expectedBody.getOwnerName();
        int expectedSongListSize = expectedBody.getSongs().size();

        //act
        ResponseEntity<?> responseEntity = albumController.getAlbumById(1L);
        HttpStatus resultStatusCode = responseEntity.getStatusCode();
        AlbumReturnDTO actualBody = (AlbumReturnDTO) responseEntity.getBody();
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
    void getAlbumByIdNullValue() {
        //arrange
        HttpStatus expectedStatusCode = HttpStatus.NOT_FOUND;
        AlbumReturnDTO expectedBody = null;

        //act
        ResponseEntity<?> responseEntity = albumController.getAlbumById(null);
        HttpStatus resultStatusCode = responseEntity.getStatusCode();
        AlbumReturnDTO actualBody = (AlbumReturnDTO) responseEntity.getBody();
        //assert
        assertEquals(expectedStatusCode, resultStatusCode);
        assertEquals(expectedBody, actualBody);
    }

    @Test
    void getAlbumByIdNegativeValue() {
        //arrange
        HttpStatus expectedStatusCode = HttpStatus.NOT_FOUND;
        AlbumReturnDTO expectedBody = null;

        //act
        ResponseEntity<?> responseEntity = albumController.getAlbumById(-1L);
        HttpStatus resultStatusCode = responseEntity.getStatusCode();
        AlbumReturnDTO actualBody = (AlbumReturnDTO) responseEntity.getBody();
        //assert
        assertEquals(expectedStatusCode, resultStatusCode);
        assertEquals(expectedBody, actualBody);
    }

    @Test
    void getAlbumByIdToHighValue() {
        //arrange
        HttpStatus expectedStatusCode = HttpStatus.NOT_FOUND;
        AlbumReturnDTO expectedBody = null;

        //act
        ResponseEntity<?> responseEntity = albumController.getAlbumById(200L);
        HttpStatus resultStatusCode = responseEntity.getStatusCode();
        AlbumReturnDTO actualBody = (AlbumReturnDTO) responseEntity.getBody();
        //assert
        assertEquals(expectedStatusCode, resultStatusCode);
        assertEquals(expectedBody, actualBody);
    }

    @Test
    void createAlbum() {
        //arrange
        HttpStatus expectedStatusCode = HttpStatus.CREATED;

        List<Long> songIds = new ArrayList<>();
        songIds.add(1L);
        songIds.add(3L);
        songIds.add(4L);
        AlbumDTO albumToAdd = new AlbumDTO("Frietzaak", songIds);

        List<Song> songs = new ArrayList<>();
        songs.add(new Song(1L, "Kaas", 1L, "C/kaas.pm3"));
        songs.add(new Song(3L, "frietje met", 2L, "C/frietje.pm3"));
        songs.add(new Song(4L, "frietje met, zonder mayo", 2L, "C/frietjeV2.pm3"));

        Long expectedId = 2L;
        String expectedName = "Frietzaak";
        String expectedOwnerName = "Ming";
        int expectedSongListSize = songs.size();

        //act
        ResponseEntity<URI> responseEntity = albumController.createAlbum(albumToAdd, token);
        HttpStatus resultStatusCode = responseEntity.getStatusCode();

        ResponseEntity<?> responseEntity2 = albumController.getAlbumById(2L);
        AlbumReturnDTO actualBody2 = (AlbumReturnDTO) responseEntity2.getBody();
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
    void createAlbumEmptyList() {
        //arrange
        HttpStatus expectedStatusCode = HttpStatus.CONFLICT;
        URI expectedBody = URI.create("fail");

        List<Long> songIds = new ArrayList<>();
        AlbumDTO albumToAdd = new AlbumDTO("Frietzaak", songIds);

        //act
        ResponseEntity<URI> responseEntity = albumController.createAlbum(albumToAdd, token);
        HttpStatus resultStatusCode = responseEntity.getStatusCode();
        URI actualBody = responseEntity.getBody();

        //assert
        assertEquals(expectedStatusCode, resultStatusCode);
        assertEquals(expectedBody, actualBody);
    }

    @Test
    void createAlbumEmptyName() {
        //arrange
        HttpStatus expectedStatusCode = HttpStatus.CONFLICT;
        URI expectedBody = URI.create("fail");

        List<Long> songIds = new ArrayList<>();
        songIds.add(1L);
        songIds.add(3L);
        songIds.add(4L);
        AlbumDTO albumToAdd = new AlbumDTO("", songIds);

        //act
        ResponseEntity<URI> responseEntity = albumController.createAlbum(albumToAdd, token);
        HttpStatus resultStatusCode = responseEntity.getStatusCode();
        URI actualBody = responseEntity.getBody();

        //assert
        assertEquals(expectedStatusCode, resultStatusCode);
        assertEquals(expectedBody, actualBody);
    }

    @Test
    void createAlbumNullValueName() {
        //arrange
        HttpStatus expectedStatusCode = HttpStatus.CONFLICT;
        URI expectedBody = URI.create("fail");

        List<Long> songIds = new ArrayList<>();
        songIds.add(1L);
        songIds.add(3L);
        songIds.add(4L);
        AlbumDTO albumToAdd = new AlbumDTO(null, songIds);

        //act
        ResponseEntity<URI> responseEntity = albumController.createAlbum(albumToAdd, token);
        HttpStatus resultStatusCode = responseEntity.getStatusCode();
        URI actualBody = responseEntity.getBody();

        //assert
        assertEquals(expectedStatusCode, resultStatusCode);
        assertEquals(expectedBody, actualBody);
    }

    @Test
    void createAlbumNullValueSongIds() {
        //arrange
        HttpStatus expectedStatusCode = HttpStatus.CONFLICT;
        URI expectedBody = URI.create("fail");

        List<Long> songIds = null;
        AlbumDTO albumToAdd = new AlbumDTO("Frietzaak", songIds);

        //act
        ResponseEntity<URI> responseEntity = albumController.createAlbum(albumToAdd, token);
        HttpStatus resultStatusCode = responseEntity.getStatusCode();
        URI actualBody = responseEntity.getBody();

        //assert
        assertEquals(expectedStatusCode, resultStatusCode);
        assertEquals(expectedBody, actualBody);
    }

    @Test
    void deleteAlbum() {
        //arrange
        HttpStatus expectedStatusCode = HttpStatus.OK;
        HttpStatus expectedStatusCodeGet = HttpStatus.NOT_FOUND;

        //act
        ResponseEntity<?> responseEntity = albumController.deleteAlbum(1L, token);
        HttpStatus actualStatusCode = responseEntity.getStatusCode();

        ResponseEntity<?> responseEntity2 = albumController.getAlbumById(1L);
        HttpStatus actualStatusCodeGet = responseEntity2.getStatusCode();

        //assert
        assertEquals(expectedStatusCode, actualStatusCode);
        assertEquals(expectedStatusCodeGet, actualStatusCodeGet);
    }

    @Test
    void deleteAlbumNullValueId() {
        //arrange
        HttpStatus expectedStatusCode = HttpStatus.NOT_FOUND;
        HttpStatus expectedStatusCodeGet = HttpStatus.OK;

        //act
        ResponseEntity<?> responseEntity = albumController.deleteAlbum(null, token);
        HttpStatus actualStatusCode = responseEntity.getStatusCode();

        ResponseEntity<?> responseEntity2 = albumController.getAlbumById(1L);
        HttpStatus actualStatusCodeGet = responseEntity2.getStatusCode();

        //assert
        assertEquals(expectedStatusCode, actualStatusCode);
        assertEquals(expectedStatusCodeGet, actualStatusCodeGet);
    }

    @Test
    void deleteAlbumNegativeValueId() {
        //arrange
        HttpStatus expectedStatusCode = HttpStatus.NOT_FOUND;
        HttpStatus expectedStatusCodeGet = HttpStatus.OK;

        //act
        ResponseEntity<?> responseEntity = albumController.deleteAlbum(-1L, token);
        HttpStatus actualStatusCode = responseEntity.getStatusCode();

        ResponseEntity<?> responseEntity2 = albumController.getAlbumById(1L);
        HttpStatus actualStatusCodeGet = responseEntity2.getStatusCode();

        //assert
        assertEquals(expectedStatusCode, actualStatusCode);
        assertEquals(expectedStatusCodeGet, actualStatusCodeGet);
    }

    @Test
    void deleteAlbumToHighValueId() {
        //arrange
        HttpStatus expectedStatusCode = HttpStatus.NOT_FOUND;
        HttpStatus expectedStatusCodeGet = HttpStatus.OK;

        //act
        ResponseEntity<?> responseEntity = albumController.deleteAlbum(200L, token);
        HttpStatus actualStatusCode = responseEntity.getStatusCode();

        ResponseEntity<?> responseEntity2 = albumController.getAlbumById(1L);
        HttpStatus actualStatusCodeGet = responseEntity2.getStatusCode();

        //assert
        assertEquals(expectedStatusCode, actualStatusCode);
        assertEquals(expectedStatusCodeGet, actualStatusCodeGet);
    }
}
