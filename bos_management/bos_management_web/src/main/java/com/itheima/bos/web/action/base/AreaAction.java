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
public class AreaAction extends CommonAction<Area>{
    public AreaAction() {
        super(Area.class);
    }
    /*}
    private Area model= new Area();
    @Override
    public Area getModel() {
        return model;
    }*/
    @Autowired
    private AreaService areaService ;
 // 获取文件名
    private String fileFileName;
    public void setFileFileName(String fileFileName) {
        this.fileFileName = fileFileName;
    }

    private File file;
    public void setFile(File file) {
        this.file = file;
    }
    @Action(value = "areaAction_importXLS", results = {
            @Result(name = "success", location = "/pages/base/area.html", type = "redirect") })
    public  String  importXLS(){
       // System.out.println(":"+file); 获取临时文件名路径
        System.out.println(fileFileName);
       //用来保存数据的集合  
        ArrayList<Area> list = new  ArrayList<>();
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
               System.out.println(citycode);
               String [] headByString = PinYin4jUtils.getHeadByString(province+city+district, true);
               System.out.println(headByString);
               //获取简码
               String shortcode = PinYin4jUtils.stringArrayToString(headByString);
               System.out.println(shortcode);
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
            areaService.save(list);
            // 释放资源
            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();  
        }
        return SUCCESS;
    }
    // 使用属性驱动获取分页数据
    /*private  int  page;// 第几页
    private  int  rows;// 每一页显示多少条数据
    public void setRows(int rows) {
        this.rows = rows;
    }
    public void setPage(int page) {
        this.page = page;
    }*/
//    分页查询
    @Action(value = "areaAction_pageQuery")
    public String pageQuery() throws IOException  {
//        Easy的page是从1开始，而PageRequest的page是从0开始的,所以要做-1的操作
        Pageable pageable =new PageRequest(page-1,rows);
//       调用业务层进行查询
        Page<Area> page= areaService.findAll(pageable);
       //  获取总条数
        /*long totalElements = pag.getTotalElements();
        //获取页面要显示的数据
        List<Area> list = pag.getContent();
       //封装获取的数据
        Map<String ,Object> map = new HashMap<>();
        map.put("total", totalElements);
        map.put("rows", list);*/
     // JSONObject转换对象和Map集合
        // JSONArray转换数组和List集合
//        获取值的类型的方法
     // ServletContextservletContext = ServletActionContext.getServletContext();
        // servletContext.getRealPath("");
        // servletContext.getMimeType("");
//         转换对象为json字符串
//      指定在生成json数据的时候要忽略的字段
       JsonConfig jsonConfig  = new JsonConfig();
       jsonConfig.setExcludes(new String[]{"subareas"});
       //提高服务器的性能，所有页面不需要的数据都忽略
//       好  现在要把数据转换长json字符串对象传过去
       //String json = JSONObject.fromObject(map,jsonConfig).toString();
//        输出出去
       // HttpServletResponse response = ServletActionContext.getResponse();
//        解决响应乱码
       // response.setContentType("application/json;charset=UTF-8");
        //response.getWriter().write(json);
       page2json(page, jsonConfig);
        return  NONE;
    }
    //新增数据
    @Action(value = "areaAction_save", results = {
            @Result(name = "success", location = "/pages/base/area.html", type = "redirect") })
    public  String  save(){
        areaService.save(getModel());
        return SUCCESS;
    }
    private String ids;
    public void setIds(String ids) {
        this.ids = ids;
    }
    //删除一条数据areaAction_deleteById.action
    @Action(value = "areaAction_deleteById", results = {
            @Result(name = "success", location = "/pages/base/area.html", type = "redirect") })
    public  String  deleteById(){
        //属性驱动获得ids的值
        System.out.println(":"+ids);
        areaService.deleteById(ids);
        return SUCCESS;
    }
    private String q;
    public void setQ(String q) {
        this.q = q;}
//  分页查询
  @Action(value = "areaAction_findAll")
  public String findAll() throws IOException  {
     // Pageable pageable =new PageRequest(page-1,rows);
//     调用业务层进行查询
     List<Area> list ;
     if(StringUtils.isNotEmpty(q)){
         list= areaService.findByQ(q);
     }else{
         Page<Area> page= areaService.findAll(null);
         list = page.getContent();
     }JsonConfig jsonConfig  = new JsonConfig();
     jsonConfig.setExcludes(new String[]{"subareas"});
     list2json(list, jsonConfig);
     return  NONE;
  }
//分页查询areaAction_findAll01.action
@Action(value = "areaAction_findAll01")
public String findAll01() throws IOException  {
   // Pageable pageable =new PageRequest(page-1,rows);
//   调用业务层进行查询
   List<String> list ;
   
   list= areaService.findByQ01(q);
   JsonConfig jsonConfig  = new JsonConfig();
   jsonConfig.setExcludes(new String[]{"subareas"});
   //list2json(list, jsonConfig);
   return  NONE;
}
//  查找所有分区

}
    
    
   
  
