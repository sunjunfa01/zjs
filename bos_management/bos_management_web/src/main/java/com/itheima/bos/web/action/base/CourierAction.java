package com.itheima.bos.web.action.base;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.dom4j.CDATA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.base.Courier;
import com.itheima.bos.domain.base.Standard;
import com.itheima.bos.service.base.CourierService;
import com.itheima.bos.web.action.CommonAction;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.sun.xml.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**  
 * ClassName:CourierAction <br/>  
 * Function:  <br/>  
 * Date:     2018年1月15日 下午5:26:23 <br/>       
 */

@Controller//注解托管类
@Scope("prototype")//多例
@ParentPackage("struts-default")//继承struts-default ActionSupport implements ModelDriven<Courier>
@Namespace("/")//命名空间
public class CourierAction  extends CommonAction<Courier>{
   /* private Courier model= new Courier();
    @Override
    public Courier getModel() {
          
        // TODO Auto-generated method stub  
        return model;
    }*/
    public CourierAction() {
        super(Courier.class);
    }
    @Autowired
    private CourierService courierService ;
    
    @Action(value = "courierAction_save", results = {
            @Result(name = "success", location = "/pages/base/courier.html", type = "redirect") })
    public  String  save(){
        courierService.save(getModel());
        return SUCCESS;
    }
   
        // TODO Auto-generated constructor stub  

 // 使用属性驱动获取分页数据
  /*  private  int page ;//第二页
    private  int rows;   // 每一页显示多少条数据
    public void setPage(int page) {
        this.page = page;
    }
    public void setRows(int rows) {
        this.rows = rows;
    }*/
//    courierAction_pageQuery
    // 分页查询,EasyUI所有的控件发请求的方式是AJAX,本方法返回的数据应该是JSON格式
    @Action(value = "courierAction_pageQuery")
    public  String  pageQuery() throws IOException{
        //构造查询条件
        Specification<Courier> specification =  new Specification<Courier>(){
            /**
             * 构建一个where条件语句
             * 
             * @param root
             *            : 根对象,简单的理解为泛型的对象,
             * @paramcb
             *            : 构建查询条件的对象
             * @return a {@link Predicate}, must not be {@literal null}.
             */
            @Override
            public Predicate toPredicate(Root<Courier> root, CriteriaQuery<?> query,
                    CriteriaBuilder cb) {
                //接收html页面传过来的数据
                String courierNum = getModel().getCourierNum();
                Standard standard = getModel().getStandard();
                String company = getModel().getCompany();
                String type = getModel().getType();
                ArrayList<Predicate> list = new ArrayList<>();
//                快递员工号不能为空  构建一个等值查询
                if(StringUtils.isNotEmpty(courierNum)){
                    // 参数1 ： 自己理解
                    // 参数2 ： 代替？的具体的值
                    Predicate p1 = cb.equal(root.get("courierNum").as(String.class), courierNum);
                    list.add(p1);
                }// 公司不为空,构建一个模糊查询
                if(StringUtils.isNotEmpty(company)){
                    // 参数1 ： 自己理解
                    // 参数2 ： 代替？的具体的值
                    Predicate p2 = cb.like(root.get("company").as(String.class), "%"+company+"%");
                    list.add(p2);
                }   // 类型不为空,构建一个等值查询
                if(StringUtils.isNotEmpty(type)){
                    // 参数1 ： 自己理解
                    // 参数2 ： 代替？的具体的值
                    Predicate p3 = cb.equal(root.get("type").as(String.class), type);
                    list.add(p3);
                }
                if(standard != null){
                    String name = standard.getName();
                    if(StringUtils.isNotEmpty(name)){
                        // 参数1 ： 自己理解
                        // 参数2 ： 代替？的具体的值
                        Join<Object, Object>join  = root.join("standard");
                        Predicate p4 = cb.equal(join.get("name").as(String.class), name);
                        list.add(p4);
                    }
                }
                //用户没有输入条件
                if(list.size()>1){
                    return null;
                }
                //  新建数组    把用户输入的条件装进去
                Predicate[] arr = new Predicate[list.size()];
//                把这个数组的数据给arr  是这样意思？
                list.toArray(arr);
                return cb.and(arr);
            }
            
        };
        Pageable pageable = new PageRequest(page-1,rows);
        Page<Courier> page= courierService.findAll(specification,pageable);
        //得到pag对象   从它获取最大记录数和  每页要显示的数据
      /*  long totalElements = pag.getTotalElements();
        List<Courier> list = pag.getContent();
        // 好现在 得到对象 要把它封装器起来（html页面要到而是  rows 和  total）
        Map<String, Object> map   =new  HashMap<>();
        map.put("total", totalElements);
        map.put("rows", list);*/
        JsonConfig jsonConfig  = new JsonConfig();
//       指定在生成json数据的时候要忽略的字段
        jsonConfig.setExcludes(new String[]{"fixedAreas","takeTime"});
        //提高服务器的性能，所有页面不需要的数据都忽略
//        好  现在要把数据转换长json字符串对象传过去
       /* String json = JSONObject.fromObject(map,jsonConfig).toString();
        HttpServletResponse response = ServletActionContext.getResponse();
//      解决响应乱码
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(json);*/
       page2json(page, jsonConfig);
        return  NONE;
    }
    
