package cn.ag.web.servlet;

import cn.ag.domain.PageBean;
import cn.ag.domain.ResultInfo;
import cn.ag.domain.Route;
import cn.ag.domain.User;
import cn.ag.service.FavoriteService;
import cn.ag.service.impl.FavoriteServiceImpl;
import cn.ag.service.impl.RouteServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/RouteServlet/*")
public class RouteServlet extends BaseServlet {
  private   RouteServiceImpl routeService = new RouteServiceImpl();
  private   FavoriteService favoriteService = new FavoriteServiceImpl();

    public void PageQuery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String currentPageStr = request.getParameter("currentPage");
        String pageSizeStr = request.getParameter("pageSize");
        String cidStr = request.getParameter("cid");
        String rname = request.getParameter("rname");
       /* if(rname==null){
            rname ="";
        }*/
        //搜索的关键词
        if ("null".equals(rname)) {
            rname = null;
        } else {
               rname = new String(rname.getBytes("iso-8859-1"), "utf-8");

        }
       // int cid = 0;//类别id
        int cid=0;
        //2.处理参数
        if (cidStr != null && cidStr.length() > 0) {
            cid = Integer.parseInt(cidStr);
        }
        int currentPage = 0;//当前页码，如果不传递，则默认为第一页
        if (currentPageStr != null && currentPageStr.length() > 0) {
            currentPage = Integer.parseInt(currentPageStr);
        } else {
            currentPage = 1;
        }

        int PageSize = 0;//每页显示条数，如果不传递，默认每页显示5条记录
        if (pageSizeStr != null && pageSizeStr.length() > 0) {
            PageSize = Integer.parseInt(pageSizeStr);
        } else {
            PageSize = 5;
        }
        PageBean<Route> pb;
        if(cid==8){
            pb = routeService.pagebean1(currentPage, PageSize, rname);
          //将pb返回给客户端
        }
      else {
            pb = routeService.pagebean(cid, currentPage, PageSize, rname);
          //将pb返回给客户端
        }
        writeValue(pb, response);


    }

    public void findrouteindex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Route> findrouteindex = routeService.findrouteindex();
        writeValue(findrouteindex, response);

    }

    public void findrouteindexguowai(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // List<Category> categoryList = categoryService.findAll();
        List<Route> findrouteindexguowai = routeService.findrouteindexguowai();
        writeValue(findrouteindexguowai, response);

    }

    /**
     * 详情页面的展示
     */
    public void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String rid = request.getParameter("rid");
        Route route = routeService.findOne(Integer.parseInt(rid));
        writeValue(route, response);


    }

    /**
     * 判断用户是否收藏
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void isFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String rid = request.getParameter("rid");
        User user = (User) request.getSession().getAttribute("user");
        int uid;
        if (user == null) {
            uid = 0;
        } else {
            uid = user.getUid();
        }
        boolean flag = favoriteService.isFavorite(Integer.parseInt(rid), uid);
        writeValue(flag, response);

    }

    /**
     * 添加用户收藏
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */

    public void addFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String rid = request.getParameter("rid");
        User user = (User) request.getSession().getAttribute("user");
        int uid;
        if (user == null) {
            ResultInfo resultInfo = new ResultInfo();
            resultInfo.setErrorMsg("您尚未登录，请登录!");
            writeValue(resultInfo, response);
            return;
        } else {
            uid = user.getUid();
        }
        favoriteService.addFavorite(Integer.parseInt(rid), uid);

    }

    /**
     * 我的收藏
     */
    public void myFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //  String rid = request.getParameter("rid");
        String currentPageStr = request.getParameter("currentPage");
        String pageSizeStr = request.getParameter("Pagesize");

        User user = (User) request.getSession().getAttribute("user");
        int uid;
        if (user == null) {
            writeValue(null, response);
            return;
        } else {
            uid = user.getUid();
        }
        int currentPage = 0;//当前页码，如果不传递，则默认为第一页
        if (currentPageStr != null && currentPageStr.length() > 0) {
            currentPage = Integer.parseInt(currentPageStr);
        } else {
            currentPage = 1;
        }

        int PageSize = 0;//每页显示条数，如果不传递，默认每页显示8条记录
        if (pageSizeStr != null && pageSizeStr.length() > 0) {
            PageSize = Integer.parseInt(pageSizeStr);
        } else {
            PageSize = 8;
        }

        PageBean<Route> pb = favoriteService.myFavorite(uid, currentPage, PageSize);
        writeValue(pb, response);


    }

    /**
     * 删除收藏
     */
    public void removefavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String rid = request.getParameter("rid");
        User user = (User) request.getSession().getAttribute("user");
        int uid;
        if (user == null) {
            ResultInfo resultInfo = new ResultInfo();
            resultInfo.setErrorMsg("您尚未登录，请登录!");
            writeValue(resultInfo, response);
            uid = 0;
        } else {
            uid = user.getUid();
        }
        favoriteService.removefavorite(Integer.parseInt(rid), uid);
    }

    /**
     * 人气旅游页面展示功能
     */
    public void indexPopularRoute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int count = 4;
        List<Route> route = routeService.findPopularRoute(count);
        writeValue(route, response);
    }

    /**
     * 最新旅游页面展示功能
     */
    public void indexNewRoute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int count = 4;
        List<Route> route = routeService.findindexNewRoute(count);
        writeValue(route, response);
    }

    /**
     * 主题旅游展示
     */
    public void findindexThemeRoute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int count = 1;
        List<List<Route>> lists = routeService.findindexThemeRoute(count);
        writeValue(lists, response);
    }

    /**
     * 热门列表展示
     */
    public void findhotfavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int count = 5;
        List<Route> route = favoriteService.findhotfavorite(count);
        writeValue(route, response);
    }

    /**
     *
     * 收藏路线排行榜
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void favoriterank(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String currentPagestr = request.getParameter("currentPage");
        String rname = request.getParameter("rname");
        String firststr = request.getParameter("first");
        int currentPage=0;
        if(currentPagestr!=null&currentPagestr.length()>0){
            currentPage=Integer.parseInt(currentPagestr);
        }else {
            currentPage=1;
        }
        if("null".equals(rname)){
         rname="";
        }else{
            //这边不需要处理中文乱码
       rname =rname;
        }
        int first =0;
        if(firststr!=null&firststr.length()>0){
            first=Integer.parseInt(firststr);
        }
        int last =0;
        String laststr= request.getParameter("last");
        if(laststr!=null&laststr.length()>0){
            last=Integer.parseInt(laststr);
        }
        int PageSize=8;

              PageBean<Route>pb=routeService.favoriterank(currentPage,rname,first,last,PageSize);
            writeValue(pb,response);

    }
}
