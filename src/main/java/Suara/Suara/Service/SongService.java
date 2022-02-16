package Suara.Suara.Service;

import Suara.Suara.DALInterface.ISongDAL;
import Suara.Suara.DTO.SongDTO;
import Suara.Suara.Logic.Validate;
import Suara.Suara.Model.Song;
import Suara.Suara.ServiceInterface.ISongService;
import Suara.Suara.ServiceInterface.ISuaraUserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("SongService")
public class SongService implements ISongService {
    private final ISongDAL songDAL;
    private final ISuaraUserService suaraUserService;

    public SongService(@Qualifier("SongDAL") ISongDAL songDAL, @Qualifier("UserService") ISuaraUserService suaraUserService) {
        this.songDAL = songDAL;
        this.suaraUserService = suaraUserService;
    }

    public List<Song> getAllSongs() {
        return songDAL.getAllSongs();
    }

    public Song getSongById(Long id) {
        return songDAL.getSongById(id);
    }

    public Song createSong(SongDTO songDTO, Authentication token) {
        //check input on necessary fields
        if (songDTO.getTitle() == null || songDTO.getTitle().equals("") || songDTO.getFile() == null || songDTO.getFile().equals(""))
            return null;

        Long userId = suaraUserService.getSuaraUserByUsername(token.getName()).getId();

        //convert the dto to the right model
        Song result = dtoToSong(songDTO, userId);

        //validate the input
        if (!Validate.Song(result))
            return null;

        //happy flow
        return songDAL.createSong(result);
    }

    private Song dtoToSong(SongDTO songDTO, Long creatorId) {
        String title = songDTO.getTitle();
        String file = songDTO.getFile();
        return new Song(title, creatorId, file);
    }

    public boolean updateSong(SongDTO songDTO, Long id, Authentication token) {
        try {
            //check input on necessary fields
            if (songDTO.getTitle() == null || songDTO.getTitle().equals("") || songDTO.getFile() == null || songDTO.getFile().equals(""))
                return false;

            Song result = getSongById(id);
            Long songOwnerId = result.getCreatorId();
            Long userId = suaraUserService.getSuaraUserByUsername(token.getName()).getId();
            if (!songOwnerId.equals(userId))
                return false;

            result.setTitle(songDTO.getTitle());
            result.setFile(songDTO.getFile());

            return songDAL.updateSong(result);
        } catch (Exception e) {
            return false;
        }

    }

    public boolean deleteSong(Long id, Authentication token) {
        try {
            Song result = getSongById(id);
            Long songOwnerId = result.getCreatorId();
            Long userId = suaraUserService.getSuaraUserByUsername(token.getName()).getId();
            if (!songOwnerId.equals(userId))
                return false;

            songDAL.deleteSong(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
