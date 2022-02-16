package Suara.Suara.Repository;

import Suara.Suara.Model.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("AlbumRepo")
public interface AlbumRepo extends JpaRepository<Album, Long> {
    List<Album> findAlbumByNameContaining(String name);
}
