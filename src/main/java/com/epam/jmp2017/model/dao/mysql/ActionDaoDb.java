package com.epam.jmp2017.model.dao.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.epam.jmp2017.model.dao.IActionDao;
import com.epam.jmp2017.model.json.ActionModel;
import com.epam.jmp2017.util.workers.PropertyManager;


public class ActionDaoDb implements IActionDao
{
	@Override
	public List<ActionModel> getAllActions() {
		List<ActionModel> actions = new ArrayList<>();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		ActionModel action;

		try{
			System.out.println("Connecting to mysql...");
			conn = DriverManager.getConnection(
					PropertyManager.getProperty("database.url"),
					PropertyManager.getProperty("database.user"),
					PropertyManager.getProperty("database.password")
			);

			System.out.println("Creating statement...");
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT id, name, type FROM Actions");

			while(rs.next()){
				action = new ActionModel();
				action.setName(rs.getString("name"));
				action.setType(rs.getString("type"));
				actions.add(action);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
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
			try{
				if(conn!=null) {
					conn.close();
				}
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		return actions;
	}
}
