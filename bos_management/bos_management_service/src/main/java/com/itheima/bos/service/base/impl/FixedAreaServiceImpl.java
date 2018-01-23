package com.itheima.bos.service.base.impl;

import java.util.ArrayList;

import javax.persistence.ManyToMany;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.base.AreaRepository;
import com.itheima.bos.dao.base.CourierRepository;
import com.itheima.bos.dao.base.FixedAreaRepository;
import com.itheima.bos.dao.base.TakeTimeRepository;
import com.itheima.bos.domain.base.Area;
import com.itheima.bos.domain.base.Courier;
import com.itheima.bos.domain.base.FixedArea;
import com.itheima.bos.domain.base.TakeTime;
import com.itheima.bos.service.base.CourierService;
import com.itheima.bos.service.base.FixedAreaService;
import com.itheima.bos.service.base.TakeTimeService;

/**  
 * ClassName:FixedAreaServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年1月19日 下午9:54:16 <br/>       
 */
@Service // 代表本类属于Service层
@Transactional // 代表本类中的方法需要使用事务
public class FixedAreaServiceImpl implements FixedAreaService {
    @Autowired
    private FixedAreaRepository fixedAreaRepository;
    
    @Override
    public void save(FixedArea model) {
        fixedAreaRepository.save(model);
        
    }
    @Override
    public Page<FixedArea> findAll(Pageable pageable) {
          
        return fixedAreaRepository.findAll(pageable);
    }
    @Autowired
    private  CourierRepository  courierRepository;
    @Autowired
    private  TakeTimeRepository takeTimeRepository;
    
//    现在做的操作是定区关联快递员
    @Override
    public void associationCourierToFixedArea(Long id, Long courierId, Long takeTimeId) {
//        通过三个表的各种的id 获取它们中的各种的一条数据（用的是jap的api）
        FixedArea fixedArea = fixedAreaRepository.findOne(id); // 定区
        Courier  courier = courierRepository.findOne(courierId);//快递员
        TakeTime takeTime = takeTimeRepository.findOne(takeTimeId);//时间
//        现在来用它们的自己的方法关联   当然这要注意到的地方是有外键放弃的表 
//        外键放弃的表要被别的表所add 
//         建立快递员和时间的关系
        courier.setTakeTime(takeTime);
        
//        现在关键的是 快递员关联定区还是定区关联快递员（它们是对多对的关系 所以这个时候就看哪个放弃了
//        只有能定区关联快递员  不能用快递员关联定区  因为快递员的设置了@ManyToMany(mappedBy = "couriers") ）
//          也就是快递员放弃了外键维护  所以要关联只能是定区关联快递员
        fixedArea.getCouriers().add(courier);
//        courier.getFixedAreas().add(fixedArea)  这个不行
        
    }
//   删除一条数据
    @Override
    public void deleteById(Long ids) {
        fixedAreaRepository.delete(ids);  
    }
    

}
  
