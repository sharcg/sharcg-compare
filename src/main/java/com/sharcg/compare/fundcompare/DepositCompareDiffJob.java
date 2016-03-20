package com.sharcg.compare.fundcompare;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import com.sharcg.compare.abs.AbstractCompareProcess;
import com.sharcg.compare.bean.BatchContext;
import com.sharcg.compare.bean.FileSummary;

public class DepositCompareDiffJob extends AbstractCompareProcess<FundAcctAmtMatchDO, PafAcctAmtMatchDO> {
	
    @Override
    protected int compareTV(FundAcctAmtMatchDO t, PafAcctAmtMatchDO v) {
        if(t.getPafAssetAcct().compareTo(v.getPafAssetAcct()) == 0) {
            if(t.getFundTotalAmt().compareTo(v.getPafTotalAmt()) == 0) {
                return 0;
            }else {
                return 99;
            }
        }else if(t.getPafAssetAcct().compareTo(v.getPafAssetAcct()) < 0) {
            return -1;
        }else {
            return 1;
        }
    }
    
    public String[] mutiCompare(int num) {
    	//
    	return null;
    }
    
    public static void test() {
//      long time = new ReaderFileToTable().readerFileToList("D:\\log\\fund_profit_check_270_20150801.txt");
//      System.out.println("插入文件数据用时：time(ms) = " + time);
      
      long beg = System.currentTimeMillis();
      DepositCompareDiffJob depositCompareDiffJobStep = new DepositCompareDiffJob();
      depositCompareDiffJobStep.setReaderT(new ObjectTDBReader());
      depositCompareDiffJobStep.setReaderV(new ObjectVDBReader());
      
      FileSummary fileSummary = new FileSummary();
      BatchContext<?> batchContext = new BatchContext<FundAcctAmtMatchDO>();
      depositCompareDiffJobStep.process(fileSummary, batchContext);
      System.out.println("核对数据用时：time(ms) = " + (System.currentTimeMillis() -  beg));
//      System.out.println("批量结束，插入文件数据用时：time(ms) = " + time + "  核对数据用时：time(ms) = " + (System.currentTimeMillis() -  beg));
    }
    //210000001000010000804916
    public static void main(String[] args) {
    	System.out.println("比对开始...");
    	CompareThread<?> t = new CompareThread<Object>(null, "210000001000010000804916");
    	Thread thread = new Thread(t);
    	thread.start();
    	
    	CompareThread<?> t2 = new CompareThread<Object>("210000001000010000804916", null);
    	Thread thread2 = new Thread(t2);
    	thread2.start();
    }

}

class CompareThread<T> implements Runnable{
	private BatchContext<?> batchContext = new BatchContext<T>();
	
	public CompareThread() {
	}
	
	public CompareThread(String beg, String end) {
		this.batchContext.setBegin(beg);
		this.batchContext.setEnd(end);
	}
	@Override
	public void run() {
		long beg = System.currentTimeMillis();
        DepositCompareDiffJob depositCompareDiffJobStep = new DepositCompareDiffJob();
        depositCompareDiffJobStep.setReaderT(new ObjectTDBReader());
        depositCompareDiffJobStep.setReaderV(new ObjectVDBReader());
        
        FileSummary fileSummary = new FileSummary();
        depositCompareDiffJobStep.process(fileSummary, batchContext);
        System.out.println("核对数据用时：time(ms) = " + (System.currentTimeMillis() -  beg));
	}
}

