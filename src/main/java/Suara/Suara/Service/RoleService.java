package Suara.Suara.Service;

import Suara.Suara.DALInterface.IRoleDAL;
import Suara.Suara.Model.Role;
import Suara.Suara.ServiceInterface.IRoleService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("RoleService")
public class RoleService implements IRoleService {
    private final IRoleDAL roleDAL;

    public RoleService(@Qualifier("RoleDAL") IRoleDAL roleDAL) {
        this.roleDAL = roleDAL;
    }

    public Role saveRole(Role role) {
        return roleDAL.saveRole(role);
    }

    public Role getRoleByName(String name) {
        return roleDAL.getRoleByName(name);
    }

    public List<Role> getAllRoles() {
        return roleDAL.getAllRoles();
    }
}
