package cn.ag.web.oldservlet;

import cn.ag.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/activeUserServlet")
public class activeUserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String code = request.getParameter("code");
        if(code!=null){
            //根据激活码查找对象
            UserServiceImpl userService = new UserServiceImpl();
          boolean flag=userService.active(code);
            String msg=null;
          if(flag){
              msg="激活成功<a href='http://localhost/travel/login.html'>登录</a>";
          }else{
              msg="激活失败！请重新激活~~~";
          }
          //将数据写回前台
          response.setContentType("text/html;charset=utf-8");
          response.getWriter().write(msg);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
}
