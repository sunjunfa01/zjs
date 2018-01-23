package com.itheima.bos.service.base.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.base.TakeTimeRepository;
import com.itheima.bos.domain.base.TakeTime;
import com.itheima.bos.service.base.TakeTimeService;

/**  
 * ClassName:TakeTimeServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年1月21日 下午2:30:47 <br/>       
 */
@Transactional
@Service
public class TakeTimeServiceImpl implements TakeTimeService {
    @Autowired
    private TakeTimeRepository takeTimeRepository;
//保存
    @Override
    public void save(TakeTime model) {
        takeTimeRepository.save(model);
        
    }
//分页查询
    @Override
    public Page<TakeTime> findAll(Pageable pageable) {
          
        // TODO Auto-generated method stub  
        return takeTimeRepository.findAll(pageable);
    }
//查询返回一个list
    @Override
    public List<TakeTime> findAll() {
          
        // TODO Auto-generated method stub  
        return takeTimeRepository.findAll();
    }
//删除数据  什么也不返回
    @Override
    public void deleteById(Long ids) {
        takeTimeRepository.delete(ids);
    }
}
  
