package com.company.sportHubPortal.Services;

import com.company.sportHubPortal.Database.Team;
import com.company.sportHubPortal.Repositories.TeamRepository;
import org.hibernate.JDBCException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@Service
public class TeamService {

    private TeamRepository teamRepository;

    @Autowired
    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public void saveTeam(Team team) {
        teamRepository.save(team);
    }

    public Team teamByName(String name){
        return teamRepository.findByName(name);
    }

    public List<Team> allTeams(Integer count, Integer page){
        return teamRepository.findAllByOrderByAddedAtDesc(PageRequest.of(page - 1, count));
    }

    public List<Team> allTeams(){
        return (List<Team>) teamRepository.findAll();
    }

    public Long teamsCount(){
        return teamRepository.count();
    }

    public Team teamByCoordinates(Double latitude, Double longitude) {
        return teamRepository.findByLatitudeAndLongitude(latitude, longitude);
    }
}
