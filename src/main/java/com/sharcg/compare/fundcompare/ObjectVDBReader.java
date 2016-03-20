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
public class ObjectVDBReader implements Reader<PafAcctAmtMatchDO> {
    
    public final static Connection con = DBCon.OracleConnect(1);
    
    @Override
    public List<PafAcctAmtMatchDO> read(FileSummary fileSummary, BatchContext<?> batchContext, boolean paging, int currentPage, int pageSize) {
//        String sql = "select PAF_ACCT_SHARE_ID id, PAF_TOTAL_SHARE amt, FUND_CODE_PAF_ASSET_ACCT acct from " +
//        		"(select p.*, rownum r from ( select * from ftsdata.T_FTS_PAF_ACCT_SHARE_MATCH p where p.PAF_ACCT_SHARE_ID>=100000 order by FUND_CODE_PAF_ASSET_ACCT) p" +
//        		") where r<=? and r>?";
//        
        String sql = "select ID, BATCH_NO, FILE_SUMMARY_ID, FUND_CODE_PAF_ASSET_ACCT, BIZ_CODE, PROD_CODE, CUST_ID, " +
        "INCOME_DATE, PAF_ASSET_ACCT, PAF_TOTAL_AMT, MATCH_FLAG, CREATE_TIME, MODIFY_TIME from " +
        		"(select p.*, rownum r from ( select * from ftsdata.T_FTS_PAF_ACCT_AMT_MATCH where PAF_ASSET_ACCT between ? and ? and ID>=100000 order by PAF_ASSET_ACCT) p where rownum<=?" +
        		") where r>?";
        String beg = batchContext.getBegin();
        String end = batchContext.getEnd();
        return queryRes(sql, beg, end, currentPage, pageSize);
    }

    @Override
    public void readSummary(FileSummary fileSummary, BatchContext<?> batchContext) {
        // TODO Auto-generated method stub
        
    }
    
    public List<PafAcctAmtMatchDO> queryRes(String sql, String beg, String end, int currentPage, int pageSize) {
        List<PafAcctAmtMatchDO> PafAcctAmtMatchDOs = new ArrayList<PafAcctAmtMatchDO>();
//        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
//            con = DBCon.OracleConnect();
            
            ps = con.prepareStatement(sql);
            
            ps.setString(1, null==beg?"  ":beg);
        	ps.setString(2, null==end?"~~":end);
        	
            ps.setInt(3, currentPage*pageSize);
            ps.setInt(4, (currentPage-1)*pageSize);
//          
            rs= ps.executeQuery();
            while(rs.next()) {
                PafAcctAmtMatchDO PafAcctAmtMatchDO = new PafAcctAmtMatchDO();
                
                PafAcctAmtMatchDO.setId(rs.getLong("ID"));
                PafAcctAmtMatchDO.setBatchNo(rs.getString("BATCH_NO"));
                PafAcctAmtMatchDO.setFileSummaryId(rs.getLong("FILE_SUMMARY_ID"));
                PafAcctAmtMatchDO.setFundCodePafAssetAcct(rs.getString("FUND_CODE_PAF_ASSET_ACCT"));
                PafAcctAmtMatchDO.setBizCode(rs.getString("BIZ_CODE"));
                PafAcctAmtMatchDO.setProdCode(rs.getString("PROD_CODE"));
                PafAcctAmtMatchDO.setCustId(rs.getString("CUST_ID"));
                PafAcctAmtMatchDO.setIncomeDate(rs.getString("INCOME_DATE"));
                PafAcctAmtMatchDO.setPafAssetAcct(rs.getString("PAF_ASSET_ACCT"));
                PafAcctAmtMatchDO.setPafTotalAmt(rs.getBigDecimal("PAF_TOTAL_AMT"));
                
                PafAcctAmtMatchDOs.add(PafAcctAmtMatchDO);
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
        
        return PafAcctAmtMatchDOs;
    }

}
