package com.sharcg.compare.bean;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 文件IO管理
 * 
 * @author Sharcg 
 * [简述]:
 * @version $Id: FileManager.java, v 0.1 2015-9-11 下午8:29:43 Sharcg Exp $
 */
public class FileManager {
    
    private String readFile;
    private String writeFile;
    private FileReader fileReader;
    private FileWriter fileWriter;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    
    private int begIndex;
    private int readIndex;
    
    private int writeBegIndex; //开始写的行数
    
    public int nextIndex() {
        indexToNext();
        return readIndex;
    }
    
    public void indexToNext() {
        ++readIndex;
    }
    
    public BufferedReader getBufferedReader(String fileName) throws IOException {
        
        if(null == readFile || (!readFile.equals(fileName))) {
            readerClose();
            readFile = fileName;
            fileReader = new FileReader(readFile);
            bufferedReader = new BufferedReader(fileReader);
        }
        return bufferedReader;
    }
    
    public BufferedWriter getBufferedWriter(String fileName) throws IOException {
        
        if(null == writeFile || (!writeFile.equals(fileName))) {
            writerClose();
            writeFile = fileName;
            fileWriter = new FileWriter(writeFile);
            bufferedWriter = new BufferedWriter(fileWriter);
        }
        return bufferedWriter;
    }
    
    public void fileClose() throws IOException {
        
        readerClose();
        writerClose();
    }
    
    public void readerClose() throws IOException {
        
        this.begIndex = 0;
        this.readIndex = 0;
        this.readFile = null;
        
        if(bufferedReader != null) bufferedReader.close();
        if(fileReader != null) fileReader.close();
    }
    
    public void writerClose() throws IOException {
        
        this.writeFile = null;
        
        if(bufferedWriter != null) bufferedWriter.close();
        if(fileWriter != null) fileWriter.close();
    }
    
    public void setReadIndexToBegIndex() {
        this.begIndex = this.readIndex;
    }
    
    public void addBegIndex(int addIndex) {
        this.begIndex += addIndex;
    }
    
    public BufferedReader getBufferedReader() {
        return bufferedReader;
    }
    public void setBufferedReader(BufferedReader bufferedReader) {
        this.bufferedReader = bufferedReader;
    }
    public BufferedWriter getBufferedWriter() {
        return bufferedWriter;
    }
    public void setBufferedWriter(BufferedWriter bufferedWriter) {
        this.bufferedWriter = bufferedWriter;
    }
    public int getReadIndex() {
        return readIndex;
    }

    public int getBegIndex() {
        return begIndex;
    }

    public void setBegIndex(int begIndex) {
        this.begIndex = begIndex;
    }

	public int getWriteBegIndex() {
		return writeBegIndex;
	}

	public void setWriteBegIndex(int writeBegIndex) {
		this.writeBegIndex = writeBegIndex;
	}
    
    
}
