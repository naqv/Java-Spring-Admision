package com.prueba.app;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class AlbumMapper implements RowMapper<Album>  {
	public Album mapRow(ResultSet rs, int rowNum) throws SQLException{
		Album a= new Album();
		a.setId(rs.getInt("id"));
		a.setUserId(rs.getInt("userId"));
		a.setTitle(rs.getString("title"));
		a.setSha1(rs.getString("sha1"));
		return a;
	}
}
