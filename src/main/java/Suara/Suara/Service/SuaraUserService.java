package Suara.Suara.Service;

import Suara.Suara.DALInterface.ISuaraUserDAL;
import Suara.Suara.DTO.UserDTO;
import Suara.Suara.Logic.Convert;
import Suara.Suara.Logic.Validate;
import Suara.Suara.Model.Role;
import Suara.Suara.Model.SuaraUser;
import Suara.Suara.ServiceInterface.IRoleService;
import Suara.Suara.ServiceInterface.ISuaraUserService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service("UserService")
public class SuaraUserService implements ISuaraUserService, UserDetailsService {
    private final ISuaraUserDAL suaraUserDAL;
    private final IRoleService roleService;
    private final PasswordEncoder passwordEncoder;

    public SuaraUserService(ISuaraUserDAL suaraUserDAL, IRoleService roleService, PasswordEncoder passwordEncoder) {
        this.suaraUserDAL = suaraUserDAL;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SuaraUser user = suaraUserDAL.getSuaraUserByName(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found in the database");
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }

    public SuaraUser getSuaraUserById(Long id) {
        return suaraUserDAL.getSuaraUserById(id);
    }

    public SuaraUser saveSuaraUser(UserDTO userDTO) {
        try {
            if (userDTO == null) {
                return null;
            }
            String username = userDTO.getUsername();
            String password = userDTO.getPassword();
            if (username == null || username.equals("") || password == null || password.equals("")) {
                return null;
            }
            Collection<Role> userRoles = new ArrayList<>();
            userRoles.add(roleService.getRoleByName("User"));
            SuaraUser suaraUser = Convert.toSuaraUser(userDTO, userRoles);
            if (!Validate.User(suaraUser)) {
                return null;
            }
            suaraUser.setPassword(passwordEncoder.encode(suaraUser.getPassword()));
            return suaraUserDAL.saveSuaraUser(suaraUser);
        } catch (Exception e) {
            return null;
        }
    }

    public boolean updateSuaraUser(Long id, String roleName) {
        try{
            if(id<=0 || roleName == null || roleName.equals(""))
                return false;
            SuaraUser suaraUser = suaraUserDAL.getSuaraUserById(id);
            Collection<Role> roles = getRoles(roleName);
            if(roles.isEmpty())
                return false;
            suaraUser.setRoles(roles);
            suaraUserDAL.updateUser(suaraUser);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public boolean deleteUser(Long id) {
        try{
            if(suaraUserDAL.getSuaraUserById(id) == null){
                return false;
            }
            suaraUserDAL.deleteUser(id);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public SuaraUser getSuaraUserByUsername(String username) {
        return suaraUserDAL.getSuaraUserByName(username);
    }

    private Collection<Role> getRoles(String rolename) {
        Collection<Role> roles = new ArrayList<>();
        List<Role> allRoles = roleService.getAllRoles();

        switch (rolename) {
            case "Admin":
                roles.add(allRoles.get(2));
                roles.add(allRoles.get(1));
                roles.add(allRoles.get(0));
                break;
            case "Creator":
                roles.add(allRoles.get(1));
                roles.add(allRoles.get(0));
                break;
            case "User":
                roles.add(allRoles.get(0));
                break;
            default:
                break;
        }

        return roles;
    }
}
