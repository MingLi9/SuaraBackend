package Suara.Suara.MockData;

import Suara.Suara.Model.Role;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RoleMockDALTest {
    private final RoleMockDAL roleMockDAL = new RoleMockDAL();

    @Test
    void saveRoleHappy() {
        //arrange
        Role roleToAdd = new Role(1L, "testing");
        Long expectedId = 4L;
        String expectedName = roleToAdd.getName();

        //act
        Role result = roleMockDAL.saveRole(roleToAdd);
        Long actualId = result.getId();
        String actualName = result.getName();

        //assert
        assertEquals(expectedId, actualId);
        assertEquals(expectedName, actualName);
    }

    @Test
    void getRoleByNameHappy() {
        //arrange
        Role expectedRole = new Role(1L, "User");
        Long expectedId = expectedRole.getId();

        //act
        Role actualRole = roleMockDAL.getRoleByName("User");
        Long actualId = actualRole.getId();

        //assert
        assertEquals(expectedId, actualId);
    }

    @Test
    void getRoleByNameFail() {
        //arrange
        Role expectedRole = null;

        //act
        Role actualRole = roleMockDAL.getRoleByName("-1");

        //assert
        assertEquals(expectedRole, actualRole);
    }

    @Test
    void getAllRoles() {
        //arrange
        int expectedSize = 3;

        //act
        int actualSize = roleMockDAL.getAllRoles().size();

        //assert
        assertEquals(expectedSize, actualSize);
    }
}