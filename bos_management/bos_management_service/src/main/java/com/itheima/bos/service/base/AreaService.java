package com.itheima.bos.service.base;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.itheima.bos.domain.base.Area;
import com.itheima.bos.domain.base.Standard;

/**  
 * ClassName:AreaService <br/>  
 * Function:  <br/>  
 * Date:     2018年1月17日 下午4:17:13 <br/>       
 */
public interface AreaService {

    void save(ArrayList<Area> list);
    void save(Area model);

    Page<Area> findAll(Pageable pageable);
    void deleteById(String ids);
    List<Area> findByQ(String q);
    List<String> findByQ01(String q);
    List<String> findDistrict();

}
  
