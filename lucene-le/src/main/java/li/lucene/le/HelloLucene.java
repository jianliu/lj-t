package li.lucene.le;


import java.io.*;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

/**
 * Created with IntelliJ IDEA.
 * User: liu
 * Date: 13-7-25
 * Time: 下午3:52
 */

public class HelloLucene {
    /**
     * 建立索引
     */
    public void index() {
        Directory directory = null;
        IndexWriter writer = null;

        try {
            // 1、创建Directory
            directory = FSDirectory.open(new File("C:/index")); // 创建在硬盘上

            // 2、创建IndexWriter
            IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_36,
                    new IKAnalyzer(true));
            writer = new IndexWriter(directory, iwc);

            // 3、创建Document对象
            Document doc = null;

            // 4、为Document对象添加Field
            File f = new File("C:/lucene");
            for (File file : f.listFiles()) {
                doc = new Document();

                // String content = FileUtils.readFileToString(file);
                // System.out.println(content);
                // doc.add(new Field("content", content, Field.Store.YES,
                // Field.Index.ANALYZED_NO_NORMS));

                doc.add(new Field("content", new FileReader(file), Field.TermVector.YES));
                doc.add(new Field("fileName", file.getName(), Field.Store.YES,
                        Field.Index.NOT_ANALYZED));
                doc.add(new Field("filePath", file.getAbsolutePath(),
                        Field.Store.YES, Field.Index.NOT_ANALYZED));

                // 5、通过IndexWriter添加文档到索引中
                writer.addDocument(doc);
            }
        } catch (CorruptIndexException | LockObtainFailedException
                | FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (CorruptIndexException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 搜索
     */
    public void search() {
        Directory directory = null;
        IndexReader reader = null;
        IndexSearcher searcher = null;

        try {
            // 1、创建Directory
            directory = FSDirectory.open(new File("C:/index")); // 创建在硬盘上

            // 2、创建IndexReader
            reader = IndexReader.open(directory);

            // 3、根据IndexReader创建IndexSearcher
            searcher = new IndexSearcher(reader);

            // 4、创建搜索的Query
            QueryParser parser = new QueryParser(Version.LUCENE_36, "content",
                    new IKAnalyzer(true));
            String q="词";
            System.err.println("-----"+q);
            Query query = parser.parse(q);

            // 5、根据searcher搜索并且返回TopDocs
            TopDocs tds = searcher.search(query, 10);

            // 6、根据TopDocs获取ScoreDocs对象
            ScoreDoc[] sds = tds.scoreDocs;
            for (ScoreDoc sd : sds) {
                // 7、根据searcher和ScoreDocs对象获取具体的Document对象
                Document document = searcher.doc(sd.doc);

                // 8、根据Document对象获取需要的值
                System.out.println(document.get("fileName") + "("
                        + document.get("filePath") + ")");

                String content = FileUtils.getFileContents(document.get("filePath"));
                System.out.println(content);
                System.out.println("--------------");

            }
        } catch (CorruptIndexException | ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 9、关闭reader
            try {
                searcher.close();
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}