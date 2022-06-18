package com.company.sportHubPortal.Controllers;
import com.company.sportHubPortal.Database.Team;
import com.company.sportHubPortal.Services.TeamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/team")
public class TeamController {

    private TeamService teamService;
    Logger logger = LoggerFactory.getLogger(TeamController.class);

    @Autowired
    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<Object> addNewTeam(@ModelAttribute Team team) {
        team.setAddedAt();
        logger.info(team.toString());
//        logger.info(teamService.teamByCoordinates(team.getLatitude(), team.getLongitude()).toString());
        if (teamService.teamByName(team.getName()) != null) {
            logger.warn("Team name must be unique: " + team);
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body("Team with name \"" + team.getName() + "\" already exists");
        } else if (teamService.teamByCoordinates(team.getLatitude(), team.getLongitude()) != null) {
            logger.warn("Latitude and longitude must be unique: " + team);
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body("Team with latitude " + team.getLatitude() + " and longitude " + team.getLongitude() + " already exists");
        }
        teamService.saveTeam(team);
        logger.info("Success: " + team);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @GetMapping("/{count}/{page}")
    public @ResponseBody List<Team> allTeams(
            @PathVariable Integer count,
            @PathVariable Integer page
    ) {
        List<Team> teams = teamService.allTeams(count, page);
        return teamService.allTeams(count, page);
    }

    @GetMapping("/count")
    public ResponseEntity<Object> getTeamCount(){
        return ResponseEntity.ok().body(teamService.teamsCount());
    }
}
