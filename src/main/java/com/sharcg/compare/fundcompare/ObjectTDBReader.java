package com.sharcg.compare.fundcompare;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sharcg.compare.DBCon;
import com.sharcg.compare.abs.Reader;
import com.sharcg.compare.bean.BatchContext;
import com.sharcg.compare.bean.FileSummary;

/**
 * 读数据库抽象类
 * 
 * @author Sharcg 
 * [简述]:
 * @version $Id: AbstractDBReader.java, v 0.1 2015-9-25 下午4:27:26 Sharcg Exp $
 */
public class ObjectTDBReader implements Reader<FundAcctAmtMatchDO> {
    
    public final static Connection con = DBCon.OracleConnect();

    @Override
    public List<FundAcctAmtMatchDO> read(FileSummary fileSummary, BatchContext<?> batchContext, boolean paging, int currentPage, int pageSize) {
//        String sql = "select FUND_ACCT_SHARE_ID id, FUND_TOTAL_SHARE amt, FUND_CODE_PAF_ASSET_ACCT acct from " +
//        		"(select f.*, rownum r from (select * from ftsdata.T_FTS_FUND_ACCT_SHARE_MATCH order by FUND_CODE_PAF_ASSET_ACCT) f" +
//        		") where r<=? and r>?";
        String sql = "select ID, BATCH_NO, FILE_SUMMARY_ID, FUND_CODE_PAF_ASSET_ACCT, BIZ_CODE, PROD_CODE, FIN_TRANS_ID, " + 
        			  "CUST_ID, INCOME_DATE, PAF_ASSET_ACCT, FUND_ASSET_ACCT, FUND_TRANS_NO, FUND_TOTAL_AMT, MATCH_FLAG, CREATE_TIME, MODIFY_TIME" +
        			  " from (select f.*, rownum r from (select *  from T_FTS_FUND_ACCT_AMT_MATCH where PAF_ASSET_ACCT between ? and ? order by PAF_ASSET_ACCT) f where rownum<=?" +
        			  ") where r>?";
        String beg = batchContext.getBegin();
        String end = batchContext.getEnd();
        return queryRes(sql, beg, end, currentPage, pageSize);
    }

    @Override
    public void readSummary(FileSummary fileSummary, BatchContext<?> batchContext) {
        // TODO Auto-generated method stub
        
    }
    
    public List<FundAcctAmtMatchDO> queryRes(String sql, String beg, String end, int currentPage, int pageSize) {
        List<FundAcctAmtMatchDO> FundAcctAmtMatchDOs = new ArrayList<FundAcctAmtMatchDO>();
//        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
//            con = DBCon.OracleConnect();
            
            ps = con.prepareStatement(sql);
//          
        	ps.setString(1, null==beg?"  ":beg);
        	ps.setString(2, null==end?"~~":end);
            
            ps.setInt(3, currentPage*pageSize);
            ps.setInt(4, (currentPage-1)*pageSize);
//          
            rs= ps.executeQuery();
            while(rs.next()) {
                FundAcctAmtMatchDO FundAcctAmtMatchDO = new FundAcctAmtMatchDO();
                FundAcctAmtMatchDO.setId(rs.getLong("ID"));
                FundAcctAmtMatchDO.setBatchNo(rs.getString("BATCH_NO"));
                FundAcctAmtMatchDO.setFileSummaryId(rs.getLong("FILE_SUMMARY_ID"));
                FundAcctAmtMatchDO.setFundCodePafAssetAcct(rs.getString("FUND_CODE_PAF_ASSET_ACCT"));
                FundAcctAmtMatchDO.setBizCode(rs.getString("BIZ_CODE"));
                FundAcctAmtMatchDO.setProdCode(rs.getString("PROD_CODE"));
                FundAcctAmtMatchDO.setFinTransId(rs.getString("FIN_TRANS_ID"));
                FundAcctAmtMatchDO.setCustId(rs.getString("CUST_ID"));
                FundAcctAmtMatchDO.setIncomeDate(rs.getString("INCOME_DATE"));
                FundAcctAmtMatchDO.setPafAssetAcct(rs.getString("PAF_ASSET_ACCT"));
                FundAcctAmtMatchDO.setFundAssetAcct(rs.getString("FUND_ASSET_ACCT"));
                FundAcctAmtMatchDO.setFundTotalAmt(rs.getBigDecimal("FUND_TOTAL_AMT"));
                FundAcctAmtMatchDOs.add(FundAcctAmtMatchDO);
            }
            
//      } catch (SQLException e) {
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
//                            if(con != null)
//                                try {
//                                    con.close();
//                                } catch (SQLException e) {
//                                    e.printStackTrace();
//                                }
                        }
                }
        }
        
        return FundAcctAmtMatchDOs;
    }

   
    
}
