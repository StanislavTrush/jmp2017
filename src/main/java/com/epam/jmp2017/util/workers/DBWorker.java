package com.epam.jmp2017.util.workers;

import java.sql.*;

public class DBWorker {
    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://127.0.0.1:3307/module6";

    private static final String USER = "adminWB9xVIt";
    private static final String PASS = "JeCJ3vHv4CNS";
    //rhc port-forward -a module6
    //https://module6-devrine.rhcloud.com/phpmyadmin/

    public static void test() {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try{
            Class.forName(JDBC_DRIVER);

            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

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
