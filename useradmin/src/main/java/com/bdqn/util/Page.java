package com.bdqn.util;
public class Page {
	/**页面大小*/
	private int pageSize =1;
	/**当前页号*/
	private int currPageNo=1;
	/**总页数*/
	private int totalPageCount=1;
	/**记录总数*/
	private int reccordCount;
	
	
	/**
	 * 得到开始记录数
	 * @return
	 */
	public int getStartRow(){
		
		return (currPageNo-1)*pageSize+1;
		
	}
	/**
	 * 得到结束记录数
	 * @return
	 */
	public int getEndRow(){
		
		return currPageNo*pageSize;
		
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getCurrPageNo() {
		return currPageNo;
	}
	public void setCurrPageNo(int currPageNo) {
		this.currPageNo = currPageNo;
	}
	// 得到总共页数
	public int getTotalPageCount() {
		if(reccordCount>pageSize&&reccordCount%pageSize>0){
			
			return reccordCount/pageSize+1;
		}
		if(reccordCount>=pageSize&&reccordCount%pageSize==0){
			
			return reccordCount/pageSize;
		}
		if(reccordCount<pageSize){
			return 1;			
		}
		return totalPageCount;
	}
	public void setTotalPageCount(int totalPageCount) {
		this.totalPageCount = totalPageCount;
	}
	public int getReccordCount() {
		return reccordCount;
	}
	public void setReccordCount(int reccordCount) {
		this.reccordCount = reccordCount;
	}
	
	
	
	
	
}
