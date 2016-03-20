package com.sharcg.compare.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.BitSet;
import java.util.Date;
import java.util.List;

public class FileSummary implements Serializable {
	/**  */
	private static final long serialVersionUID = 880196523912914080L;
	/** 文件id */
	private Long id;
	private Long parentId;
	/** 文件所属批次号 */
	private String batchNo;
	/** 产品id */
	private String prodId;
	/** 文件名 */
	private String fileName;
	/** 文件类型 txt-00, excel-01 */
	private String fileType;
	/** 文件处理交易类型 */
	private String bizType;
	/** 总金额 */
	private BigDecimal totalAmount;
	/** 总笔数 */
	private Integer totalCount;
	/** 处理状态 */
	private String dealStatus;
	/** 生成时间 */
	private Date createTime;

	private String fundCompanyCode; //基金公司代码
	private String fundCode; // 不可为空，多个fundCode以下划线分割
	private String incomeDate; // 收益日期
	private Integer jobBeginIndex;
	private Integer jobEndIndex;
	private String extField;
	
	//以下字段只就用来做上下文传递，不进行持久化
	private List<String> filePathList;
	private BitSet fundHasPafNo;
	private BitSet pafHasFundNo;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo == null ? null : batchNo.trim();
	}

	public String getProdId() {
		return prodId;
	}

	public void setProdId(String prodId) {
		this.prodId = prodId == null ? null : prodId.trim();
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName == null ? null : fileName.trim();
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType == null ? null : fileType.trim();
	}

	public String getBizType() {
		return bizType;
	}

	public void setBizType(String bizType) {
		this.bizType = bizType == null ? null : bizType.trim();
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public String getDealStatus() {
		return dealStatus;
	}

	public void setDealStatus(String dealStatus) {
		this.dealStatus = dealStatus == null ? null : dealStatus.trim();
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getFundCompanyCode() {
		return fundCompanyCode;
	}

	public void setFundCompanyCode(String fundCompanyCode) {
		this.fundCompanyCode = fundCompanyCode;
	}

	public String getIncomeDate() {
		return incomeDate;
	}

	public void setIncomeDate(String incomeDate) {
		this.incomeDate = incomeDate;
	}

	public String getFundCode() {
		return fundCode;
	}

	public void setFundCode(String fundCode) {
		this.fundCode = fundCode;
	}

	public Integer getJobBeginIndex() {
		return jobBeginIndex;
	}

	public void setJobBeginIndex(Integer jobBeginIndex) {
		this.jobBeginIndex = jobBeginIndex;
	}

	public Integer getJobEndIndex() {
		return jobEndIndex;
	}

	public void setJobEndIndex(Integer jobEndIndex) {
		this.jobEndIndex = jobEndIndex;
	}

	public String getExtField() {
		return extField;
	}

	public void setExtField(String extField) {
		this.extField = extField;
	}

	public List<String> getFilePathList() {
		return filePathList;
	}

	public void setFilePathList(List<String> filePathList) {
		this.filePathList = filePathList;
	}

	public BitSet getFundHasPafNo() {
		return fundHasPafNo;
	}

	public void setFundHasPafNo(BitSet fundHasPafNo) {
		this.fundHasPafNo = fundHasPafNo;
	}

	public BitSet getPafHasFundNo() {
		return pafHasFundNo;
	}

	public void setPafHasFundNo(BitSet pafHasFundNo) {
		this.pafHasFundNo = pafHasFundNo;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FileSummary [id=").append(id).append(", parentId=")
				.append(parentId).append(", batchNo=").append(batchNo)
				.append(", prodId=").append(prodId).append(", fileName=")
				.append(fileName).append(", fileType=").append(fileType)
				.append(", bizType=").append(bizType).append(", totalAmount=")
				.append(totalAmount).append(", totalCount=").append(totalCount)
				.append(", dealStatus=").append(dealStatus)
				.append(", createTime=").append(createTime)
				.append(", fundCompanyCode=").append(fundCompanyCode)
				.append(", fundCode=").append(fundCode).append(", incomeDate=")
				.append(incomeDate).append(", jobBeginIndex=")
				.append(jobBeginIndex).append(", jobEndIndex=")
				.append(jobEndIndex).append(", extField=").append(extField)
				.append(", filePathList=").append(filePathList).append("]");
		return builder.toString();
	}
	
}