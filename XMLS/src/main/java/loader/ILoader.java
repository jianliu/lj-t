package loader;

import any.Translate;

/**
 * Created with IntelliJ IDEA.
 * User: liu
 * Date: 13-9-30
 * Time: 下午4:49
 * To change this template use File | Settings | File Templates.
 */
public interface ILoader {

    /**
     * 一个默认的xml内容转换器
     *
     * @return 内容转换器
     */
    public Translate getDefaultTranslate();

    /**
     * 加载xml文件内容 ，它会使用默认的内容转换器
     *
     * @param filename
     * @return
     */
    public Object loadXml(String filename);

    /**
     * 加载xml文件内容
     *
     * @param filename
     * @param translate xml内容转换器
     * @return
     */
    public Object loadXml(String filename, Translate translate);

    /**
     * 获得是否验证xml格式 using xsd
     *
     * @return
     */
    public Boolean getValidatorXsd();

    /**
     * 设置是否验证xml格式   using xsd
     *
     * @param validatorXsd
     */
    public void setValidatorXsd(Boolean validatorXsd);
}
