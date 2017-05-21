package com.epam.jmp2017.model.dao.mysql;

import java.sql.*;
import java.util.List;

import javax.sql.DataSource;

import com.epam.jmp2017.model.json.DataModel;
import com.epam.jmp2017.model.json.impl.data.Dog;
import com.epam.jmp2017.model.json.impl.data.Fridge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;

import com.epam.jmp2017.model.dao.IDataDao;
import org.springframework.stereotype.Repository;

@Repository
public class DataDaoDb implements IDataDao {
    @Autowired
    private DataSource dataSource;

    @Override
    public boolean save(List<DataModel> dataList) {
        Connection conn = null;
        PreparedStatement stmtDogs = null;
        PreparedStatement stmtFridges = null;
        try {
            conn = DataSourceUtils.getConnection(dataSource);

            Dog dog;
            Fridge fridge;
            stmtDogs = conn.prepareStatement("INSERT IGNORE INTO Dogs (name, color) VALUES(?, ?)");
            stmtFridges = conn.prepareStatement("INSERT IGNORE INTO Fridges (weight, brand) VALUES(?, ?)");
            for (DataModel data : dataList) {
                if (data.getTypeCode() == 1) {
                    dog = (Dog) data;
                    stmtDogs.setString(1, dog.getName());
                    stmtDogs.setString(2, dog.getColor());
                    stmtDogs.addBatch();
                }
                if (data.getTypeCode() == 2) {
                    fridge = (Fridge) data;
                    stmtFridges.setInt(1, fridge.getWeight());
                    stmtFridges.setString(2, fridge.getBrand());
                    stmtFridges.addBatch();
                }
            }
            stmtDogs.executeBatch();
            stmtFridges.executeBatch();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmtDogs != null) {
                    stmtDogs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (stmtFridges != null) {
                    stmtFridges.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DataSourceUtils.releaseConnection(conn, dataSource);
        }
        return false;
    }
}
