package cn.mister.dao;

import cn.mister.entity.Player;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface PlayerDao {
    List<Player> findByGroupId(Integer groupId);

    List<Player> findAll();

    void update(Player player);

    Player findById(Integer id);
}
