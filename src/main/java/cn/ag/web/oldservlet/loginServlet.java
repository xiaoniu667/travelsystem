package cn.ag.web.oldservlet;


import cn.ag.domain.ResultInfo;
import cn.ag.domain.User;
import cn.ag.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/loginServlet")
public class loginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.接受username和password数据
        Map<String, String[]> map = request.getParameterMap();
        //2.封装对象
        User user = new User();
        try {
            BeanUtils.populate(user,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        UserServiceImpl userService = new UserServiceImpl();
           User u=userService.login(user);
        ResultInfo resultInfo = new ResultInfo();
        //3.判断一下
        if(u==null){
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("用户名或密码不存在！");
        }
        if(u!=null && !"Y".equals(u.getStatus())){
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("您尚未激活，请激活！");
        }
        if (u!=null&&"Y".equals(u.getStatus())){
            HttpSession session = request.getSession();
            session.setAttribute("login_name",u.getName());
            resultInfo.setFlag(true);
        }
        //转化为json数据写回到客户端
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(resultInfo);
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);


    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
}
