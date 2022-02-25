package com.codechallenge.flowers.controller;



import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.codechallenge.flowers.model.ListParams;
import com.codechallenge.flowers.model.UserData;

@RestController
@RequestMapping("/challange/api")

public class ChallangeController {
	@Autowired 
	public Application app;
	
	public UserData userData;
	
	
	public static List readJsonData()  {
		JSONParser jsonParser = new JSONParser();
		List list = null;
		try {
			JSONArray jsonArray =  (JSONArray) jsonParser.parse(new FileReader("C://Users//91918//Downloads//1800flowers//src//main//resources//data.json"));
			 list =(List)  jsonArray.stream().map( emp -> parseEmployeeObject( (JSONObject) emp ) ).distinct().collect(Collectors.toList());
			
		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
		
	}
	
	private static long parseEmployeeObject(JSONObject employee) 
    {
        //Get employee object within list
        
        //Get employee first name
        long userId = (long) employee.get("userId");    
        System.out.println(userId);
         
        //Get employee last name
        long id = (long) employee.get("id");  
        System.out.println(id);
         
        //Get employee website name
        String title = (String) employee.get("title");    
        System.out.println(title);
        
        String body = (String) employee.get("body");    
        System.out.println(body);
    return userId;
    }

	private static JSONObject modifyObject(JSONObject employee) 
    {
        //Get employee object within list
        JSONObject user = new JSONObject();
        //Get employee first name
        long userId = (long) employee.get("userId");    
        System.out.println(userId);
        user.put("userId",userId);
         
        //Get employee last name
        long id = (long) employee.get("id");  
        System.out.println(id);
        user.put("d",id);;
        //Get employee website name
        String title = (String) employee.get("title");    
        System.out.println(title);
        user.put("title","1800Flowers");
        user.put("body","1800Flowers");
        
        return user;
        
    }

  	
   @RequestMapping(value="/list", method= RequestMethod.POST)
   public ResponseEntity <ListParams> getList(@RequestBody ListParams parms) throws Exception{
	 	
	   System.out.println("Sravani");
	   parms.setStatusMessage("suceesfully responded");
	   parms.setUniqueValues(ChallangeController.readJsonData());
	   
	   return new ResponseEntity<ListParams>(parms,HttpStatus.OK);
	}
   
   



@RequestMapping(value="/modiftyObject", method= RequestMethod.POST)
public ResponseEntity <ListParams> modifyObject(@RequestBody ListParams parms) throws Exception{
	  JSONParser jsonParser = new JSONParser();
			JSONArray jsonArray =  (JSONArray) jsonParser.parse(new FileReader("C://Users//91918//Downloads//1800flowers//src//main//resources//data.json"));
		
	   System.out.println(""+ChallangeController.modifyObject((JSONObject) jsonArray.get(parms.getObjectIndex())));
	   parms.setStatusMessage("suceesfully responded");
	   parms.setOriginal(jsonArray.get(parms.getObjectIndex()).toString());
	   
	   parms.setModified(ChallangeController.modifyObject((JSONObject) jsonArray.get(parms.getObjectIndex())).toString());
	   
	   return new ResponseEntity<ListParams>(parms,HttpStatus.OK);
	}


}
