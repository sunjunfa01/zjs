package com.itheima.webservice;

import java.util.Collection;

import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.client.WebClient;

import com.itheima.bos.domain.base.Customer;


/**  
 * ClassName:CXFTest <br/>  
 * Function:  <br/>  
 * Date:     2018年1月20日 下午4:45:57 <br/>       
 */
public class CXFTest {
    public static void main(String[] args) {
        Collection<? extends Customer> collection = WebClient.create("http://localhost:8180/crm/webService/customerService/findAll")
        .type(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .getCollection(Customer.class);
        
        for (Customer customer : collection) {
            System.out.println(customer);
        }
    }

}
  
