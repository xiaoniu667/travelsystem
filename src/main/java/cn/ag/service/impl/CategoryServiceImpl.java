package cn.ag.service.impl;

import cn.ag.dao.CategoryDao;
import cn.ag.dao.impl.CategoryDaoImpl;
import cn.ag.domain.Category;
import cn.ag.service.CategoryService;

import java.util.List;

public class CategoryServiceImpl implements CategoryService {
    private CategoryDao categoryDao = new CategoryDaoImpl();

    @Override
    public List<Category> findAll() {
      /*  //1.从redis中获取数据
        Jedis jedis = JedisUtil.getJedis();
        Set<String> category = jedis.zrange("category", 0, -1);
        List<Category> cs=null;
        if(category==null||category.size()==0){
            //从数据库中查询
            cs=  categoryDao.findAll();
            //将数据存到redis中
            for (int i = 0; i < cs.size(); i++) {
                jedis.zadd("category",cs.get(i).getCid(),cs.get(i).getCname());
            }
        }else {
            cs = new ArrayList<Category>();
            for (String  name : category) {
                Category category1 = new Category();
                category1.setCname(name);
                cs.add(category1);
            }
        }
        return  cs;
    }*/
        return  categoryDao.findAll();
    }
}