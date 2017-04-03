package com.epam.jmp2017.model.dao.mysql;

import com.epam.jmp2017.model.dao.IConditionDao;
import com.epam.jmp2017.model.json.ConditionModel;
import com.epam.jmp2017.util.workers.PropertyManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConditionDaoDb implements IConditionDao {
    @Override
    public List<ConditionModel> getConditionsForActionId(int actionId) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try{
            conn = DriverManager.getConnection(
                    PropertyManager.getProperty("database.url"),
                    PropertyManager.getProperty("database.user"),
                    PropertyManager.getProperty("database.password")
            );

            stmt = conn.createStatement();
            rs = stmt.executeQuery(
                    "SELECT id, actionId, parentId, operation, attribute, value, className FROM Conditions where actionId = "
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
            try{
                if(conn!=null) {
                    conn.close();
                }
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
        return new ArrayList<>();
    }

    private List<ConditionData> getRoot(ResultSet rs, int actionId) throws SQLException {
        List<ConditionData> conditions = new ArrayList<>();
        ConditionData condition;
        while (rs.next()) {
            condition = new ConditionData();
            condition.setId(rs.getInt("id"));
            condition.setActionId(rs.getInt("actionId"));
            condition.setParentId(rs.getInt("parentId"));
            condition.setOperation(rs.getString("operation"));
            condition.setAttribute(rs.getString("attribute"));
            condition.setValue(rs.getString("value"));
            condition.setClassName(rs.getString("className"));
            conditions.add(condition);
        }
        List<ConditionData> retult = new ArrayList<>();
        for (ConditionData conditionData : conditions) {
            if (conditionData.getActionId() == actionId) {
                conditionData.setConditions(toModelList(getChilds(conditions, conditionData.getId())));
                conditions.add(conditionData);
            }
        }



        return retult;
    }
    private List<ConditionData> getChilds(List<ConditionData> conditions, int parentId) {
        List<ConditionData> result = new ArrayList<>();
        for (ConditionData conditionData : conditions) {
            if (conditionData.getParentId() == parentId) {
                conditionData.setConditions(toModelList(getChilds(conditions, conditionData.getId())));
                result.add(conditionData);
            }
        }
        return result;
    }

    public List<ConditionModel> toModelList(List<ConditionData> data) {
        List<ConditionModel> result = new ArrayList<>();
        ConditionModel model;
        for (ConditionData condition : data) {
            model = new ConditionModel();
            model.setOperation(condition.getOperation());
            model.setAttribute(condition.getAttribute());
            model.setValue(condition.getValue());
            model.setClassName(condition.getClassName());
            model.setConditions(condition.getConditions());
            result.add(model);
        }
        return result;
    }
}
