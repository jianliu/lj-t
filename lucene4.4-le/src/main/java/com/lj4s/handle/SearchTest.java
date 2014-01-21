package com.lj4s.handle;

import org.apache.lucene.document.Document;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;

/**
 * Created with IntelliJ IDEA.
 * User: liu
 * Date: 13-9-4
 * Time: 下午4:23
 * To change this template use File | Settings | File Templates.
 */
public class SearchTest {


    public static void main(String[] args) throws Exception {

         String prefixHTML = "<font color='red'>";
         String suffixHTML = "</font>";
        PageSearch pageSearch = new PageSearch();
        pageSearch.doSearch(new SimpleHightlightSearcher(),1, "特价 女士");
        Document[] documents = pageSearch.documents;
        for (int i = 0; i < documents.length; i++) {
            String path = documents[i].get("contents");

            System.out.println(path);
        }
        System.out.println("--------");

    }

}
