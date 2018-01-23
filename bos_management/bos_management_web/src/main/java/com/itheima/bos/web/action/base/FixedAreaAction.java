package com.itheima.bos.web.action.base;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import com.itheima.bos.domain.base.Area;
import com.itheima.bos.domain.base.Courier;
import com.itheima.bos.domain.base.Customer;
import com.itheima.bos.domain.base.FixedArea;
import com.itheima.bos.domain.base.Standard;
import com.itheima.bos.domain.base.SubArea;
import com.itheima.bos.service.base.AreaService;
import com.itheima.bos.service.base.CourierService;
import com.itheima.bos.service.base.FixedAreaService;
import com.itheima.bos.service.base.TakeTimeService;
import com.itheima.bos.web.action.CommonAction;
import com.itheima.utils.PinYin4jUtils;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**  
 * ClassName:CourierAction <br/>  
 * Function:  <br/>  
 * Date:     2018年1月15日 下午5:26:23 <br/>       
 */

@Controller//注解托管类
@Scope("prototype")//多例
@ParentPackage("struts-default")//继承struts-default
@Namespace("/")//命名空间
public class FixedAreaAction extends CommonAction<FixedArea>{
    public FixedAreaAction() {
        super(FixedArea.class);
    }
  
    @Autowired
    private FixedAreaService fixedAreaService ;
    
  //新增数据
    @Action(value = "fixedAreaAction_save", results = {
            @Result(name = "success", location = "/pages/base/fixed_area.html", type = "redirect") })
    public  String  save(){
        fixedAreaService.save(getModel());
        return SUCCESS;
    }//fixedAreaAction_pageQuery.action
    
    
    
    
    
//  分页查询  +条件查询
  @Action(value = "fixedAreaAction_pageQuery")
  public String pageQuery() throws IOException  {
      Pageable pageable =new PageRequest(page-1,rows);
//     调用业务层进行查询
      Page<FixedArea> page= fixedAreaService.findAll(pageable);
      JsonConfig jsonConfig  = new JsonConfig();
      jsonConfig.setExcludes(new String[]{"subareas","couriers"});
      page2json(page, jsonConfig);
     return  NONE;
  }
  
  //findCustomersUnAssociated 查找为关联的数据 fixedAreaAction_findCustomersUnAssociated
  @Action(value = "fixedAreaAction_findCustomersUnAssociated")
  public String findCustomersUnAssociated() throws IOException  {
      List<Customer> list = (List<Customer>)WebClient.create("http://localhost:8180/crm/webService/customerService/findCustomersUnAssociated")
      .type(MediaType.APPLICATION_JSON)
      .accept(MediaType.APPLICATION_JSON)
      .getCollection(Customer.class);
      list2json(list, null);
     return  NONE;
  }
  //fixedAreaAction_findCustomersAssociated2FixedArea
  @Action(value = "fixedAreaAction_findCustomersAssociated2FixedArea")
  public String findCustomersAssociated2FixedArea() throws IOException  {
      System.out.println("hao ");
      List<Customer> list = (List<Customer>)WebClient.create("http://localhost:8180/crm/webService/customerService/findCustomersAssociated2FixedArea")
      .type(MediaType.APPLICATION_JSON)
      .accept(MediaType.APPLICATION_JSON)
      .query("fixedAreaId",getModel().getId())
      .getCollection(Customer.class);
      list2json(list, null);
     return  NONE;
  }//fixedAreaAction_assignCustomers2FixedArea
  private Long[] customerIds;
  public void setCustomerIds(Long[] customerIds) {
    this.customerIds = customerIds;
}
  //向crm发起请求   关联客户到指定的定区
 // @Action(value = "fixedAreaAction_assignCustomers2FixedArea")
  @Action(value = "fixedAreaAction_assignCustomers2FixedArea", results = {
          @Result(name = "success", location = "/pages/base/fixed_area.html", type = "redirect") })
  public String assignCustomers2FixedArea() throws IOException  {
      WebClient.create("http://localhost:8180/crm/webService/customerService/assignCustomers2FixedArea")
      .type(MediaType.APPLICATION_JSON)
      .accept(MediaType.APPLICATION_JSON)
      .query("fixedAreaId",getModel().getId())
      .query("customerIds", customerIds)
      .put(null);
      //list2json(list, null);
     return  SUCCESS;
  }
  //定区关联分区
  
  
  
  
  
  
  
  //定区关联快递员
//  用属性驱动获得两个curierId   和  takeTimeId
//  fixedAreaAction_associationCourierToFixedArea.action
  private Long courierId;
  private Long  takeTimeId;
  public void setCourierId(Long courierId) {
    this.courierId = courierId;
}
  public void setTakeTimeId(Long takeTimeId) {
    this.takeTimeId = takeTimeId;
  }

  
  @Action(value = "fixedAreaAction_associationCourierToFixedArea", results = {
          @Result(name = "success", location = "/pages/base/fixed_area.html", type = "redirect") })
  public String associationCourierToFixedArea() throws IOException  {
     //System.out.println(courierId+":"+takeTimeId);
//     courierService
     fixedAreaService.associationCourierToFixedArea(getModel().getId(),courierId,takeTimeId);
     return  SUCCESS;
  }
  
  private Long ids;
  public void setIds(Long ids) {
    this.ids = ids;
}
//删除数据？  有关联的  可以删除？fixedArea_deleteById
  @Action(value = "fixedArea_deleteById", results = {
          @Result(name = "success", location = "/pages/base/fixed_area.html", type = "redirect") })
  public  String  deleteById(){
      //属性驱动获得ids的值
      System.out.println(":"+ids);
      fixedAreaService.deleteById(ids);
      return SUCCESS;
  }
  

  
  
} 


    
    
   
  
