package com.api.rest.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;

//서비스단 (컨트롤러 부분의 작업을 줄이기 위해서 쓰이는 페이지)
public class RestAPIServiceImpl implements RestAPIService{
	
	private RestAPIDAO restAPIDAO;

    public void setrestAPIDAO(RestAPIDAO restAPIDAO){
        this.restAPIDAO = restAPIDAO;
    }

	public List<Map<String, Object>> listControl(String queryName, Map<String, Object> dataMap) throws SQLException{
        return restAPIDAO.listControl(queryName, dataMap);
    }

    public Map<String, Object> mapDataControl(String queryName, Map<String, Object> dataMap) throws SQLException{
        return restAPIDAO.mapDataControl(queryName, dataMap);
    }
    
    public int resultKeyInsertControl(String queryName, Map<String, Object> dataMap)throws SQLException{
    	return restAPIDAO.resultKeyInsertControl(queryName, dataMap);
    }    

    public void insertControl(String queryName, Map<String, Object> dataMap)throws SQLException{   	
    	restAPIDAO.insertControl(queryName, dataMap);
    }

    public void updateControl(String queryName, Map<String, Object> dataMap)throws SQLException{
    	restAPIDAO.updateControl(queryName, dataMap);
    }

    public void deleteControl(String queryName, Map<String, Object> dataMap)throws SQLException{
    	restAPIDAO.deleteControl(queryName, dataMap);
    }

	//로그인시 아이디 패스워드 체크
	@SuppressWarnings("unchecked")
	public JSONObject login_check(Map<String, Object> dataMap) throws SQLException{
		//입력한 아이디의 DB 패스워드 조회
//		System.out.println("login_check : dataMap="+dataMap);
		Map<String, Object> db_map= restAPIDAO.mapDataControl("user_login", dataMap);
		String user_id="";
		if(db_map!=null) user_id = (String) db_map.get("USER_ID");
		int result=0;
    	JSONObject jsonObject = new JSONObject();
		if(user_id!=null&&!user_id.isEmpty()){
			result=LOGIN_OK;
			jsonObject.put("result", result);
			jsonObject.put("user_id", user_id);
			jsonObject.put("user_name", db_map.get("USER_NAME"));

		}else{
			result=PWD_MISMATCH;
			jsonObject.put("result", result);
		}
		return jsonObject;
	}
   
}
