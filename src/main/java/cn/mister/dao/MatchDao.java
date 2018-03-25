package cn.mister.dao;

import cn.mister.entity.Match;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface MatchDao {
    List<Match> findAll();

    void deleteById(Integer id);

    void insert(Match match);

    Match findById(Integer id);

}
