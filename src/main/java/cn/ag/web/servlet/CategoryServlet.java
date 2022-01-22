package cn.ag.web.servlet;

import cn.ag.domain.Category;
import cn.ag.service.CategoryService;
import cn.ag.service.impl.CategoryServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/CategoryServlet/*")
public class CategoryServlet extends BaseServlet {
   private      CategoryService categoryService=new CategoryServiceImpl();
    public void findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Category> categoryList = categoryService.findAll();
  /*      ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(),categoryList);*/

         writeValue(categoryList,response);

    }



}
