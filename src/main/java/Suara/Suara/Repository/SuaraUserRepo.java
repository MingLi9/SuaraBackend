package Suara.Suara.Repository;

import Suara.Suara.Model.SuaraUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("UserRepo")
public interface SuaraUserRepo extends JpaRepository<SuaraUser, Long> {
    SuaraUser findSuaraUserById(Long id);
    SuaraUser findSuaraUserByUsername(String Username);
}
