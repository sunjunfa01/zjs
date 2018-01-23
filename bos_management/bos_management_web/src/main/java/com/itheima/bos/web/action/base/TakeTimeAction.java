package com.itheima.bos.web.action.base;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.codehaus.stax2.ri.typed.ValueDecoderFactory.LongArrayDecoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import com.itheima.bos.domain.base.Area;
import com.itheima.bos.domain.base.Standard;
import com.itheima.bos.domain.base.SubArea;
import com.itheima.bos.domain.base.TakeTime;
import com.itheima.bos.service.base.AreaService;
import com.itheima.bos.service.base.TakeTimeService;
import com.itheima.bos.web.action.CommonAction;
import com.itheima.utils.PinYin4jUtils;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import net.sf.json.JSON;
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
public class TakeTimeAction extends CommonAction<TakeTime>{
    public TakeTimeAction() {
        super(TakeTime.class);
    }
//    
    @Autowired
    private TakeTimeService takeTimeService;
    
  //新增数据                subareaAction_save takeTimeAction_save.action
    @Action(value = "takeTimeAction_save", results = {
            @Result(name = "success", location = "/pages/base/take_time.html", type = "redirect") })
    public  String  save(){
       // System.out.println(getModel());
//        operatingTime
        Date operatingTime = getModel().getOperatingTime();
        //Tue Jan 19 00:00:00 GMT+08:00 2016
        
        System.out.println(operatingTime);
        takeTimeService.save(getModel());
        return SUCCESS;
    }//../../take_timeAction_pageQuery.action
    //taketimeAction_pageQuery.action
    @Action(value = "takeTimeAction_pageQuery")
    public String pageQuery() throws IOException  {
//        Easy的page是从1开始，而PageRequest的page是从0开始的,所以要做-1的操作
        Pageable pageable =new PageRequest(page-1,rows);
//       调用业务层进行查询
        Page<TakeTime> page= takeTimeService.findAll(pageable);
        
        /*if(value instanceof Date){  
            SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.UK);  
            return sdf.format(value);  
        }  */
//      过滤
       //JsonConfig jsonConfig  = new JsonConfig();
       //jsonConfig.setExcludes(new String[]{"fixedArea","subareas"});
        
        Map<String, Object> map = new HashMap<>();
        map.put("total", page.getTotalElements());
        map.put("rows", page.getContent());
        String json = com.alibaba.fastjson.JSON.toJSONString(map);
        HttpServletResponse response = ServletActionContext.getResponse();
        // 设置编码
        response.setContentType("application/json;charset=UTF-8");
        // 写出数据
        response.getWriter().write(json);
        return  NONE;
    }//takeTime_findAvalible2
    
    @Action(value = "takeTime_findAvalible2")
    public String findAvalible2() throws IOException  {
//        Easy的page是从1开始，而PageRequest的page是从0开始的,所以要做-1的操作
       
//       调用业务层进行查询
       List<TakeTime> list= takeTimeService.findAll();
       
//      过滤
       // List<TakeTime> content = page.getContent();
       //JsonConfig jsonConfig  = new JsonConfig();
       //jsonConfig.setExcludes(new String[]{"fixedArea","subareas"});
       list2json(list, null);
        return  NONE;
    }//takeTimeAction_deleteById
    
    private Long ids;
    public void setIds(Long ids) {
        this.ids = ids;
    }
    //删除一条数据areaAction_deleteById.action
    @Action(value = "takeTimeAction_deleteById", results = {
            @Result(name = "success", location = "/pages/base/take_time.html", type = "redirect") })
    public  String  deleteById(){
        //属性驱动获得ids的值
        System.out.println(":"+ids);
        takeTimeService.deleteById(ids);
        return SUCCESS;
    }
}
    
    
   
  
