package com.sharcg.compare;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBCon {
	public final static int connMax = 1;
	
	private static List<Connection> connections = new ArrayList<Connection>();
	private static Connection connection;
	
	static {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			System.out.println("加载驱动出错：");
			e.printStackTrace();
		}
	}
	
	public static Connection OracleConnect(int index) {
		if(index < 1) index = 1;
		if(index > connMax) return null;
		if(connections.size() >= index) return connections.get(index-1);
		try {
			for(int i=0; i< connMax; i++)
				connections.add(DriverManager.getConnection("jdbc:oracle:thin:@192.168.1.18:1521:d0posb", "ftsopr", "ftsopr1234"));
		} catch (SQLException e) {
			System.out.println("连接数据库出错：");
			e.printStackTrace();
		}
		return connections.get(index);
	}
	
	public static Connection OracleConnect() {
		if(null != connection) return connection;
		try {
//			connection = DriverManager.getConnection("jdbc:oracle:thin:@192.168.1.18:1521:d0posb", "ftsopr", "ftsopr1234");
			connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:oracle", "system", "xiachenggong");
		} catch (SQLException e) {
			System.out.println("连接数据库出错：");
			e.printStackTrace();
		}
		return connection;
	}
	
	public static void main(String[] args) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
//		String sql = "select user_id,user_name,user_sex from users where user_id like ?";
		String sql = "select count(*) from ftsdata.t_fts_transaction";
		try {
			con = DBCon.OracleConnect();
			System.out.println(con);
			ps = con.prepareStatement(sql);
//			ps.setString(1, "1234567890sharcg");
//			
			rs= ps.executeQuery();
			while(rs.next()) {
				System.out.print("count: "+rs.getInt(1)+"\t "+"\n");
			}
			
//		} catch (SQLException e) {
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}finally {
					if(ps != null)
						try {
							ps.close();
						} catch (SQLException e) {
							e.printStackTrace();
						}finally {
							if(con != null)
								try {
									con.close();
								} catch (SQLException e) {
									e.printStackTrace();
								}
						}
				}
		}
		
	}
	
}
