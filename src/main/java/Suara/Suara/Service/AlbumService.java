package Suara.Suara.Service;

import Suara.Suara.DALInterface.IAlbumDAL;
import Suara.Suara.DTO.AlbumDTO;
import Suara.Suara.DTO.AlbumReturnDTO;
import Suara.Suara.Logic.Validate;
import Suara.Suara.Model.Album;
import Suara.Suara.Model.Song;
import Suara.Suara.ServiceInterface.IAlbumService;
import Suara.Suara.ServiceInterface.ISongService;
import Suara.Suara.ServiceInterface.ISuaraUserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("AlbumService")
public class AlbumService implements IAlbumService {
    private final IAlbumDAL albumDAL;
    private final ISuaraUserService suaraUserService;
    private final ISongService songService;

    public AlbumService(@Qualifier("AlbumDAL") IAlbumDAL albumDAL, @Qualifier("UserService") ISuaraUserService suaraUserService, ISongService songService) {
        this.albumDAL = albumDAL;
        this.suaraUserService = suaraUserService;
        this.songService = songService;
    }

    public List<AlbumReturnDTO> getAllAlbum() {
        try {
            List<Album> allAlbum = albumDAL.getAllAlbum();
            return toListAlbumReturnDTO(allAlbum);
        } catch (Exception e) {
            throw e;
        }
    }

    public List<AlbumReturnDTO> getAllAlbumByPartName(String chars) {
        try {
            List<Album> allAlbum = albumDAL.getAllAlbumByPartName(chars);
            return toListAlbumReturnDTO(allAlbum);
        } catch (Exception e) {
            throw e;
        }
    }

    public AlbumReturnDTO getAlbumById(Long id) {
        try {
            Album result = albumDAL.getAlbumById(id);
            if (result == null) {
                return null;
            }
            return toAlbumReturnDTO(result);
        } catch (Exception e) {
            throw e;
        }
    }

    public AlbumReturnDTO createAlbum(AlbumDTO albumDTO, Authentication token) {
        try {
            List<Long> songIds = albumDTO.getSongIds();
            String name = albumDTO.getName();
            if (songIds == null || songIds.isEmpty() || name == null || name.length() == 0) {
                return null;
            }
            Album album = toAlbum(albumDTO, token);
            if (!Validate.Album(album)) {
                return null;
            }
            return toAlbumReturnDTO(albumDAL.createAlbum(album));
        } catch (Exception e) {
            throw e;
        }
    }

    public boolean deleteAlbum(Long id, Authentication token) {
        try {
            AlbumReturnDTO resultAlbum = getAlbumById(id);
            if (resultAlbum == null) {
                return false;
            }
            if (!resultAlbum.getOwnerName().equals(token.getName())) {
                return false;
            }
            albumDAL.deleteAlbum(id);
            return true;
        } catch (Exception e) {
            throw e;
        }
    }

    private Album toAlbum(AlbumDTO albumDTO, Authentication token) {
        String name = albumDTO.getName();
        Long ownerId = suaraUserService.getSuaraUserByUsername(token.getName()).getId();
        List<Song> songList = new ArrayList<>();

        for (Long id : albumDTO.getSongIds()) {
            songList.add(songService.getSongById(id));
        }

        return new Album(name, ownerId, songList);
    }

    private List<AlbumReturnDTO> toListAlbumReturnDTO(List<Album> allAlbum) {
        List<AlbumReturnDTO> returnDTOList = new ArrayList<>();
        for (Album album : allAlbum) {
            returnDTOList.add(toAlbumReturnDTO(album));
        }
        return returnDTOList;
    }

    private AlbumReturnDTO toAlbumReturnDTO(Album album) {
        Long id = album.getId();
        String name = album.getName();
        String ownerName = suaraUserService.getSuaraUserById(album.getOwnerId()).getUsername();
        List<Song> songs = album.getSongs();
        return new AlbumReturnDTO(id, name, ownerName, songs);
    }
}
