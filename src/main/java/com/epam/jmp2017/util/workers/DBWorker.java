package com.epam.jmp2017.util.workers;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBWorker {
    private static final Logger LOG = Logger.getLogger(DBWorker.class.getName());
    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    //rhc port-forward -a module6
    //https://module6-devrine.rhcloud.com/phpmyadmin/

    private Connection conn = null;
    private Statement stmt = null;
    private ResultSet rs = null;

    public void test() {
        try{
            System.out.println("Connecting to mysql...");
            conn = DriverManager.getConnection(
                  PropertyManager.getProperty("database.url"),
                  PropertyManager.getProperty("database.user"),
                  PropertyManager.getProperty("database.password")
            );

            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT id, name, type FROM Actions";
            rs = stmt.executeQuery(sql);

            while(rs.next()){
                int id  = rs.getInt("id");
                String name = rs.getString("name");
                String type = rs.getString("type");

                //Display values
                System.out.print("ID: " + id);
                System.out.print(", Name: " + name);
                System.out.println(", Type: " + type);
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
    }
}
