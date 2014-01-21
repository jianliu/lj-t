package shiro.realm;

import model.dao.IUserService;
import model.dao.UserService;
import model.mapper.UserMapper;
import model.pojo.User;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: liu
 * Date: 13-6-19
 * Time: 下午5:44
 * To change this template use File | Settings | File Templates.
 */
public class DBRealm extends AuthorizingRealm {
    //    @Autowired
//    private IUserService iUserService;
    @Autowired
    private UserService userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //获取当前登录的用户名
        User user = (User) super.getAvailablePrincipal(principals);

        List<String> roles = new ArrayList<String>();
        List<String> permissions = new ArrayList<String>();
        if (user != null) {
            roles.add("user");
            permissions.add("list:*");
/*            if (user.getRoles() != null && user.getRoles().size() > 0) {
                for (Role role : user.getRoles()) {
                    roles.add(role.getName());
                    if (role.getPmss() != null && role.getPmss().size() > 0) {
                        for (Permission pmss : role.getPmss()) {
                            if(!StringUtils.isEmpty(pmss.getPermission())){
                                permissions.add(pmss.getPermission());
                            }
                        }
                    }
                }
            }*/
        } else {
            throw new AuthorizationException();
        }
        //给当前用户设置角色
        info.addRoles(roles);
        //给当前用户设置权限
        info.addStringPermissions(permissions);
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        User user = null;
        try {
            user = userService.getUser(token.getUsername(), new String(token.getPassword()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (user != null) {
            return new SimpleAuthenticationInfo(user, user
                    .getLoginPassword(), "");
        } else
            return null;
    }
}
