package Suara.Suara.DALInterface;

import Suara.Suara.Model.SuaraUser;

public interface ISuaraUserDAL {
    SuaraUser getSuaraUserByName(String name);

    SuaraUser getSuaraUserById(Long id);

    SuaraUser saveSuaraUser(SuaraUser suaraUser);

    void updateUser(SuaraUser suaraUser);

    void deleteUser(Long id);
}
