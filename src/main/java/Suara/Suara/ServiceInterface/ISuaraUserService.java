package Suara.Suara.ServiceInterface;

import Suara.Suara.DTO.UserDTO;
import Suara.Suara.Model.SuaraUser;

public interface ISuaraUserService {
    SuaraUser getSuaraUserById(Long id);

    SuaraUser saveSuaraUser(UserDTO userDTO);

    boolean updateSuaraUser(Long id, String roleName);

    boolean deleteUser(Long id);

    SuaraUser getSuaraUserByUsername(String username);
}
