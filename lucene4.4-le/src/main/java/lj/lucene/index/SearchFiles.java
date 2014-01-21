package lj.lucene.index;

/*
* Licensed to the Apache Software Foundation (ASF) under one or more
* contributor license agreements.  See the NOTICE file distributed with
* this work for additional information regarding copyright ownership.
* The ASF licenses this file to You under the Apache License, Version 2.0
* (the "License"); you may not use this file except in compliance with
* the License.  You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

import lj.lucene.utils.FileUtil;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;

/**
 * Simple command-line based search demo.
 */
public class SearchFiles {

    private SearchFiles() {
    }

    /**
     * Simple command-line based search demo.
     */
    public static void main(String[] args) throws Exception {
//        System.setProperty("main_dict", "main2012.dic");
//        System.setProperty("quantifier_dict", "quantifier.dic");
        String usage =
                "Usage:\tjava org.apache.lucene.demo.SearchFiles [-index dir] [-field f] [-repeat n] [-queries file] [-query string] [-raw] [-paging hitsPerPage]\n\nSee http://lucene.apache.org/core/4_1_0/demo/ for details.";
        if (args.length > 0 && ("-h".equals(args[0]) || "-help".equals(args[0]))) {
            System.out.println(usage);
            System.exit(0);
        }
        String qustr = "秦岚陆川";
        String substr="秦岚";
        String phrastr = "笑了";
        String index = "E:/index01";
        String field = "contents";


        IndexReader reader = DirectoryReader.open(FSDirectory.open(new File(index)));
        IndexSearcher searcher = new IndexSearcher(reader);
        Analyzer analyzer = new IKAnalyzer(true);
//        Terms terms= reader.getTermVector(1, field);


        QueryParser parser = new QueryParser(Version.LUCENE_44, field, analyzer);
        parser.setDefaultOperator(QueryParser.AND_OPERATOR);

//        Term t = new Term("contents", qustr);
//        Query query = new TermQuery(t);

//        PhraseQuery query = new PhraseQuery();
//        query.setSlop(5);
//        query.add(new Term("contents",qustr));
//        query.add(new Term("contents",phrastr));
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

    /**
     * This demonstrates a typical paging search scenario, where the search engine presents
     * pages of size n to the user. The user can then go to the next page if interested in
     * the next hits.
     * <p/>
     * When the query is executed for the first time, then only enough results are collected
     * to fill 5 result pages. If the user wants to page beyond this limit, then the query
     * is executed another time and all hits are collected.
     */
    public static void doPagingSearch(BufferedReader in, IndexSearcher searcher, Query query,
                                      int hitsPerPage, boolean raw, boolean interactive) throws IOException {

        // Collect enough docs to show 5 pages
        TopDocs results = searcher.search(query, 5 * hitsPerPage);
        ScoreDoc[] hits = results.scoreDocs;

        int numTotalHits = results.totalHits;
        System.out.println(numTotalHits + " total matching documents");

        int start = 0;
        int end = Math.min(numTotalHits, hitsPerPage);

        while (true) {
            if (end > hits.length) {
                System.out.println("Only results 1 - " + hits.length + " of " + numTotalHits + " total matching documents collected.");
                System.out.println("Collect more (y/n) ?");
                String line = in.readLine();
                if (line.length() == 0 || line.charAt(0) == 'n') {
                    break;
                }

                hits = searcher.search(query, numTotalHits).scoreDocs;
            }

            end = Math.min(hits.length, start + hitsPerPage);

            for (int i = start; i < end; i++) {
                if (raw) {                              // output raw format
                    System.out.println("doc=" + hits[i].doc + " score=" + hits[i].score);
                    continue;
                }

                Document doc = searcher.doc(hits[i].doc);
                String path = doc.get("path");
                if (path != null) {
                    System.out.println((i + 1) + ". " + path);
                    String title = doc.get("title");
                    if (title != null) {
                        System.out.println("   Title: " + doc.get("title"));
                    }
                } else {
                    System.out.println((i + 1) + ". " + "No path for this document");
                }

            }

            if (!interactive || end == 0) {
                break;
            }

            if (numTotalHits >= end) {
                boolean quit = false;
                while (true) {
                    System.out.print("Press ");
                    if (start - hitsPerPage >= 0) {
                        System.out.print("(p)revious page, ");
                    }
                    if (start + hitsPerPage < numTotalHits) {
                        System.out.print("(n)ext page, ");
                    }
                    System.out.println("(q)uit or enter number to jump to a page.");

                    String line = in.readLine();
                    if (line.length() == 0 || line.charAt(0) == 'q') {
                        quit = true;
                        break;
                    }
                    if (line.charAt(0) == 'p') {
                        start = Math.max(0, start - hitsPerPage);
                        break;
                    } else if (line.charAt(0) == 'n') {
                        if (start + hitsPerPage < numTotalHits) {
                            start += hitsPerPage;
                        }
                        break;
                    } else {
                        int page = Integer.parseInt(line);
                        if ((page - 1) * hitsPerPage < numTotalHits) {
                            start = (page - 1) * hitsPerPage;
                            break;
                        } else {
                            System.out.println("No such page");
                        }
                    }
                }
                if (quit) break;
                end = Math.min(numTotalHits, start + hitsPerPage);
            }
        }
    }
}
