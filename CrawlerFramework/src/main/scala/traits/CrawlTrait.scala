package traits

/**
 * Created with IntelliJ IDEA.
 * User: liu
 * Date: 13-11-26
 * Time: 下午4:56
 * To change this template use File | Settings | File Templates.
 */
trait CrawlTrait {

  var crawlerKit: CrawlerKit = CrawlerKit(getProxyRuleName)

  /**
   * 抓销量还是抓deal
   * @return
   */
  def getTypeId: Int

  private def getSiteId: Long = getTypeId.toLong

  def useProxy: Boolean = false

  def getProxyRuleName: String = "s-baidu"

  def getHtml3 = crawlerKit.get3(_: String, _: String, _: Map[String, String], _: String, this.useProxy, this.useProxy, _: ((String, Int) => Boolean), this.getSiteId, 20L)

  def getHtml4 = crawlerKit.get4(_: String, _: String, _: Map[String, String], _: String, this.useProxy, this.useProxy, _: ((String, Int) => Boolean), this.getSiteId, 5L, 0, 1, 15000, 15000)

  def getHtml4s = crawlerKit.get4(_: String, _: String, _: Map[String, String], _: String, this.useProxy, this.useProxy, _: ((String, Int) => Boolean), this.getSiteId, 2L, 0, 1, 10000, 10000)

  def postHtml = crawlerKit.post3(_: String, _: String, _: Map[String, String], _: String, this.useProxy, this.useProxy, _: ((String, Int) => Boolean), this.getSiteId, 20L)


}
