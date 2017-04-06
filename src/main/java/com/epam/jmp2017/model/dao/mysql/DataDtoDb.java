package com.epam.jmp2017.model.dao.mysql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.epam.jmp2017.model.json.DataModel;
import com.epam.jmp2017.model.json.impl.data.Dog;
import com.epam.jmp2017.model.json.impl.data.Fridge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;

import com.epam.jmp2017.model.dao.IDataDto;


public class DataDtoDb implements IDataDto
{
	@Autowired
	private DataSource dataSource;
	@Override
	public boolean save(List<DataModel> dataList)
	{
		Connection conn = null;
		Statement stmt = null;
		try{
			conn = DataSourceUtils.getConnection(dataSource);

			Dog dog;
			Fridge fridge;
			stmt = conn.createStatement();
			for (DataModel data : dataList) {
				if (data.getTypeCode() == 1) {
					dog = (Dog) data;
					stmt.addBatch("INSERT IGNORE INTO Dogs (name, color)" +
							" VALUES('" + dog.getName() + "', '" + dog.getColor() + "')");
				}
				if (data.getTypeCode() == 2) {
					fridge = (Fridge) data;
					stmt.addBatch("INSERT IGNORE INTO Fridges (weight, brand)" +
							" VALUES('" + fridge.getWeight() + "', '" + fridge.getBrand() + "')");
				}
			}
			stmt.executeBatch();
			return true;
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try{
				if(stmt!=null) {
					stmt.close();
				}
			}catch(SQLException e){
				e.printStackTrace();
			}
			DataSourceUtils.releaseConnection(conn, dataSource);
		}
		return false;
	}
}
