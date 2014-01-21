package com.lj4s.bean;

import org.apache.lucene.document.Document;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;

/**
 * Created with IntelliJ IDEA.
 * User: liu
 * Date: 13-8-29
 * Time: 下午2:58
 * To change this template use File | Settings | File Templates.
 */
public class SearchResult {

    private Long lastingTime;

    private TopDocs topDocs;

    private ScoreDoc[] scoreDocs;
    private Document[] documents;
    private Query query;

    public Query getQuery() {
        return query;
    }

    public void setQuery(Query query) {
        this.query = query;
    }

    public Document[] getDocuments() {
        return documents;
    }

    public void setDocuments(Document[] documents) {
        this.documents = documents;
    }

    public Long getLastingTime() {
        return lastingTime;
    }

    public void setLastingTime(Long lastingTime) {
        this.lastingTime = lastingTime;
    }

    public ScoreDoc[] getScoreDocs() {
        return scoreDocs;
    }

    public void setScoreDocs(ScoreDoc[] scoreDocs) {
        this.scoreDocs = scoreDocs;
    }

    public TopDocs getTopDocs() {
        return topDocs;
    }

    public void setTopDocs(TopDocs topDocs) {
        this.topDocs = topDocs;
    }
}
