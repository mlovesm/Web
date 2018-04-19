package com.api.rest.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
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
@Path("/Check")
@RequestMapping("/Check")
public class CheckController {
	
	private RestAPIService restAPIService;

	public void setrestAPIService(RestAPIService restAPIService) {
		this.restAPIService = restAPIService;
	}
	    
    @Context UriInfo uriInfo;
 
    @Context HttpServletRequest request;
        
	//----------------------------------- 점검관리 ----------------------------------------
    
    //점검 리스트
    @GET
    @Path("checkMInfoList/{scheck_date}/{echeck_date}/{pc_type}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response checkMInfoList(@PathParam("scheck_date") final String scheck_date
    		, @PathParam("echeck_date") final String echeck_date, @PathParam("pc_type") final String pc_type) {
    	Map<String, Object> dataMap = DataMap.getDataMap(request);
		dataMap.put("scheck_date", scheck_date);
		dataMap.put("echeck_date", echeck_date);
		dataMap.put("pc_type", pc_type);
    	System.out.println("checkMInfoList : dataMap="+dataMap);
    	
    	List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
    	JSONObject jsonObject = new JSONObject();
    	try {		
    		list= restAPIService.listControl("checkMInfoList", dataMap);

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
    
    //마스터 점검 정보
    @GET
    @Path("checkMInfoList/checkInfo={check_date}/{chk_no}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response checkMInfoView(@PathParam("check_date") final String check_date,@PathParam("chk_no") final String chk_no) {
    	Map<String, Object> dataMap = DataMap.getDataMap(request);
    	System.out.println("checkMInfoView : dataMap="+dataMap);
    	
    	List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
    	JSONObject jsonObject = new JSONObject();
    	try {
    		dataMap.put("check_date", check_date);
    		dataMap.put("chk_no", chk_no);    		
    		list= restAPIService.listControl("checkMInfoList", dataMap);

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
    
    //디테일 점검 정보
    @GET
    @Path("checkDInfoList/checkInfo={equip_no : (.+)?}/{check_date}/{chk_no}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response checkDInfoList(@PathParam("check_date") final String check_date
    		,@PathParam("chk_no") final String chk_no, @PathParam("equip_no") final String equip_no) {
    	Map<String, Object> dataMap = DataMap.getDataMap(request);
    	
    	List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
    	JSONObject jsonObject = new JSONObject();
    	try {
    		dataMap.put("equip_no", equip_no);
    		dataMap.put("check_date", check_date);
    		dataMap.put("chk_no", chk_no);
        	System.out.println("checkDInfoList : dataMap="+dataMap);
    		list= restAPIService.listControl("checkDInfoList", dataMap);

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

    //점검관리 작성
	@POST
	@Path("checkInfoInsert")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response checkMInfoInsert(MultivaluedMap<String, String> params) {
		System.out.println("params="+params);
		
		Map<String, Object> dataMap = DataMap.getDataMap(request);	
		
		for (Entry<String, List<String>> entry : params.entrySet()) {
			dataMap.put(entry.getKey(),entry.getValue().get(0));
		}
		
		JSONObject jsonObject = new JSONObject();
		try {
			restAPIService.insertControl("checkMInfoInsert", dataMap);
			jsonObject.put("status", "halfSuccess");
		} catch (SQLException e) {
			jsonObject.put("status", "failed");
			e.printStackTrace();
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		//디테일
		if(jsonObject.get("status").equals("halfSuccess")){
			try {
				int size= Integer.valueOf(params.getFirst("list_size"));
				for(int i=0; i<size; i++){
					dataMap.put("d_etc", params.getFirst("d_etc"+i));
					dataMap.put("chk_state", params.getFirst("chk_state"+i));
					dataMap.put("check_key", params.getFirst("check_key"+i));

					restAPIService.insertControl("checkDInfoInsert", dataMap);
				}
				jsonObject.put("status", "success");		
				
			} catch (SQLException e) {
				try {
					restAPIService.deleteControl("checkMInfoDelete", dataMap);
				} catch (SQLException e1) {
					e1.printStackTrace();
					throw new WebApplicationException(Response.Status.NOT_FOUND);
				}
				jsonObject.put("status", "failed");
				e.printStackTrace();
				throw new WebApplicationException(Response.Status.NOT_FOUND);
			}			
		}
		System.out.println(jsonObject);
		return Response.status(200).entity(jsonObject).build();                   
	}
	
    //그룹별 점검관리 작성
	@POST
	@Path("groupCheckInfoInsert")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response groupCheckInfoInsert(MultivaluedMap<String, String> params) {
		System.out.println("params="+params);
		
		Map<String, Object> dataMap = DataMap.getDataMap(request);	
		for (Entry<String, List<String>> entry : params.entrySet()) {
			dataMap.put(entry.getKey(),entry.getValue().get(0));
		}
		
		List<Map<String, Object>> equipList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> equipCheckInfoList = new ArrayList<Map<String, Object>>();
		JSONObject jsonObject = new JSONObject();
		try {
			int result=0;
			boolean check= false;
			equipList= restAPIService.listControl("getCheckEquipNo", dataMap);
			if(equipList.size()>0) {
				for(int i=0; i<equipList.size(); i++){
					if(equipList.get(i).get("USER_ID")==null) {
						System.out.println(equipList.get(i).get("EQUIP_NO"));
						
						dataMap.put("equip_no", equipList.get(i).get("EQUIP_NO"));
						result= restAPIService.resultKeyInsertControl("checkMInfoInsert", dataMap);
						
						if(result>0) {
							equipCheckInfoList= restAPIService.listControl("getEquipCheckInfo", dataMap);
							if(equipCheckInfoList.size()>0) {
								try {
									for(int j=0; j<equipCheckInfoList.size(); j++){									
										dataMap.put("check_key", equipCheckInfoList.get(j).get("CHECK_KEY"));
										dataMap.put("chk_state", "1");
										dataMap.put("d_etc", "");
										restAPIService.insertControl("checkDInfoInsert", dataMap);
									}								
								} catch (Exception e) {						
									restAPIService.deleteControl("checkMInfoDelete", dataMap);
									
									jsonObject.put("status", "failed");
									e.printStackTrace();
									throw new WebApplicationException(Response.Status.NOT_FOUND);
								}	

							}
							check= true;
							jsonObject.put("status", "success");
						}
					}else {
						if(!check) jsonObject.put("status", "half");
					}

				}//for 1
				
			}//if 1
			
		} catch (SQLException e) {
			jsonObject.put("status", "failed");
			e.printStackTrace();
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		System.out.println(jsonObject);
		return Response.status(200).entity(jsonObject).build();                   
	}	
		
    //점검관리 수정
	@PUT
    @Path("checkInfoUpdate")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response facilCheckModify(MultivaluedMap<String, String> params) {
		System.out.println("params="+params);
		Map<String, Object> dataMap = DataMap.getDataMap(request);
		
		for (Entry<String, List<String>> entry : params.entrySet()) {
			dataMap.put(entry.getKey(),entry.getValue().get(0));
		}	
				
		JSONObject jsonObject = new JSONObject();
		int equipNo_cng=0;
		try {
			Map<String, Object> getMap= restAPIService.mapDataControl("getSelectChkNo", dataMap);
			if(getMap!=null){
				if(dataMap.get("key_check_date").equals(dataMap.get("check_date"))){
					dataMap.put("chk_no", dataMap.get("key_chk_no"));
				}else{
					dataMap.put("chk_no", getMap.get("chk_no"));
				}
				restAPIService.updateControl("checkMInfoUpdate", dataMap);
				jsonObject.put("status", "halfSuccess");
			}
			
		} catch (SQLException e) {
			jsonObject.put("status", "failed");
			e.printStackTrace();
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		//디테일
		if(jsonObject.get("status").equals("halfSuccess")){
			try {
				int size= Integer.valueOf(params.getFirst("list_size"));
				if(!dataMap.get("equip_no").equals(params.getFirst("cu_equip_no")) &&
						!dataMap.get("eGroup_no").equals(params.getFirst("cu_eGroup_no"))){
					equipNo_cng=1;
				}
				if(equipNo_cng==1) {	//값이 다를시 지우고
					restAPIService.deleteControl("checkDInfoDelete", dataMap);
				}
				for(int i=0; i<size; i++){
					dataMap.put("d_etc", params.getFirst("d_etc"+i));
					if(params.getFirst("chk_state"+i).equals("")){
						dataMap.put("chk_state", "1");
					}else{
						dataMap.put("chk_state", params.getFirst("chk_state"+i));
					}
					dataMap.put("check_key", params.getFirst("check_key"+i));
					
					if(equipNo_cng==1) {	//새로 등록
						restAPIService.insertControl("checkDInfoInsert", dataMap);
					}else {		//업데이트
						restAPIService.updateControl("checkDInfoUpdate", dataMap);
					}
				}
				jsonObject.put("status", "success");
			} catch (SQLException e) {
				jsonObject.put("status", "failed");
				e.printStackTrace();
				throw new WebApplicationException(Response.Status.NOT_FOUND);
			}			
		}		
		System.out.println(jsonObject);
	   return Response.status(200).entity(jsonObject).build();                   
	}	
	
	//점검관리 삭제
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("checkInfoDelete/checkInfo")
    public Response checkDInfoDelete(@QueryParam("check_date") final String check_date,@QueryParam("chk_no") final String chk_no) {
    	Map<String, Object> dataMap = DataMap.getDataMap(request);
    	System.out.println("checkInfoDelete : dataMap="+dataMap);
    	
    	JSONObject jsonObject = new JSONObject();
    	try {    		 
    		dataMap.put("check_date", check_date);
    		dataMap.put("chk_no", chk_no);		
    		restAPIService.deleteControl("checkInfoDelete", dataMap);

    		jsonObject.put("status", "success");
		} catch (SQLException e) {
			e.printStackTrace();
			jsonObject.put("status", "failed");
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
       
    	return Response.status(200).entity(jsonObject).build();
    }
 
    
	//----------------------------------- 미점검리스트 ----------------------------------------
	
    @GET
    @Path("unCheckFailCheckList")
    @Produces(MediaType.APPLICATION_JSON)
    public Response unCheckFailCheckList() {
    	Map<String, Object> dataMap = DataMap.getDataMap(request);
    	System.out.println("unCheckFailCheckList : dataMap="+dataMap);
    	
    	List<Map<String, Object>> unCheckList = new ArrayList<Map<String, Object>>();
    	List<Map<String, Object>> failCheckList = new ArrayList<Map<String, Object>>();
    	JSONObject jsonObject = new JSONObject();
    	
    	try {		
    		unCheckList= restAPIService.listControl("unCheckList", dataMap);
    		failCheckList= restAPIService.listControl("failCheckList", dataMap);

    		jsonObject.put("datasA", unCheckList);
    		jsonObject.put("datasB", failCheckList);
    		jsonObject.put("countA", unCheckList.size());
    		jsonObject.put("countB", failCheckList.size());
    		jsonObject.put("status", "success");
		} catch (SQLException e) {
			e.printStackTrace();
			jsonObject.put("status", "failed");
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
       
    	return Response.status(200).entity(jsonObject).build();
    }
    
    @GET
    @Path("unCheckList")
    @Produces(MediaType.APPLICATION_JSON)
    public Response unCheckList() {
    	Map<String, Object> dataMap = DataMap.getDataMap(request);
    	System.out.println("unCheckList : dataMap="+dataMap);
    	
    	List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
    	JSONObject jsonObject = new JSONObject();
    	try {		
    		list= restAPIService.listControl("unCheckList", dataMap);

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
    
    @GET
    @Path("unCheckList/equipNo/{equip_no : (.+)?}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response unCheckListData(@PathParam("equip_no") final String equip_no) {
    	Map<String, Object> dataMap = DataMap.getDataMap(request);
    	dataMap.put("equip_no", equip_no);
    	dataMap.put("use_part1", "");
    	System.out.println("unCheckList : dataMap="+dataMap);
    	
    	List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
    	JSONObject jsonObject = new JSONObject();
    	try {		
    		list= restAPIService.listControl("unCheckList", dataMap);

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
    
    
	//----------------------------------- 점검이상리스트 ----------------------------------------
    
    @GET
    @Path("failCheckList")
    @Produces(MediaType.APPLICATION_JSON)
    public Response failCheckList() {
    	Map<String, Object> dataMap = DataMap.getDataMap(request);
    	System.out.println("failCheckList : dataMap="+dataMap);
    	
    	List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
    	JSONObject jsonObject = new JSONObject();
    	try {		
    		list= restAPIService.listControl("failCheckList", dataMap);

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
    
    @GET
    @Path("failCheckList/equipNo/{equip_no : (.+)?}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response failCheckListData(@PathParam("equip_no") final String equip_no) {
    	Map<String, Object> dataMap = DataMap.getDataMap(request);
    	dataMap.put("equip_no", equip_no);
    	dataMap.put("use_part1", "");
    	System.out.println("failCheckList : dataMap="+dataMap);
    	
    	List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
    	JSONObject jsonObject = new JSONObject();
    	try {		
    		list= restAPIService.listControl("failCheckList", dataMap);

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
    
    
	//----------------------------------- NFC ----------------------------------------    
	
    //NFC 태그 터치
    @GET
    @Path("tagDataInfo/{tag_no : (.+)?}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response tagDataInfo(@PathParam("tag_no") final String tag_no
    		,@QueryParam("pc_type") final String pc_type) {
    	Map<String, Object> dataMap = DataMap.getDataMap(request);
    	System.out.println("tagDataInfo : dataMap="+dataMap);
    	
    	List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
    	JSONObject jsonObject = new JSONObject();
    	try {
    		dataMap.put("tag_no", tag_no);
    		dataMap.put("pc_type", pc_type);
        	list= restAPIService.listControl("equipGroupList", dataMap);
        	if(list.size()>0) {
        		jsonObject.put("status", "groupY");
        	}else {
            	list= restAPIService.listControl("equipmentList", dataMap);
            	jsonObject.put("status", "groupN");
        	}
    		 		
    		jsonObject.put("datas", list);
    		jsonObject.put("count", list.size());
		} catch (SQLException e) {
			e.printStackTrace();
			jsonObject.put("status", "failed");
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
       
    	return Response.status(200).entity(jsonObject).build();
    }    	
    
}//c
