package com.itheima.bos.service.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.itheima.bos.domain.base.Courier;
import com.itheima.bos.domain.base.Standard;

/**  
 * ClassName:CourierService <br/>  
 * Function:  <br/>  
 * Date:     2018年1月15日 下午5:36:46 <br/>       
 */
public interface CourierService {
    void save(Courier model);

    Page<Courier> findAll(Specification<Courier> specification, Pageable pageable);

    void batchDel(Long parseLong);

    void restore(Long parseLong);

    void deleteById(String ids);
}
  
