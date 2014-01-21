package model.dao.impl;

import model.dao.UserService;
import model.pojo.User;
import org.springframework.stereotype.Service;
import utils.files.FileUtils;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: liu
 * Date: 13-6-6
 * Time: 下午5:42
 * To change this template use File | Settings | File Templates.
 */
@Service
public class UserServiceImpl implements UserService {

    private static String userPath = "/users.txt";
    private static java.util.List<User> users = loadAllUsers();


    public Boolean isPermissionUser(User user) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public User getUser(String mail, String password) throws FileNotFoundException, UnsupportedEncodingException {
        if(users==null){
            users=loadAllUsers();
        }
        User user = null;
        for (User u : users) {
            if (u.getMail().equals(mail)  && u.getLoginPassword().equals( password)) {
                user = u;
            }
        }
        return user;
    }

    public static java.util.List<User> loadAllUsers() {
        java.util.List<User> users = new ArrayList<User>(10);
        String userStrings = null;
        try {
            userStrings = FileUtils.getFileContents(FileUtils.path + "/users.txt").trim();
            String[] contents = userStrings.split(FileUtils.LINE_SEPARATOR, -1);
            for (String content : contents) {
                String[] arr = content.trim().split(" ", -1);
                if (arr.length == 4) {
                    User user = new User();
                    user.setUsername(arr[0].replaceAll("@.*", ""));
                    user.setMail(arr[0]);
                    user.setLoginPassword(arr[1]);
                    user.setPassword(arr[2]);
                    user.setMailTo(arr[3]);
                    users.add(user);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return users;
    }

}
