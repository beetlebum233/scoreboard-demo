package cn.mister.controller;

import cn.mister.entity.Match;
import cn.mister.service.MatchService;
import cn.mister.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/match")
public class MatchController {
    @Autowired
    private MatchService matchService;
    @Autowired
    private PlayerService playerService;

    @RequestMapping("/add")
    @ResponseBody
    public String add(Integer homePlayerId, Integer homeGoal, Integer awayPlayerId, Integer awayGoal){
        matchService.add(homePlayerId, homeGoal, awayPlayerId, awayGoal);
        return "success";
    }

    @RequestMapping("/delete")
    @ResponseBody
    public String delete(Integer matchId){
        matchService.delete(matchId);
        return "success";
    }

    @RequestMapping("/findAll")
    @ResponseBody
    public List<Match> findAll(){
        return matchService.findAll();
    }
}
