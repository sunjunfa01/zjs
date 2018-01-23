package com.itheima.bos.dao.base.impl;

import java.util.List;

import  org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.itheima.bos.dao.base.StandardRepository;
import com.itheima.bos.domain.base.Standard;


/**  
 * ClassName:StandarRepositoryTest <br/>  
 * Function:  <br/>  
 * Date:     2018年1月14日 下午5:49:11 <br/>       
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class StandarRepositoryTest {

    @Autowired
    private  StandardRepository  standarRepository;
    
    @Test
    public void test() {
//      查询    
        List<Standard> list = standarRepository.findAll();
        for (Standard standard : list) {
            System.out.println("aa");
            
        }
    }
    @Test
    public void testAdd() {
//        保存（添加一条数据）
        Standard standard = new Standard(); 
        standard.setMaxLength(500);
        standard.setName("fudan002");
        standarRepository.save(standard);
    }
    @Test
    public void testUpdat() {
//        更新 先查询 后更新
//        进行更改数据  必须传入主键
        Standard standard = new  Standard();
        Standard findOne = standarRepository.findOne(1L);
        standard.setId(1L);
        findOne.setName("张三2001");
        standarRepository.save(standard);
    }
    @Test
    public void testFindone(){
//        查询id为一的数据
       // Standard standard = new  Standard();
        Standard findOne = standarRepository.findOne(1L);
        System.out.println(findOne);
    }
    @Test
    public void testDelete(){
//        删除id为1l的整条数据
        standarRepository.delete(1L);
    }
    //
    @Test
    public void testFindByName(){
//        根据自己类的属性来做值findByName（name属性名）
     // 使用用户名进行查询
        List<Standard> list = standarRepository.findByName("张三");
        if(list !=null){
            System.out.println(list.get(0));
            System.out.println("----------------");
        }
        for (Standard standard : list) {
            System.out.println(standard);
        }
    }
    @Test
    public void testFindByNameLike(){
//        /模拟条件查询
        List<Standard> list = standarRepository.findByNameLike("%fudan%");
        for (Standard standard : list) {
            System.out.println(standard);
        }
    }
    @Test
    public void  testFindByNameAndMaxLength(){
//        and条件查询     都是自定义的
        List<Standard> list = standarRepository.findByNameAndMaxLength("fudan001", 500);
        for (Standard standard : list) {
            System.out.println(standard);
        }
        
    }
    @Test
    public void  testFindByNameAndMaxLengthxx(){
//        and条件查询     都是自定义的
        List<Standard> list = standarRepository.findByNameAndMaxLengthxx("fudan001", 500);
        for (Standard standard : list) {
            System.out.println(standard);
        }
    }
    
    @Test
    public void  testFindByNameAndMaxLengthxxxx2(){
//        and条件查询     都是自定义的
        List<Standard> list = standarRepository. findByNameAndMaxLengthxxxxx2(500,"fudan001");
        for (Standard standard : list) {
            System.out.println(standard);
        }
    }
    @Test
    public void  deleteByName(){
//        自定义删除操作
        standarRepository.deleteByName("fudan001");
    }
    @Test
    public void  updateByName(){
//        自定义 更新操作
        Standard standard = new Standard();
        
        standarRepository.updateByName(600,"fudan002");
    }
}
  
