package com.xjz.ssmmanager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping("/{path}")
    public String toHome(@PathVariable("path") String path){
        System.out.println(path);
        return path;
    }

    @RequestMapping("/page/{parent}/{path}")
    public String toPage(@PathVariable("parent") String parent, @PathVariable("path") String path){
        String realPath = "page/" + parent + "/" + path;
        System.out.println(realPath);
        return realPath;
    }

    @RequestMapping(value = {"/"})
    public String index(){
        return "index";
    }

    /*@RequestMapping("/main")
    public String home(){
        return "main";
    }
*/
}
