package lj.factory;

import lj.crawler.ICrawler;

/**
 * Created with IntelliJ IDEA.
 * User: liu
 * Date: 13-10-31
 * Time: 下午2:06
 * To change this template use File | Settings | File Templates.
 */
public interface CrawlerFactory {


    public ICrawler getCrawler(String id);


}
