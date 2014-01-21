package com.lj4s.handle.index;


import com.lj4s.bean.Deal;
import com.lj4s.db.LoaderData;
import com.lj4s.env.Environment;
import lj.lucene.utils.ThreadPoolUtil;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.*;
import java.sql.SQLException;
import java.util.Date;
import java.util.concurrent.*;

/**
 * Index all text files under a directory.
 * <p/>
 * This is a command-line application demonstrating simple Lucene indexing.
 * Run it with no command-line arguments for usage information.
 * Use IK-Analyzer to analysis chinese
 * 本例在索引时进行了简单的优化
 * <p/>
 * 多线程并没有明显的提升速度，主要是因为索引单个文档耗时在addDocument,此时需要同步
 */
public class IndexDB {

    final static String charset = "GBK";

    static int corePoolSize = 30;
    static int maximumPoolSize = 30;
    static int queueSize = 40;
    static long keepAliveTime = 10;
    static BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>(queueSize);
    static ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize,
            keepAliveTime, TimeUnit.SECONDS, workQueue, new ThreadPoolExecutor.CallerRunsPolicy());

    static Byte[] lock = new Byte[]{};

    /**
     * Index all text files under a directory.
     */
    public static void main(String[] args) throws SQLException {
        IndexWriterConfig iwc = null;
        Analyzer analyzer = null;
        String indexPath = Environment.Index_Filename_Deals;


        Date start = new Date();
        try {
            System.out.println("Indexing to directory '" + indexPath + "'...");
            Directory directory = FSDirectory.open(new File(indexPath));
            analyzer = new IKAnalyzer(true);
            //建立内存索引对象
            iwc = new IndexWriterConfig(Version.LUCENE_44, analyzer);

            // 控制写入一个新的segment前内存中保存的document的数目，设置较大的数目可以加快建索引速度，默认为10。
            iwc.setMaxBufferedDocs(100);
            iwc.setOpenMode(OpenMode.CREATE);

            // Optional: for better indexing performance, if you
            // are indexing many documents, increase the RAM
            // buffer.  But if you do this, increase the max heap
            // size to the JVM (eg add -Xmx512m or -Xmx1g):
            //
            iwc.setRAMBufferSizeMB(256.0);
            final IndexWriter writer = new IndexWriter(directory, iwc);
            Deal[] deals = LoaderData.loadDeal();
            for (int i = 0; i < deals.length; i++) {
                final Deal deal = deals[i];

                Future<?> future= poolExecutor.submit(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("进入多线程");
                        try {
                            indexDocs(writer, deal);
                        } catch (IOException e) {
                            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                        }

                    }
                });




            }


            writer.close();

            Date end = new Date();
            System.out.println(end.getTime() - start.getTime() + " total milliseconds");
            ThreadPoolUtil.shutdownPool(poolExecutor, 5);

        } catch (IOException e) {
            System.out.println(" caught a " + e.getClass() +
                    "\n with message: " + e.getMessage());
        }
    }

    static void indexDocs(IndexWriter writer, final Deal deal) throws IOException {
        // do not try to index files that cannot be read
        if (deal != null) {
            // make a new, empty document
            Document doc = new Document();

            Field pathField = new LongField("id", deal.getId(), Field.Store.YES);
            doc.add(pathField);
            doc.add(new LongField("nid", deal.getNumIid(), Field.Store.NO));
            doc.add(new LongField("modified", System.currentTimeMillis(), Field.Store.NO));
            if (deal.getTaobaoTitle() == null) {
                deal.setTaobaoTitle("");
            }
            doc.add(new TextField("contents", deal.getTaobaoTitle(),Field.Store.YES));
            synchronized (lock) {
                long lstart = System.currentTimeMillis();
                if (writer.getConfig().getOpenMode() == OpenMode.CREATE) {
                    System.out.println("adding " + deal.getId());
                    writer.addDocument(doc);
                } else {

                    System.out.println("updating " + deal.getId());
                    writer.updateDocument(new Term("id", deal.getId().toString()), doc);
                }

                System.out.println("cost: " + (System.currentTimeMillis() - lstart) + " ms");

            }

        }
    }
}
