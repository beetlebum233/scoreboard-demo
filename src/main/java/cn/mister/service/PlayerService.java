package cn.mister.service;

import cn.mister.dao.PlayerDao;
import cn.mister.entity.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerService {
    @Autowired
    private PlayerDao dao;

    public List<Player> findByGroupId(Integer groupId){
        return dao.findByGroupId(groupId);
    }

    public List<Player> findAll(){
        return dao.findAll();
    }

    public void update(Player player){
        dao.update(player);
    }

}
