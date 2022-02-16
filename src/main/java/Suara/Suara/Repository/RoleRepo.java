package Suara.Suara.Repository;

import Suara.Suara.Model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("RoleRepo")
public interface RoleRepo extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
