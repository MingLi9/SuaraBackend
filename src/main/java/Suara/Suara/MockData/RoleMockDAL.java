package Suara.Suara.MockData;

import Suara.Suara.DALInterface.IRoleDAL;
import Suara.Suara.Model.Role;

import java.util.ArrayList;
import java.util.List;

public class RoleMockDAL implements IRoleDAL {
    private List<Role> roles = new ArrayList<>();

    public RoleMockDAL(){
        roles.add(new Role(1L, "User"));
        roles.add(new Role(2L, "Creator"));
        roles.add(new Role(3L, "Admin"));
    }

    public Role saveRole(Role role) {
        Long newId = roles.get(roles.size()-1).getId() +1;
        role.setId(newId);
        roles.add(role);
        return role;
    }

    public Role getRoleByName(String name) {
        for(Role role : roles){
            if(role.getName().equals(name)){
                return role;
            }
        }
        return null;
    }

    public List<Role> getAllRoles() {
        return roles;
    }
}
