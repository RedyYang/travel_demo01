package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Route;

import java.util.List;

public interface RouteDao {
    /**
     * 查找总记录数
     * @param cid
     * @return
     */
    int findTotalCount(int cid,String name);

    /**
     * 通过类别、开始记录、每页记录查询路线的list集合
     * @param cid
     * @param start
     * @param pageSize
     * @return
     */
    List<Route> findByPage(int cid, int start, int pageSize,String rname);

    /**
     * 根据id查路线信息
     * @param rid
     * @return
     */
    Route findOne(int rid);
}
