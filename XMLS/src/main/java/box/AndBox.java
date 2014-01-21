package box;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: liu
 * Date: 13-9-27
 * Time: 下午3:31
 * To change this template use File | Settings | File Templates.
 */
public class AndBox<T extends List<ArrayBean>> extends MBox<T> {
    private T t;

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }

    public AndBox(T t) {
        this.t = t;
    }

}
