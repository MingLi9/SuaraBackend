package Suara.Suara.Repository;

import Suara.Suara.Model.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("SongRepo")
public interface SongRepo extends JpaRepository<Song, Long> {
}
