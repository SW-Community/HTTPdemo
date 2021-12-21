package com.example.httpdemo;

import java.sql.*;

public class ConnectDB {
    private Connection connection=null;
    private Statement statement=null;
    private ResultSet resultset=null;

    public void init()
    {

        try {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            connection= DriverManager.getConnection("jdbc:mysql:///webdb","root","1234");
            statement= connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public String getWord(String abbr)
    {
        String res=null;
        String sql="select hotwords.word from hotwords where hotwords.abbr='"+abbr+"'";
        try {
            resultset=statement.executeQuery(sql);
            while(resultset.next())
            {
                res=resultset.getString(1);
            }
            return res;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getMeaning(String word)
    {
        String res=null;
        String sql="select hotwords.description from hotwords where hotwords.word='"+word+"'";
        try {
            resultset=statement.executeQuery(sql);
            while(resultset.next())
            {
                res=resultset.getString(1);
                return res;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  null;
    }

    public boolean writeRecord(String abbr,String word,String description)
    {
        if(abbr==""){abbr="null";}
        String sql="insert into hotwords values("+abbr+",'"+word+"','"+description+"')";
        int isok;
        try {
            isok=statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        if(isok==1){return true;}
        return false;
    }

    public void close()
    {
        if(statement!=null)
        {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(connection!=null)
        {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
