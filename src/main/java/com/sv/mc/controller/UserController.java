package com.sv.mc.controller;

import com.google.gson.*;
import com.sv.mc.pojo.sysUserEntity;
import com.sv.mc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLDecoder;
import java.util.Date;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    
    @GetMapping(value = "/user/index")
    public String index() {
        return "Hello world!";
    }

    @GetMapping(value = "/user/getAllUsers")
    public @ResponseBody
    List<sysUserEntity> getAllUsers() {
        List<sysUserEntity> sysUserEntityList = this.userService.findAllEntities();
        return sysUserEntityList;
    }

//    @GetMapping(value = "/user/save")
//    public void saveOrUpdateUser(@RequestBody sysUserEntity user) {
//        this.userService.saveOrUpdate(user);
//    }

    @PostMapping(value = "/user/updateUser")
    public @ResponseBody
    String updateUser(@RequestBody String gridParams) {
        System.out.println(gridParams);
        String url = new String();
        try {
            url = URLDecoder.decode(gridParams, "UTF-8");
            url = url.replaceAll("models=", "");
            //url = url.substring(1, url.length()-1);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        // Creates the json object which will manage the information received
        GsonBuilder builder = new GsonBuilder();

        // Register an adapter to manage the date types as long values
        builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
            public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                return new Date(json.getAsJsonPrimitive().getAsLong());
            }
        });

        Gson gson = builder.create();
        sysUserEntity userMap = gson.fromJson(url, sysUserEntity.class);
        System.out.println(userMap);
        return "ok";
    }
}
