package lj.lucene;

import com.lj4s.cache.Manager;
import com.lj4s.handle.PageSearch;
import org.apache.lucene.document.Document;

/**
 * Created with IntelliJ IDEA.
 * User: liu
 * Date: 13-9-4
 * Time: 下午4:23
 * To change this template use File | Settings | File Templates.
 */
public class NewTest {


    public static void main(String[] args) throws Exception {

        PageSearch pageSearch = new PageSearch();
        pageSearch.doSearch(1, "秦岚");
        Document[] documents = pageSearch.documents;
        for (int i = 0; i < documents.length; i++) {
            String path = documents[i].get("path");
            System.out.println(path);
        }
        System.out.println("--------");
        pageSearch.doSearch(2, "秦岚");
        documents = pageSearch.documents;
        for (int i = 0; i < documents.length; i++) {
            String path = documents[i].get("path");
            System.out.println(path);
        }
        System.out.println("--------");
        PageSearch.perPage = 20;
        pageSearch.doSearch(1, "秦岚");
        documents = pageSearch.documents;
        for (int i = 0; i < documents.length; i++) {
            String path = documents[i].get("path");
            System.out.println(path);
        }

//        Manager.manager.shutdown();
//        System.exit(1);
//        System.exit(1);
    }

}
