package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class FavoriteDaoImpl implements FavoriteDao {
    JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public Favorite isFavorite(String rid, int uid) {
        Favorite favorite = null;
        try {
            String sql = "select * from tab_favorite where rid = ? and uid = ?";
            favorite = template.queryForObject(sql,new BeanPropertyRowMapper<Favorite>(Favorite.class),rid,uid);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }

        return favorite;
    }

    @Override
    public int findCountByRid(int rid) {
        String sql = "select count(*) from tab_favorite where rid = ? ";
        return template.queryForObject(sql,Integer.class,rid);
    }

    @Override
    public void add(int rid, int uid) {
        String sql = "insert into tab_favorite values(?,?,?)";
        template.update(sql,rid,new Date(),uid);
    }

    @Override
    public int findTotalCount(int uid) {
        String sql = "select count(*) from tab_favorite a , tab_route b where a.rid = b.rid and uid = ?";

        return template.queryForObject(sql, Integer.class,uid);

    }

    @Override
    public List<Route> findByPage(int uid, int start, int pageSize) {
        String sql = "select b.rid,rname,price,routeIntroduce,rflag,rdate,isThemeTour,count,cid,rimage,sid,sourceId from tab_favorite a, tab_route b where a.rid = b.rid";
        StringBuilder sb = new StringBuilder(sql);
        List params = new ArrayList();
        if(uid!=0){
            sb.append(" and uid = ? ");
            params.add(uid);
        }
        sb.append(" limit ? ,? ");
        params.add(start);
        params.add(pageSize);
        sql = sb.toString();
        return template.query(sql,new BeanPropertyRowMapper<Route>(Route.class),params.toArray());
    }

    @Override
    public int findRankTotalCount(int lowPrice, int highPrice, String rname) {
        String sql = "select count(*) from tab_route where count != 0 ";
        StringBuilder sb = new StringBuilder(sql);
        List params = new ArrayList();
        if(lowPrice!=0){
            sb.append(" and price >= ? ");
            params.add(lowPrice);
        }
        if(highPrice!=0){
            sb.append(" and price <= ? ");
            params.add(highPrice);
        }
        if(rname!=null&&rname.length()>0&&!rname.equals("null")){
            sb.append(" and rname like ? ");
            params.add("%"+rname+"%");
        }
        sql = sb.toString();
        return template.queryForObject(sql,Integer.class,params.toArray());
    }

    @Override
    public List<Route> findRankByPage(int lowPrice, int highPrice, int start, int pageSize, String rname) {
        String sql = "select * from tab_route where count !=0 ";
        //select *
        //FROM
        //(select *
        //from tab_favorite
        //GROUP BY rid
        //ORDER BY count(*) desc) a,tab_route b
        //WHERE a.rid = b.rid
        StringBuilder sb = new StringBuilder(sql);
        List params = new ArrayList();
        if(lowPrice!=0){
            sb.append(" and price >= ? ");
            params.add(lowPrice);
        }
        if(highPrice!=0){
            sb.append(" and price <= ? ");
            params.add(highPrice);
        }
        if(rname!=null&&rname.length()>0&&!rname.equals("null")){
            sb.append("and rname like ? ");
            params.add("%"+rname+"%");
        }
        sb.append(" ORDER BY count desc ");
        sb.append(" limit ?,? ");

        params.add(start);
        params.add(pageSize);
        sql = sb.toString();

        return template.query(sql,new BeanPropertyRowMapper<Route>(Route.class),params.toArray());
    }

    @Override
    public void setCount(int rid,int count) {
        String sql = "update tab_route set count = ? where rid = ?";
        template.update(sql,count,rid);
    }


}
