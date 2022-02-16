package Suara.Suara.MockData;

import Suara.Suara.DALInterface.ISuaraUserDAL;
import Suara.Suara.Model.Role;
import Suara.Suara.Model.SuaraUser;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SuaraUserMockDAL implements ISuaraUserDAL {

    private List<SuaraUser> users = new ArrayList<>();

    public SuaraUserMockDAL() {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        Collection<Role> admin = new ArrayList<>();
        Collection<Role> creator = new ArrayList<>();
        Collection<Role> user = new ArrayList<>();
        Role rUser = new Role(1L, "User");
        Role rCreator = new Role(2L, "Creator");
        Role rAdmin = new Role(3L, "Admin");
        admin.add(rUser);
        admin.add(rCreator);
        admin.add(rAdmin);
        creator.add(rUser);
        creator.add(rCreator);
        user.add(rUser);

        users.add(new SuaraUser(1L, "Ming", passwordEncoder.encode("Ming123!"), admin));
        users.add(new SuaraUser(2L, "Li", passwordEncoder.encode("Li12345!"), creator));
        users.add(new SuaraUser(3L, "Sen", passwordEncoder.encode("Sen1234!"), user));
    }

    public SuaraUser getSuaraUserByName(String name) {
        for (SuaraUser user : users) {
            if (user.getUsername().equals(name)) {
                return user;
            }
        }
        return null;
    }

    public SuaraUser getSuaraUserById(Long id) {
        for (SuaraUser user : users) {
            if (user.getId().equals(id)) {
                return user;
            }
        }
        return null;
    }

    public SuaraUser saveSuaraUser(SuaraUser suaraUser) {
        Long newId = users.get(users.size() - 1).getId() + 1;
        suaraUser.setId(newId);
        users.add(suaraUser);
        return suaraUser;
    }

    public void updateUser(SuaraUser suaraUser) {
        int index = 0;
        for (SuaraUser user : users) {
            if (user.getId().equals(suaraUser.getId())) {
                break;
            }
            index++;
        }
        if (index == users.size()) {
            return;
        }
        users.set(index, suaraUser);
    }

    public void deleteUser(Long id) {
        int index = 0;
        for (SuaraUser user : users) {
            if (user.getId().equals(id)) {
                break;
            }
            index++;
        }
        if (index == users.size()) {
            return;
        }
        users.remove(index);
    }
}
