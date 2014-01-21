package any;

import org.dom4j.Document;

/**
 * Created with IntelliJ IDEA.
 * User: liu
 * Date: 13-9-27
 * Time: 上午11:21
 * 内容转换器
 */
public interface Translate {

    /**
     * 转换文档
     *
     * @param document
     * @return
     */
    public Object translate(Document document);

    /**
     * 转换文档 ,利用其附带的信息
     *
     * @param message
     * @param document
     * @return
     */
    public Object translateV(Object message, Document document);

}
