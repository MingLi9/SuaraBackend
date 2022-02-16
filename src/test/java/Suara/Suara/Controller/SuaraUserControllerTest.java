package Suara.Suara.Controller;

import Suara.Suara.DALInterface.IRoleDAL;
import Suara.Suara.DALInterface.ISuaraUserDAL;
import Suara.Suara.DTO.UserDTO;
import Suara.Suara.MockData.RoleMockDAL;
import Suara.Suara.MockData.SuaraUserMockDAL;
import Suara.Suara.Model.Role;
import Suara.Suara.Model.SuaraUser;
import Suara.Suara.Service.RoleService;
import Suara.Suara.Service.SuaraUserService;
import Suara.Suara.ServiceInterface.IRoleService;
import Suara.Suara.ServiceInterface.ISuaraUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.net.URI;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class SuaraUserControllerTest {
    private SuaraUserController suaraUserController;

    @BeforeEach
    void setUp() {
        IRoleDAL iRoleDAL = new RoleMockDAL();
        IRoleService iRoleService = new RoleService(iRoleDAL);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        ISuaraUserDAL iSuaraUserDAL = new SuaraUserMockDAL();
        ISuaraUserService iSuaraUserService = new SuaraUserService(iSuaraUserDAL, iRoleService, passwordEncoder);
        suaraUserController = new SuaraUserController(iSuaraUserService);
    }

    @Test
    void getUserById() {
        //arrange
        HttpStatus expectedStatus = HttpStatus.OK;
        String expectedUsername = "Ming";
        String notExpectedPassword = "Ming123!";
        int expectedRoleListSize = 3;

        //act
        ResponseEntity<SuaraUser> responseEntity = suaraUserController.getUserById(1L);
        HttpStatus actualStatus = responseEntity.getStatusCode();
        SuaraUser actualBody = responseEntity.getBody();
        assert actualBody != null;

        String actualUsername = actualBody.getUsername();
        String actualPassword = actualBody.getPassword();
        int actualRoleListSize = actualBody.getRoles().size();

        //assert
        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedUsername, actualUsername);
        assertNotEquals(notExpectedPassword, actualPassword);
        assertEquals(expectedRoleListSize, actualRoleListSize);
    }

    @Test
    void getUserByIdNegativeValue() {
        //arrange
        HttpStatus expectedStatus = HttpStatus.NOT_FOUND;

        //act
        ResponseEntity<SuaraUser> responseEntity = suaraUserController.getUserById(-1L);
        HttpStatus actualStatus = responseEntity.getStatusCode();

        //assert
        assertEquals(expectedStatus, actualStatus);
    }

    @Test
    void getUserByIdZeroValue() {
        //arrange
        HttpStatus expectedStatus = HttpStatus.NOT_FOUND;

        //act
        ResponseEntity<SuaraUser> responseEntity = suaraUserController.getUserById(0L);
        HttpStatus actualStatus = responseEntity.getStatusCode();

        //assert
        assertEquals(expectedStatus, actualStatus);
    }

    @Test
    void getUserByIdToHighValue() {
        //arrange
        HttpStatus expectedStatus = HttpStatus.NOT_FOUND;

        //act
        ResponseEntity<SuaraUser> responseEntity = suaraUserController.getUserById(100L);
        HttpStatus actualStatus = responseEntity.getStatusCode();

        //assert
        assertEquals(expectedStatus, actualStatus);
    }

    @Test
    void getUserByIdNullValue() {
        //arrange
        HttpStatus expectedStatus = HttpStatus.NOT_FOUND;

        //act
        ResponseEntity<SuaraUser> responseEntity = suaraUserController.getUserById(null);
        HttpStatus actualStatus = responseEntity.getStatusCode();

        //assert
        assertEquals(expectedStatus, actualStatus);
    }

    @Test
    void createUser() {
        //arrange
        HttpStatus expectedStatus = HttpStatus.CREATED;
        HttpStatus expectedStatusGet = HttpStatus.OK;
        Long expectedId = 4L;
        String expectedUsername = "Testuser";
        String notExpectedPassword = "TestTest123!";
        UserDTO userDTO = new UserDTO(expectedUsername, notExpectedPassword);
        int expectedRoleListSize = 1;

        //act
        ResponseEntity<?> responseEntity = suaraUserController.createUser(userDTO);
        HttpStatus actualStatus = responseEntity.getStatusCode();

        ResponseEntity<SuaraUser> responseEntity2 = suaraUserController.getUserById(4L);
        HttpStatus actualStatusGet = responseEntity2.getStatusCode();
        SuaraUser actualBody = responseEntity2.getBody();
        assert actualBody != null;

        Long actualId = actualBody.getId();
        String actualUsername = actualBody.getUsername();
        String actualPassword = actualBody.getPassword();
        int actualRoleListSize = actualBody.getRoles().size();

        //assert
        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedStatusGet, actualStatusGet);
        assertEquals(expectedId, actualId);
        assertEquals(expectedUsername, actualUsername);
        assertNotEquals(notExpectedPassword, actualPassword);
        assertEquals(expectedRoleListSize, actualRoleListSize);
    }

    @Test
    void createUserNullValueUsername() {
        createFail(null, "TestTest123!");
    }

    private void createFail(String expectedUsername, String notExpectedPassword) {
        //arrange
        HttpStatus expectedStatus = HttpStatus.CONFLICT;
        UserDTO userDTO = new UserDTO(expectedUsername, notExpectedPassword);
        URI expectedBody = URI.create("fail");

        //act
        ResponseEntity<URI> responseEntity = suaraUserController.createUser(userDTO);
        HttpStatus actualStatus = responseEntity.getStatusCode();
        URI actualbody = responseEntity.getBody();

        //assert
        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedBody, actualbody);
    }

    @Test
    void createUserEmptyValueUsername() {
        createFail("", "TestTest123!");
    }

    @Test
    void createUserNullValuePassword() {
        createFail("TestUser", null);
    }

    @Test
    void createUserEmptyValuePassword() {
        createFail("TestUser", "");
    }

    @Test
    void createUserNotLongEnoughValuePassword() {
        createFail("TestUser", "Te2!");
    }

    @Test
    void createUserNoCapitalValuePassword() {
        createFail("TestUser", "test123!");
    }

    @Test
    void createUserNoLowercaseValuePassword() {
        createFail("TestUser", "TEST123!");
    }

    @Test
    void createUserNoNumbersValuePassword() {
        createFail("TestUser", "Testtes!");
    }

    @Test
    void createUserNoSymbolValuePassword() {
        createFail("TestUser", "Test1234");
    }

    @Test
    void updateUser() {
        //arrange
        HttpStatus expectedStatus = HttpStatus.NO_CONTENT;

        //act
        ResponseEntity<?> responseEntity = suaraUserController.updateUser(1L, "Creator");
        HttpStatus actualStatus = responseEntity.getStatusCode();
        int expectedRoleListSize = 2;

        ResponseEntity<SuaraUser> responseEntity2 = suaraUserController.getUserById(1L);
        SuaraUser actualBody = responseEntity2.getBody();
        assert actualBody != null;

        Collection<Role> roles = actualBody.getRoles();
        int actualRoleListSize = roles.size();

        //assert
        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedRoleListSize, actualRoleListSize);
    }

    @Test
    void updateUserNullValueId() {
        //arrange
        HttpStatus expectedStatus = HttpStatus.CONFLICT;

        //act
        ResponseEntity<?> responseEntity = suaraUserController.updateUser(null, "Creator");
        HttpStatus actualStatus = responseEntity.getStatusCode();

        //assert
        assertEquals(expectedStatus, actualStatus);
    }

    @Test
    void updateUserNegativeValueId() {
        //arrange
        HttpStatus expectedStatus = HttpStatus.CONFLICT;

        //act
        ResponseEntity<?> responseEntity = suaraUserController.updateUser(-1L, "Creator");
        HttpStatus actualStatus = responseEntity.getStatusCode();

        //assert
        assertEquals(expectedStatus, actualStatus);
    }

    @Test
    void updateUserZeroValueId() {
        UpdateUserFail(0L, "Creator");
    }

    private void UpdateUserFail(Long id, String roleName) {
        //arrange
        HttpStatus expectedStatus = HttpStatus.CONFLICT;

        //act
        ResponseEntity<?> responseEntity = suaraUserController.updateUser(id, roleName);
        HttpStatus actualStatus = responseEntity.getStatusCode();

        //assert
        assertEquals(expectedStatus, actualStatus);
    }

    @Test
    void updateUserToHighValueId() {
        UpdateUserFail(100L, "Creator");
    }

    @Test
    void updateUserNullValueRolename() {
        UpdateUserFail(1L, null);
    }

    @Test
    void updateUserEmptyValueRolename() {
        UpdateUserFail(1L, "");
    }

    @Test
    void updateUserWrongValueRolename() {
        UpdateUserFail(1L, "creat");
    }

    @Test
    void deleteUser() {
        //arrange
        HttpStatus expectedStatus = HttpStatus.OK;
        HttpStatus expectedStatusGet = HttpStatus.NOT_FOUND;

        //act
        ResponseEntity<?> responseEntity = suaraUserController.deleteUser(1L);
        HttpStatus actualStatus = responseEntity.getStatusCode();

        ResponseEntity<?> responseEntityGet = suaraUserController.getUserById(1L);
        HttpStatus actualStatusGet = responseEntityGet.getStatusCode();
        //assert
        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedStatusGet, actualStatusGet);
    }

    @Test
    void deleteUserNullValue() {
        //arrange
        HttpStatus expectedStatus = HttpStatus.NOT_FOUND;

        //act
        ResponseEntity<?> responseEntity = suaraUserController.deleteUser(null);
        HttpStatus actualStatus = responseEntity.getStatusCode();

        //assert
        assertEquals(expectedStatus, actualStatus);
    }

    @Test
    void deleteUserNegativeValue() {
        //arrange
        HttpStatus expectedStatus = HttpStatus.NOT_FOUND;

        //act
        ResponseEntity<?> responseEntity = suaraUserController.deleteUser(-1L);
        HttpStatus actualStatus = responseEntity.getStatusCode();

        //assert
        assertEquals(expectedStatus, actualStatus);
    }

    @Test
    void deleteUserZeroValue() {
        //arrange
        HttpStatus expectedStatus = HttpStatus.NOT_FOUND;

        //act
        ResponseEntity<?> responseEntity = suaraUserController.deleteUser(0L);
        HttpStatus actualStatus = responseEntity.getStatusCode();

        //assert
        assertEquals(expectedStatus, actualStatus);
    }

    @Test
    void deleteUserToHighValue() {
        //arrange
        HttpStatus expectedStatus = HttpStatus.NOT_FOUND;

        //act
        ResponseEntity<?> responseEntity = suaraUserController.deleteUser(100L);
        HttpStatus actualStatus = responseEntity.getStatusCode();

        //assert
        assertEquals(expectedStatus, actualStatus);
    }
}