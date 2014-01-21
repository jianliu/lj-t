package com.lj4s.handle;

/**
 * Created with IntelliJ IDEA.
 * User: liu
 * Date: 13-9-4
 * Time: 下午1:15
 * To change this template use File | Settings | File Templates.
 */
public interface Translator<T> {

    public T translateObj(Object obj);

}
