package com.itheima.bos.dao.base;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itheima.bos.domain.base.SubArea;
import com.itheima.bos.domain.base.TakeTime;

/**  
 * ClassName:TakeTimeRepository <br/>  
 * Function:  <br/>  
 * Date:     2018年1月21日 下午2:26:08 <br/>       
 */
//继承 JpaRepository
public interface TakeTimeRepository extends JpaRepository<TakeTime, Long> {

}
  
