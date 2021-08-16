package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.service.FavoriteService;
import cn.itcast.travel.service.impl.FavoriteServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/favorite/*")
public class FavoriteServlet extends BaseServlet {
    private FavoriteService favoriteService = new FavoriteServiceImpl();

    public void pageQueryFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String currentPageStr = request.getParameter("currentPage");
        String pageSizeStr = request.getParameter("pageSize");
        String uidStr = request.getParameter("uid");


        int uid = 0;
        if (uidStr != null && uidStr.length() > 0 && !uidStr.equals("null")) {
            uid = Integer.parseInt(uidStr);
        }
        int currentPage = 0;
        if (currentPageStr != null && currentPageStr.length() > 0 && !currentPageStr.equals("null")) {
            currentPage = Integer.parseInt(currentPageStr);
        } else {
            currentPage = 1;
        }

        int pageSize = 0;
        if (pageSizeStr != null && pageSizeStr.length() > 0 && !pageSizeStr.equals("null")) {
            pageSize = Integer.parseInt(pageSizeStr);
        } else {
            pageSize = 12;
        }
        PageBean<Route> pb = favoriteService.pageQueryFavorite(uid, currentPage, pageSize);
        writeValue(pb, response);
    }

    public void pageQueryFavoriteRank(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String currentPageStr = request.getParameter("currentPage");
        String pageSizeStr = request.getParameter("pageSize");
        String lowPriceStr = request.getParameter("lowPrice");
        String highPriceStr = request.getParameter("highPrice");
        String rname = request.getParameter("rname");
        rname = new String(rname.getBytes("iso-8859-1"), "utf-8");


        int lowPrice = 0;
        if (lowPriceStr != null && lowPriceStr.length() > 0 && !lowPriceStr.equals("null")) {
            lowPrice = Integer.parseInt(lowPriceStr);
        }
        int highPrice = 0;
        if (highPriceStr != null && highPriceStr.length() > 0 && !highPriceStr.equals("null")) {
            highPrice = Integer.parseInt(highPriceStr);
        }
        int currentPage = 0;
        if (currentPageStr != null && currentPageStr.length() > 0 && !currentPageStr.equals("null")) {
            currentPage = Integer.parseInt(currentPageStr);
        } else {
            currentPage = 1;
        }

        int pageSize = 0;
        if (pageSizeStr != null && pageSizeStr.length() > 0 && !pageSizeStr.equals("null")) {
            pageSize = Integer.parseInt(pageSizeStr);
        } else {
            pageSize = 8;
        }
        if (rname.equals("null")) {
            rname = "";
        }
        PageBean<Route> pb = favoriteService.pageQueryFavoriteRank(lowPrice,highPrice,currentPage, pageSize, rname);
        writeValue(pb, response);
    }
}
