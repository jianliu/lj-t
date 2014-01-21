package model.dao;

import model.pojo.User;

/**
 * Created with IntelliJ IDEA.
 * User: liu
 * Date: 13-6-19
 * Time: 下午6:01
 * To change this template use File | Settings | File Templates.
 */
public interface  IUserService {

    public Boolean isPermissionUser(User user);


    public User getUser(String mail,String password);

}
