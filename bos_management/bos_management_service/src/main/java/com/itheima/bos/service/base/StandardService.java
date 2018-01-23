package com.itheima.bos.service.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.itheima.bos.domain.base.Standard;

/**  
 * ClassName:StandardService <br/>  
 * Function:  <br/>  
 * Date:     2018年1月14日 下午9:25:29 <br/>       
 */
public interface StandardService {

    void save(Standard model);

    Page<Standard>  findAll(Pageable pageable);

    void deleteById(String ids);
    

}
  
