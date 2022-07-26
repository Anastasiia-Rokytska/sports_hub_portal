package com.company.sportHubPortal.Services;

import com.company.sportHubPortal.Models.Team;
import com.company.sportHubPortal.Repositories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamService {

    private final TeamRepository teamRepository;

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

    public Team teamById(Integer id){
        return teamRepository.findById(id).orElse(null);
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
    public List<Team> teamBySubcategory(Long id){
        return teamRepository.findAllTeamsBySubcategoryId(id);
    }

    public List<Team> allTeamsByArticlesSize(Integer page) {
        return teamRepository.findAllByArticlesSize(PageRequest.of(page - 1, 5));
    }

    public Team getTeamWithSubscribers(Team team) {
        return teamRepository.findTeamWithSubscribers(team);
    }
}
