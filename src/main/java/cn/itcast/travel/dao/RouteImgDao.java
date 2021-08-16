package cn.itcast.travel.dao;

import cn.itcast.travel.domain.RouteImg;

import java.util.List;

public interface RouteImgDao {
    /**
     * 通过id找图片集合
     * @param rid
     * @return
     */
    List<RouteImg> fingByRid(int rid);
}
