package com.api.rest.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.api.rest.dao.RestAPIService;
import com.common.DataMap;

@SuppressWarnings("unchecked")
@Controller
@Path("/Equipment")
@RequestMapping("/Equipment")
public class EquipmentController {
	
	private RestAPIService restAPIService;

	public void setrestAPIService(RestAPIService restAPIService) {
		this.restAPIService = restAPIService;
	}
	    
    @Context UriInfo uriInfo;
 
    @Context HttpServletRequest request;
    
    @Context HttpHeaders httpHeaders;
        
	//----------------------------------- 장치관리 ----------------------------------------
    
    //장치 그룹 리스트
    @GET
    @Path("equipGroupList")
    @Produces(MediaType.APPLICATION_JSON)
    public Response equipGroupList(@QueryParam("eGroup_no") final String eGroup_no
    		, @QueryParam("pc_type") final String pc_type) {
    	Map<String, Object> dataMap = DataMap.getDataMap(request);
    	System.out.println("equipGroupList : dataMap="+dataMap);
    	System.out.println(eGroup_no);
    	    	
    	List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
    	JSONObject jsonObject = new JSONObject();
    	try {
        	dataMap.put("eGroup_no", eGroup_no);
        	dataMap.put("pc_type", pc_type);
    		list= restAPIService.listControl("equipGroupList", dataMap);

    		jsonObject.put("datas", list);
    		jsonObject.put("count", list.size());
    		jsonObject.put("status", "success");
		} catch (SQLException e) {
			e.printStackTrace();
			jsonObject.put("status", "failed");
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
       
    	return Response.status(200).entity(jsonObject).build();
    }        
    
    //장치 리스트
    @GET
    @Path("equipmentList/{eGroup_no : (.+)?}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response equipmentList(@PathParam("eGroup_no") final String eGroup_no
    		,@QueryParam("pc_type") final String pc_type) {
    	Map<String, Object> dataMap = DataMap.getDataMap(request);
    	System.out.println("equipmentList : dataMap="+dataMap);
    	System.out.println(eGroup_no);
    	    	
    	List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
    	JSONObject jsonObject = new JSONObject();
    	try {
        	dataMap.put("eGroup_no", eGroup_no);
        	dataMap.put("pc_type", pc_type);
    		list= restAPIService.listControl("equipmentList", dataMap);

    		jsonObject.put("datas", list);
    		jsonObject.put("count", list.size());
    		jsonObject.put("status", "success");
		} catch (SQLException e) {
			e.printStackTrace();
			jsonObject.put("status", "failed");
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
       
    	return Response.status(200).entity(jsonObject).build();
    }    
    
    //해당 장치 정보
    @GET
    @Path("equipmentDetail/equipNo/{equip_no : (.+)?}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response equipmentDetail(@PathParam("equip_no") final String equip_no) {
    	Map<String, Object> dataMap = DataMap.getDataMap(request);
    	System.out.println("equipmentDetail : dataMap="+dataMap);
    	
    	List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
    	JSONObject jsonObject = new JSONObject();
    	try {
    		dataMap.put("equip_no", equip_no);		
    		list= restAPIService.listControl("equipmentDetail", dataMap);

    		jsonObject.put("datas", list);
    		jsonObject.put("count", list.size());
    		jsonObject.put("status", "success");
		} catch (SQLException e) {
			e.printStackTrace();
			jsonObject.put("status", "failed");
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
       
    	return Response.status(200).entity(jsonObject).build();
    }
    
    //해당 장치 정보 이력
    @GET
    @Path("equipmentInfo/equipNo/{equip_no : (.+)?}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response equipmentInfo(@PathParam("equip_no") final String equip_no) {
    	Map<String, Object> dataMap = DataMap.getDataMap(request);
    	System.out.println("equipmentInfo : dataMap="+dataMap);
    	
    	List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
    	JSONObject jsonObject = new JSONObject();
    	try {
    		dataMap.put("equip_no", equip_no);		
    		list= restAPIService.listControl("equipmentInfo", dataMap);

    		jsonObject.put("datas", list);
    		jsonObject.put("count", list.size());
    		jsonObject.put("status", "success");
		} catch (SQLException e) {
			e.printStackTrace();
			jsonObject.put("status", "failed");
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
       
    	return Response.status(200).entity(jsonObject).build();
    }

    //해당 장치 정보 이력 리스트
    @GET
    @Path("equipmentInfoHistory/{equip_no : (.+)?}/{scheck_date}/{echeck_date}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response equipmentInfoHistory(@PathParam("equip_no") final String equip_no, 
    		@PathParam("scheck_date") final String scheck_date, @PathParam("echeck_date") final String echeck_date) {
    	Map<String, Object> dataMap = DataMap.getDataMap(request);
    	System.out.println("equipmentInfoHistory : dataMap="+dataMap);
    	
    	List<Map<String, Object>> infoList = new ArrayList<Map<String, Object>>();
    	List<Map<String, Object>> historyList = new ArrayList<Map<String, Object>>();
    	JSONObject jsonObject = new JSONObject();
    	try {
    		dataMap.put("equip_no", equip_no);
    		dataMap.put("scheck_date", scheck_date);
    		dataMap.put("echeck_date", echeck_date);
    		infoList= restAPIService.listControl("equipmentInfo", dataMap);
    		historyList= restAPIService.listControl("equipmentInfoHistory", dataMap);

    		jsonObject.put("datasA", infoList);
    		jsonObject.put("datasB", historyList);
    		jsonObject.put("countA", infoList.size());
    		jsonObject.put("countB", historyList.size());
    		jsonObject.put("status", "success");
		} catch (SQLException e) {
			e.printStackTrace();
			jsonObject.put("status", "failed");
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
       
    	return Response.status(200).entity(jsonObject).build();
    }
    
}//c
