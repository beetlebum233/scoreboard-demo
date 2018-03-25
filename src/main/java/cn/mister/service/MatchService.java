package cn.mister.service;

import cn.mister.dao.MatchDao;
import cn.mister.dao.PlayerDao;
import cn.mister.dao.ScoreItemDao;
import cn.mister.entity.Match;
import cn.mister.entity.Player;
import cn.mister.entity.ScoreItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class MatchService {
    @Autowired
    private MatchDao matchDao;
    @Autowired
    private PlayerDao playerDao;
    @Autowired
    private ScoreItemDao scoreItemDao;

    public List<Match> findAll(){
        return matchDao.findAll();
    }

    public void deleteById(Integer id){
        matchDao.deleteById(id);
    }

    @Transactional(propagation= Propagation.REQUIRED,rollbackFor=Exception.class)
    public void add(Integer homePlayerId, Integer homeGoal, Integer awayPlayerId, Integer awayGoal){
        Player homePlayer = playerDao.findById(homePlayerId);
        Player awayPlayer = playerDao.findById(awayPlayerId);
        Integer homeGoalDiff = homeGoal - awayGoal;
        ScoreItem homeItem = new ScoreItem();
        ScoreItem awayItem = new ScoreItem();
        if(homeGoalDiff > 0){
            homePlayer.setScore(homePlayer.getScore() + 3);
            homeItem.setScore(3);
            awayItem.setScore(0);
        }else if(homeGoalDiff == 0 ){
            homePlayer.setScore(homePlayer.getScore() + 1);
            awayPlayer.setScore(awayPlayer.getScore() + 1);
            homeItem.setScore(1);
            awayItem.setScore(1);
        }else if(homeGoalDiff < 0){
            awayPlayer.setScore(awayPlayer.getScore() + 3);
            homeItem.setScore(0);
            awayItem.setScore(3);
        }

        homePlayer.setGoal(homePlayer.getGoal() + homeGoal);
        awayPlayer.setGoal(awayPlayer.getGoal() + awayGoal);
        homePlayer.setGoalDifference(homePlayer.getGoalDifference() + homeGoalDiff);
        awayPlayer.setGoalDifference(awayPlayer.getGoalDifference() - homeGoalDiff);
        playerDao.update(homePlayer);
        playerDao.update(awayPlayer);

        Match match = new Match();
        StringBuilder sb = new StringBuilder();
        sb.append(homePlayer.getName());
        sb.append(" ");
        sb.append(homeGoal);
        sb.append("-");
        sb.append(awayGoal);
        sb.append(" ");
        sb.append(awayPlayer.getName());
        match.setResult(sb.toString());
        match.setCreatedTime(new Date().toString());

        matchDao.insert(match);

        homeItem.setPlayerId(homePlayer.getId());
        homeItem.setMatchId(match.getId());
        homeItem.setGoal(homeGoal);
        homeItem.setGoalDifference(homeGoalDiff);

        awayItem.setPlayerId(awayPlayer.getId());
        awayItem.setMatchId(match.getId());
        awayItem.setGoal(awayGoal);
        awayItem.setGoalDifference(-homeGoalDiff);

        scoreItemDao.insert(homeItem);
        scoreItemDao.insert(awayItem);
    }

    @Transactional(propagation= Propagation.REQUIRED,rollbackFor=Exception.class)
    public void delete(Integer matchId){
        Match match = matchDao.findById(matchId);
        if(match == null){
            return;
        }
        List<ScoreItem> list = scoreItemDao.find(matchId);

        if(list.size() > 0){
            for(ScoreItem item : list){
                Player player = playerDao.findById(item.getPlayerId());
                player.setGoalDifference(player.getGoalDifference() - item.getGoalDifference());
                player.setGoal(player.getGoal() - item.getGoal());
                player.setScore(player.getScore() - item.getScore());
                playerDao.update(player);
            }
        }
        matchDao.deleteById(matchId);
    }

}