    private  String  ids;
    public void setIds(String ids) {
        this.ids = ids;
    }
    @Action(value = "courierAction_batchDel", results = {
            @Result(name = "success", location = "/pages/base/courier.html", type = "redirect") })
    public  String  batchDel(){  //更新账号  不能使用 
        System.out.println(ids);//得到从页面传过来的id（字符串  点逗号的）
//        切割ids
        //如果不为空  就执行更新使用权限问题
        if (StringUtils.isNotEmpty(ids)) {
            String[] split = ids.split(",");
            for (int i = 0; i < split.length; i++) {
                String string01 = split[i];
                Long parseLong = Long.parseLong(string01);
                courierService.batchDel(parseLong);
            }
        }
        return  SUCCESS;
    }
    @Action(value = "courierAction_restore", results = {
            @Result(name = "success", location = "/pages/base/courier.html", type = "redirect") })
    public  String  restore(){  //更新账号  不能使用 
        System.out.println(ids);//得到从页面传过来的id（字符串  点逗号的）
//        切割ids
        //如果不为空  就执行更新使用权限问题
        if (StringUtils.isNotEmpty(ids)) {
            String[] split = ids.split(",");
            for (int i = 0; i < split.length; i++) {
                String string01 = split[i];
                Long parseLong = Long.parseLong(string01);
                courierService.restore(parseLong);
            }
        }
        return  SUCCESS;
    }
    @Action(value = "courierAction_deleteById", results = {
            @Result(name = "success", location = "/pages/base/courier.html", type = "redirect") })
    public  String  deleteById(){
        //属性驱动获得ids的值
        System.out.println(":"+ids);
        courierService.deleteById(ids);
        return SUCCESS;
    }//../../courierAction_findAvalible.action
    
    
 // 分页查询,EasyUI所有的控件发请求的方式是AJAX,本方法返回的数据应该是JSON格式
    @Action(value = "courierAction_findAvalible")
    public  String  findAvalible() throws IOException{
        //构造查询条件
        Specification<Courier> specification =  new Specification<Courier>(){
            @Override
            public Predicate toPredicate(Root<Courier> root, CriteriaQuery<?> query,
                    CriteriaBuilder cb) {
                return cb.isNull(root.get("deltag").as(Character.class));
            }
        };
//          List<Courier>findByDeltagIsNull();  第二种方法  找为null的数据
        Page<Courier> findAll = courierService.findAll(specification, null);
//       指定在生成json数据的时候要忽略的字段
        List<Courier> list = findAll.getContent();
        JsonConfig jsonConfig  = new JsonConfig();
//      指定在生成json数据的时候要忽略的字段
       jsonConfig.setExcludes(new String[]{"fixedAreas","takeTime"});
        list2json(list, jsonConfig);
        return  NONE;
    }
} 
    
    
   
  
