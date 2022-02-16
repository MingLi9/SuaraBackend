package Suara.Suara.Controller;

import Suara.Suara.DTO.UserDTO;
import Suara.Suara.Model.SuaraUser;
import Suara.Suara.ServiceInterface.ISuaraUserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("user")
public class SuaraUserController {
    private final ISuaraUserService userService;

    public SuaraUserController(@Qualifier("UserService") ISuaraUserService userService) {
        this.userService = userService;
    }

    @GetMapping("{id}")
    public ResponseEntity<SuaraUser> getUserById(@PathVariable Long id) {
        SuaraUser result = userService.getSuaraUserById(id);
        if (result != null)
            return ResponseEntity.ok().body(result);

        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<URI> createUser(@RequestBody UserDTO userDTO) {
        SuaraUser result = userService.saveSuaraUser(userDTO);
        if (result != null) {
            URI uri = URI.create("login");
            return ResponseEntity.created(uri).build();
        }
        URI uri = URI.create("fail");
        return ResponseEntity.status(HttpStatus.CONFLICT).body(uri);
    }

    @PutMapping("role")
    public ResponseEntity<SuaraUser> updateUser(@RequestParam("id") Long id, @RequestParam("roleName") String roleName) {
        if (userService.updateSuaraUser(id, roleName))
            return ResponseEntity.noContent().build();
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<SuaraUser> deleteUser(@PathVariable Long id) {
        if (userService.deleteUser(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

}
