package Suara.Suara.Service;

import Suara.Suara.DALInterface.IPlaylistDAL;
import Suara.Suara.DTO.PlaylistDTO;
import Suara.Suara.DTO.PlaylistReturnDTO;
import Suara.Suara.Logic.Validate;
import Suara.Suara.Model.Playlist;
import Suara.Suara.Model.Song;
import Suara.Suara.ServiceInterface.IPlaylistService;
import Suara.Suara.ServiceInterface.ISongService;
import Suara.Suara.ServiceInterface.ISuaraUserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("PlaylistService")
public class PlaylistService implements IPlaylistService {
    private final IPlaylistDAL playlistDAL;
    private final ISuaraUserService suaraUserService;
    private final ISongService songService;

    public PlaylistService(@Qualifier("PlaylistDAL") IPlaylistDAL playlistDAL, @Qualifier("UserService") ISuaraUserService suaraUserService, ISongService songService) {
        this.playlistDAL = playlistDAL;
        this.suaraUserService = suaraUserService;
        this.songService = songService;
    }

    public List<PlaylistReturnDTO> getAllPlaylist() {
        try {
            List<Playlist> allPlaylist = playlistDAL.getAllPlaylist();
            return toListPlaylistReturnDTO(allPlaylist);
        } catch (Exception e) {
            throw e;
        }
    }

    public List<PlaylistReturnDTO> getAllPlaylistByPartName(String chars) {
        try {
            List<Playlist> allPlaylist = playlistDAL.getAllPlaylistByPartName(chars);
            return toListPlaylistReturnDTO(allPlaylist);
        } catch (Exception e) {
            throw e;
        }
    }

    public PlaylistReturnDTO getPlaylistById(Long id) {
        try {
            Playlist result = playlistDAL.getPlaylistById(id);
            if (result == null) {
                return null;
            }
            return toPlaylistReturnDTO(result);
        } catch (Exception e) {
            throw e;
        }
    }

    public PlaylistReturnDTO createPlaylist(PlaylistDTO playlistDTO, Authentication token) {
        try {
            List<Long> songIds = playlistDTO.getSongIds();
            String name = playlistDTO.getName();
            if (songIds == null || songIds.isEmpty() || name == null || name.length() == 0) {
                return null;
            }
            Playlist playlist = toPlaylist(playlistDTO, token);
            if (!Validate.Playlist(playlist)) {
                return null;
            }
            playlist.setId(0L);
            return toPlaylistReturnDTO(playlistDAL.createPlaylist(playlist));
        } catch (Exception e) {
            throw e;
        }
    }

    public boolean updatePlaylist(PlaylistDTO playlistDTO, Long id, Authentication token) {
        try {
            if (playlistDTO == null) {
                return false;
            }
            List<Long> songIds = playlistDTO.getSongIds();
            String name = playlistDTO.getName();
            if (songIds == null || songIds.isEmpty() || name == null || name.length() == 0 || id == null || id <= 0) {
                return false;
            }
            PlaylistReturnDTO resultPlaylist = getPlaylistById(id);
            if (resultPlaylist == null) {
                return false;
            }
            if (!resultPlaylist.getOwnerName().equals(token.getName())) {
                return false;
            }
            Playlist playlist = toPlaylist(playlistDTO, token);
            playlist.setId(id);
            if (!Validate.Playlist(playlist)) {
                return false;
            }
            return playlistDAL.updatePlaylist(playlist);
        } catch (Exception e) {
            throw e;
        }
    }

    public boolean deletePlaylist(Long id, Authentication token) {
        try {
            PlaylistReturnDTO resultPlaylist = getPlaylistById(id);
            if (resultPlaylist == null) {
                return false;
            }
            if (!resultPlaylist.getOwnerName().equals(token.getName())) {
                return false;
            }
            playlistDAL.deletePlaylist(id);
            return true;
        } catch (Exception e) {
            throw e;
        }
    }

    private Playlist toPlaylist(PlaylistDTO playlistDTO, Authentication token) {
        String name = playlistDTO.getName();
        Long ownerId = suaraUserService.getSuaraUserByUsername(token.getName()).getId();
        List<Song> songList = new ArrayList<>();

        for (Long id : playlistDTO.getSongIds()) {
            songList.add(songService.getSongById(id));
        }

        return new Playlist(name, ownerId, songList);
    }

    private List<PlaylistReturnDTO> toListPlaylistReturnDTO(List<Playlist> allPlaylist) {
        List<PlaylistReturnDTO> returnDTOList = new ArrayList<>();
        for (Playlist playlist : allPlaylist) {
            returnDTOList.add(toPlaylistReturnDTO(playlist));
        }
        return returnDTOList;
    }

    private PlaylistReturnDTO toPlaylistReturnDTO(Playlist playlist) {
        Long id = playlist.getId();
        String name = playlist.getName();
        String ownerName = suaraUserService.getSuaraUserById(playlist.getOwnerId()).getUsername();
        List<Song> songs = playlist.getSongs();
        return new PlaylistReturnDTO(id, name, ownerName, songs);
    }
}
