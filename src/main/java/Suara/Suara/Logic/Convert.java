package Suara.Suara.Logic;

import Suara.Suara.DTO.UserDTO;
import Suara.Suara.Model.Role;
import Suara.Suara.Model.SuaraUser;

import java.util.Collection;

public class Convert {
    public static SuaraUser toSuaraUser(UserDTO userDTO, Collection<Role> roles) {
        return new SuaraUser(0L, userDTO.getUsername(), userDTO.getPassword(), roles);
    }
}
