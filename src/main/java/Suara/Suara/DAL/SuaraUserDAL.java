package Suara.Suara.DAL;

import Suara.Suara.DALInterface.ISuaraUserDAL;
import Suara.Suara.Model.SuaraUser;
import Suara.Suara.Repository.SuaraUserRepo;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository("UserDAL")
public class SuaraUserDAL implements ISuaraUserDAL {
    private final SuaraUserRepo suaraUserRepo;

    public SuaraUserDAL(@Qualifier("UserRepo") SuaraUserRepo suaraUserRepo) {
        this.suaraUserRepo = suaraUserRepo;
    }

    public SuaraUser getSuaraUserByName(String name) {
        return suaraUserRepo.findSuaraUserByUsername(name);
    }

    public SuaraUser getSuaraUserById(Long id) {
        return suaraUserRepo.findSuaraUserById(id);
    }

    public SuaraUser saveSuaraUser(SuaraUser suaraUser) {
        return suaraUserRepo.save(suaraUser);
    }

    public void updateUser(SuaraUser suaraUser) {
        suaraUserRepo.save(suaraUser);
    }

    public void deleteUser(Long id) {
        suaraUserRepo.deleteById(id);
    }
}
