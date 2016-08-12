/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dc;
/**
 *
 * @author Dell
 */
//import com.mysql.jdbc.Connection;
//import com.mysql.jdbc.Statement;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.*;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author lovey's laptop
 */
public class Connector {
    Connection conn;
    Statement st;
    public Connector(){
        conn = null;
    }
    public void createConnection(String host,String user, String password,String db){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost/"+db,user,password);
         } catch (ClassNotFoundException ex) {
            Logger.getLogger(Connector.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Connector.class.getName()).log(Level.SEVERE, null, ex);
        }
                
    }
    public void getData(){
        try {
            ResultSet rs = null;
            Statement stmt = conn.createStatement();
            if(stmt.execute("select * from event")){
                rs = stmt.getResultSet();
                while(rs.next()){
                   System.out.println(rs.getString(1));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Connector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void create()
    {   try {
            st = conn.createStatement();
            String sql = "CREATE TABLE IP (ip char(20) , filename varchar(50))";
            st.executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(Connector.class.getName()).log(Level.SEVERE, null, ex);
        }
            
    }
    
    public void insert(String a , String b){
        try {
            int executeUpdate = st.executeUpdate("insert into IP values('"+a+"' , '"+b+"' )");
               } catch (SQLException ex) {
            Logger.getLogger(Connector.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    public void deleteip(String a)
    {
        try {
            int executeUpdate = st.executeUpdate("delete from IP where ip = '"+a+"'");
        } catch (SQLException ex) {
            Logger.getLogger(Connector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
         
    public ResultSet query(String a)
    { System.out.println(a);
        try {
            ResultSet rs = null;
               if(st.execute("select * from IP where filename='"+a+"'"))
               {
                    rs = st.getResultSet();
                   return rs;

        }                              
        } catch (SQLException ex) {
            Logger.getLogger(Connector.class.getName()).log(Level.SEVERE, null, ex);
        }
  return null;  }    

}