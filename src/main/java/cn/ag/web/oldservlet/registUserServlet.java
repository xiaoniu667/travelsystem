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

@WebServlet("/registUserServlet")
public class registUserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //校验验证码，因为验证码在session中
        String check = request.getParameter("check");
        HttpSession session = request.getSession();
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");
        if(checkcode_server==null||!checkcode_server.equalsIgnoreCase(check)){
            ResultInfo resultInfo = new ResultInfo();
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("验证码错误！");
            //(1).将提示信息转为json
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(resultInfo);
            // (2.设置响应头contertType
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(json);
            return;

        }

        //1.获取数据
        Map<String, String[]> map = request.getParameterMap();
        //2.封装user对象
        User user = new User();
        try {
            BeanUtils.populate(user, map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        //3.调用service完成注册
        UserServiceImpl userService = new UserServiceImpl();
        boolean flag = userService.regist(user);

        //4.根据service的返回提示信息
        ResultInfo resultInfo = new ResultInfo();
        if (flag) {
            resultInfo.setFlag(true);
        } else {
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("用户名已经存在!");
        }
        //(1).将提示信息转为json
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(resultInfo);
        // (2.设置响应头contertType
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
}
