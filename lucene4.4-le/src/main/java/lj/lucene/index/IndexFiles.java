package lj.lucene.index;


import lj.lucene.utils.ThreadPoolUtil;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.*;
import java.util.Date;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Index all text files under a directory.
 * <p/>
 * This is a command-line application demonstrating simple Lucene indexing.
 * Run it with no command-line arguments for usage information.
 * Use IK-Analyzer to analysis chinese
 * 本例在索引时进行了简单的优化
 */
public class IndexFiles {
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
    public static void main(String[] args) {
        IndexWriterConfig iwc = null;
        Analyzer analyzer = null;
        String indexPath = "E:/index01";
        String docsPath = "E:/qinglan";

        final File docDir = new File(docsPath);

        Date start = new Date();
        try {
            System.out.println("Indexing to directory '" + indexPath + "'...");
            Directory directory = FSDirectory.open(new File(indexPath));
            analyzer = new IKAnalyzer(true);
            //建立内存索引对象
            iwc = new IndexWriterConfig(Version.LUCENE_40, analyzer);

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
            if (docDir.canRead()) {
                if (docDir.isDirectory()) {
                    final String[] files = docDir.list();
                    // an IO error could occur
                    if (files != null) {
                        for (int i = 0; i < files.length; i++) {

                            final String filename = files[i];
                            poolExecutor.submit(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        System.out.println("进入多线程");
                                        indexDocs(writer, new File(docDir, filename));
                                    } catch (IOException e) {
                                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                                    }
                                }
                            });

                        }
                        ThreadPoolUtil.shutdownPool(poolExecutor, 5);

                    }

                }
            }

            writer.close();

            Date end = new Date();
            System.out.println(end.getTime() - start.getTime() + " total milliseconds");

        } catch (IOException e) {
            System.out.println(" caught a " + e.getClass() +
                    "\n with message: " + e.getMessage());
        }
    }

    static void indexDocs(IndexWriter writer, File file)
            throws IOException {
        // do not try to index files that cannot be read
        if (file.canRead()) {
            if (file.isFile()) {
                FileInputStream fis;
                try {
                    fis = new FileInputStream(file);
                } catch (FileNotFoundException fnfe) {

                    return;
                }
                try {
                    // make a new, empty document
                    Document doc = new Document();

                    Field pathField = new StringField("path", file.getPath(), Field.Store.YES);
                    doc.add(pathField);

                    doc.add(new LongField("modified", file.lastModified(), Field.Store.NO));

                    doc.add(new TextField("contents", new BufferedReader(new InputStreamReader(fis, charset))));
                    synchronized (lock) {
                        long lstart=System.currentTimeMillis();
                        if (writer.getConfig().getOpenMode() == OpenMode.CREATE) {
                            System.out.println("adding " + file);
                            writer.addDocument(doc);
                        } else {

                            System.out.println("updating " + file);
                            writer.updateDocument(new Term("path", file.getPath()), doc);
                        }

                        System.out.println("cost: " + (System.currentTimeMillis()-lstart)+" ms");

                    }
                } finally {
                    fis.close();
                }
            }
        }
    }
}
