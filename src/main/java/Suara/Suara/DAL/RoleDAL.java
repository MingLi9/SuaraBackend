package Suara.Suara.DAL;

import Suara.Suara.DALInterface.IRoleDAL;
import Suara.Suara.Model.Role;
import Suara.Suara.Repository.RoleRepo;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("RoleDAL")
public class RoleDAL implements IRoleDAL {
    private final RoleRepo roleRepo;

    public RoleDAL(@Qualifier("RoleRepo") RoleRepo roleRepo) {
        this.roleRepo = roleRepo;
    }

    public Role saveRole(Role role) {
        return roleRepo.save(role);
    }

    public Role getRoleByName(String name){
        return roleRepo.findByName(name);
    }

    public List<Role> getAllRoles() {
        return roleRepo.findAll();
    }
}
