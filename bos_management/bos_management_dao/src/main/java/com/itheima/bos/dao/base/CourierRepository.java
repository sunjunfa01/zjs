package com.itheima.bos.dao.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.itheima.bos.domain.base.Courier;

/**  
 * ClassName:CourierRepository <br/>  
 * Function:  <br/>  
 * Date:     2018年1月15日 下午5:39:05 <br/>       
 */
public interface CourierRepository extends  JpaRepository<Courier, Long>,JpaSpecificationExecutor<Courier> {
    //JpaSpecificationExecutor<Courier>  多条件查询需要继承接口  （接口可以多继承   类只能单继承  ）
    @Modifying
    @Query("update  Courier set deltag= 0 where id=?")
    void batchDel(Long spilt);
    @Modifying
    @Query("update  Courier set deltag= 1 where id=?")
    void restore(Long parseLong);
   //自定义删除
    @Modifying
    @Query("delete from  Courier where id = ?")
    void deleteById(Long parseLong);
}

  
