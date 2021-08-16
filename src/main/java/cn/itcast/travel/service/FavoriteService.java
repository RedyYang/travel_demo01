package cn.itcast.travel.service;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;

public interface FavoriteService {
    /**
     * 查询是否收藏
     * @param rid
     * @param uid
     * @return
     */
    boolean isFavorite(String rid, int uid);

    /**
     * 收藏
     * @param rid
     * @param uid
     */
    void add(String rid, int uid);

    /**
     * 查询收藏的路线
     * @param uid
     * @param currentPage
     * @param pageSize
     * @param rname
     * @return
     */
    PageBean<Route> pageQueryFavorite(int uid, int currentPage, int pageSize);

    /**
     * 按条件查询收藏排行榜
     * @param lowPrice
     * @param highPrice
     * @param currentPage
     * @param pageSize
     * @param rname
     * @return
     */
    PageBean<Route> pageQueryFavoriteRank(int lowPrice, int highPrice, int currentPage, int pageSize, String rname);
}
