package com.sharcg.compare.fundcompare;

import java.io.BufferedReader;
import java.io.FileReader;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.sharcg.compare.DBCon;

public class ReaderFileToTable {
    
    public final static int maxline = 1000000000;
    
    public final static int maxCommit = 1000;
    
    public final static int readMax = 10000;
    
    public static long baseAcct = 10000000L;
    
    public final static Connection conn = DBCon.OracleConnect();

    public long readerFileToList(String fileName) {
        long beg = System.currentTimeMillis();
        BufferedReader bufferedReader = null;
        List<String> listStr = new ArrayList<String>();
        try {
            bufferedReader = new BufferedReader(new FileReader(fileName));

            String line = null;
            int index = 0;
            while ((line = bufferedReader.readLine()) != null && index < maxline) {
                listStr.add(line);
                index++;
                if(index%readMax == 0) {
                    System.out.println("读取部分数据完成 index: " + index);
                    baseAcct += listStr.size();
                    insertTableTotal(listStr);
                    listStr = new ArrayList<String>();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(bufferedReader != null)
                try {
                    bufferedReader.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
        
        return System.currentTimeMillis()-beg;
    }

    public void insertFundAcctShareMatch(List<FundAcctAmtMatchDO> list) {
        PreparedStatement pstmt = null;
        try {
//            conn = DBCon.OracleConnect();
//            String sql = "  insert into T_FTS_FUND_ACCT_SHARE_MATCH (FUND_ACCT_SHARE_ID, " +
//            		"FUND_COMPANY_CODE, FUND_CODE, INCOME_DATE, PAF_ASSET_ACCT, FUND_CODE_PAF_ASSET_ACCT, " +
//            		"FUND_ASSET_ACCT, FUND_TOTAL_SHARE," +
//            		"CREATE_TIME, MODIFY_TIME, FILE_SUMMARY_ID) values (?,?,?,?,?,?,?,?,?,?,?)";
            String sql =    " insert into T_FTS_FUND_ACCT_AMT_MATCH (ID, BATCH_NO, FILE_SUMMARY_ID,"+
            				"FUND_CODE_PAF_ASSET_ACCT,BIZ_CODE, PROD_CODE,   "    +
            				"FIN_TRANS_ID, CUST_ID, INCOME_DATE,             "    +
            				"PAF_ASSET_ACCT, FUND_ASSET_ACCT, FUND_TRANS_NO, "    +
            				"FUND_TOTAL_AMT, MATCH_FLAG, CREATE_TIME,        "    +
            				"MODIFY_TIME)                                    "    +
            				"values (?, ?,?, ?,?, ?,?, ?,?, ?,?, ?,?, ?, sysdate, sysdate)   ";
            conn.setAutoCommit(false);
            pstmt =   conn.prepareStatement(sql);
            for(int i = 0; i<list.size(); i++) {
                if(i != 11) {
                    FundAcctAmtMatchDO fts = list.get(i);
                    pstmt.setLong(1, fts.getId());
                    pstmt.setString(2, fts.getBatchNo());
                    pstmt.setLong(3, fts.getFileSummaryId());
                    pstmt.setString(4, fts.getFundCodePafAssetAcct());
                    pstmt.setString(5, fts.getBizCode());
                    pstmt.setString(6, fts.getProdCode());
                    pstmt.setString(7, fts.getFinTransId());
                    pstmt.setString(8, fts.getCustId());
                    pstmt.setString(9, fts.getIncomeDate());
                    pstmt.setString(10, fts.getPafAssetAcct());
                    pstmt.setString(11, fts.getFundAssetAcct());
                    pstmt.setString(12, fts.getFundTransNo());
                    if(i==9001)
                    	pstmt.setBigDecimal(13, fts.getFundTotalAmt().add(BigDecimal.TEN));
                    else
                    	pstmt.setBigDecimal(13, fts.getFundTotalAmt());
                    pstmt.setString(14, fts.getMatchFlag());
                    pstmt.addBatch();
                }
                
                if((i != 0 && i%maxCommit == 0) || i == list.size()-1) {
                    pstmt.executeBatch();
                    conn.commit();
                    System.out.println("insertFundAcctShareMatch()-基金核对数据批量提交： " + i);
                }
            }
           
        } catch(Exception e) {
            e.printStackTrace();
        }
        finally {
         try {
            if(pstmt != null)
                            pstmt.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    
//                    try {
////                        if(conn != null) {
////                            conn.close();
////                        }
//                    }  catch (SQLException e) {
//                        e.printStackTrace();
//                    }
         }
    }

    public void insertPafAcctShareMatch(List<FundAcctAmtMatchDO> list) {
//        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
//            conn = DBCon.OracleConnect();
//            String sql = "insert into T_FTS_PAF_ACCT_SHARE_MATCH (PAF_ACCT_SHARE_ID, FUND_COMPANY_CODE, FUND_CODE," +
//            		" INCOME_DATE, PAF_ASSET_ACCT, FUND_CODE_PAF_ASSET_ACCT, PAF_TOTAL_SHARE, CREATE_TIME," +
//            		" MODIFY_TIME, FILE_SUMMARY_ID) values  (?,?,?,?,?,?,?,?,?,?)";
            String sql =  "insert into T_FTS_PAF_ACCT_AMT_MATCH (ID, BATCH_NO, FILE_SUMMARY_ID,    "   +
            	      		"FUND_CODE_PAF_ASSET_ACCT, BIZ_CODE, PROD_CODE,                        "   +
            	      		"CUST_ID, INCOME_DATE, PAF_ASSET_ACCT,                                 "   +
            	      		"PAF_TOTAL_AMT, MATCH_FLAG, CREATE_TIME,                               "   +
            	      		"MODIFY_TIME)                                                          "   +
            	    	   "values (?, ?, ?,?, ?, ?,?, ?, ?, ?, ?, sysdate, sysdate)";
            conn.setAutoCommit(false);
            pstmt =   conn.prepareStatement(sql);
            for(int i = 0; i<list.size(); i++) {
                
                if(i != 20 && i !=3003) {
                    FundAcctAmtMatchDO fts = list.get(i);
                    pstmt.setLong(1, fts.getId());
                    pstmt.setString(2, fts.getBatchNo());
                    pstmt.setLong(3, fts.getFileSummaryId());
                    pstmt.setString(4, fts.getFundCodePafAssetAcct());
                    pstmt.setString(5, fts.getBizCode());
                    pstmt.setString(6, fts.getProdCode());
                    pstmt.setString(7, fts.getCustId());
                    pstmt.setString(8, fts.getIncomeDate());
                    pstmt.setString(9, fts.getPafAssetAcct());
                    pstmt.setBigDecimal(10, fts.getFundTotalAmt());
                    pstmt.setString(11, fts.getMatchFlag());
                    pstmt.addBatch();
                }
                
                if((i != 0 && i%maxCommit == 0) || i == list.size()-1) {
                    pstmt.executeBatch();
                    conn.commit();
                    System.out.println("insertPafAcctShareMatch()-平安付核对数据批量提交： " + i);
                }
            }
           
        } catch(Exception e) {
            e.printStackTrace();
        }
        finally {
         try {
            if(pstmt != null)
                            pstmt.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    
//                    try {
//                        if(conn != null) {
//                            conn.close();
//                        }
//                    }  catch (SQLException e) {
//                        e.printStackTrace();
//                        }
                    }
    }

    public List<String> readFile(String fileName) {
        BufferedReader bufferedReader = null;
        List<String> list = new ArrayList<String>();
        try {
            bufferedReader = new BufferedReader(new FileReader(fileName));

            String line = null;
            int index = 0;
            while ((line = bufferedReader.readLine()) != null && index < maxline) {
                list.add(line);
                index++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(bufferedReader != null)
                try {
                    bufferedReader.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
        return list;

    }
    
    public void insertTableTotal(List<String> lines) {
        
        List<FundAcctAmtMatchDO> list = new ArrayList<FundAcctAmtMatchDO>();
        
//        long begin = 10000000L;
        int index = 0;
        for(String line : lines) {
            String[] splits = line.split("\\|", 9);
            
            FundAcctAmtMatchDO FundAcctAmtMatchDO = new FundAcctAmtMatchDO();
            
            FundAcctAmtMatchDO.setId(baseAcct + index);
            FundAcctAmtMatchDO.setFileSummaryId(1111111111L);
            FundAcctAmtMatchDO.setFundCodePafAssetAcct(splits[0]);
            FundAcctAmtMatchDO.setFundTotalAmt(new BigDecimal(splits[3]));
            FundAcctAmtMatchDO.setCreateTime(new Date());
            FundAcctAmtMatchDO.setModifyTime(new Date());
            FundAcctAmtMatchDO.setFundAssetAcct(splits[1]);
            FundAcctAmtMatchDO.setFinTransId(splits[0]);
            FundAcctAmtMatchDO.setBizCode("1000");
            FundAcctAmtMatchDO.setProdCode("999");
            FundAcctAmtMatchDO.setPafAssetAcct(splits[0]);
            FundAcctAmtMatchDO.setIncomeDate("21001311");
            list.add(FundAcctAmtMatchDO);
            index ++;
        }
        
        insertFundAcctShareMatch(list);
        
        insertPafAcctShareMatch(list);
    }
    
    public void deleteTable() {
        PreparedStatement pstmt = null;
        try {
//            conn = DBCon.OracleConnect();
            String sql = "delete from T_FTS_PAF_ACCT_SHARE_MATCH where rownum <= 1000";
            
//            String sql = "delete from T_FTS_PAF_ACCT_SHARE_MATCH where PAF_ACCT_SHARE_ID";
            conn.setAutoCommit(false);
            pstmt =   conn.prepareStatement(sql);
           
            for(int i= 0; i< 200; i++) {
                pstmt.addBatch();


                pstmt.executeBatch();
                conn.commit();
                System.out.println("insertPafAcctShareMatch()-平安付核对数据批量提交");
            }
           
        } catch(Exception e) {
            e.printStackTrace();
        }
        finally {
         try {
            if(pstmt != null)
                            pstmt.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    
//                    try {
//                        if(conn != null) {
//                            conn.close();
//                        }
//                    }  catch (SQLException e) {
//                        e.printStackTrace();
//                        }
                    }
    }

    public static void main(String[] args) {
//        System.out.println(new java.sql.Date(System.currentTimeMillis()));
        
//        List<String> list = new ArrayList<String>();
//        list.add("111");
//        list.add("222");
//        list.add("333");
//        list.add("444");
//        list.add("555");
//        list.add("666");
//        
//        System.out.println(list.size() +" | "+ list);
//        
//        List<String> list2 = new ArrayList<String>(1024);
//        Collections.copy(list2, list.subList(0, list.size()-3));
//        System.out.println();
//        System.out.println(list2.size() +" | "+ list2);
//        System.out.println(list.size() +" | "+ list);
//        
//        list2.remove(1);
//        System.out.println();
//        System.out.println(list2.size() +" | "+ list2);
//        System.out.println(list.size() +" | "+ list);
        
//        list2 = list.subList(list.size(), list.size());
//        System.out.println();
//        System.out.println(list2.size() +" | "+ list2);
//        System.out.println(list.size() +" | "+ list);
        
        
          new ReaderFileToTable().deleteTable();
        
        
        
    }
}
