package com.rom.util.httpclient;


import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

public class BaseDao
{
  protected Logger log = Logger.getLogger(getClass());

  public int executeSql(String sql, Object[] params)
  {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    try
    {
      connection = ConnectionFactory.getInstance().getConnection();
      preparedStatement = connection.prepareStatement(sql);

      if (params != null)
      {
        for (int i = 0; i < params.length; i++)
        {
          if (params[i] != null) {
            if ((params[i] instanceof Date))
              preparedStatement
                .setTimestamp(i + 1, new Timestamp(
                ((Date)params[i]).getTime()));
            else
              preparedStatement.setObject(i + 1, params[i]);
          }
          else {
            preparedStatement.setString(i + 1, "");
          }
        }
      }
      return preparedStatement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
      this.log.error(e.getMessage() + "code = " + e.getErrorCode() + ",sql:" + sql);

      return -1;
    } finally {
      ConnectionFactory.getInstance().closeConnection(connection, 
        preparedStatement, resultSet);
    }
  }

  public int[] executeBatchSql(String sql, Object[][] paramsArr)
  {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    try {
      connection = ConnectionFactory.getInstance().getConnection();
      preparedStatement = connection.prepareStatement(sql);

      if (paramsArr != null) {
        for (int s = 0; s < paramsArr.length; s++) {
          Object[] params = paramsArr[s];
          if (params != null)
          {
            for (int i = 0; i < params.length; i++) {
              if (params[i] != null) {
                if ((params[i] instanceof Date))
                  preparedStatement.setTimestamp(
                    i + 1, 
                    new Timestamp(((Date)params[i])
                    .getTime()));
                else
                  preparedStatement.setObject(i + 1, 
                    params[i]);
              }
              else {
                preparedStatement.setString(i + 1, "");
              }
            }
            preparedStatement.addBatch();
          }
        }
      }
      return preparedStatement.executeBatch();
    } catch (SQLException e) {
      e.printStackTrace();

      this.log.error(e.getMessage() + "code = " + e.getErrorCode() + ",sql:" + sql);
    } finally {
      ConnectionFactory.getInstance().closeConnection(connection, preparedStatement, resultSet);
    }
    return null;
  }

  public int[] executeBatchSql(String[] sql)
  {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    connection = ConnectionFactory.getInstance().getConnection();
    Statement state = null;
    try {
      if ((sql != null) && (sql.length > 0)) {
        boolean autoCommit = connection.getAutoCommit();
        connection.setAutoCommit(false);
        state = connection.createStatement();
        for (int i = 0; i < sql.length; i++) {
          state.addBatch(sql[i]);
        }
        int[] j = state.executeBatch();
        connection.commit();
        connection.setAutoCommit(autoCommit);
        state.close();
        ConnectionFactory.getInstance().closeConnection(connection, preparedStatement, resultSet);
        return j;
      }
    } catch (SQLException e) {
      state = null;
      ConnectionFactory.getInstance().closeConnection(connection, preparedStatement, resultSet);
    }
    return null;
  }

  public int[] executeBatchSql(String sql, List<Object[]> paramsList)
  {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    try
    {
      connection = ConnectionFactory.getInstance().getConnection();
      preparedStatement = connection.prepareStatement(sql);

      if (paramsList == null) {
        return null;
      }

      for (int i = 0; i < paramsList.size(); i++) {
        Object[] tObj = (Object[])paramsList.get(i);
        if (tObj != null)
        {
          for (int j = 0; j < tObj.length; j++) {
            Object curObj = tObj[j];
            if (curObj != null) {
              if ((curObj instanceof Date))
                preparedStatement.setTimestamp(j + 1, 
                  new Timestamp(((Date)curObj).getTime()));
              else
                preparedStatement.setObject(j + 1, curObj);
            }
            else {
              preparedStatement.setString(j + 1, "");
            }
          }
          preparedStatement.addBatch();
        }
      }
      return preparedStatement.executeBatch();
    } catch (SQLException e) {
      e.printStackTrace();
      this.log.error(e.getMessage() + "code = " + e.getErrorCode() + ",sql:" + sql);
    } finally {
      ConnectionFactory.getInstance().closeConnection(connection, 
        preparedStatement, resultSet);
    }
    return null;
  }

  public List<Map<String, Object>> queryForList(String sql, Object[] params)
  {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    try
    {
      connection = ConnectionFactory.getInstance().getConnection();
      preparedStatement = connection.prepareStatement(sql);

      if (params != null) {
        for (int i = 0; i < params.length; i++)
        {
          preparedStatement.setObject(i + 1, params[i]);
        }
      }
      resultSet = preparedStatement.executeQuery();
      ResultSetMetaData md = resultSet.getMetaData();
      int columnCount = md.getColumnCount();
      List list = new ArrayList();
      Map rowData = new HashMap();
      while (resultSet.next()) {
        rowData = new HashMap(columnCount);
        for (int i = 1; i <= columnCount; i++) {
          rowData.put(md.getColumnLabel(i), resultSet.getObject(i));
        }
        list.add(rowData);
      }

      return list;
    } catch (SQLException e) {
      System.out.println(e.getMessage() + "code = " + e.getErrorCode() + ",sql:" + sql);
      this.log.error(e.getMessage() + "code = " + e.getErrorCode() + ",sql:" + sql);
    } finally {
      ConnectionFactory.getInstance().closeConnection(connection, 
        preparedStatement, resultSet);
    }
    return null;
  }

  public Map<String, Object> queryForMap(String sql, Object[] params)
  {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    try
    {
      connection = ConnectionFactory.getInstance().getConnection();
      preparedStatement = connection.prepareStatement(sql);

      if (params != null) {
        for (int i = 0; i < params.length; i++)
        {
          preparedStatement.setObject(i + 1, params[i]);
        }
      }
      resultSet = preparedStatement.executeQuery();
      ResultSetMetaData md = resultSet.getMetaData();
      int columnCount = md.getColumnCount();
      Map rowData = null;
      if (resultSet.next()) {
        rowData = new HashMap(columnCount);
        for (int i = 1; i <= columnCount; i++) {
          rowData.put(md.getColumnLabel(i), resultSet.getObject(i));
        }

      }

      return rowData;
    } catch (SQLException e) {
      this.log.error(e.getMessage() + "code = " + e.getErrorCode() + ",sql:" + sql);
    } finally {
      ConnectionFactory.getInstance().closeConnection(connection, 
        preparedStatement, resultSet);
    }
    return null;
  }
}
