package com.epam.jmp2017.model.dao.mysql;

import com.epam.jmp2017.model.dao.IConditionDao;
import com.epam.jmp2017.model.json.ConditionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;

@Repository
public class ConditionDaoDb implements IConditionDao {
    @Autowired
    private DataSource dataSource;

    private Consumer<AutoCloseable> close = (st) -> {
        try {
            if (st != null) {
                st.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    };

    @Override
    public List<ConditionData> getAllConditions() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DataSourceUtils.getConnection(dataSource);

            stmt = conn.prepareStatement(
                    "SELECT id, actionId, parentId, operation, attribute, value, className FROM Conditions");
            rs = stmt.executeQuery();

            return getRoot(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close.accept(rs);
            close.accept(stmt);
            DataSourceUtils.releaseConnection(conn, dataSource);
        }
        return new ArrayList<>();
    }

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
            close.accept(rs);
            close.accept(stmt);
            DataSourceUtils.releaseConnection(conn, dataSource);
        }
        return new ArrayList<>();
    }

    @Override
    public boolean removeConditionById(int conditionId) {
        Connection conn = null;
        PreparedStatement stmt = null;
        int result;

        try {
            conn = DataSourceUtils.getConnection(dataSource);
            stmt = conn.prepareStatement(
                    "DELETE FROM Conditions WHERE id = ?");
            stmt.setInt(1, conditionId);
            result = stmt.executeUpdate();

            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close.accept(stmt);
            DataSourceUtils.releaseConnection(conn, dataSource);
        }
        return false;
    }

    @Override
    public boolean addCondition(String operation, String attribute, String value, String className, int parentId, int actionId) {
        Connection conn = null;
        Statement statement1 = null;
        Statement statement2 = null;
        PreparedStatement stmt = null;
        int result;

        try {
            conn = DataSourceUtils.getConnection(dataSource);
            statement1 = conn.createStatement();
            statement1.execute("SET FOREIGN_KEY_CHECKS=0");

            stmt = conn.prepareStatement(
                    "INSERT INTO Conditions(operation, attribute, value, className, parentId, actionId)" +
                            " VALUES (?, ?, ?, ?, ?, ?)");
            stmt.setString(1, operation);
            stmt.setString(2, attribute);
            stmt.setString(3, value);
            stmt.setString(4, className);
            if(parentId != 0) {
                stmt.setInt(5, parentId);
            } else {
                stmt.setNull(5, Types.INTEGER);
            }
            if(parentId != 0) {
                stmt.setInt(6, actionId);
            } else {
                stmt.setNull(6, Types.INTEGER);
            }
            result = stmt.executeUpdate();

            statement2 = conn.createStatement();
            statement2.execute("SET FOREIGN_KEY_CHECKS=1");

            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close.accept(statement1);
            close.accept(statement2);
            close.accept(stmt);
            DataSourceUtils.releaseConnection(conn, dataSource);
        }
        return false;
    }

    private List<ConditionData> getRoot(ResultSet rs) throws SQLException {
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
        List<ConditionData> result = new ArrayList<>();
        List<ConditionData> temp = new ArrayList<>();
        temp.addAll(conditions);
        temp.sort(Comparator.comparingInt(ConditionData::getActionId));
        temp.forEach((conditionData) -> {
            if (conditionData.getActionId() > 0) {
                conditionData.setConditions(getChilds(conditions, conditionData.getId()));
                result.add(conditionData);
            }
        });
        return result;
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
        temp.forEach((conditionData) -> {
            if (conditionData.getActionId() == actionId) {
                conditionData.setConditions(getChilds(conditions, conditionData.getId()));
                retult.add(conditionData);
            }
        });

        return retult;
    }

    private List<ConditionData> getChilds(List<ConditionData> conditions, int parentId) {
        List<ConditionData> result = new ArrayList<>();
        conditions.forEach((conditionData) -> {
            if (conditionData.getParentId() == parentId) {
                conditionData.setConditions(getChilds(conditions, conditionData.getId()));
                result.add(conditionData);
            }
        });
        return result;
    }

    public List<ConditionModel> toModelList(List<ConditionData> data) {
        List<ConditionModel> result = new ArrayList<>();
        data.forEach((condition) -> {
            ConditionModel model = new ConditionModel();
            model.setOperation(condition.getOperation());
            model.setAttribute(condition.getAttribute());
            model.setValue(condition.getValue());
            model.setClassName(condition.getClassName());
            model.setConditions(toModelList(condition.getConditions()));
            result.add(model);
        });
        return result;
    }
}
