package Suara.Suara.DALInterface;

import Suara.Suara.Model.Album;

import java.util.List;

public interface IAlbumDAL {
    List<Album> getAllAlbum();

    List<Album> getAllAlbumByPartName(String chars);

    Album getAlbumById(Long id);

    Album createAlbum(Album playlist);

    void deleteAlbum(Long id);
}
