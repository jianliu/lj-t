package com.lj4s.handle;

import com.lj4s.bean.SearchResult;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: liu
 * Date: 13-9-10
 * Time: 下午3:16
 * To change this template use File | Settings | File Templates.
 */
public interface ISearcher {


    public SearchResult search(String qStr, Analyzer analyzer, Sort sort, ScoreDoc after, int pageSize) throws ParseException, IOException;


    public SearchResult search(String qStr, int pageSize) throws Exception;

    public SearchResult search(String qStr, ScoreDoc after, int pageSize) throws Exception ;

}
