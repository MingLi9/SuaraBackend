package Suara.Suara.DAL;

import Suara.Suara.DALInterface.IAlbumDAL;
import Suara.Suara.Model.Album;
import Suara.Suara.Repository.AlbumRepo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("AlbumDAL")
public class AlbumDAL implements IAlbumDAL {
    private final AlbumRepo albumRepo;

    public AlbumDAL(AlbumRepo albumRepo) {
        this.albumRepo = albumRepo;
    }


    public List<Album> getAllAlbum() {
        return albumRepo.findAll();
    }

    public List<Album> getAllAlbumByPartName(String chars) {
        return albumRepo.findAlbumByNameContaining(chars);
    }

    public Album getAlbumById(Long id) {
        return albumRepo.getById(id);
    }

    public Album createAlbum(Album album) {
        return albumRepo.save(album);
    }

    public void deleteAlbum(Long id) {
        albumRepo.deleteById(id);
    }

}
