package model.dao;

import model.pojo.User;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

/**
 * Created with IntelliJ IDEA.
 * User: liu
 * Date: 13-6-6
 * Time: 下午5:41
 * To change this template use File | Settings | File Templates.
 */
public interface UserService {

    public Boolean isPermissionUser(User user);


    public User getUser(String mail,String password) throws FileNotFoundException, UnsupportedEncodingException;

}
