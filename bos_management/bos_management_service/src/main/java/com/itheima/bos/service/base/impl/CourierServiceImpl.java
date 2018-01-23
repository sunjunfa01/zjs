package com.itheima.bos.service.base.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.base.CourierRepository;
import com.itheima.bos.domain.base.Courier;
import com.itheima.bos.service.base.CourierService;

/**  
 * ClassName:CourierServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年1月15日 下午5:37:03 <br/>       
 */
@Service // 代表本类属于Service层
@Transactional // 代表本类中的方法需要使用事务
public class CourierServiceImpl implements CourierService {
    @Autowired
    private CourierRepository courierRepository;
    
    @Override
    public void save(Courier model) {
        courierRepository.save(model);
        // TODO Auto-generated method stub  
        
    }

    @Override
    public Page<Courier> findAll(Specification<Courier> specification, Pageable pageable) {
        // TODO Auto-generated method stub  
        return courierRepository.findAll(specification, pageable);
    }

    @Override
    public void batchDel(Long spilt) {
          //更新账号使用权限
        courierRepository.batchDel(spilt);
        // TODO Auto-generated method stub  
        
    }

    @Override
    public void restore(Long parseLong) {
        //还原
        courierRepository.restore(parseLong);
        // TODO Auto-generated method stub  
        
    }

    @Override
    public void deleteById(String ids) {
        //删除信息
          //注意 别的表对应这个外键  要删这个表要先清除它的外键的数据
          if(StringUtils.isNotEmpty(ids)){
              String[] split = ids.split(",");
              for (int i = 0; i < split.length; i++) {
                  Long parseLong = Long.parseLong(split[i]);
                  courierRepository.deleteById(parseLong);
              }
          }
    }


}
  
