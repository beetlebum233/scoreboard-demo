package cn.mister.dao;

import cn.mister.entity.ScoreItem;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ScoreItemDao {
    List<ScoreItem> find(Integer matchId);

    void insert(ScoreItem scoreItem);
}
