package cn.ag.service.impl;

import cn.ag.dao.impl.UserDaoImpl;
import cn.ag.domain.User;
import cn.ag.service.UserService;
import cn.ag.util.MailUtils;
import cn.ag.util.UuidUtil;

public class UserServiceImpl implements UserService {
private     UserDaoImpl userDao = new UserDaoImpl();
    @Override
    public boolean regist(User user) {
        User u = userDao.findbyusername(user.getUsername());
        //registUser (User user)1.调用dao根据用户名查询用户~*存在:;直接返回false
        if(u!=null){
            return  false;
        }else {
            //不存在
            //调用dao保存用户信息
            user.setCode(UuidUtil.getUuid());
            user.setStatus("N");
            userDao.save(user);
            String context="<a href='http://localhost/travel/UserServlet/activeUser?code="+user.getCode()+"'>点击我激活</a>";
            //发送邮件
            MailUtils.sendMail(user.getEmail(),context,"激活");



            return true;
        }

    }

    @Override
    public boolean active(String code) {
        //根据code查询对象
        User user = userDao.findbycode(code);
        if(user!=null){
            userDao.updateStatus(user);
            return  true;
        }else{
            return false;
        }

    }

    public User login(User user) {
             return   userDao.findbyusernameandpassword(user.getUsername(),user.getPassword());
    }
}
