package lj.lucene;

import java.io.IOException;
import java.io.StringReader;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class Test2 {
    public static void main(String[] args) throws IOException {
        String text="将文档分成一个一个单独的单词";
        //创建分词对象
        Analyzer anal=new IKAnalyzer(true);
        StringReader reader=new StringReader(text);
//        System.setProperty("main_dict","main2012.dic");
//        System.setProperty("quantifier_dict","quantifier.dic");
        //分词
        TokenStream ts=anal.tokenStream("m", reader);
        CharTermAttribute term=ts.getAttribute(CharTermAttribute.class);
        //遍历分词数据
        while(ts.incrementToken()){

            System.out.print(term.toString()+"|");
        }
        reader.close();
        System.out.println();
    }

}