package com.itheima.bos.dao.base;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.domain.base.Standard;


/**  
 * ClassName:StandarRepository <br/>  
 * Function:  <br/>  
 * Date:     2018年1月14日 下午5:42:22 <br/>       
 */
//泛型一类   泛型二  类的主键名
public interface StandardRepository  extends  JpaRepository<Standard, Long>{
    List<Standard>  findByName(String name);
//    模拟查询
    List<Standard>  findByNameLike(String name);
//    根据名字和最大长度去查数据
    List<Standard> findByNameAndMaxLength(String name,Integer maxLength);
//    自定义    根据名字和最大长度去查数据
    @Query("from  Standard where name =? and maxLength =?")
    List<Standard> findByNameAndMaxLengthxx(String name,Integer maxLength);
    // 根据用户名和最大长度进行查询
    @Query("from Standard where name = ?2 and maxLength = ?1") // JPQL === HQL
    List<Standard> findByNameAndMaxLengthxxxxx2(Integer maxLength, String name);

    // 自定义更新操作   删除
    @Transactional
    @Modifying
    @Query("delete  from  Standard s where s.name = ?")
    void  deleteByName(String name);
//    自定更新操作
    @Transactional
    @Modifying
    @Query("update from  Standard set  maxLength=?  where name = ?")
    void updateByName(Integer maxLength , String name);
    //自定义删除
    @Modifying
    @Query("delete from Standard where id = ?")
    void deleteById(Long parseLong);
}
  
