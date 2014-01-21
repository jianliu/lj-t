package com.lj4s.env;

/**
 * Created with IntelliJ IDEA.
 * User: liu
 * Date: 13-8-29
 * Time: 下午3:17
 * To change this template use File | Settings | File Templates.
 */
public class Environment {

    public final static String Index_Filename = System.getProperty("env.index.filename", "E:/dbindex");

    public final static String Index_Filename_Deals = System.getProperty("env.index.filename", "E:/dealsIndex");

    public final static String Field_Name = "contents";

}
