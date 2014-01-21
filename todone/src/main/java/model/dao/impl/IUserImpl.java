package model.dao.impl;

import model.dao.IUserService;
import model.pojo.User;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * User: liu
 * Date: 13-6-19
 * Time: 下午6:02
 * To change this template use File | Settings | File Templates.
 */
@Service
public class IUserImpl implements IUserService {

    public Boolean isPermissionUser(User user) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public User getUser(String mail, String password) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
