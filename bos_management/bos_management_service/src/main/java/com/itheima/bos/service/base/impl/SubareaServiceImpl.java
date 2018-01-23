package com.itheima.bos.service.base.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.base.AreaRepository;
import com.itheima.bos.dao.base.SubareaRepository;
import com.itheima.bos.domain.base.Area;
import com.itheima.bos.domain.base.Standard;
import com.itheima.bos.domain.base.SubArea;
import com.itheima.bos.service.base.SubareaService;

/**  
 * ClassName:SubareaServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年1月18日 下午2:28:35 <br/>       
 */
@Transactional
@Service
public class SubareaServiceImpl implements SubareaService {
    @Autowired
    private SubareaRepository subareaRepository;

    @Override
    public void save(SubArea model) {
        subareaRepository.save(model); 
        // TODO Auto-generated method stub  
    }
//ajax下拉查询
    @Override
    public Page<SubArea> findAll(Pageable pageable) {
        return subareaRepository.findAll(pageable);
    }
    //删除
    @Override
    public void deleteById(String ids) {
        //删除信息
          //注意 别的表对应这个外键  要删这个表要先清除它的外键的数据
          if(StringUtils.isNotEmpty(ids)){
              String[] split = ids.split(",");
              for (int i = 0; i < split.length; i++) {
                  Long parseLong = Long.parseLong(split[i]);
                  subareaRepository.deleteById(parseLong);
              }
          }
    }
    @Override
    public List<SubArea> findAll2() {
      
        return  subareaRepository.findByFixedAreaIdIsNull();
    }
//    查找已经关联的分区
    @Override
    public List<SubArea> findAll022(Long id) {
        return subareaRepository.findByFixedAreaId(id); 
        // TODO Auto-generated method stub  
    }
    @Override
    public void assignSubarea22FixedArea(Long[] customerIds, Long fixedAreaId) {
//          先解绑
        subareaRepository.unbindBySubArea(fixedAreaId);
        if(customerIds.length>0&&customerIds!=null){
            for (Long id : customerIds) {
                subareaRepository.bindBySubArea(fixedAreaId, id);
           }
        }
    }
}
  
