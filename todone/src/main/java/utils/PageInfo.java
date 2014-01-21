package utils;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: liu
 * Date: 13-6-5
 * Time: 下午3:15
 * To change this template use File | Settings | File Templates.
 */
public class PageInfo<T> implements java.io.Serializable {
    public static int DEFAULT_PAGESIZE = 10;
    private int start;
    private long totalSize;
    private int pageSize;
    private java.util.List<T> data;

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public long getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(long totalSize) {
        this.totalSize = totalSize;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public PageInfo(int start, long totalSize, int pageSize, List data) {
        this.start = start;
        this.totalSize = totalSize;
        this.pageSize = pageSize;
        this.data = data;
    }

    public long getCurrentPageNo() {
        if ((start+1) % pageSize == 0)
            return (start+1) / pageSize;
        else
            return (start+1) / pageSize + 1;
    }
    public long getTotalCount() {
       return totalSize;
    }

    public long getTotalPageCount() {
        if (totalSize % pageSize == 0)
            return totalSize / pageSize;
        else
            return totalSize / pageSize + 1;
    }
}
