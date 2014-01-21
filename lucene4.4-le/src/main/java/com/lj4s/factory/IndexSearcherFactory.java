package com.lj4s.factory;

import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.FSDirectory;

import java.io.File;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: liu
 * Date: 13-8-29
 * Time: 上午10:11
 * To change this template use File | Settings | File Templates.
 */
public class IndexSearcherFactory {


    public static IndexSearcher getDefaultIndexSearch(String index) throws IOException {
        IndexReader reader = DirectoryReader.open(FSDirectory.open(new File(index)));
        IndexSearcher searcher = new IndexSearcher(reader);
        return searcher;
    }

    public static IndexSearcher getDefaultIndexSearch(File index) throws IOException {
        IndexReader reader = DirectoryReader.open(FSDirectory.open(index));
        IndexSearcher searcher = new IndexSearcher(reader);
        return searcher;
    }


}
