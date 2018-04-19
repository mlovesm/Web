package com.api.rest.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import net.sf.json.JSONException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.api.rest.dao.RestAPIService;
import com.common.DataMap;
import com.common.Utility;
//2번째
@SuppressWarnings("unchecked")
@Controller
@Path("/Board")
@RequestMapping("/Board")
public class BoardController {
	
	private RestAPIService restAPIService;

	public void setrestAPIService(RestAPIService restAPIService) {
		this.restAPIService = restAPIService;
	}
	    
    // The @Context annotation allows us to have certain contextual objects
    // injected into this class.
    // UriInfo object allows us to get URI information (no kidding).
    @Context
    UriInfo uriInfo;
 
    // Another "injected" object. This allows us to use the information that's
    // part of any incoming request.
    // We could, for example, get header information, or the requestor's address.
    @Context
    HttpServletRequest request;
    
    @Context HttpHeaders httpHeaders;
    
    //test
	@POST
	@Path("test")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response test(@FormParam("arg_json") String params) throws JsonParseException, JsonMappingException, IOException {
		
        HashMap<String,String> mapFromJson = (HashMap<String,String>)new ObjectMapper().readValue(params, HashMap.class);
        //add your processing 
	    
		System.out.println("params="+params);
		System.out.println("mapFromJson="+mapFromJson);
		Map<String, Object> dataMap = DataMap.getDataMap(request);
			
		for (Entry<String, String> entry : mapFromJson.entrySet()) {
			dataMap.put(entry.getKey(),entry.getValue());
		}		
//		System.out.println(dataMap.get("data"));
		List<Map<String, Object>> list= (List<Map<String, Object>>) dataMap.get("data");
		System.out.println(list.get(1).get("mac_address"));
		JSONObject jsonObject = new JSONObject();

		try {
			jsonObject.put("status", "success");
		} catch (Exception e) {
			jsonObject.put("status", "failed");
			e.printStackTrace();
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		System.out.println(jsonObject);
	   return Response.status(200).entity(jsonObject).build();                   
	}    
    
    /*-------------------------------- MSDS -------------------------------------*/
    //MSDS 목록
    @GET
    @Path("msdsInfoList")
    @Produces(MediaType.APPLICATION_JSON)
    public Response msdsInfoList() {
    	Map<String, Object> dataMap = DataMap.getDataMap(request);
    	List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
    	JSONObject jsonObject = new JSONObject();

    	try {
    		String comp_database = httpHeaders.getRequestHeader("comp_database").get(0);
    		dataMap.put("comp_database", comp_database);
    		list= restAPIService.listControl("msdsInfoList", dataMap);

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
    
    
    
    
    
    
    
    
    
    //공지사항 공장별/직책별 데이터
    @GET
    @Path("noticeBoardCodeList/push_target/{push_target}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response penaltyCodeList(@PathParam("push_target") final String push_target) {
    	Map<String, Object> dataMap = DataMap.getDataMap(request);
    	dataMap.put("push_target", push_target);
    	System.out.println("noticeBoardCodeList : dataMap="+dataMap);
    	
    	List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
    	JSONObject jsonObject = new JSONObject();
    	try {    		   		
    		list= restAPIService.listControl("noticeBoardCodeList", dataMap);

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
    
    //공지사항 목록
    @GET
    @Path("noticeBoardList")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSampleListData() {
    	Map<String, Object> dataMap = DataMap.getDataMap(request);
    	List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
    	JSONObject jsonObject = new JSONObject();
    	try {    		   		
    		list= restAPIService.listControl("noticeBoardList", dataMap);

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
  
    //공지사항 작성
	@POST
	@Path("noticeBoardWrite")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response noticeBoardWrite(MultivaluedMap<String, String> params) {
	    
		System.out.println("params="+params);
		
		Map<String, Object> dataMap = DataMap.getDataMap(request);
		String nowDate= Utility.getSysDate();
		
		dataMap.put("push_date", nowDate);
		dataMap.put("push_target", params.getFirst("push_target"));
		dataMap.put("detail_target", params.getFirst("detail_target"));
		dataMap.put("push_title", params.getFirst("push_title"));
		dataMap.put("push_text", params.getFirst("push_text"));
		dataMap.put("writer_sabun", params.getFirst("writer_sabun").trim());
		
		JSONObject jsonObject = new JSONObject();
		try {
			restAPIService.insertControl("noticeBoardInsert", dataMap);
			jsonObject.put("status", "success");
		} catch (SQLException e) {
			jsonObject.put("status", "failed");
			e.printStackTrace();
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		System.out.println(jsonObject);
	   return Response.status(200).entity(jsonObject).build();                   
	}
	
    //공지사항 상세
    @GET
    @Path("noticeBoardDetail/{idx}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response noticeBoardDetail(@PathParam("idx") final String idx) {
    	Map<String, Object> dataMap = DataMap.getDataMap(request);
    	dataMap.put("push_key", idx);
    	Map<String, Object> map = new HashMap<String, Object>();
    	JSONObject jsonObject = new JSONObject();
    	try {   		
    		map= restAPIService.mapDataControl("noticeBoardList", dataMap);
    		JSONArray array = new JSONArray();
    		array.add(map);
    		jsonObject.put("datas", array);
    		jsonObject.put("status", "success");
		} catch (SQLException e) {
			e.printStackTrace();
			jsonObject.put("status", "failed");
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
       
    	return Response.status(200).entity(jsonObject).build();
    }    
    
    //공지사항 수정
	@PUT
	@Path("noticeBoardModify/{idx}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response noticeBoardModify(@PathParam("idx") final String idx, MultivaluedMap<String, String> params) {
	    
		System.out.println("params="+params);
		Map<String, Object> dataMap = DataMap.getDataMap(request);
		String nowDate= Utility.getSysDate();
		
		dataMap.put("push_key", idx);		
		dataMap.put("push_date", nowDate);
		dataMap.put("push_target", params.getFirst("push_target"));
		dataMap.put("detail_target", params.getFirst("detail_target"));
		dataMap.put("push_title", params.getFirst("push_title"));
		dataMap.put("push_text", params.getFirst("push_text"));
		dataMap.put("writer_sabun", params.getFirst("writer_sabun").trim());
		
		JSONObject jsonObject = new JSONObject();
		try {
			restAPIService.updateControl("noticeBoardUpdate", dataMap);
			jsonObject.put("status", "success");
			jsonObject.put("push_key", idx);
		
		} catch (SQLException e) {
			jsonObject.put("status", "failed");
			e.printStackTrace();
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		return Response.status(200).entity(jsonObject).build();                   
	}
	
	//공지사항 삭제
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("noticeBoardDelete/{idx}")
	public Response noticeBoardDelete(@PathParam("idx") String idx) {
		
		Map<String, Object> dataMap = DataMap.getDataMap(request);
		System.out.println("noticeBoardDelete : dataMap="+dataMap);
		dataMap.put("push_key", idx);
		
		JSONObject jsonObject = new JSONObject();
		try {
			restAPIService.deleteControl("noticeBoardDelete", dataMap);
			jsonObject.put("status", "success");
		
		} catch (SQLException e) {
			e.printStackTrace();
			jsonObject.put("status", "failed");
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		System.out.println(jsonObject);
		return Response.status(200).entity(jsonObject).build();                  
	}
	
	@POST
	@Path("fcmTokenData")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response fcmTokenData(MultivaluedMap<String, String> params) {
	    
//		System.out.println("fcmTokenData params="+params.get("Token"));
		
		Map<String, Object> dataMap = DataMap.getDataMap(request);
		Map<String, Object> tokenCountMap = new HashMap<String, Object>();
		Map<String, Object> andIdCountMap = new HashMap<String, Object>();
		Map<String, Object> compIdMap = new HashMap<String, Object>();
		String nowDate= Utility.getSysDate();
		
		dataMap.put("token_date", nowDate);
		dataMap.put("token", params.getFirst("Token"));
		dataMap.put("phone_num", params.getFirst("phone_num"));
		dataMap.put("sabun_no", params.getFirst("sabun_no"));
		dataMap.put("comp_user_id", params.getFirst("userId"));
		dataMap.put("id", params.getFirst("userId"));
		dataMap.put("and_id", params.getFirst("and_id"));
		
		JSONObject jsonObject = new JSONObject();
		try {
			tokenCountMap= restAPIService.mapDataControl("tokenDataCheck", dataMap);
			
			if(tokenCountMap.get("CNT").toString().equals("0")){
				andIdCountMap= restAPIService.mapDataControl("AndIDCheck", dataMap);
				
				if(andIdCountMap.get("CNT").toString().equals("0")){
					compIdMap= restAPIService.mapDataControl("userCheck", dataMap);
					dataMap.put("comp_id", compIdMap.get("comp_id"));
					restAPIService.insertControl("tokenDataInsert", dataMap);					
				}else{
					restAPIService.updateControl("tokenDataUpdate", dataMap);
				}
			}else{
				restAPIService.updateControl("tokenDataUpdate", dataMap);

			}
			jsonObject.put("status", "success");
		} catch (SQLException e) {
			jsonObject.put("status", "failed");
			e.printStackTrace();
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
//		System.out.println(jsonObject);
		return Response.status(200).entity(jsonObject).build();                   
	}
	
    @RequestMapping(value="/uncheckPushData.do",method=RequestMethod.GET)
    public ModelAndView uncheckPushData_get(HttpServletRequest request, HttpServletResponse response) {
    	
    	ModelAndView mv = new ModelAndView();
        
        mv.setViewName("/push/push_board");
        return mv;    	
    }
	
//	@Scheduled(fixedRate=5000)
    @GET
    @Path("uncheckPushData")
    @Produces(MediaType.APPLICATION_JSON)    
    public Response uncheckPushData() {
    	Map<String, Object> dataMap = DataMap.getDataMap(request);
            	
    	JSONObject jsonObject = new JSONObject();
    	Map<String, Object> uncheckEquipDataMap = new HashMap<String, Object>();
		List<Map<String, Object>> pushCompanyList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> pushCompanyUserList = new ArrayList<Map<String, Object>>();
    	try {
    		pushCompanyList= restAPIService.listControl("pushCompanyList", dataMap);
    		
    		if(pushCompanyList.size()>0) {
    			for (int i = 0; i < pushCompanyList.size(); i++) {
					dataMap.put("push_company_id", pushCompanyList.get(i).get("comp_id"));
					dataMap.put("comp_database", pushCompanyList.get(i).get("comp_database"));

					pushCompanyUserList= restAPIService.listControl("pushCompanyUserList", dataMap);
					if(pushCompanyUserList.size()>0) {
						for (int j = 0; j < pushCompanyUserList.size(); j++) {
							System.out.println(j+","+pushCompanyUserList.get(j));
							dataMap.put("part1_cd", pushCompanyUserList.get(j).get("part1_cd"));
							
							uncheckEquipDataMap= restAPIService.mapDataControl("uncheckEquipData", dataMap);
				    		System.out.println(uncheckEquipDataMap);
				    		
							String title= "";
							String message= "";
							
							if(uncheckEquipDataMap!=null){
								if(uncheckEquipDataMap.get("ALRAM_BDATE").toString().equals("0")) {
									title= "점검대상 알림 입니다.";
								}else {
									title= "점검대상 "+uncheckEquipDataMap.get("ALRAM_BDATE")+"일 전 입니다.";
								}
								if(uncheckEquipDataMap.get("SIDE_CNT").toString().equals("0")) {
									message= uncheckEquipDataMap.get("EQUIP_NM")+" 점검대상 입니다.";
								}else {
									message= uncheckEquipDataMap.get("EQUIP_NM")+" 외 "+uncheckEquipDataMap.get("SIDE_CNT")+" 건 점검대상 입니다.";
								}		
								
								FCM_Setting.send_FCM_Notification( pushCompanyUserList.get(j).get("token").toString(), title, message);
								System.out.println(pushCompanyUserList.get(j).get("token").toString()+", "+ title+", "+message);
							}else{
								System.out.println("미점검 없음");
							}							
						}
					}
				}
    			
    		}

//    		jsonObject.put("datas", pushList);
//    		jsonObject.put("count", pushList.size());
    		jsonObject.put("status", "success");
		} catch (SQLException e) {
			e.printStackTrace();
			jsonObject.put("status", "failed");
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
    	return Response.status(200).entity(jsonObject).build();
    }
	
	@POST
	@Path("sendPushData")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response pushData(MultivaluedMap<String, String> params) {
		System.out.println("sendPushData params="+params);
		
		Map<String, Object> dataMap = DataMap.getDataMap(request);
		List<Map<String, Object>> pushList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> commonPushlist = new ArrayList<Map<String, Object>>();
		String nowDate= Utility.getSysDate();
		dataMap.put("push_date", nowDate);
		dataMap.put("loginUserId", params.getFirst("loginUserId"));
		dataMap.put("comp_id", params.getFirst("comp_id"));
		dataMap.put("chk_no", params.getFirst("chk_no"));
		dataMap.put("check_date", params.getFirst("check_date"));
		dataMap.put("state", params.getFirst("state"));
		dataMap.put("title", params.getFirst("title"));
		dataMap.put("message", params.getFirst("message"));

		JSONObject jsonObject = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		HashMap<String, Object> datasMap = new HashMap<String, Object>();
		try {
			pushList= restAPIService.listControl("pushDataList", dataMap);
			commonPushlist= restAPIService.listControl("commonPushDataList", dataMap);
			
			//Method to send Push Notification
			String title= params.getFirst("title");
			String message= params.getFirst("message");
			
			for (int i = 0; i < pushList.size(); i++) {
				FCM_Setting.send_FCM_Notification( pushList.get(i).get("token").toString(), title, message);
			}
			if(pushList.size()>0){
				datasMap.put("pushSend", "success");
				datasMap.put("count", pushList.size());
			}else{
				datasMap.put("pushSend", "empty");
			}
			
			for (int i = 0; i < commonPushlist.size(); i++) {
				FCM_Setting.send_FCM_Notification( commonPushlist.get(i).get("token").toString(), title, message);
			}
			if(pushList.size()>0){
				datasMap.put("commonPushSend", "success");
				datasMap.put("commonCount", commonPushlist.size());
			}else{
				datasMap.put("commonPushSend", "empty");
			}
			if(datasMap.get("pushSend").toString().equals("success")
					||datasMap.get("commonPushSend").toString().equals("success")) {
				
				restAPIService.insertControl("pushHistoryInsert", dataMap);
				String comp_database = httpHeaders.getRequestHeader("comp_database").get(0);
				dataMap.put("comp_database", comp_database);
				restAPIService.updateControl("checkMInfoPushUpdate", dataMap);
			}
  	  		jsonArray.add(datasMap);
  	  		jsonObject.put("datas", jsonArray);
    		jsonObject.put("status", "successOnPush");
		} catch (Exception e) {
			jsonObject.put("status", "failed");
			e.printStackTrace();
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}

		return Response.status(200).entity(jsonObject).build();                   
	}

	
    public static class FCM_Setting {

        final static private String FCM_URL = "https://fcm.googleapis.com/fcm/send";
        final static private String SERVER_KEY = "AAAAz0jmtEs:APA91bH5RYvgA8hHvjokOgdAgE33ry_0Cnb6Rz7u9ZHkG5Uzf0x-1hF2KSUtJOlNst2hVWh2h72yclCGxKl1Db8HZl_qWDrTASSZ4V6661FTgAsGA_kFIGkhltSsM47uw2Ulbu3spapf";
        /**
         *
         * Method to send push notification to Android FireBased Cloud messaging Server.
         *
         * @param tokenId Generated and provided from Android Client Developer
         * @param server_key Key which is Generated in FCM Server
         * @param message which contains actual information.
         *
         */
        static void send_FCM_Notification(String tokenId, String title, String message){
            try{

                // Create URL instance.
                URL url = new URL(FCM_URL);

                // create connection.
                HttpURLConnection conn;
                conn = (HttpURLConnection) url.openConnection();
                conn.setUseCaches(false);
                conn.setDoInput(true);
                conn.setDoOutput(true);

                //set method as POST or GET
                conn.setRequestMethod("POST");

                //pass FCM server key
                conn.setRequestProperty("Authorization","key="+SERVER_KEY);

                //Specify Message Format
                conn.setRequestProperty("Content-Type","application/json");

                //Create JSON Object & pass value
                JSONObject infoJson = new JSONObject();

                infoJson.put("title", title);
                infoJson.put("message", message);

                JSONObject json = new JSONObject();

                json.put("to",tokenId.trim());
                json.put("data", infoJson);

                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                wr.write(json.toString());
                wr.flush();

                int status = 0;
                if( null != conn ){
                    status = conn.getResponseCode();
                }

                if( status != 0){
                    if( status == 200 ){
                        //SUCCESS message
                        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        System.out.println("Android Notification Response : " + reader.readLine());

                    }else if(status == 401){
                        //client side error
                        System.out.println("Notification Response : TokenId : " + tokenId + " Error occurred :");

                    }else if(status == 501){
                        //server side error
                        System.out.println("Notification Response : [ errorCode=ServerError ] TokenId : " + tokenId);

                    }else if( status == 503){
                        //server side error
                        System.out.println("Notification Response : FCM Service is Unavailable  TokenId : " + tokenId);

                    }
                }

            }catch(MalformedURLException mlfexception){
                // Protocol Error
                System.out.println("Error occurred while sending push Notification!.." + mlfexception.getMessage());

            }catch(IOException mlfexception){
                //URL problem
                System.out.println("Reading URL, Error occurred while sending push Notification!.." + mlfexception.getMessage());

            }catch(JSONException jsonexception){
                //Message format error
                System.out.println("Message Format, Error occurred while sending push Notification!.." + jsonexception.getMessage());

            }catch (Exception exception) {
                //General Error or exception.
                System.out.println("Error occurred while sending push Notification!.." + exception.getMessage());

            }

        }

    }//class FCM
}//c
