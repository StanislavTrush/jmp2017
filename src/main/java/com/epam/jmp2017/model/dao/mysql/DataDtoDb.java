package com.epam.jmp2017.model.dao.mysql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;

import com.epam.jmp2017.model.dao.IDataDto;


public class DataDtoDb implements IDataDto
{
	@Autowired
	private DataSource dataSource;
	@Override
	public boolean save()
	{
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try{
			conn = DataSourceUtils.getConnection(dataSource);

			stmt = conn.createStatement();
			rs = stmt.executeUpdate(
					"SELECT id, actionId, parentId, operation, attribute, value, className FROM Conditions WHERE actionId = "
							+ actionId + " OR actionId IS NULL");

			return toModelList(getRoot(rs, actionId));
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
			} catch(SQLException e) {
				e.printStackTrace();
			}
			try{
				if(stmt!=null) {
					stmt.close();
				}
			}catch(SQLException e){
				e.printStackTrace();
			}
			DataSourceUtils.releaseConnection(conn, dataSource);
		}
		return new ArrayList<>();
	}
}
