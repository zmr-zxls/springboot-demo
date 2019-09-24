package com.web.utils;

import java.io.Serializable;
import java.util.List;

/**
 * 简单分页包装类
 * @author zxls-zmr
 * @param <T>
 */
public class SimplePage<T> implements Serializable {
  private static final long serialVersionUID = -3579518962447408751L;
  private List<T> list;
  private long total;
  private long size;
  private long pageIndex;

  public SimplePage(List<T> list) {
    this.list = list;
  }

  public SimplePage(List<T> list, long total) {
    this(list);
		this.total = total;
  }

  public SimplePage(List<T> list, long total, long size, long pageIndex) {
    this(list, total);
    this.size = size;
    this.pageIndex = pageIndex;
  }
  
  public List<T> getList() {
    return list;
  }

  public void setList(List<T> list) {
    this.list = list;
  }

  public long getTotal() {
    return total;
  }

  public void setTotal(long total) {
    this.total = total;
  }

  public long getSize() {
    return size;
  }

  public void setSize(long size) {
    this.size = size;
  }

  public long getPageIndex() {
    return pageIndex;
  }

  public void setPageIndex(long pageIndex) {
    this.pageIndex = pageIndex;
  }
  
  
}