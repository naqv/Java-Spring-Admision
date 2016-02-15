package com.prueba.app;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;


public class AlbumJDBCTemplate implements AlbumDAO{
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplateObject;
	
	public void setDataSource(DataSource datasource){
		this.dataSource = datasource;
		this.jdbcTemplateObject = new JdbcTemplate(datasource);
	}

	@Override
	public void create(Integer id, Integer userId, String title, String hash1) {
		String SQL = "insert into Album (id, userId, title, hash1) values (?,?,?,?)";
		jdbcTemplateObject.update(SQL,id,userId,title, hash1);
		System.out.print("Created record, id:" + id + " userId:" + userId + " title: " + title + " hash1: " + hash1);
		return;
	}

	@Override
	public Album getAlbum(Integer id) {
		String SQL = "select * from Album where id = ?";
		Album a = jdbcTemplateObject.queryForObject(SQL, new Object[]{id}, new AlbumMapper());
		return a;
	}

	@Override
	public List<Album> listAlbums() {
		String SQL = "select * from Album";
		List<Album> albums = jdbcTemplateObject.query(SQL, new AlbumMapper());
		return albums;
	}

	@Override
	public void delete(Integer id) {
		String SQL = "delete from Album where id = ?";
		jdbcTemplateObject.update(SQL,id);
		System.out.println("Deleted record with id " + id);
		return ;
	}

	@Override
	public void update(Integer id, Integer userId, String title, String hash1) {
		String SQL = "update album set userId = ?, title = ?, hash1 = ? where id = ?";
		jdbcTemplateObject.update(SQL,userId,title,hash1,id);
		System.out.println("Updated record with id " + id);
		return;
	}
	
	
	
	
}
