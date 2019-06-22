package com.sbs.shiro;

import com.sbs.domain.UserDO;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import java.security.Principal;
import java.util.Collection;
import java.util.List;

public class ShiroUtils {
    @Autowired
    private static SessionDAO sessionDAO;

    public static Subject getSubjct() {
        return SecurityUtils.getSubject();
    }
    public static UserDO getUser() {
        Object object = getSubjct().getPrincipal();
        UserDO userDO = (UserDO)object;
//        DeptDao dao = ApplicationContextRegister.getBean(DeptDao.class);
//        if (userDO!=null && userDO.getDeptId()!= null) {
//            DeptDO deptDO = dao.get(userDO.getDeptId());
//            if(deptDO!=null) {
//                userDO.setDeptName(deptDO.getName());
//            }
//        }
        return userDO;
    }
    public static Integer getUserId() {
        return getUser().getUserId();
    }
    public static void logout() {
        getSubjct().logout();
    }

    public static List<Principal> getPrinciples() {
        List<Principal> principals = null;
        Collection<Session> sessions = sessionDAO.getActiveSessions();
        return principals;
    }
}
