package com.ljy.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;


/**
 * 
 * @author Junyao
 * util class for databases
 */
public class SqlHelper {
	
	private static Connection ct = null;
	
	//Use PreparedStatement to replace Statement can prevent SQL注入
	private static PreparedStatement ps = null;
	private static ResultSet rs = null;
	private static CallableStatement cs = null;
	
	//parameter for connection to database
	private static String url = "";
	private static String username = "";
	private static String driver = "";
	private static String password = "";
	//Read configuration file
	private static Properties pp = null;
	private static InputStream fis = null;
	
	//Load driver(only need once)
	static{
		try {
			//Read config from dbinfo.propertis
			pp = new Properties();
			//fis = new FileInputStream("../src/dbinfo.properties");//tomcat main catalog is at Tomcat/bin
			//读取文件要使用类加载器ClassLoader()[因为类加载器去读取资源的时候，默认的主目录是src]
			fis = SqlHelper.class.getClassLoader().getResourceAsStream("dbinfo.properties");
			pp.load(fis);
			url = pp.getProperty("url");
			username = pp.getProperty("username");
			driver = pp.getProperty("driver");
			password = pp.getProperty("password");
			Class.forName(driver);
		} catch (Exception e) {
			
			e.printStackTrace();
			
		} finally {
			try {
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			fis = null;
		}
	}
	
	//Obtain connection
	public static Connection getConnection(){
		
		try {
			ct = DriverManager.getConnection(url, username, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ct;
	}
	
	//Page Division
	public static ResultSet executeQuery2() {
		return null;
	}
	
	//Load store process
	//sql call process(?,?,?)
	public static CallableStatement callPro2(String sql, String[] inparameters, Integer[] outparameters){
		
		try {
			ct = getConnection();
			cs = ct.prepareCall(sql);
			if(inparameters != null){
				for(int i = 0; i < inparameters.length; i++){
					cs.setObject(i+1, inparameters[i]);
				}
			}
			
			//assign value to out
			if(outparameters != null){
				for(int i = 0; i < outparameters.length; i++){
					cs.registerOutParameter(inparameters.length+1+i, outparameters[i]);
				}				
			}
			
			cs.execute();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		} finally {
			//Do not need to close
		}
		return cs;
	}
	
	public static void callPro1(String sql, String[] parameters) {
		try {
			ct = getConnection();
			cs = ct.prepareCall(sql);
			
			//assgin value to ?
			if(parameters != null){
				for(int i = 0; i < parameters.length; i++){
					cs.setObject(i+1, parameters[i]);
				}
			}
			cs.execute();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		} finally {
			close(rs, cs, ct);
		}
	}

	public static ResultSet executeQuery(String sql, String[] parameters){
		try {
			ct = getConnection();
			ps = ct.prepareStatement(sql);
			
			//assgin value to ?
			if(parameters != null && !parameters.equals("")){
				for(int i = 0; i < parameters.length; i++){
					ps.setString(i+1, parameters[i]);
				}
			}
			rs = ps.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		} finally {
			//close(rs, ps, ct);
		}
		return rs;
	}
	
	public static void executeUpdate2(String[] sql, String[][] parameters){
		try
        {
            ct = getConnection();
            ct.setAutoCommit(false);
            
            for(int i=0;i<sql.length;i++)
            {
                
                if(parameters[i] != null)
                {
                    ps = ct.prepareStatement(sql[i]);
                    for(int j=0;j<parameters[i].length;j++)
                    {
                        ps.setString(j+1,parameters[i][j]);
                    }
                    ps.executeUpdate();
                }
                
            }
            
            ct.commit();
            
            
        }catch (Exception e)
        { 
            e.printStackTrace();
            //回滚
            try
            {
                ct.rollback();
            }
            catch (SQLException e1)
            {
                e1.printStackTrace();
            }
            throw  new RuntimeException(e.getMessage());
        }finally
        {
            close(rs,ps,ct);
        }
	}
	
	public static void executeUpdate(String sql,String[] parameters)
    {
        //create ps
		try
        {
            ct=getConnection();
            ps = ct.prepareStatement(sql);
            if(parameters!=null)
            {
                for(int i=0;i<parameters.length;i++)
                {
                    ps.setString(i+1,parameters[i]);
                }
                            
            }
            ps.executeUpdate();
        }
        catch(Exception e)
        {
            e.printStackTrace();//开发阶段
            //抛出异常
            //可以处理，也可以不处理
            throw new RuntimeException(e.getMessage());
        }
        finally
        {
            close(rs,ps,ct);
        }
    }
	public static void close(ResultSet rs,Statement ps,Connection ct)
    {
        //关闭资源(先开后关)
        if(rs!=null)
        {
            try
            {
                rs.close();
            }
            catch(SQLException e)
            {
                e.printStackTrace();
            }
            rs=null; 
        }
        if(ps!=null)
        {
            try
            {
                ps.close();
            }
            catch(SQLException e)
            {
                e.printStackTrace();
            }
            ps=null;
        }
        if(null!=ct)
        {
            try
            {
                ct.close();
            }
            catch(SQLException e)
            {
                e.printStackTrace();
            }
            ct=null;
        }
    }
	
	public static Connection getCt(){
		return ct;
	}
	public static PreparedStatement getPs(){
		return ps;
	}
	public static ResultSet getRs(){
		return rs;
	}	
	public static CallableStatement getCs(){
		return cs;
	}
}
