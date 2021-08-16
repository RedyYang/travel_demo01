package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Seller;

public interface SellerDao {
    /**
     * 通过id查找商家
     * @param sid
     * @return
     */
    Seller findById(int sid);
}
