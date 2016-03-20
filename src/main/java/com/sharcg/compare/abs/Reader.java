package com.sharcg.compare.abs;

import java.util.List;

import com.sharcg.compare.bean.BatchContext;
import com.sharcg.compare.bean.FileSummary;

public interface Reader<T> {
    
    public List<T> read(FileSummary fileSummary, BatchContext<?> batchContext, boolean paging, int currentPage, int pageSize);
    
    public void readSummary(FileSummary fileSummary, BatchContext<?> batchContext);

}
