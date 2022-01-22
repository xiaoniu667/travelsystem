package cn.ag.web.servlet;

import cn.ag.domain.ResultInfo;
import cn.ag.domain.User;
import cn.ag.service.impl.UserServiceImpl;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/UserServlet/*")
public class UserServlet extends BaseServlet {
    /**
     *用户注册
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void regist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //校验验证码，因为验证码在session中
        String check = request.getParameter("check");
        HttpSession session = request.getSession();
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");
        if (checkcode_server == null || !checkcode_server.equalsIgnoreCase(check)) {
            ResultInfo resultInfo = new ResultInfo();
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("验证码错误！");
            //(1).将提示信息转为json
          /*  ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(resultInfo);*/
            String json = writeValueAsString(resultInfo);
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
        String json = writeValueAsString(resultInfo);
        // (2.设置响应头contertType
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);

    }

    /**
     *
     * 用户登录
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */


    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String check = request.getParameter("check");
        HttpSession session = request.getSession();
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");
        if (checkcode_server == null || !checkcode_server.equalsIgnoreCase(check)) {
            ResultInfo resultInfo = new ResultInfo();
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("验证码错误！");
            //(1).将提示信息转为json
          /*  ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(resultInfo);*/
            String json = writeValueAsString(resultInfo);
            // (2.设置响应头contertType
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(json);
            return;

        }
        //1.接受username和password数据
        Map<String, String[]> map = request.getParameterMap();

        /*String remember = request.getParameter("remember");*/
     /*   System.out.println(remember);*/
      /*  String username = request.getParameter("username");
        String password = request.getParameter("password");*/
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
           /* if("1".equals(remember)){
                Cookie cookie1 = new Cookie("username", u.getUsername());
                Cookie cookie2 = new Cookie("password", u.getPassword());
                cookie1.setMaxAge(60 * 60 * 24 * 7);//存七天
                cookie2.setMaxAge(60 * 60 * 24 * 7);
                cookie1.setPath("/");
                cookie2.setPath("/");
                response.addCookie(cookie1);
                response.addCookie(cookie2);
            }*/
            if(map.containsKey("auto_login")){
                Cookie cookie1 = new Cookie("username", u.getUsername());
                Cookie cookie2 = new Cookie("password", u.getPassword());
                cookie1.setMaxAge(60 * 60 * 24 * 7);//存七天
                cookie2.setMaxAge(60 * 60 * 24 * 7);
                cookie1.setPath("/");
                cookie2.setPath("/");
                response.addCookie(cookie1);
                response.addCookie(cookie2);

            }
       //     HttpSession session = request.getSession();
            session.setAttribute("login_name", u.getName());
            session.setAttribute("user", u);
            resultInfo.setFlag(true);
        }
        //转化为json数据写回到客户端
        String json = writeValueAsString(resultInfo);
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);





    }

    /**
     *
     * 显示用户功能
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findbyusername(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Object login_name =  session.getAttribute("login_name");
        /*ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(),login_name);*/
        writeValue(login_name,response);


    }

    /**
     *
     * 用户退出功能
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void exit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.invalidate();
        response.sendRedirect(request.getContextPath() + "/index.html");


    }

    /**
     *
     * 用户邮件激活功能
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void activeUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
    public void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Object user =  session.getAttribute("user");
        /*ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(),login_name);*/
        writeValue(user,response);


    }




}
