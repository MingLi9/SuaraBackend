package Suara.Suara.DALInterface;

import Suara.Suara.Model.Role;

import java.util.List;

public interface IRoleDAL {
    Role saveRole(Role role);
    Role getRoleByName(String name);
    List<Role> getAllRoles();
}
