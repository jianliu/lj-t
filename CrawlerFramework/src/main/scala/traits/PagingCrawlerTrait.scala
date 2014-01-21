package traits

import scala.reflect.BeanProperty
import scala.actors.threadpool.ThreadPoolExecutor

/**
 * Created with IntelliJ IDEA.
 * User: liu
 * Date: 13-11-26
 * Time: 下午5:04
 * To change this template use File | Settings | File Templates.
 */
trait PagingCrawlerTrait extends CrawlTrait {

  @BeanProperty
  var finalPageCrawler: CrawlerTrait = null

  val maxPageNum: Int = 100

  /**
   * 多线程抓取列表页的pool
   */
  def getPagingPagePool: ThreadPoolExecutor = null

  def crawlPage(entryData: EntryData, callback: CrawlerData => Unit) {
    var pageNum: Int = 1
    var pagingPage: PageData = null
    val entryUrl = entryData.getUrl()
    do {
      try {
        //拼凑入口页url，它可以是列表页url，也可以是详情页url，根据具体情况自己处理
        val url: String = this.buildPageEntryUrl(entryUrl, pageNum)
        logger.info("抓取url：{}，页码：{}，翻页后url：{}", Array[AnyRef](entryUrl, pageNum.toString, url))
        pagingPage = finalPageCrawler.crawlData(entryData)
        if (pagingPage == null || pagingPage.list == null || pagingPage.list.size == 0) {
          logger.warn("列表页没有分析出item，entryUrl：{}，pageUrl：{},这个入口页将被废弃，不再翻页", entryUrl, url)
        }
      } catch {
        case e: Throwable =>
          e.printStackTrace()
      }

    } while ((!this.isStopped()) && pagingPage != null && !pagingPage.getEnd())
  }


  def handleCrawlerData(crawlerData:CrawlerData)

  /**
   * 拼凑入口页url，它可以是列表页url，也可以是详情页url，根据具体情况自己处理
   */
  def buildPageEntryUrl(url: String, pageNum: Int): String = url

}
