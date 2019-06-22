package server.util;

import java.sql.*;
import java.util.ArrayList;

/**
 * Dao的基类，使用JDBC连接数据库、释放资源、执行sql，可以被其他Dao实现类继承或实例化使用
 */
public abstract class BaseDao {
    //数据库驱动程序
    public static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    //数据库连接URL
    public static final String URL = "jdbc:mysql://47.107.244.7:3306/chatroom?useUnicode=true&characterEncoding=utf8";
    //数据库帐户名
    public static final String DBNAME ="chat";
    //数据库帐户密码
    public static final String DBPASS ="chat123456";
    //分页大小
    //public static final int PAGESIZE = 20;
    private Connection conn = null;//数据库连接
    protected PreparedStatement pstmt = null;
    protected ResultSet rs = null;

    /**
     * 创建与数据库的连接
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws NamingException
     */
    public Connection getConn() throws SQLException, ClassNotFoundException {
        //加载驱动程序
        Class.forName("com.mysql.cj.jdbc.Driver");
        //获取到数据库的连接
        conn = DriverManager.getConnection(URL, DBNAME, DBPASS);
        System.out.println("数据库连接成功。");
        //返回连接
        return conn;
    }

    /**
     * 释放资源
     * @param conn
     * @param pstmt
     * @param rs
     */
    public void closeAll() {
        /* 如果rs不空，关闭rs */
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        /* 如果pstmt不空，关闭pstmt */
        if (pstmt != null) {
            try {
                pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        /* 如果conn不空，关闭conn */
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 执行带参数的SQL查询语句，并返回查询结果集
     * @param preparedSql  查询SQL语句
     * @param params  SQL参数
     * @return
     * @throws Exception
     */
    public ResultSet execute(String preparedSql, ArrayList params){
        rs = null;
        try {
            conn = getConn();//获取数据库连接
            pstmt = conn.prepareStatement(preparedSql);//准备SQL语句
            if(params!=null){
                int i = 0;
                // 设置SQL语句参数
                for (Object param : params) {
                    //根据参数的类型分别进行处理
                    if (param instanceof String) {//字符串类型
                        pstmt.setString(++i, param.toString());
                    }else if(param instanceof Integer){
                        pstmt.setInt(++i, ((Integer)param).intValue());
                    }
                }
            }
            // 执行SQL语句
            rs = pstmt.executeQuery();
        }catch(ClassNotFoundException ce){
            System.out.println(ce.getMessage());
        }catch(SQLException se){
            System.out.println(se.getMessage());
        }
        return rs;
    }

    /**
     * 执行无返回结果集的SQL语句update,delete,insert
     * @param preparedSql
     * @param params
     * @return
     */
    public int executeSQL(String preparedSql, ArrayList params) {
        int num = 0;
        pstmt = null;
        try {// 取数据库连接
            conn = getConn();
            pstmt = conn.prepareStatement(preparedSql);
            int i = 0;
            // 设置SQL语句参数
            for (Object param : params) {
                //根据参数的类型分别进行处理
                if (param instanceof String) {//字符串类型
                    pstmt.setString(++i, (String) param);
                }else if(param instanceof Integer){
                    pstmt.setInt(++i, ((Integer)param).intValue());
                }
            }
            // 执行SQL语句
            num = pstmt.executeUpdate();
        }catch(ClassNotFoundException ce){
            System.out.println(ce.getMessage());
        }catch(SQLException se){
            System.out.println(se.getMessage());
        } finally {
            closeAll();// 关闭连接
        }
        return num;
    }

    /**
     * 执行SQL语句，并返回执行后的对象集
     * @param preparedSql
     * @param params
     * @return
     */
    public ArrayList executeQuery(String preparedSql, ArrayList params){
        ArrayList result = null;
        try {
            rs = this.execute(preparedSql, params);//执行查执语句
            if (rs != null) {
                result = createObject(rs);//组装查询结果
            }
        } catch (Exception er) {
            er.printStackTrace();
        } finally {
            this.closeAll();
        }
        return result;
    }

    /**
     * 根据结果集组装成相应的对象
     * @return
     */
    protected abstract ArrayList createObject(ResultSet rs) throws SQLException;
}