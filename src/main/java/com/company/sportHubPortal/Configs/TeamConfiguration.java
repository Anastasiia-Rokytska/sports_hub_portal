package com.company.sportHubPortal.Configs;

import com.company.sportHubPortal.Database.Team;
import com.company.sportHubPortal.Services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Arrays;

@Configuration
public class TeamConfiguration {

    private TeamService teamService;

    @Autowired
    public TeamConfiguration(TeamService teamService) {
        this.teamService = teamService;
    }

    @Bean
    public void addTeams() throws IOException, SQLException {
        String pathToConfigImages = "src/main/resources/static/assets/images/configs/team/";
        teamService.saveTeam(new Team("Los Angeles Lakers", "Los Angeles, California",
                34.052235, -118.243683,
                new SerialBlob(new FileInputStream(pathToConfigImages + "LosAngelesLakers.png").readAllBytes())));
        teamService.saveTeam(new Team("Houston Rockets", "Houston, Texas",
                29.749907, -95.358421,
                new SerialBlob(new FileInputStream(pathToConfigImages + "HoustonRockets.png").readAllBytes())));
        teamService.saveTeam(new Team("Memphis Grizzlies", "Memphis, Tennessee",
                35.117500, -89.971107,
                new SerialBlob(new FileInputStream(pathToConfigImages + "MemphisGrizzlies.png").readAllBytes())));
        teamService.saveTeam(new Team("Utah Jazz", "Salt Lake City, Utah",
                40.758701, -111.876183,
                new SerialBlob(new FileInputStream(pathToConfigImages + "UtahJazz.png").readAllBytes())));
        teamService.saveTeam(new Team("Los Angeles Lakers1", "Los Angeles, California",
                25.0, 44.0,
                new SerialBlob(new FileInputStream(pathToConfigImages + "LosAngelesLakers.png").readAllBytes())));
        teamService.saveTeam(new Team("Houston Rockets1", "Houston, Texas",
                22.0, 18.0,
                new SerialBlob(new FileInputStream(pathToConfigImages + "HoustonRockets.png").readAllBytes())));
        teamService.saveTeam(new Team("Memphis Grizzlies1", "Memphis, Tennessee",
                59.0, 67.0,
                new SerialBlob(new FileInputStream(pathToConfigImages + "MemphisGrizzlies.png").readAllBytes())));
        teamService.saveTeam(new Team("Utah Jazz1", "Salt Lake City, Utah",
                -23.0, -55.0,
                new SerialBlob(new FileInputStream(pathToConfigImages + "UtahJazz.png").readAllBytes())));
        teamService.saveTeam(new Team("Los Angeles Lakers0", "Los Angeles, California",
                34.435, -118.8983,
                new SerialBlob(new FileInputStream(pathToConfigImages + "LosAngelesLakers.png").readAllBytes())));
        teamService.saveTeam(new Team("Houston Rockets0", "Houston, Texas",
                -29.35907, 95.8421,
                new SerialBlob(new FileInputStream(pathToConfigImages + "HoustonRockets.png").readAllBytes())));
        teamService.saveTeam(new Team("Memphis Grizzlies0", "Memphis, Tennessee",
                -35.0, 89.7,
                new SerialBlob(new FileInputStream(pathToConfigImages + "MemphisGrizzlies.png").readAllBytes())));
        teamService.saveTeam(new Team("Utah Jazz0", "Salt Lake City, Utah",
                30.701, 111.7883,
                new SerialBlob(new FileInputStream(pathToConfigImages + "UtahJazz.png").readAllBytes())));
        teamService.saveTeam(new Team("Los Angeles Lakers10", "Los Angeles, California",
                33.0, 55.0,
                new SerialBlob(new FileInputStream(pathToConfigImages + "LosAngelesLakers.png").readAllBytes())));
        teamService.saveTeam(new Team("Houston Rockets10", "Houston, Texas",
                12.0, 67.0,
                new SerialBlob(new FileInputStream(pathToConfigImages + "HoustonRockets.png").readAllBytes())));
        teamService.saveTeam(new Team("Memphis Grizzlies10", "Memphis, Tennessee",
                74.0, 34.0,
                new SerialBlob(new FileInputStream(pathToConfigImages + "MemphisGrizzlies.png").readAllBytes())));
        teamService.saveTeam(new Team("Utah Jazz10", "Salt Lake City, Utah",
                -23.124, -45.666,
                new SerialBlob(new FileInputStream(pathToConfigImages + "UtahJazz.png").readAllBytes())));
    }
}