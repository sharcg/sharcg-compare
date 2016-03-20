package com.sharcg.compare.abs;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sharcg.compare.bean.BatchContext;
import com.sharcg.compare.bean.FileSummary;

public abstract class AbstractCompareProcess<T,V>{
    
    /** 日志 */
    protected Logger logger = LoggerFactory.getLogger(getClass().getName());
    
    private int pageSize = 20000;
    
  //是否分页，默认false
    private boolean paging = true;
    
    //读取数据
    private Reader<T> readerT;
    
    //读取数据
    private Reader<V> readerV;
    
    public static int readindex = 0;

    public void process(FileSummary fileSummary, BatchContext<?> batchContext) {
        
        compareProcess(fileSummary, batchContext);
        
    }

    private void compareProcess(FileSummary fileSummary, BatchContext<?> batchContext) {
        AbstractCompareHandle<T, V>  handle = new AbstractCompareHandle<T, V>();
        compareInteval(fileSummary, batchContext, handle);
        
    }

    protected void compareInteval(FileSummary fileSummary, BatchContext<?> batchContext, AbstractCompareHandle<T, V> handle) {
    	int index = readindex ++;
        System.out.println((index) + ": " + System.currentTimeMillis() + ":比对进行中 。。。");
        List<T> listT = handle.getListT();
        List<V> listV = handle.getListV();
        int pageT = handle.getCurrentPageT();
        int pageV = handle.getCurrentPageV();
        System.out.println((index)  + ": " + System.currentTimeMillis() + ":-->是否查询库（开始） 。。。");
        if(null == listT || listT.size() == 0) {
        	System.out.println((index) + ": " + System.currentTimeMillis() + ":-->查询库T 。。。");
            listT = readerT.read(fileSummary, batchContext, paging, pageT + 1, pageSize);
            if(null == listT) {
                listT = new ArrayList<T>();
            }
        }
        if(null == listV || listV.size() == 0) {
        	System.out.println((index) + ": " + System.currentTimeMillis() + ":-->查询库V 。。。");
            listV = readerV.read(fileSummary, batchContext, paging, pageV + 1, pageSize);
            if(null == listV) {
                listV = new ArrayList<V>();
            }
        }
        System.out.println((index) + ": " + System.currentTimeMillis() + ":-->是否查询库（结束） 。。。");
        if(0 == listT.size() && 0 ==  listV.size()) {
            //TODO 保存差异结果数据
            handle.saveAllDiff();
            return;
        }else if(0 == listT.size() && listV.size() > 0) {
            handle.addExtraV(listV);
            handle.nextPageInitV();
            compareInteval(fileSummary, batchContext, handle);
        } else if(0 == listV.size() && listT.size() > 0) {
            handle.addExtraT(listT);
            handle.nextPageInitT();
            compareInteval(fileSummary, batchContext, handle);
        }

        int indexT = 0;
        int indexV = 0;
        System.out.println((index) + ": " + System.currentTimeMillis() + ":-->while（开始） 。。。");
        while(indexT < listT.size() && indexV < listV.size()) {
            
            int compareRes = compareTV(listT.get(indexT), listV.get(indexV));
            if(0 == compareRes) {
                indexT += 1;
                indexV += 1;
            } else if(1 == compareRes) {
                handle.addExtraV(listV.get(indexV));
                indexV += 1;
            } else if(-1 == compareRes) {
                handle.addExtraT(listT.get(indexT));
                indexT += 1;
            } else if(99 == compareRes) {
                handle.addDiffTV(listT.get(indexT));
                indexT += 1;
                indexV += 1;
            } else {
                return;
            }
        }
        System.out.println((index) + ": " + System.currentTimeMillis() + ":-->while（结束） 。。。");
        if(0 == indexT && 0 == indexV) {
            return;
        } else {
            if(indexT == listT.size()) {
                handle.nextPageInitT();
                handle.setListV(listV.subList(indexV, listV.size()));
            }
            if(indexV == listV.size()) {
                handle.nextPageInitV();
                handle.setListT(listT.subList(indexT, listT.size()));
            }
            compareInteval(fileSummary, batchContext, handle);
        }
    }

    /**
     * 具体对账内容
     * 
     * @param t
     * @param v
     * @return 0：比对相同，-1：T中账号小于V中，1：T中账号大于V，99账号相等份额不等
     * @author Sharcg
     */
    protected abstract int compareTV(T t, V v);

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public Reader<T> getReaderT() {
        return readerT;
    }

    public void setReaderT(Reader<T> readerT) {
        this.readerT = readerT;
    }

    public Reader<V> getReaderV() {
        return readerV;
    }

    public void setReaderV(Reader<V> readerV) {
        this.readerV = readerV;
    }

}
