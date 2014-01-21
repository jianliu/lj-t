package com.lj4s.cache;

import org.apache.lucene.search.TopDocs;

/**
 * Created with IntelliJ IDEA.
 * User: liu
 * Date: 13-9-4
 * Time: 下午2:03
 * To change this template use File | Settings | File Templates.
 */
public class Docs {

    private TopDocs topDocs;
    private Integer docSize;

    public TopDocs getTopDocs() {
        return topDocs;
    }

    public void setTopDocs(TopDocs topDocs) {
        this.topDocs = topDocs;
    }

    public Integer getDocSize() {
        return docSize;
    }

    public void setDocSize(Integer docSize) {
        this.docSize = docSize;
    }
}
