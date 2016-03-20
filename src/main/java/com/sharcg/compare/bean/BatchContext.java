package com.sharcg.compare.bean;

import java.util.List;

public class BatchContext<T> {
    
    private FileManager fileManager;
    private List<T> ojbectTList;
    
    private String  begin;
	private String  end;
    
    public BatchContext() {
        fileManager = new FileManager();
    }
    
    public List<T> getOjbectTList() {
        return ojbectTList;
    }
    public void setOjbectTList(List<T> ojbectTList) {
        this.ojbectTList = ojbectTList;
    }
    public FileManager getFileManager() {
        return fileManager;
    }
    public void setFileManager(FileManager fileManager) {
        this.fileManager = fileManager;
    }

	public String getBegin() {
		return begin;
	}

	public void setBegin(String begin) {
		this.begin = begin;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}
    
    
}
