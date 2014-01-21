package com.lj4s.handle;

import com.lj4s.bean.SearchResult;
import com.lj4s.cache.Manager;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.TopDocs;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: liu
 * Date: 13-9-4
 * Time: 下午2:42
 * To change this template use File | Settings | File Templates.
 */
public class PageSearch {

    static SimpleSearcher createSearch() {
        return new SimpleSearcher();
    }

    private final Integer maxCacheSizePerQuery = 100;
    private Byte[] lock = new Byte[]{};
    public static int perPage = 10;        //每页显示的商品数量
    public static int before = 5;        //当前页码之前的页码个数
    public static int after = 5;        //当前页面之后的页码个数
    public int pages = 0;        //总的页面数
    public int curPage = 1;
    public int startPage = 1;     //分页显示的第一个页码
    public int endPage = 0;        //分页显示的最后一个页码
    public int len = 0;            //总共商品数量
    public SearchResult result;
    public Document[] documents;
    public static ISearcher search = createSearch();
    public static Cache cache = Manager.getCache();
    public String queryString = "";
    public String searchingTime = "";

    public PageSearch() {

    }

    public Element getCacheValue(String key, ISearcher search) throws Exception {
        Element element = cache.get(key);
        if (element == null) {
            synchronized (lock) {
                element = cache.get(key);
                if (result == null) {
                    SearchResult result = search.search(key, maxCacheSizePerQuery);
                    element = new Element(key, result);
                    cache.put(element);
                }
            }
        }
        return element;
    }

    public void doSearch(ISearcher search, int curPag, String keywords) throws Exception {

        SearchResult result = (SearchResult) getCacheValue(keywords, search).getObjectValue();
//        SearchResult result = search.search(keywords, 100);
        this.queryString = result.getQuery().toString();
        this.searchingTime=result.getLastingTime()+"";
        this.len = result.getTopDocs().totalHits;
        this.pages = len / perPage + 1;
        if (curPag < 1) {
            curPag = 1;
        }
        this.curPage = curPag;
        this.documents = getDocs(result, this.curPage, perPage);
        this.endPage = this.pages;
    }


    public void doSearch(int curPag, String keywords) throws Exception {

        SearchResult result = (SearchResult) getCacheValue(keywords, search).getObjectValue();
//        SearchResult result = search.search(keywords, 100);
        this.len = result.getTopDocs().totalHits;
        this.pages = len / perPage + 1;
        if (curPag < 1) {
            curPag = 1;
        }
        this.curPage = curPag;
        this.documents = getDocs(result, this.curPage, perPage);
        this.endPage = this.pages;
    }

    public Document[] getDocs(SearchResult result, int curPage, int pageSize) {
        int start = (curPage - 1) * pageSize;
        Document[] documents = result.getDocuments();
        if (documents.length - 1 < start) {
            return null;
        }
        List<Document> list = Arrays.asList(documents);
        int end = Math.min(start + pageSize, documents.length);

        list = list.subList(start, end);
        Document[] docs = list.toArray(new Document[list.size()]);

        return docs;

    }
}
