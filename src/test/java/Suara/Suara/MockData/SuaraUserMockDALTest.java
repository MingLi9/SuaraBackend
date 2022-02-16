package Suara.Suara.MockData;

import Suara.Suara.Model.Role;
import Suara.Suara.Model.SuaraUser;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SuaraUserMockDALTest {

    private final SuaraUserMockDAL suaraUserMockDAL = new SuaraUserMockDAL();

    @Test
    void getSuaraUsersByUsername(){
        //arrange
        Long expectedId = 1L;

        //act
        SuaraUser result = suaraUserMockDAL.getSuaraUserByName("Ming");
        Long actualId = result.getId();

        //assert
        assertEquals(expectedId, actualId);
    }

    @Test
    void getSuaraUsersByUsernameNullValue(){
        //arrange
        SuaraUser expectedValue = null;

        //act
        SuaraUser actualValue = suaraUserMockDAL.getSuaraUserByName(null);

        //assert
        assertEquals(expectedValue, actualValue);
    }

    @Test
    void getSuaraUsersByUsernameEmpltyValue(){
        //arrange
        SuaraUser expectedValue = null;

        //act
        SuaraUser actualValue = suaraUserMockDAL.getSuaraUserByName("");

        //assert
        assertEquals(expectedValue, actualValue);
    }

    @Test
    void getSuaraUserByName(){
        //arrange
        String expectedName = "Li";

        //act
        SuaraUser result = suaraUserMockDAL.getSuaraUserById(2L);
        String actualName = result.getUsername();

        //assert
        assertEquals(expectedName, actualName);
    }

    @Test
    void getSuaraUserByNameNullValue(){
        //arrange
        SuaraUser expectedValue = null;

        //act
        SuaraUser actualValue = suaraUserMockDAL.getSuaraUserById(null);

        //assert
        assertEquals(expectedValue, actualValue);
    }

    @Test
    void getSuaraUserByNameNegativeValue(){
        //arrange
        SuaraUser expectedValue = null;

        //act
        SuaraUser actualValue = suaraUserMockDAL.getSuaraUserById(-2L);

        //assert
        assertEquals(expectedValue, actualValue);
    }

    @Test
    void getSuaraUserByNameToHighValue(){
        //arrange
        SuaraUser expectedValue = null;

        //act
        SuaraUser actualValue = suaraUserMockDAL.getSuaraUserById(200L);

        //assert
        assertEquals(expectedValue, actualValue);
    }

    @Test
    void saveSuaraUser(){
        //arrange
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        Collection<Role> admin = new ArrayList<>();
        admin.add(new Role(1L, "User"));
        admin.add(new Role(2L, "Creator"));
        admin.add(new Role(3L, "Admin"));
        SuaraUser suaraUser = new SuaraUser(4L, "Test", passwordEncoder.encode("Test123!"), admin);
        Long expectedId = suaraUser.getId();
        String notExpectedPW = "Test123!";

        //act
        SuaraUser result = suaraUserMockDAL.saveSuaraUser(suaraUser);
        Long actualId = result.getId();
        String actualPW = result.getPassword();

        //assert
        assertEquals(expectedId, actualId);
        assertNotEquals(notExpectedPW, actualPW);
    }

    @Test
    void saveSuaraUserNegativeValue(){
        //arrange
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        Collection<Role> admin = new ArrayList<>();
        admin.add(new Role(1L, "User"));
        admin.add(new Role(2L, "Creator"));
        admin.add(new Role(3L, "Admin"));
        SuaraUser suaraUser = new SuaraUser(-4L, "Test", passwordEncoder.encode("Test123!"), admin);
        Long expectedId = 4L;

        //act
        SuaraUser actualResult = suaraUserMockDAL.saveSuaraUser(suaraUser);
        Long actualId = actualResult.getId();

        //assert
        assertEquals(expectedId, actualId);
    }

    @Test
    void saveSuaraUserNullValue(){
        //arrange
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        Collection<Role> admin = new ArrayList<>();
        admin.add(new Role(1L, "User"));
        admin.add(new Role(2L, "Creator"));
        admin.add(new Role(3L, "Admin"));
        SuaraUser suaraUser = new SuaraUser(null, "Test", passwordEncoder.encode("Test123!"), admin);
        Long expectedId = 4L;

        //act
        SuaraUser actualResult = suaraUserMockDAL.saveSuaraUser(suaraUser);
        Long actualId = actualResult.getId();

        //assert
        assertEquals(expectedId, actualId);
    }
}