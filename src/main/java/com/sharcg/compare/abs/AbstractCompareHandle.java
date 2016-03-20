package com.sharcg.compare.abs;

import java.util.ArrayList;
import java.util.List;

public class AbstractCompareHandle<T, V> {
    
    /** 要比对的数据列表T */
    private List<T> listT = new ArrayList<T>();
    
    /** 要比对的数据列表V */
    private List<V> listV = new ArrayList<V>();
    
    /** T列表中多余数据 */
    private List<T> extraT = new ArrayList<T>();
    
    /** V列表中多余数据 */
    private List<V> extraV = new ArrayList<V>();
    
    /** T、V两列表中差异数据 */
    private List<T> diffTV = new ArrayList<T>();
    
    private Integer currentPageT = 0;
    
    private Integer currentPageV = 0;
    
    private Integer persistMaxSize = 1000;
    
    public void nextPageInitT() {
        currentPageT += 1;
        listT = new ArrayList<T>();
    }
    
    public void nextPageInitV() {
        currentPageV += 1;
        listV = new ArrayList<V>();
    }
    
    public void addExtraT(List<T> t) {
        extraT.addAll(t);
//        if(getPersistMaxSize() <= extraT.size()) {
//            persistObjectT(extraT.subList(0, persistMaxSize));
//        }
    }
    
    public void addExtraT(T t) {
        extraT.add(t);
//        if(getPersistMaxSize() <= extraT.size()) {
//            persistObjectT(extraT.subList(0, persistMaxSize));
//        }
    }
    
    public void addExtraV(List<V> v) {
        extraV.addAll(v);
//        if(getPersistMaxSize() <= extraT.size()) {
//            persistObjectT(extraT.subList(0, persistMaxSize));
//        }
    }
    
    public void addExtraV(V v) {
        extraV.add(v);
//        if(getPersistMaxSize() <= extraT.size()) {
//            persistObjectT(extraT.subList(0, persistMaxSize));
//        }
    }
    
    public void addDiffTV(List<T> t) {
        diffTV.addAll(t);
//        if(getPersistMaxSize() <= extraT.size()) {
//            persistObjectT(extraT.subList(0, persistMaxSize));
//        }
    }
    
    public void addDiffTV(T t) {
        diffTV.add(t);
//        if(getPersistMaxSize() <= extraT.size()) {
//            persistObjectT(extraT.subList(0, persistMaxSize));
//        }
    }
    
    
    protected void insertObjectT(T t) {
        extraT.add(t);
//        if(getPersistMaxSize() <= extraT.size()) {
//          persistObjectT(extraT.subList(0, persistMaxSize));
//      }
    }
    
    protected void persistObjectT(final List<T> subList) {
//        transactionTemplate.execute(new TransactionCallback<Integer>() {
//
//            public Integer doInTransaction(TransactionStatus status) {
//
//                for (T t : subList) {
//                    insertObjectT(t);
//                }
//                status.flush();
//                
//                return 0;
//            }
//
//        });
//        extraT = this.extraT.subList(persistMaxSize, extraT.size());
    }

    public List<T> getListT() {
        return listT;
    }

    public void setListT(List<T> listT) {
        this.listT = listT;
    }

    public List<V> getListV() {
        return listV;
    }

    public void setListV(List<V> listV) {
        this.listV = listV;
    }

    public List<T> getExtraT() {
        return extraT;
    }

    public void setExtraT(List<T> extraT) {
        this.extraT = extraT;
    }

    public List<V> getExtraV() {
        return extraV;
    }

    public void setExtraV(List<V> extraV) {
        this.extraV = extraV;
    }

    public List<T> getDiffTV() {
        return diffTV;
    }

    public void setDiffTV(List<T> diffTV) {
        this.diffTV = diffTV;
    }

    public Integer getPersistMaxSize() {
        return persistMaxSize;
    }

    public void setPersistMaxSize(Integer persistMaxSize) {
        this.persistMaxSize = persistMaxSize;
    }

    public Integer getCurrentPageT() {
        return currentPageT;
    }

    public void setCurrentPageT(Integer currentPageT) {
        this.currentPageT = currentPageT;
    }

    public Integer getCurrentPageV() {
        return currentPageV;
    }

    public void setCurrentPageV(Integer currentPageV) {
        this.currentPageV = currentPageV;
    }

    public void saveAllDiff() {

        System.out.println("extraT.size:" + extraT.size() + "  extraT:" + extraT);
        System.out.println("extraV.size:" + extraV.size() + "  extraV:" + extraV);
        System.out.println("diffTV.size:" + diffTV.size() + "  diffTV:" + diffTV);
        
    }
    
    
}
