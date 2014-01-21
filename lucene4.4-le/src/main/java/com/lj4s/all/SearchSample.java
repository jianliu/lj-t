package com.lj4s.all;

import lj.lucene.utils.FileUtil;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: liu
 * Date: 13-8-29
 * Time: 上午10:18
 * To change this template use File | Settings | File Templates.
 */
public class SearchSample {

    public static void  main(String[] args) throws IOException, ParseException {
        String index="E:/index01";
        String field="contents";
        IndexReader reader = DirectoryReader.open(FSDirectory.open(new File(index)));
        IndexSearcher searcher = new IndexSearcher(reader);
        Analyzer analyzer = new IKAnalyzer(true);

        QueryParser parser = new QueryParser(Version.LUCENE_44, field, analyzer);
        parser.setDefaultOperator(QueryParser.AND_OPERATOR);

        String qustr = "秦岚陆川";
        String substr="秦岚";

        Query query = parser.parse(qustr);
        System.out.println("Searching for: " + query.toString(field));
        System.out.println("Query = " + query);


//        TermDocs td = ir.TermDocs(new Term("Title", query));
        Sort sort = new Sort(new SortField("path", SortField.Type.STRING,true));
        TopDocs topDocs = searcher.search(query, 10,sort);
        //输出结果
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        System.out.println("命中  " + topDocs.totalHits + "个");
        long dstart = System.currentTimeMillis();
        for (int i = 0; i < scoreDocs.length - 1; i++) {
            int docId = scoreDocs[i].doc;
            Document targetDoc = searcher.doc(docId);
            String path = targetDoc.get("path");
            System.out.println("path：" + path);
            System.out.println();
            String value = FileUtil.getFileContents(path);
            try{
                System.out.println("内容：" + value.substring(value.indexOf(substr)));
            }catch (Exception e){

            }
            System.out.println();
            System.out.println("-----------");
        }
        System.out.println("耗时：" + (System.currentTimeMillis() - dstart) + " ms");

        reader.close();

    }


}
