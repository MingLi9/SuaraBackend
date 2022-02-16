package Suara.Suara.Controller;

import Suara.Suara.DTO.AlbumDTO;
import Suara.Suara.DTO.AlbumReturnDTO;
import Suara.Suara.DTO.SongDTO;
import Suara.Suara.Model.Album;
import Suara.Suara.Model.Song;
import Suara.Suara.ServiceInterface.IAlbumService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("album")
public class AlbumController {
    private final IAlbumService albumService;

    public AlbumController(@Qualifier("AlbumService") IAlbumService albumService) {
        this.albumService = albumService;
    }

    @GetMapping
    public ResponseEntity<List<AlbumReturnDTO>> getAllAlbum(){
        List<AlbumReturnDTO> albums = albumService.getAllAlbum();
        if(albums != null) {
            return ResponseEntity.ok().body(albums);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("part/{name}")
    public ResponseEntity<List<AlbumReturnDTO>> getAllAlbumByPartName(@PathVariable String name){
        List<AlbumReturnDTO> albums = albumService.getAllAlbumByPartName(name);
        if(albums != null && !albums.isEmpty()) {
            return ResponseEntity.ok().body(albums);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("{id}")
    public ResponseEntity<AlbumReturnDTO> getAlbumById(@PathVariable Long id){
        AlbumReturnDTO result = albumService.getAlbumById(id);
        if(result != null) {
            return ResponseEntity.ok().body(result);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<URI> createAlbum(@RequestBody AlbumDTO albumDTO, Authentication token){
        AlbumReturnDTO result = albumService.createAlbum(albumDTO, token);
        if (result != null) {
            String url = "album" + "/" + result.getId(); // url of the created song
            URI uri = URI.create(url);
            return ResponseEntity.created(uri).build();
        }
        URI uri = URI.create("fail");
        return ResponseEntity.status(HttpStatus.CONFLICT).body(uri);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Album> deleteAlbum(@PathVariable Long id, Authentication token){
        if (albumService.deleteAlbum(id, token)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
