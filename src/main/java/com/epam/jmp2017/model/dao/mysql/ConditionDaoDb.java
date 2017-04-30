package com.epam.jmp2017.model.dao.mysql;

import com.epam.jmp2017.model.dao.IConditionDao;
import com.epam.jmp2017.model.json.ConditionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ConditionDaoDb implements IConditionDao {
    @Autowired
    private DataSource dataSource;

    @Override
    public List<ConditionModel> getConditionsForActionId(int actionId) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DataSourceUtils.getConnection(dataSource);

            stmt = conn.prepareStatement(
                    "SELECT id, actionId, parentId, operation, attribute, value, className FROM Conditions WHERE actionId = ? OR actionId IS NULL");
            stmt.setInt(1, actionId);
            rs = stmt.executeQuery();

            return toModelList(getRoot(rs, actionId));
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DataSourceUtils.releaseConnection(conn, dataSource);
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
        List<ConditionData> temp = new ArrayList<>();
        temp.addAll(conditions);
        for (ConditionData conditionData : temp) {
            if (conditionData.getActionId() == actionId) {
                conditionData.setConditions(toModelList(getChilds(conditions, conditionData.getId())));
                retult.add(conditionData);
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
