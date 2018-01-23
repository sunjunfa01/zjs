package com.itheima.bos.dao.base;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.itheima.bos.domain.base.SubArea;

/**  
 * ClassName:SubareaRepository <br/>  
 * Function:  <br/>  
 * Date:     2018年1月18日 下午2:31:02 <br/>       
 */
public interface SubareaRepository extends  JpaRepository<SubArea, Long>  {

    //自定义删除
    @Modifying
    @Query("delete from  SubArea where id = ?")
    void deleteById(Long parseLong);
    
    
//    查找，没有关联的
    List<SubArea> findByFixedAreaIdIsNull();
//    有关联
    //@Query("select *  from SubArea  where fixedArea=?")
    List<SubArea> findByFixedAreaId (Long id);

    
//  把关联到定区id的客户全部解绑
  @Modifying
  @Query("update SubArea set  fixedArea.id=null where fixedArea.id=?")
  void unbindBySubArea(Long fixedAreaId);
//把所有选中的全部绑定
  @Modifying
  @Query("update SubArea set fixedArea.id=? where id=?")
  void bindBySubArea(Long fixedAreaId,Long id);

}
  
