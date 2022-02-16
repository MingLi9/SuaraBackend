package Suara.Suara.ServiceInterface;

import Suara.Suara.DTO.AlbumDTO;
import Suara.Suara.DTO.AlbumReturnDTO;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface IAlbumService {
    List<AlbumReturnDTO> getAllAlbum();

    List<AlbumReturnDTO> getAllAlbumByPartName(String chars);

    AlbumReturnDTO getAlbumById(Long id);

    AlbumReturnDTO createAlbum(AlbumDTO albumDTO, Authentication token);

    boolean deleteAlbum(Long id, Authentication token);
}
