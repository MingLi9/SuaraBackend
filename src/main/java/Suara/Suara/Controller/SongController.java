package Suara.Suara.Controller;

import Suara.Suara.DTO.SongDTO;
import Suara.Suara.ServiceInterface.ISongService;
import Suara.Suara.Model.Song;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("song")
public class SongController {
    private final ISongService songService;

    public SongController(@Qualifier("SongService") ISongService songService) {
        this.songService = songService;
    }

    @GetMapping
    public ResponseEntity<List<Song>> getAllSongs(){
        List<Song> songs = songService.getAllSongs();
        if(songs != null) {
            return ResponseEntity.ok().body(songs);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("{id}")
    public ResponseEntity<Song> getSongByID(@PathVariable Long id){
        Song result = songService.getSongById(id);
        if(result != null) {
            return ResponseEntity.ok().body(result);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<URI> createSong(@RequestBody SongDTO songDTO, Authentication token){
        Song result = songService.createSong(songDTO, token);
        if (result != null) {
            String url = "song" + "/" + result.getId();
            URI uri = URI.create(url);
            return ResponseEntity.created(uri).build();
        }
        URI uri = URI.create("fail");
        return ResponseEntity.status(HttpStatus.CONFLICT).body(uri);
    }

    @PutMapping("{id}")
    public ResponseEntity<Song> updateSong(@RequestBody SongDTO songDTO, @PathVariable Long id, Authentication token){
        if (songService.updateSong(songDTO, id, token)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Song> deleteSong(@PathVariable Long id, Authentication token){
        if(songService.deleteSong(id, token)){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
