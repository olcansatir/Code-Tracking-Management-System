package com.mergentech.ctms.services;
import org.json.simple.parser.JSONParser;
import java.io.*;
import java.util.ArrayList;
import java.util.Stack;
import org.json.simple.*;

public class scriptRunner
{
    // Variables
    public JSONArray output = new JSONArray();
    ArrayList<String> parsed = new ArrayList<String>();

    private String pythonPath;
    public String getPath() {return this.pythonPath;}
    public void setPath(String path){this.pythonPath = path;}

    private String Results = "";
    public String getResults() {return this.Results;}
    private void setResults(String res){this.Results += res;}

    // Converts JSON string to JSON object
    private JSONObject stringToJSON(String jsonString)
    {
        JSONObject json = new JSONObject();
        JSONParser parser = new JSONParser();
        try{ 
            json = (JSONObject) parser.parse(jsonString);
        }
        catch(org.json.simple.parser.ParseException e){
            System.out.println("Error parsing JSON");
            }
        return json;
    }

    //private JSONArray stringToJSONarr(String jsonString)
    //{
    //    JSONArray json = new JSONArray();
    //    JSONParser parser = new JSONParser();
    //    try{ 
    //        json = (JSONArray) parser.parse(jsonString);
    //    }
    //    catch(org.json.simple.parser.ParseException e){
    //        System.out.println("Error parsing JSON");
    //        }
    //    return json;
    //}

    // returns JSON String representation of the JSON object
    //private String JSONToString(JSONObject jsonobjeObject)
    //{
    //    return jsonobjeObject.toString();
    //}

    // Utility function to convert from perceval format into JSON format
    public void ParseString(String allValues){
	    Stack<Character> stack = new Stack<Character>();
	    String temp="";

    	for(int i=0;i<allValues.length();i++){
	        
            // change ' character to " and true, false, none into json style
            if((i+3)<allValues.length()){
                if(allValues.charAt(i) == 'N' && allValues.charAt(i+1)=='o' && allValues.charAt(i+2)=='n' && allValues.charAt(i+3)=='e') {temp+="\"NULL\""; i += 4;}}
            if((i+3)<allValues.length()){
                if(allValues.charAt(i) == 'T' && allValues.charAt(i+1)=='r' && allValues.charAt(i+2)=='u' && allValues.charAt(i+3)=='e') {temp+="\"TRUE\"";i += 4;}}
            if((i+4)<allValues.length()){
                if(allValues.charAt(i) == 'F' && allValues.charAt(i+1)=='a' && allValues.charAt(i+2)=='l' && allValues.charAt(i+3)=='s' && allValues.charAt(i+4)=='e' ) {temp+="\"FALSE\"";i += 5;}}
            if(allValues.charAt(i) == '\'') {
                temp += "\"";
            }
            else{
                temp+=allValues.charAt(i);
            }

            // adds it to our parsed list
	        if(allValues.charAt(i)=='{'){
		        stack.push('{');
	        }
	        else if(allValues.charAt(i)=='}'){
		        stack.pop();
		        if(stack.isEmpty()){
		            parsed.add(temp);
		            temp="";
	            }
	        }
        }
    }
    
    public JSONObject getJSONofResult()
    {
        return stringToJSON(Results);
    }

    // runs python script and returns the result
    public void runPythonCode() throws IOException
    {
        String path = "python " + "CTMS/src/main/java/com/mergentech/ctms/services/pythonScripts/ImportData.py";
        String[] commandsStrings = new String[]{"cmd.exe","/c",path};
        Process pythonProcess = Runtime.getRuntime().exec(commandsStrings);

        // to read output of python script
        BufferedReader stdInput = new BufferedReader(new 
        InputStreamReader(pythonProcess.getInputStream()));

        // to read errors of python script
        BufferedReader stdError = new BufferedReader(new 
        InputStreamReader(pythonProcess.getErrorStream()));

        String s = null;
        // read inputs
        while ((s = stdInput.readLine()) != null) {
            setResults(s);
        }
        // read errors if any
        while ((s = stdError.readLine()) != null) {
            setResults(s);
        }
        // Utility function to convert from perceval format into JSON format
        ParseString(Results);
        
        // adds all the JSON objects into a JSON Array
        for(int i = 0; i< parsed.size(); i++){
            output.add(stringToJSON(parsed.get(i)));
        }
    }
}
