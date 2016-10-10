package com.rom.util.httpclient;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConnectionFactory
{
  public static ConnectionFactory connectionFactory = new ConnectionFactory();
  private ComboPooledDataSource ds;

  private ConnectionFactory()
  {
    this.ds = new ComboPooledDataSource();
    try {
      String DriverClass = PropertyUtils.getValue("DriverClass");

      if (DriverClass != null)
        this.ds.setDriverClass(DriverClass);
    }
    catch (PropertyVetoException localPropertyVetoException)
    {
    }
    String JdbcUrl = PropertyUtils.getValue("JdbcUrl");

    if (JdbcUrl != null) {
      this.ds.setJdbcUrl(JdbcUrl);
    }

    String User = PropertyUtils.getValue("User");

    if (User != null) {
      this.ds.setUser(User);
    }

    String Password = PropertyUtils.getValue("Password");

    if (Password != null) {
      this.ds.setPassword(Password);
    }

    String InitialPoolSize = PropertyUtils.getValue("InitialPoolSize");

    if (InitialPoolSize != null) {
      this.ds.setInitialPoolSize(Integer.parseInt(InitialPoolSize));
    }

    String MaxPoolSize = PropertyUtils.getValue("MaxPoolSize");

    if (MaxPoolSize != null) {
      this.ds.setMaxPoolSize(Integer.parseInt(MaxPoolSize));
    }

    String CheckoutTimeout = PropertyUtils.getValue("CheckoutTimeout");

    if (CheckoutTimeout != null) {
      this.ds.setCheckoutTimeout(Integer.parseInt(CheckoutTimeout));
    }

    String MaxIdleTime = PropertyUtils.getValue("MaxIdleTime");
    if (CheckoutTimeout != null)
      this.ds.setMaxIdleTime(Integer.parseInt(MaxIdleTime));
  }

  public static ConnectionFactory getInstance()
  {
    return connectionFactory;
  }

  public Connection getConnection()
  {
    try
    {
      return this.ds.getConnection();
    } catch (SQLException e) {
      throw new RuntimeException(e.getMessage() + "code = " + e.getErrorCode());
    }
  }

  public void closeConnection(Connection connection, PreparedStatement prepareStatement, ResultSet resultSet)
  {
    try
    {
      if (resultSet != null) {
        resultSet.close();
      }
      if (prepareStatement != null) {
        prepareStatement.close();
      }
      if (connection != null)
        connection.close();
    }
    catch (SQLException e) {
      throw new RuntimeException(e.getMessage() + "code = " + e.getErrorCode());
    }
  }

  public static void main(String[] args)
  {
  }
}
