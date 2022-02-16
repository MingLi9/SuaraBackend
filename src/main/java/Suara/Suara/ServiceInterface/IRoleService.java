package Suara.Suara.ServiceInterface;

import Suara.Suara.Model.Role;

import java.util.List;

public interface IRoleService {
    Role saveRole(Role role);
    Role getRoleByName(String name);
    List<Role> getAllRoles();

}
