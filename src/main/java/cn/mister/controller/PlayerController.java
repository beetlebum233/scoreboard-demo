package cn.mister.controller;

import cn.mister.entity.Player;
import cn.mister.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/player")
public class PlayerController {
    @Autowired
    private PlayerService playerService;

    @RequestMapping("/findByGroupId")
    @ResponseBody
    public List<Player> findByGroupId(Integer groupId){
        return playerService.findByGroupId(groupId);
    }

    @RequestMapping("/findAll")
    @ResponseBody
    public List<Player> findAll(){
        return playerService.findAll();
    }
}
