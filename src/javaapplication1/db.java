/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication1;
import java.sql.*;

/**
 *
 * @author Administrator
 */
public class db {
    public Connection conn;
    public db(){
        //System.out.print("aaa");
        String driver ="org.apache.derby.jdbc.EmbeddedDriver";
        String connectionURL ="jdbc:derby://localhost:1527/mis";
        
        try {
           Class.forName(driver);
        }catch(java.lang.ClassNotFoundException e){
            e.printStackTrace();
        }
        
        try{
            this.conn = DriverManager.getConnection(connectionURL, "ls", "123456");
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    public ResultSet cha(String sql){
        ResultSet r=null;
        try{
            Statement stmt = this.conn.createStatement();
            r = stmt.executeQuery(sql);
        }catch (Exception e) {
            e.printStackTrace();
        }finally{
            return r;
        }
    }
    
    public String runSql(String sql){
        try{
            Statement stmt = this.conn.createStatement();
            stmt.execute(sql);
            return ("OKAY");
        }catch (Exception e) {
            e.printStackTrace();
        }
        return ("FAIL");
    }
}
