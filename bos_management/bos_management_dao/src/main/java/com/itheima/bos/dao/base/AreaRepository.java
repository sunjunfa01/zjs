package com.itheima.bos.dao.base;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.itheima.bos.domain.base.Area;

/**  
 * ClassName:AreaRepository <br/>  
 * Function:  <br/>  
 * Date:     2018年1月17日 下午4:19:06 <br/>       
 */
public interface AreaRepository extends  JpaRepository<Area, Long> {

  //自定义删除
    @Modifying
    @Query("delete from Area where id = ?")
    void deleteById(Long parseLong);
    @Query("from Area where province like ?1 or city like ?1 or district like ?1 or postcode like ?1 or citycode like ?1 or shortcode like ?1 ")
    List<Area> findByQ(String q);
    
    @Query("select distinct province from Area ")
    List<String> findByQ01(String q);
    
    
    @Query("select district from Area")
    List<String> findDistrict();
}
  
