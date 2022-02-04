package com.mergentech.ctms;
import org.json.simple.JSONArray;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.mergentech.ctms.services.scriptRunner;

@RestController
public class testControler{
    private scriptRunner pythonExecutor = new scriptRunner();
    
    @GetMapping("/api/url") // GET request and end point of website
    @ResponseBody
    public JSONArray initizlizeData(String URL)
    {
        // Execute python script
        try {
            pythonExecutor.runPythonCode();
            return pythonExecutor.output;
        }
        catch (Exception e) {
        }
        return null;
    }
    
    @RequestMapping("/") // test mapping
    public String index(){
        return "Hello mother!";
    }
    @RequestMapping("/donthelp") // test mapping
    public String index1(){
        return "dont help!";
    }
    @RequestMapping("/help") // test mapping
    public String index2(){
        return "help!";
    }
    @RequestMapping("/help2") // test mapping
    public void index3(){
    }
}