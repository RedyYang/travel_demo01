package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.impl.FavoriteDaoImpl;
import cn.itcast.travel.dao.impl.RouteDaoImpl;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.service.FavoriteService;

import java.util.List;

public class FavoriteServiceImpl implements FavoriteService {
    FavoriteDao favoriteDao = new FavoriteDaoImpl();
    @Override
    public boolean isFavorite(String rid, int uid) {

        Favorite favorite = favoriteDao.isFavorite(rid,uid);
        return favorite!=null;
    }

    @Override
    public void add(String rid, int uid) {
        favoriteDao.add(Integer.parseInt(rid),uid);
    }

    @Override
    public PageBean<Route> pageQueryFavorite(int uid, int currentPage, int pageSize) {
        PageBean<Route> pb = new PageBean<Route>();
        pb.setCurrentPage(currentPage);
        pb.setPageSize(pageSize);

        int totalCount = favoriteDao.findTotalCount(uid);
        pb.setTotalCount(totalCount);

        int start = (currentPage-1)*pageSize;
        List<Route> list = favoriteDao.findByPage(uid,start,pageSize);
        pb.setList(list);

        int totalPage = totalCount%pageSize ==0?totalCount/pageSize:(totalCount/pageSize)+1;
        pb.setTotalPage(totalPage);

        return pb;
    }

    @Override
    public PageBean<Route> pageQueryFavoriteRank(int lowPrice, int highPrice, int currentPage, int pageSize, String rname) {
        PageBean<Route> pb = new PageBean<Route>();
        pb.setCurrentPage(currentPage);
        pb.setPageSize(pageSize);

        int totalCount = favoriteDao.findRankTotalCount(lowPrice,highPrice,rname);
        pb.setTotalCount(totalCount);

        int start = (currentPage-1)*pageSize;
        List<Route> list = favoriteDao.findRankByPage(lowPrice,highPrice,start,pageSize,rname);
        pb.setList(list);

        int totalPage = totalCount%pageSize ==0?totalCount/pageSize:(totalCount/pageSize)+1;
        pb.setTotalPage(totalPage);

        return pb;
    }
}
