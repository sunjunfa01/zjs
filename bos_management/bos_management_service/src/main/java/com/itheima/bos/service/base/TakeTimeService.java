package com.itheima.bos.service.base;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.itheima.bos.domain.base.SubArea;
import com.itheima.bos.domain.base.TakeTime;

/**  
 * ClassName:TakeTimeService <br/>  
 * Function:  <br/>  
 * Date:     2018年1月21日 下午2:29:52 <br/>       
 */
public interface TakeTimeService {

    void save(TakeTime model);

    Page<TakeTime> findAll(Pageable pageable);

    List<TakeTime> findAll();

    void deleteById(Long ids);

}
  
