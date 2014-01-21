package com.lj4s.handle;

import com.lj4s.bean.SearchResult;
import com.lj4s.env.Environment;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.search.highlight.*;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;

/**
 * Created with IntelliJ IDEA.
 * User: liu
 * Date: 13-8-29
 * Time: 下午2:42
 * To change this template use File | Settings | File Templates.
 */
public class SimpleHightlightSearcher implements ISearcher {

    private org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(SimpleHightlightSearcher.class);

    private String preString = "<b><font color='red'>";
    private String subString = "</font></b>";
    private String indexFilename = Environment.Index_Filename;
    private String fieldName = Environment.Field_Name;
    private Integer pageSize = 10;
    private Boolean firstPage = true;
    private Boolean highlight = true;
    /**
     * 建议第一页时使用
     */
    private Boolean useCache = true;
    private Integer cachedPage = 5;

    public Boolean getFirstPage() {
        return firstPage;
    }

    public void setFirstPage(Boolean firstPage) {
        this.firstPage = firstPage;
    }

    public Integer getCachedPage() {
        return cachedPage;
    }

    public void setCachedPage(Integer cachedPage) {
        this.cachedPage = cachedPage;
    }

    public Boolean getUseCache() {
        return useCache;
    }

    public void setUseCache(Boolean useCache) {
        this.useCache = useCache;
    }

    public SimpleHightlightSearcher() {

    }

    public SimpleHightlightSearcher(String indexFilename) {
        this.indexFilename = indexFilename;
    }

    public void setIndexFilename(String indexFilename) {
        this.indexFilename = indexFilename;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public IndexReader getDefaultIndexReader() throws IOException {
        IndexReader reader = DirectoryReader.open(FSDirectory.open(new File(indexFilename)));
        return reader;
    }


    public Analyzer getDefaultAnalyzer() {
        return new IKAnalyzer(true);
    }

    public QueryParser genDefaultQueryParser(Analyzer analyzer) {

        QueryParser parser = new QueryParser(Version.LUCENE_44, fieldName, analyzer);
        parser.setDefaultOperator(QueryParser.AND_OPERATOR);
        return parser;
    }

    public Highlighter getHighlighter(Query query) {
        SimpleHTMLFormatter simpleHTMLFormatter = new SimpleHTMLFormatter(preString, subString);
        Highlighter highlighter = new Highlighter(simpleHTMLFormatter, new QueryScorer(query));
        highlighter.setTextFragmenter(new SimpleFragmenter(/*Integer.MAX_VALUE*/));
        return highlighter;
    }

    public SearchResult search(String qStr, Analyzer analyzer, Sort sort, ScoreDoc after, int pageSize) throws ParseException, IOException {
        IndexReader reader = getDefaultIndexReader();
        IndexSearcher iSearcher = new IndexSearcher(reader);
        Query query = genDefaultQueryParser(analyzer).parse(qStr);
        String searchFor = query.toString(fieldName);

        if (sort == null) {
            sort = new Sort();
            //  new SortField(Field_Name, SortField.Type.STRING,true)
        }

        long searchBegin = System.currentTimeMillis();
        TopDocs topDocs = iSearcher.searchAfter(after, query, pageSize, sort);
//        iSearcher.search(query, collector);
        Highlighter highlighter = getHighlighter(query);
        //输出结果
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        if (logger.isDebugEnabled())
            logger.debug("qStr:{} --- 命中 {} 个", searchFor, topDocs.totalHits);

        if (logger.isDebugEnabled())
            logger.debug("qStr:{} --- 返回 {} 个", searchFor, topDocs.scoreDocs.length);

        long lastingTime = System.currentTimeMillis() - searchBegin;
        if (logger.isDebugEnabled())
            logger.debug("耗时：" + lastingTime + " ms");
        SearchResult searchResult = new SearchResult();
        searchResult.setQuery(query);
        searchResult.setTopDocs(topDocs);
        searchResult.setLastingTime(lastingTime);
        searchResult.setScoreDocs(scoreDocs);
        Document[] docs = new Document[scoreDocs.length];
        for (int i = 0; i < scoreDocs.length; i++) {
            int docId = scoreDocs[i].doc;
            docs[i] = iSearcher.doc(docId);
            String value = docs[i].get(fieldName);
            if (value != null) {
                TokenStream tokenStream = analyzer.tokenStream(fieldName, new StringReader(value));
                try {
                    String str = highlighter.getBestFragment(tokenStream, value);
                    value = str;
                    //替换
                    docs[i].removeField(fieldName);
                    docs[i].add(new TextField(fieldName, value, Field.Store.NO));
//                    System.out.println(str);
                } catch (InvalidTokenOffsetsException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
        }
        searchResult.setDocuments(docs);
        try {
            return searchResult;
        } finally {
            reader.close();
        }

    }

    public SearchResult search(String qStr, int pageSize) throws Exception {
        Analyzer analyzer = getDefaultAnalyzer();
        return search(qStr, analyzer, null, null, pageSize);
    }

    public SearchResult search(String qStr, ScoreDoc after, int pageSize) throws Exception {
        Analyzer analyzer = getDefaultAnalyzer();
        return search(qStr, analyzer, null, after, pageSize);
    }

    public static void main(String[] args) throws Exception {
        Document[] docs = new Document[10];
        int len = docs.length;
        Document d = docs[1];
        SimpleHightlightSearcher simpleSearcher = new SimpleHightlightSearcher();
        FieldDoc fieldDoc = new FieldDoc(12, 0.0f, new Object[]{0.06403067f});
        fieldDoc = null;
        SearchResult searchResult = simpleSearcher.search("秦岚", fieldDoc, 900);
        for (int i = 0; i < searchResult.getDocuments().length; i++) {
            Document targetDoc = searchResult.getDocuments()[i];
            String path = targetDoc.get("path");
            System.out.print(searchResult.getScoreDocs()[i].doc + "--" + "path：" + path);
//            String value = FileUtil.getFileContents(path);
            System.out.println("-----------");
        }

        System.out.println();
    }

}
