package Suara.Suara.Repository;

import Suara.Suara.Model.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("PlaylistRepo")
public interface PlaylistRepo extends JpaRepository<Playlist, Long> {
    List<Playlist> findPlaylistByNameContaining(String name);
}
