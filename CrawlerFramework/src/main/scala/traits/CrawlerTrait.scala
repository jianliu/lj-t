package traits

import scala.Predef._
import scala._
import actors.threadpool.ThreadPoolExecutor
import com.tuan800.deal.monitor.core.listener.LifeStatus
import com.tuan800.deal.monitor.core.data.{CrawlerData, EntryData}
import com.zhe800.deal.monitor.core.cache.StoreFacade
import com.zhe800.deal.monitor.core.utils.BloomFilter
import com.tuan800.deal.monitor.core.toolkit.{HookAndPoolUtil, CralwerResponse, CrawlerKit}


/**
 * User: chenlei
 * Date: 12-4-18
 * Time: 下午12:09
 * Email: poison7@yeah.net
 */

/**
 * 抓取任务特性   单线程
 */
trait CrawlerTrait extends CrawlTrait {


  def getTypeId: Int = 1

  /**
   * 多线程抓取最终页的pool
   */
  def getFinalPagePool: ThreadPoolExecutor = null

  /**
   * 抓取deal或者销量的最终页面
   * @param entryData
   * @return
   */
  def crawlData(entryData: EntryData): CrawlerData

  def crawlData(entryData: EntryData, callback: CrawlerData => Unit) {
    //Empty
  }

  def shutdownFinalPagePool() {
    HookAndPoolUtil.shutdownPool(getFinalPagePool)
    logger.info("finalPagePool shutdown")
  }

  /**
   * 抓取结束
   */
  def crawlExit() {
    shutdownFinalPagePool()
  }

}