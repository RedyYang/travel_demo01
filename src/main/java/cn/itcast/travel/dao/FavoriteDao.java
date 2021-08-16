package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.domain.Route;

import java.util.List;

public interface FavoriteDao {
    /**
     * 查询是否收藏
     * @param rid
     * @param uid
     * @return
     */
    Favorite isFavorite(String rid, int uid);

    /**
     * 查询收藏次数
     * @param rid
     * @return
     */
    int findCountByRid(int rid);

    /**
     * 收藏
     * @param rid
     * @param uid
     */
    void add(int rid, int uid);


    /**
     * 按条件和用户i查询路线条数
     * @param uid
     * @return
     */
    int findTotalCount(int uid);

    /**
     * 通过用户id条件查询收藏路线
     * @param uid
     * @param start
     * @param pageSize
     * @return
     */
    List<Route> findByPage(int uid, int start, int pageSize);

    /**
     * 条件查询获取排行榜条数
     * @param lowPrice
     * @param highPrice
     * @param rname
     * @return
     */
    int findRankTotalCount(int lowPrice, int highPrice, String rname);

    /**
     * 条件分页查询收藏路线并排序
     * @param lowPrice
     * @param highPrice
     * @param start
     * @param pageSize
     * @param rname
     * @return
     */
    List<Route> findRankByPage(int lowPrice, int highPrice, int start, int pageSize, String rname);

    /**
     * 设置收藏次数
     * @param rid
     * @param count
     */
    void setCount(int rid,int count);
}
