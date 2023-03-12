package productSystem.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JOptionPane;

import connUtil.ConnectionUtil;
import connUtil.bean.ProductBean;
import productSystem.ProductDAO;

public class ProductDAOImpl implements ProductDAO {
//	@Override
//	public boolean updateProduct(ProductBean product) {
//		String sql = "UPDATE [dbo].[產品資料表]" + "   SET [產品名稱] =?" + "      ,[價格] =?" + "      ,[庫存] = ?"
//				+ " WHERE P_ID=?";
//		ConnectionUtil cUtil = new ConnectionUtil();
//		Connection conn = null;
//		try {
//			conn = cUtil.getConnection();
//			PreparedStatement pstmt = conn.prepareStatement(sql);
//			// System.out.println(sql);
//			pstmt.setString(1, product.getpName());
//			pstmt.setInt(2, product.getPrice());
//			pstmt.setInt(3, product.getStock());
//			pstmt.setInt(4, product.getpId());
//			int executeUpdate = pstmt.executeUpdate();
//			return executeUpdate > 0;
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} finally {
//			try {
//				if (conn != null) {
//					if (!conn.isClosed()) {
//						conn.close();
//					}
//				}
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//		return false;
//	}

	

	public void crawlToCSVAndDatabase(String otc) {
		String sql = "INSERT INTO otcstockprice\r\n"
				+ " VALUES (?, ?, ?, ?, ?, ?,?, ?, ?, ?, ?, ?,?, ?, ?, ?, ?, ?)";

		ConnectionUtil cUtil = new ConnectionUtil();
		Connection conn = null;
		ArrayList<String> tradedate = new ArrayList<>();
		try {
			URL url = new URL(
					"https://query1.finance.yahoo.com/v7/finance/download/2330.TW?\r\n"
					+ "period1\r\n"
					+ "=1590537600&period2=1669507200&interval=1d&events=history&\r\n"
					+ "includeAdjustedClose=true");
			InputStream openStream = url.openStream();
			InputStreamReader irs = new InputStreamReader(openStream);
			BufferedReader br = null;
			br = new BufferedReader(irs);
			String data = "";

			int i = 0;

			while ((data = br.readLine()) != null) {
				String split[] = data.split(",");
				tradedate.add(split[0]);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (int s = 1; s < tradedate.size(); s++) {
			String day1 = tradedate.get(s);
			String day = tradedate.get(s)
					.replace(tradedate.get(s).substring(0, 4),
							Integer.toString(Integer.parseInt(tradedate.get(s).substring(0, 4)) - 1911))
					.replace("-", "/");
			try {

				URL url = new URL(
						"https://www.tpex.org.tw/web/stock/aftertrading/otc_quotes_no1430/stk_wn1430_result.php?l=zh-tw&o=csv&d="
								+ day + "&se=EW&s=0,asc,0");
				// System.out.println(url);

				InputStream openStream = url.openStream();
				InputStreamReader irs = new InputStreamReader(openStream);
				BufferedReader br = null;
				br = new BufferedReader(irs);
				String data = "";
				String key = "資料日期";

				while ((data = br.readLine()) != null) {
					String[] split = data.split(",");
					// System.out.println(data);
					if (split[0].contains(key))
						break;

				} // 將字元串流讀到證券代號前一行第0個index的值為"(元
				conn = cUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
				BufferedWriter bw = new BufferedWriter(new FileWriter("C://temp/otc" + day1 + ".csv"));
				int n = 0;
				while ((data = br.readLine()) != null) {
					if (n == 0) {
						bw.write("日期,");
						String split[] = data.split(",");
						bw.write(data);
						bw.newLine();

						System.out.println(Arrays.toString(split));
					} else {
						bw.write(day1 + ",");
						String split[] = data.replace(",", "").replace("\"\"", "\",\"").split(",");

						System.out.println(Arrays.toString(split));

						for (int a = 0; a < split.length; a++) {

							bw.write(split[a] + ",");

						}
						pstmt.setString(1, day1);
						for (int i = 0; i < split.length; i++) {
							pstmt.setString(i + 2, split[i].replace("\"", ""));
						}
						pstmt.addBatch();
						bw.newLine();
						bw.flush(); // 將緩衝區資料寫入檔案

					}
					pstmt.executeBatch();
					n++;
				} // 繼續將其讀完並寫出csv
				br.close();
				System.out.println("ok");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {

				try {
					if (conn != null) {
						if (!conn.isClosed()) {
							conn.close();
						}
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

	}

	public void crawlToCSVAndDatabase() {
		String sql = "INSERT INTO twsestockpricedemo VALUES (?, ?, ?, ?, ?, ?,?, ?, ?, ?, ?, ?,?, ?, ?, ?, ?)";
		
		ConnectionUtil cUtil = new ConnectionUtil();
		Connection conn = null;
		ArrayList<String> tradedate = new ArrayList<>();
		try {
			URL url = new URL(
					"https://query1.finance.yahoo.com/v7/finance/download/2330.TW?period1=1590537600&period2=1669507200&interval=1d&events=history&includeAdjustedClose=true");
			InputStream openStream = url.openStream();
			InputStreamReader irs = new InputStreamReader(openStream);
			BufferedReader br = null;
			br = new BufferedReader(irs);
			String data = "";

			int i = 0;

			while ((data = br.readLine()) != null) {
				String split[] = data.split(",");
				tradedate.add(split[0]);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (int s = 1; s < tradedate.size(); s++) {
			String day = tradedate.get(s).replace("-", "");
			String day1 = tradedate.get(s);

			try {

				URL url = new URL("https://www.twse.com.tw/zh/exchangeReport/MI_INDEX?response=csv&date=" + day
						+ "&type=ALLBUT0999");
				// System.out.println(url);

				InputStream openStream = url.openStream();
				InputStreamReader irs = new InputStreamReader(openStream);
				BufferedReader br = null;
				br = new BufferedReader(irs);
				String data = "";
				String key = "\"(元";

				while ((data = br.readLine()) != null) {
					String[] split = data.split(",");
					// System.out.println(data);
					if (split[0].contains(key))
						break;

				} // 將字元串流讀到證券代號前一行第0個index的值為"(元
				conn = cUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
				BufferedWriter bw = new BufferedWriter(new FileWriter("C://temp2/twse" + day1 + ".csv"));
				int n = 0;
				while ((data = br.readLine()) != null) {
					if (n == 0) {
						bw.write("日期,");
						String split[] = data.split(",");
						bw.write(data);
						bw.newLine();

						System.out.println(Arrays.toString(split));
					} else {
						bw.write(day1 + ",");
						String split[] = data.replace(",", "").replace("\"\"", "\",\"").split(",");

						System.out.println(Arrays.toString(split));

						for (int a = 0; a < split.length; a++) {

							bw.write(split[a] + ",");

						}
						pstmt.setString(1, day1);
						for (int i = 0; i < split.length; i++) {
							pstmt.setString(i + 2, split[i].replace("\"", "").replace("=", ""));
						}
						pstmt.addBatch();
						bw.newLine();
						bw.flush(); // 將緩衝區資料寫入檔案

					}
					pstmt.executeBatch();
					n++;
				} // 繼續將其讀完並寫出csv
				br.close();
				System.out.println("ok");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {

				try {
					if (conn != null) {
						if (!conn.isClosed()) {
							conn.close();
						}
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (s % 10 == 0)
				try {
					Thread.sleep(20000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}

	}

	public ProductBean getStockPriceByDateId(String inputdate,String inputid) {
//		String inputdate=JOptionPane.showInputDialog("請輸入日期");
//		String inputid=JOptionPane.showInputDialog("請輸入代號");
		ConnectionUtil connUtil = new ConnectionUtil();
		Connection conn = null;
		String sql = "select [日期],[證券代號],[證券名稱],[成交股數],[開盤價],[最高價],[最低價],[收盤價]\r\n"
				+ " from (select [日期],[證券代號],[證券名稱],[成交股數],[開盤價],[最高價],[最低價],[收盤價]\r\n"
				+"from [midterm].[dbo].[otc202211] where 日期=? union select [日期],[證券代號],[證券名稱],\r\n"
				+"[成交股數],[開盤價],[最高價],[最低價],[收盤價]  from [midterm].[dbo].[twse202211] where 日期=?) as a where 證券代號=?";
		ProductBean pb = null;
		try {
			conn = connUtil.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, inputdate);
			pstmt.setString(2, inputdate);
			pstmt.setString(3, inputid);
			
			
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			pb = new ProductBean();
			pb.setDate(rs.getString("日期"));
			pb.setId(rs.getString("證券代號"));
			pb.setStockname(rs.getString("證券名稱"));
			pb.setVol1(rs.getString("成交股數"));
			pb.setOpen(rs.getString("開盤價"));
			pb.setHigh(rs.getString("最高價"));
			pb.setLow(rs.getString("最低價"));
			pb.setClose(rs.getString("收盤價"));

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null) {
					if (!conn.isClosed()) {
						conn.close();
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		pb.show();
		return pb;

	}

	public ProductBean getStockPriceByDate(String input) {
//		String input= JOptionPane.showInputDialog("請輸入日期:格式為yyyy-mm-dd");
//		String inputdb=input.charAt(5)=='0'? input.substring(0,7).replace("-0", "") : input.substring(0,7).replace("-", "");
		ConnectionUtil connUtil = new ConnectionUtil();
		Connection conn = null;
		String sql = "select 日期,證券代號,證券名稱,成交股數,開盤價,最高價,最低價,收盤價 from midterm.dbo.otc202211 where 日期=? union\r\n"
				+ " select 日期,證券代號,證券名稱,成交股數,開盤價,最高價,最低價,收盤價  from midterm.dbo.twse202211 where 日期=?";
		ProductBean pb = null;
		List<String> lst = new ArrayList<String>();
		try {
			conn = connUtil.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, input);
			pstmt.setString(2, input);
			ResultSet rs = pstmt.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			while (rs.next()) {
				for (int i = 1; i <= rsmd.getColumnCount(); i++) {
					// lst.add(rs.getString(i));
					//System.out.print(rs.getString(i) + "\t");
					
					//String a="%"+String.valueOf(25-rs.getString(i).length())+"s";
					
						System.out.format("%30s",rs.getString(i).trim()+"");
					
				}
				System.out.println();

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null) {
					if (!conn.isClosed()) {
						conn.close();
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return pb;
	}

	public void sortYearMonthPrice() {
		ConnectionUtil connUtil = new ConnectionUtil();
		Connection conn = null;
		for (int year = 2020; year <= 2022; year++) {
			for (int month = 1; month <= 12; month++) {
				String otcortwse[]=new String[2];
				otcortwse[0]="twse";	otcortwse[1]="otc";
				for(int i=0;i<=1;i++ ) {
					String sql = "select 日期,證券代號,證券名稱,成交股數,開盤價,最高價,最低價,收盤價 into [midterm].[dbo].["+otcortwse[i] + year + month
							+ "]from midterm.dbo"+otcortwse[i]+"stockprice where cast(substring(日期,1,4) as int) =? and cast(substring(日期,6,2) as int) =? order \r\n"
									+ "by 日期,證券代號";
					try {
						conn = connUtil.getConnection();
						PreparedStatement pstmt = conn.prepareStatement(sql);
						pstmt.setString(1, String.valueOf(year));
						pstmt.setString(2, String.valueOf(month));
						pstmt.executeUpdate();
					} catch (SQLException e) {
						e.printStackTrace();
					} finally {
						try {
							if (conn != null) {
								if (!conn.isClosed()) {
									conn.close();
								}
							}
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
					System.out.println("ok");
				}
			}
		}
	}

	public void bulkInsertcsv() {
		ConnectionUtil connUtil = new ConnectionUtil();
		Connection conn = null;
		for(int year=108;year<=110;year++ ) {
			//store procedure
			String otcortwse[]=new String[2];
			otcortwse[0]="twse";	otcortwse[1]="otc";
			for(int i=0;i<=1;i++ ) {
				String sql="INSERT INTO [dbo].[operatingprofit]  VALUES (?, ?, ?, ?, ?, ?,?,?)";
				try {
					conn = connUtil.getConnection();
					PreparedStatement pstmt = conn.prepareStatement(sql);
				
					
					FileReader fr=new FileReader("C:\\temp1\\"+otcortwse[i]+String.valueOf(year)+".csv");
					
					BufferedReader br = null;
					br=new BufferedReader(fr);
					String data = "";
					int s=0;
					while ((data = br.readLine()) != null) {
							s++;
							if(s==1) continue;
							
							String split[] = data.replace(",", "").replace("\"\"", "\",\"").split(",");

							
							pstmt.setString(1,String.valueOf(split[0].replace("\"", "")));
							pstmt.setString(2,String.valueOf(split[1].replace("\"", "")));
							pstmt.setDouble(3,split[2].trim().equals("\"\"")? 0.0 :Double.valueOf(split[2].replace("\"", "")));
							pstmt.setDouble(4,split[3].trim().equals("\"\"")? 0.0 :Double.valueOf(split[3].replace("\"", "")));
							pstmt.setDouble(5,split[4].trim().equals("\"\"")? 0.0 :Double.valueOf(split[4].replace("\"", "")));
							pstmt.setDouble(6,split[5].trim().equals("\"\"")? 0.0 :Double.valueOf(split[5].replace("\"", "")));
							pstmt.setDouble(7,split[6].trim().equals("\"\"")? 0.0 :Double.valueOf(split[6].replace("\"", "")));
							pstmt.setString(8, String.valueOf(year));
							pstmt.addBatch();
							
					}
					pstmt.executeBatch();
				} 
				
				catch (SQLException e) {
					e.printStackTrace();
				} 
				catch (IOException e) {
					e.printStackTrace();
				}finally {
					try {
						if (conn != null) {
							if (!conn.isClosed()) {
								conn.close();
							}
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				System.out.println("ok");
			}	
		}	
	
	}

	
	
	
	
	
	
	public void deleteSomeData() {
		for (int year = 2020; year <= 2022; year++) {
			for (int month = 1; month <= 12; month++) {
				String otcortwse[]=new String[2];
				otcortwse[0]="twse";	otcortwse[1]="otc";
				for(int i=0;i<=1;i++ ) {
					String sql = "DELETE FROM [dbo].["+ otcortwse[i]+ year + month+"]\r\n" + "WHERE 證券代號 LIKE '共%'";
					ConnectionUtil cUtil = new ConnectionUtil();
					Connection conn = null;
					try {
						conn = cUtil.getConnection();
						PreparedStatement pstmt = conn.prepareStatement(sql);
						pstmt.executeUpdate();
					
					} catch (SQLException e) {
						e.printStackTrace();
					} finally {
						try {
							if (conn != null) {
								if (!conn.isClosed()) {
									conn.close();
								}
							}
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
					System.out.println("ok");
				}	
			}
			
		}	
	}

	
	
	
	
	
	
	
	public void unionPriceInnerjoinOperating() {
		ConnectionUtil connUtil = new ConnectionUtil();
		Connection conn = null;
		String sql =  "select a.日期,a.證券代號,a.證券名稱,a.成交股數,a.開盤價,a.最高價,a.最低價,a.收盤價 into #unionprice\r\n"
				+ "from (select 日期,證券代號,證券名稱,成交股數,開盤價,最高價,最低價,收盤價 from otc202211 union select 日期,\r\n"
				+ "證券代號,證券名稱,成交股數,開盤價,最高價,最低價,收盤價 from twse202211) as a where 日期=?\r\n"
				+ "\r\n"
				+ "\r\n"
				+ "select 公司代號,公司名稱,營業收入,毛利率,營業利益率,稅前純益率,稅後純益率,日期 into #op from \r\n"
				+ "operatingprofit where 日期=? \r\n"
				+ "\r\n"
				+ "\r\n"
				+ "select u.日期,u.證券代號,u.證券名稱,u.成交股數,u.開盤價,u.最高價,u.最低價,u.收盤價,\r\n"
				+ "o.營業收入,o.毛利率,o.營業利益率,o.稅前純益率,o.稅後純益率\r\n"
				+ "from operatingprofit as o\r\n"
				+ "inner join #unionprice as u on u.證券代號=o.公司代號 and substring(u.日期,0,5)=(o.日期)+1912";
		ProductBean pb = null;
		try {
			conn = connUtil.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "2022-11-22");
			pstmt.setString(2, "110");
			
			ResultSet rs = pstmt.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			while (rs.next()) {
				for (int i = 1; i <= rsmd.getColumnCount(); i++) {
					// lst.add(rs.getString(i));
					System.out.print(rs.getString(i) + "\t");

				}
				System.out.println();

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null) {
					if (!conn.isClosed()) {
						conn.close();
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
		

	}

	
	
	
	
	
	
	
	
	
	
	public boolean updatePrice(ProductBean product) {
		String sql=
//				"while(select count(*) from midterm.dbo.a where 日期 != '2022-11-01' and 開盤價 in('--','---','----')>0)\r\n
				"with cte as (\r\n"
				+ "SELECT\r\n"
				+ "    日期,證券代號 \r\n"
				+ "    , Lag(開盤價,1,null) OVER (partition by 證券代號 ORDER by [日期]) as 上期價格\r\n"
				+ "    , Lag(日期,1,null) OVER (partition by 證券代號 ORDER by [日期]) as 上期\r\n"
				+ "FROM midterm.dbo.a\r\n"
				+ ")\r\n"
				+ "\r\n"
				+ "select s.日期,上期,s.證券代號,證券名稱,成交股數,開盤價,c.上期價格,最高價,最低價,收盤價\r\n into midterm.dbo.l1"
				+ " FROM ( midterm.dbo.a s\r\n"
				+ "JOIN cte c\r\n"
				+ " ON s.證券代號 = c.證券代號 and s.日期=c.日期)\r\n";
				
				
				
					


		ConnectionUtil cUtil = new ConnectionUtil();
		Connection conn = null;
		try {
			conn = cUtil.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			int executeUpdate = pstmt.executeUpdate();
			return executeUpdate > 0;
		} catch (SQLException e) {
			
			e.printStackTrace();
		} finally {
			try {
				if (conn != null) {
					if (!conn.isClosed()) {
						conn.close();
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	
	
	
	
	
	
	public boolean updateP(ProductBean product) {
		String sql = "UPDATE [dbo].[a]" + "   SET [證券代號] =?\r\n"
				+ " WHERE 日期=?";
		ConnectionUtil cUtil = new ConnectionUtil();
		Connection conn = null;
		try {
			conn = cUtil.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "0050");
			pstmt.setString(2, "2022-09-01");
			
			int executeUpdate = pstmt.executeUpdate();
			return executeUpdate > 0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (conn != null) {
					if (!conn.isClosed()) {
						conn.close();
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	
}
