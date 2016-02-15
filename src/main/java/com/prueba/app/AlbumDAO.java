package com.prueba.app;
import java.util.List;
import javax.sql.DataSource;


public interface AlbumDAO {
	public void setDataSource(DataSource ds);
	
	public void create(Integer id, Integer userId, String title, String hash1);
	
	public Album getAlbum(Integer id);
	
	public List<Album> listAlbums();
	
	public void delete(Integer id);
	
	public void update(Integer id, Integer userId, String title, String hash1);
	
}
