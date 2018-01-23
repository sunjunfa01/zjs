package com.itheima.bos.service.base.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.base.AreaRepository;
import com.itheima.bos.dao.base.CourierRepository;
import com.itheima.bos.domain.base.Area;
import com.itheima.bos.domain.base.Courier;
import com.itheima.bos.service.base.AreaService;

/**  
 * ClassName:AreaServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年1月17日 下午4:17:55 <br/>       
 */
@Service // 代表本类属于Service层
@Transactional // 代表本类中的方法需要使用事务
public class AreaServiceImpl implements AreaService {
    @Autowired
    private AreaRepository areaRepository;

    @Override
    public void save(ArrayList<Area> list) {
        areaRepository.save(list);
    }

    @Override
    public Page<Area> findAll(Pageable pageable) {
        //areaRepository.findAll(pageable);
        // TODO Auto-generated method stub  
        return areaRepository.findAll(pageable);
    }

    @Override
    public void save(Area model) {
        //保存数据
        areaRepository.saveAndFlush(model);
        // TODO Auto-generated method stub  
        
    }

    @Override
    public void deleteById(String ids) {
        // 删除数据
      //删除信息
        //注意 别的表对应这个外键  要删这个表要先清除它的外键的数据
        if(StringUtils.isNotEmpty(ids)){
            String[] split = ids.split(",");
            for (int i = 0; i < split.length; i++) {
                Long parseLong = Long.parseLong(split[i]);
                areaRepository.deleteById(parseLong);
            }
        }
        
    }

    @Override
    public List<Area> findByQ(String q) {
        q = "%"+q.toUpperCase()+"%";
        return  areaRepository.findByQ(q);
        // TODO Auto-generated method stub  
    }

    @Override
    public List<String> findByQ01(String q) {
        return areaRepository.findByQ01(q);
    }

    @Override
    public List<String> findDistrict() {
       
        return  areaRepository.findDistrict();
    }
    
    
}
  
