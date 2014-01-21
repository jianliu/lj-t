package com.lj4s.all;

import lj.lucene.utils.FileUtil;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.core.WhitespaceTokenizer;
import org.apache.lucene.analysis.miscellaneous.LengthFilter;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
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
import java.io.Reader;
import java.io.StringReader;

/**
 * Created with IntelliJ IDEA.
 * User: liu
 * Date: 13-8-29
 * Time: 上午10:18
 * To change this template use File | Settings | File Templates.
 */
public class TokenSample {

    public static void  main(String[] args) throws Exception {

       final Version matchVersion = Version.LUCENE_44; // Substitute desired Lucene version for XY
        Analyzer analyzer = new Analyzer(){
            @Override
            protected TokenStreamComponents createComponents(String fieldName, Reader reader) {
                final Tokenizer source = new WhitespaceTokenizer(matchVersion, reader);
                TokenStream result = new LengthFilter(matchVersion, source, 5, Integer.MAX_VALUE);
                return new TokenStreamComponents(source, result);
            }
        }; // or any other analyzer
        TokenStream ts = analyzer.tokenStream("myfield", new StringReader("someas text goes here"));
        OffsetAttribute offsetAtt = ts.addAttribute(OffsetAttribute.class);
        CharTermAttribute term=ts.getAttribute(CharTermAttribute.class);

        try {
            ts.reset(); // Resets this stream to the beginning. (Required)
            while (ts.incrementToken()) {
                // Use AttributeSource.reflectAsString(boolean)
                // for token stream debugging.
                System.out.println("token: " + ts.reflectAsString(true));
                System.out.println(term.toString()+"  ---  ");
                System.out.println("token start offset: " + offsetAtt.startOffset());
                System.out.println("  token end offset: " + offsetAtt.endOffset());
            }
            ts.end();   // Perform end-of-stream operations, e.g. set the final offset.
        } finally {
            ts.close(); // Release resources associated with this stream.
        }


    }


}
