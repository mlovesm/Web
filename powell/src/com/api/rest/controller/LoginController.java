package com.api.rest.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.api.rest.dao.RestAPIService;
import com.common.DataMap;
import com.common.Utility;

@SuppressWarnings("unchecked")
@Controller
@Path("/Login")
@RequestMapping("/Login")
public class LoginController {
	
	private RestAPIService restAPIService;

	public void setrestAPIService(RestAPIService restAPIService) {
		this.restAPIService = restAPIService;
	}
	    
    @Context
    UriInfo uriInfo;
 
    @Context
    HttpServletRequest request;
    
    final String LATEST_APP_VER= "1.0.0";
    
    //로그인 체크 앱
	@POST
	@Path("loginCheckApp")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")    
    public Response loginDataCheckApp(MultivaluedMap<String, String> params) {
//		System.out.println("params="+params);
		Map<String, Object> dataMap = DataMap.getDataMap(request);
		String id= params.getFirst("id");
		String password= params.getFirst("password");
    	dataMap.put("id", id);
    	dataMap.put("password", password);
    	
    	JSONObject jsonObject = new JSONObject();
    	
        try{
        	int flag= 0;
			String nowDate= Utility.getSysDate();
			dataMap.put("nowDate", nowDate);		
        	
			switch (flag) {
			case 0:
				jsonObject = restAPIService.login_check(dataMap);
	    		jsonObject.put("status", true);
	    		jsonObject.put("LATEST_APP_VER", LATEST_APP_VER);
	    		
	            if(Integer.valueOf(jsonObject.get("result").toString())==RestAPIService.LOGIN_OK){
	                
	            }else if(Integer.valueOf(jsonObject.get("result").toString())==RestAPIService.PWD_MISMATCH){
	            	System.out.println("PWD_MISMATCH");
	            	
	            }else{
	            	System.out.println("NONE_ID");
	            }
	            break;

			default:
				jsonObject.put("result", 3);
				jsonObject.put("flag", flag);
				
				break;
			}
        
        }
        catch(SQLException e){
            System.out.println("로그인 처리 실패");
			e.printStackTrace();
			jsonObject.put("status", false);
			throw new WebApplicationException(Response.Status.NOT_FOUND);            
        }
    	System.out.println(jsonObject);
    	return Response.status(200).entity(jsonObject).build();
    }    
    
    //유저 검색
    @GET
    @Path("searchUserData/gubun={gubun}/param={param}")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response searchUserData(@PathParam("gubun") final String gubun, @PathParam("param") final String param) {
    	Map<String, Object> dataMap = DataMap.getDataMap(request);
    	System.out.println("searchUserData : dataMap="+dataMap);
    	
		dataMap.put("gubun", gubun);
		dataMap.put("param", param);
    	JSONObject jsonObject = new JSONObject();

    	try {
			List<Map<String, Object>> list = restAPIService.listControl("searchUserData", dataMap);	
			if(list!=null){
	    		jsonObject.put("datas", list);
	    		jsonObject.put("status", true);
			}else{
				jsonObject.put("status", false); 
			}

		} catch (SQLException e) {
			e.printStackTrace();
			jsonObject.put("status", false);
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}    
        
    	return Response.status(200).entity(jsonObject).build();
    }    

}//c
