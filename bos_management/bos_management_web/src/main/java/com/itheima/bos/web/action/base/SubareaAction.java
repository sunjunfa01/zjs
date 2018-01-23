package com.itheima.bos.web.action.base;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

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
import org.springframework.stereotype.Controller;
import com.itheima.bos.domain.base.Area;
import com.itheima.bos.domain.base.Standard;
import com.itheima.bos.domain.base.SubArea;
import com.itheima.bos.service.base.AreaService;
import com.itheima.bos.service.base.SubareaService;
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
public class SubareaAction extends CommonAction<SubArea>{
    public SubareaAction() {
        super(SubArea.class);
    }
    @Autowired
    private  SubareaService  subareaService;
  //新增数据                subareaAction_save
    @Action(value = "subareaAction_save", results = {
            @Result(name = "success", location = "/pages/base/sub_area.html", type = "redirect") })
    public  String  save(){
       // System.out.println(getModel());
        subareaService.save(getModel());
        return SUCCESS;
    }
    ///subareaAction_pageQuery/subareaAction_pageQuery
//  分页查询
  @Action(value = "subareaAction_pageQuery")
  public String pageQuery() throws IOException  {
//      Easy的page是从1开始，而PageRequest的page是从0开始的,所以要做-1的操作
      Pageable pageable =new PageRequest(page-1,rows);
//     调用业务层进行查询
      Page<SubArea> page= subareaService.findAll(pageable);
//    过滤
     JsonConfig jsonConfig  = new JsonConfig();
     jsonConfig.setExcludes(new String[]{"fixedArea","subareas"});
     page2json(page, jsonConfig);
      return  NONE;
  }
  private  String ids;
  public void setIds(String ids) {
    this.ids = ids;
}
  @Action(value = "subareaAction_deleteById", results = {
          @Result(name = "success", location = "/pages/base/sub_area.html", type = "redirect") })
  public  String  deleteById(){
      //属性驱动获得ids的值
      System.out.println(":"+ids);
      subareaService.deleteById(ids);
      return SUCCESS;
  }
  
//获取文件名
  private String fileFileName;
  public void setFileFileName(String fileFileName) {
      this.fileFileName = fileFileName;
  }
  private File file;
  public void setFile(File file) {
      this.file = file;
  }
  @Action(value = "subareaAction_importXLS", results = {
          @Result(name = "success", location = "/pages/base/sub_area.html", type = "redirect") })
  public  String  importXLS(){
      //导入的包  导入进来   没有具体实现    要看别人提供的文件在改 
      
      System.out.println("文件导出");
     // System.out.println(":"+file); 获取临时文件名路径
      System.out.println(fileFileName);
    /* //用来保存数据的集合  
      ArrayList<SubArea> list = new  ArrayList<>();
      try {
          HSSFWorkbook  workbook = new  HSSFWorkbook(new  FileInputStream(file));
          //读取工作铺（读取exce文档）
          HSSFSheet sheetAt = workbook.getSheetAt(0);
          //遍历行
          for (Row row : sheetAt) {
              //第一行是标题 这一行不要
              if(row.getRowNum()==0){
                  continue;
              }
              //读取表格数据(从列的第二个字段开始  第一字段不要)
              String province = row.getCell(1).getStringCellValue();
              String city = row.getCell(2).getStringCellValue();
              String district = row.getCell(3).getStringCellValue();
              String postcode = row.getCell(4).getStringCellValue();
              
              //截取省市区的最后一个字符
              province = province.substring(0, province.length()-1);
              city = city.substring(0, city.length()-1);
              district = district.substring(0, district.length()-1);
              //获取城市编码
             String citycode=  PinYin4jUtils.hanziToPinyin(city,"").toUpperCase();
            // System.out.println(citycode);
             String [] headByString = PinYin4jUtils.getHeadByString(province+city+district, true);
            // System.out.println(headByString);
             //获取简码
             String shortcode = PinYin4jUtils.stringArrayToString(headByString);
             //System.out.println(shortcode);
             // 新建一个Area
             Area area  = new Area();
             area.setCitycode(citycode);
             area.setDistrict(district);
             area.setCity(city);
             area.setShortcode(shortcode);
             area.setProvince(province);
             area.setPostcode(postcode);
             //添加到集合中去
             list.add(area);
          }
          //一次性保存数据 
          subareaService.save(list);
          // 释放资源
          workbook.close();
      } catch (Exception e) {
          e.printStackTrace();  
          
      }*/
      return SUCCESS;
  }
  @Action(value = "subareaAction_importOutXLS", results = {
          @Result(name = "success", location = "/pages/base/sub_area.html", type = "redirect") })
  public  String  importOutXLS(){ 
      System.out.println("文件导出");
      return  SUCCESS;
  }
  
  
//  左边没有关联的
  @Action(value = "subareaAction_pageQuery01")
  public String pageQuery01() throws IOException  {
//     调用业务层进行查询
      List<SubArea> findAll2 = subareaService.findAll2();
      JsonConfig jsonConfig  = new JsonConfig();
      jsonConfig.setExcludes(new String[]{"subareas","fixedArea"});
      list2json(findAll2, jsonConfig);
      return  NONE;
  }
//  右边有关联的
  @Action(value = "subareaAction_pageQuery02")
  public String pageQuery02() throws IOException  {
//     调用业务层进行查询
      List<SubArea> findAll2 = subareaService.findAll022(getModel().getId());
      System.out.println(findAll2+":");
      JsonConfig jsonConfig  = new JsonConfig();
      jsonConfig.setExcludes(new String[]{"subareas","fixedArea"});
      list2json(findAll2, jsonConfig);
      return  NONE;
  }
  private Long[] customerIds;
  public void setCustomerIds(Long[] customerIds) {
    this.customerIds = customerIds;
}
//把之前有关系的全部解绑/pages/base/fixed_area.html
//  fixedAreaAction_assignSubarea22FixedArea
  @Action(value = "subareaAction_assignSubarea22FixedArea", results = {
          @Result(name = "success", location = "/pages/base/fixed_area.html", type = "redirect") })
  public String assignSubarea22FixedArea(){
      subareaService.assignSubarea22FixedArea(customerIds,getModel().getId());
      return  SUCCESS;
  }
// 现在绑定 
  
  
} 
    
    
   
  
