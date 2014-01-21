package box;

/**
 * Created with IntelliJ IDEA.
 * User: liu
 * Date: 13-9-27
 * Time: 下午3:22
 * To change this template use File | Settings | File Templates.
 */
public class ArrayBean {

    private ExpressType expressType;
    private Object object;

    public ArrayBean(ExpressType expressType, Object object) {
        this.expressType = expressType;
        this.object = object;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public ArrayBean() {
    }

    public ExpressType getExpressType() {
        return expressType;
    }

    public void setExpressType(ExpressType expressType) {
        this.expressType = expressType;
    }


}
