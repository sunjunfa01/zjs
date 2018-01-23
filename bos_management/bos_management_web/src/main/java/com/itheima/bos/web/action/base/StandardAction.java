package com.itheima.bos.web.action.base;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
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

import com.itheima.bos.domain.base.Standard;
import com.itheima.bos.service.base.StandardService;
import com.itheima.bos.web.action.CommonAction;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


@Controller
@Scope("prototype") // 等价于applicationContext.xml中scope属性
@Namespace("/") // 等价于struts.xml中package节点中namespace属性
@ParentPackage("struts-default") // 等价于struts.xml中package节点中extends属性
public class StandardAction extends CommonAction<Standard>{

    public StandardAction() {
        super(Standard.class);
    }

    private static final long serialVersionUID = 5059676695054900556L;
    /*private Standard model = new Standard();
    @Override
    public Standard getModel() {
        return model;
    }*/

    @Autowired
    private StandardService standardService;

    // 保存派送标准
    // Action中的value等价于以前struts.xml中<action>节点的name
    // Result中的name等价于以前struts.xml中<result>节点的name
    // Result中的location等价于以前struts.xml中<result>节点之间的内容
    @Action(value = "standardAction_save", results = {
            @Result(name = "success", location = "/pages/base/standard.html", type = "redirect") })
    public String save() {
        standardService.save(getModel());
        return SUCCESS;
    }
    /*// 使用属性驱动获取分页数据
    private  int  page;// 第几页
    private  int  rows;// 每一页显示多少条数据
    public void setRows(int rows) {
        this.rows = rows;
    }
    public void setPage(int page) {
        this.page = page;
    }*/
    // 分页查询,EasyUI所有的控件发请求的方式是AJAX,本方法返回的数据应该是JSON格式
    @Action(value = "standardAction_pageQuery")
    public String pageQuery() throws IOException  {
        //Sort sort =new Sort(Direction.DESC,"minWeight");//降序排序
//        Easy的page是从1开始，而PageRequest的page是从0开始的,所以要做-1的操作
        Pageable pageable =new PageRequest(page-1,rows);
//       调用业务层进行查询
        Page<Standard> page= standardService.findAll(pageable);
       //  获取总条数
       // long totalElements = pag.getTotalElements();
        //获取页面要显示的数据
        //List<Standard> list = pag.getContent();
       //封装获取的数据
       // Map<String ,Object> map = new HashMap<>();
        //map.put("total", totalElements);
        //map.put("rows", list);
     // JSONObject转换对象和Map集合
        // JSONArray转换数组和List集合
//        获取值的类型的方法
     // ServletContextservletContext = ServletActionContext.getServletContext();
        // servletContext.getRealPath("");
        // servletContext.getMimeType("");
//         转换对象为json字符串
        //String json = JSONObject.fromObject(map).toString();
//        输出出去
       // HttpServletResponse response = ServletActionContext.getResponse();
//        解决响应乱码
       // response.setContentType("application/json;charset=UTF-8");
        //response.getWriter().write(json);
        page2json(page, null);
        return  NONE;
    }
    
    // 分页查询,EasyUI所有的控件发请求的方式是AJAX,本方法返回的数据应该是JSON格式
    @Action(value = "standard_findAll")
    public String findAll() throws IOException  {
//       调用业务层进行查询
        Page<Standard> pag= standardService.findAll(null);
        //获取页面要显示的数据
        List<Standard> content = pag.getContent();
       //封装获取的数据
        //String json = JSONArray.fromObject(list).toString();
//        输出出去
        //HttpServletResponse response = ServletActionContext.getResponse();
//        解决响应乱码
       // response.setContentType("application/json;charset=UTF-8");
       // response.getWriter().write(json);
        list2json(content, null);
        return  NONE;
    }//standardAction_deleteById
    private String ids;
    public void setIds(String ids) {
        this.ids = ids;
    }
    @Action(value = "standardAction_deleteById", results = {
            @Result(name = "success", location = "/pages/base/standard.html", type = "redirect") })
    public  String  deleteById(){
        //属性驱动获得ids的值
        System.out.println(":"+ids);
        standardService.deleteById(ids);
        return SUCCESS;
    }
    
    

}
  
