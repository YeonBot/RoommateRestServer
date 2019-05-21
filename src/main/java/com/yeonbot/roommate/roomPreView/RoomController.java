package com.yeonbot.roommate.roomPreView;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/room")
public class RoomController {

    @RequestMapping("/test")
    public String hello(){
        return "Hello Wrold !";
    }
}
