package com.uhc.aarp.preferences.automation.util;


import com.uhc.aarp.automation.util.PropertyUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

public class DbUtils {

    private static Connection getConnection() throws SQLException {
        TimeZone timeZone = TimeZone.getTimeZone("CST");
        TimeZone.setDefault(timeZone);
        DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
        return DriverManager.getConnection(PropertyUtils.getProperty("db.connection"), PropertyUtils.getProperty("db.userId"), PropertyUtils.getProperty("db.password"));
    }


    public static Connection getCompasDbConnection() throws SQLException {
        TimeZone timeZone = TimeZone.getTimeZone("CST");
        TimeZone.setDefault(timeZone);
        DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
        return DriverManager.getConnection(PropertyUtils.getProperty("compasdb.connection"), PropertyUtils.getProperty("compasdb.userId"), PropertyUtils.getProperty("compasdb.password"));
    }

    public static List<HashMap<String, String>> acesQuery(String query) throws SQLException, ClassNotFoundException {

        Connection conn = getConnection();

        List<HashMap<String, String>> rows = new ArrayList<HashMap<String, String>>();
        try {
            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(query);
            ResultSetMetaData md = rs.getMetaData();
            int columns = md.getColumnCount();


            while(rs.next()) {
                HashMap<String, String> row = new HashMap<String, String>(columns);
                for (int i = 1; i <= columns; ++i) {
                    row.put(md.getColumnName(i), rs.getString(i) == null ? "" : rs.getString(i));
                }
                rows.add(row);
            }
        } finally {
            if(conn != null && !conn.isClosed()) {
                conn.close();
            }
        }

        return rows;

    }

    public static List<HashMap<String, String>> compasQuery(String query) throws SQLException, ClassNotFoundException {

        Connection conn = getCompasDbConnection();

        List<HashMap<String, String>> rows = new ArrayList<HashMap<String, String>>();
        try {
            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(query);
            ResultSetMetaData md = rs.getMetaData();
            int columns = md.getColumnCount();


            while(rs.next()) {
                HashMap<String, String> row = new HashMap<String, String>(columns);
                for (int i = 1; i <= columns; ++i) {
                    row.put(md.getColumnName(i), rs.getString(i) == null ? "" : rs.getString(i));
                }
                rows.add(row);
            }
        } finally {
            if(conn != null && !conn.isClosed()) {
                conn.close();
            }
        }

        return rows;

    }

    public static void execute(String... statements) throws SQLException, ClassNotFoundException {

        Connection conn = getConnection();

        try {
            Statement stmt = conn.createStatement();
            conn.setAutoCommit(false);

            for(String statement : statements) {
                stmt.execute(statement);
            }
            conn.commit();


        } finally {
            if(conn != null && !conn.isClosed()) {
                conn.close();
            }
        }

    }

}
