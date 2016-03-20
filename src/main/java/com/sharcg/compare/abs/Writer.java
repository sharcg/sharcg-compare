package com.sharcg.compare.abs;

import java.util.List;

import com.sharcg.compare.bean.BatchContext;
import com.sharcg.compare.bean.FileSummary;

public interface Writer<T> {
    
    public void write(List<T> list, FileSummary fileSummary, BatchContext<?> batchContext);
    
    public int writeSummary(FileSummary fileSummary, BatchContext<?> batchContext);

}
