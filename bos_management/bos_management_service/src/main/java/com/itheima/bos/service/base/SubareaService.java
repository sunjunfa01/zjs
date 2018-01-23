package com.itheima.bos.service.base;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import com.itheima.bos.domain.base.Area;
import com.itheima.bos.domain.base.Standard;
import com.itheima.bos.domain.base.SubArea;

/**  
 * ClassName:SubareaService <br/>  
 * Function:  <br/>  
 * Date:     2018年1月18日 下午2:27:56 <br/>       
 */
public interface SubareaService {

    void save(SubArea model);

    Page<SubArea> findAll(Pageable pageable);

    void deleteById(String ids);
    List<SubArea> findAll2();

    List<SubArea> findAll022(Long id);

    void assignSubarea22FixedArea(Long[] customerIds, Long id);

   
    
}
  
