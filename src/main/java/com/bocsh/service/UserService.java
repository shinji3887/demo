package com.bocsh.service;

import org.springframework.stereotype.Service;

@Service
public class UserService {

    public String getTellerName(Long id){

        if(id == 2222){
            return "{\"name\":\"Alice\",\"age\":25,\"school\":\"市三女中\"}";
        }
        else {
            return "Tom";
        }
    }
}
