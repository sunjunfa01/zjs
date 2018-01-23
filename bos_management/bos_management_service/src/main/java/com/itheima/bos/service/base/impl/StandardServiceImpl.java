package com.itheima.bos.service.base.impl;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.base.StandardRepository;
import com.itheima.bos.domain.base.Standard;
import com.itheima.bos.service.base.StandardService;

/**  
 * ClassName:StandardServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年1月14日 下午9:27:33 <br/>       
 */
@Service // 代表本类属于Service层
@Transactional // 代表本类中的方法需要使用事务
public class StandardServiceImpl  implements StandardService{
    @Autowired
    private StandardRepository standardRepository;
    
    @Override
    public void save(Standard model) {
        //保存信息
        standardRepository.save(model);
        //standardRepository.findAll();
        // TODO Auto-generated method stub  
        
    }

    @Override
    public Page<Standard> findAll(Pageable pageable) {
          //分页查询
        // TODO Auto-generated method stub  
        return standardRepository.findAll(pageable);
    }

    @Override
    public void deleteById(String ids) {
        //删除信息
        //注意 别的表对应这个外键  要删这个表要先清除它的外键的数据
        if(StringUtils.isNotEmpty(ids)){
            String[] split = ids.split(",");
            for (int i = 0; i < split.length; i++) {
                Long parseLong = Long.parseLong(split[i]);
                standardRepository.deleteById(parseLong);
            }
        }
    }
 
}
  
