package Suara.Suara.Controller;

import Suara.Suara.DTO.PlaylistDTO;
import Suara.Suara.DTO.PlaylistReturnDTO;
import Suara.Suara.DTO.SongDTO;
import Suara.Suara.Model.Playlist;
import Suara.Suara.Model.Song;
import Suara.Suara.ServiceInterface.IPlaylistService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("playlist")
public class PlaylistController {
    private final IPlaylistService playlistService;

    public PlaylistController(@Qualifier("PlaylistService") IPlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @GetMapping
    public ResponseEntity<List<PlaylistReturnDTO>> getAllPlaylist(){
        List<PlaylistReturnDTO> playlists = playlistService.getAllPlaylist();
        if(playlists != null) {
            return ResponseEntity.ok().body(playlists);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("part/{name}")
    public ResponseEntity<List<PlaylistReturnDTO>> getAllPlaylistByPartName(@PathVariable String name){
        List<PlaylistReturnDTO> playlists = playlistService.getAllPlaylistByPartName(name);
        if(playlists != null && !playlists.isEmpty()) {
            return ResponseEntity.ok().body(playlists);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("{id}")
    public ResponseEntity<PlaylistReturnDTO> getPlaylistById(@PathVariable Long id){
        PlaylistReturnDTO result = playlistService.getPlaylistById(id);
        if(result != null) {
            return ResponseEntity.ok().body(result);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<URI> createPlaylist(@RequestBody PlaylistDTO playlistDTO, Authentication token){
        PlaylistReturnDTO result = playlistService.createPlaylist(playlistDTO, token);
        if (result != null) {
            String url = "playlist" + "/" + result.getId(); // url of the created song
            URI uri = URI.create(url);
            return ResponseEntity.created(uri).build();
        }
        URI uri = URI.create("fail");
        return ResponseEntity.status(HttpStatus.CONFLICT).body(uri);
    }

    @PutMapping("{id}")
    public ResponseEntity<String> updatePlaylist(@RequestBody PlaylistDTO playlistDTO, @PathVariable Long id, Authentication token){
        if (playlistService.updatePlaylist(playlistDTO, id, token)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Playlist> deletePlaylist(@PathVariable Long id, Authentication token){
        if (playlistService.deletePlaylist(id, token)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
