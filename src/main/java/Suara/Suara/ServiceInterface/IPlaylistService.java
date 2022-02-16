package Suara.Suara.ServiceInterface;

import Suara.Suara.DTO.PlaylistDTO;
import Suara.Suara.DTO.PlaylistReturnDTO;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface IPlaylistService {
    List<PlaylistReturnDTO> getAllPlaylist();

    List<PlaylistReturnDTO> getAllPlaylistByPartName(String chars);

    PlaylistReturnDTO getPlaylistById(Long id);

    PlaylistReturnDTO createPlaylist(PlaylistDTO playlistDTO, Authentication token);

    boolean updatePlaylist(PlaylistDTO playlistDTO, Long id, Authentication token);

    boolean deletePlaylist(Long id, Authentication token);
}
