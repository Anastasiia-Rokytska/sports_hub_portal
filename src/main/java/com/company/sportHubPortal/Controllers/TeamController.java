package com.company.sportHubPortal.Controllers;
import com.company.sportHubPortal.Models.Category;
import com.company.sportHubPortal.Models.Team;
import com.company.sportHubPortal.Models.User;
import com.company.sportHubPortal.POJO.TeamPOJO;
import com.company.sportHubPortal.Security.CustomUserDetails;
import com.company.sportHubPortal.Services.CategoryServices.CategoryService;
import com.company.sportHubPortal.Services.TeamService;
import com.company.sportHubPortal.Services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/team")
public class TeamController {

    private final TeamService teamService;
    private final CategoryService categoryService;
    private final UserService userService;
    Logger logger = LoggerFactory.getLogger(TeamController.class);

    @Autowired
    public TeamController(TeamService teamService,
                          CategoryService categoryService,
                          UserService userService) {
        this.teamService = teamService;
        this.categoryService = categoryService;
        this.userService = userService;
    }

    @PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<Object> addNewTeam(@ModelAttribute TeamPOJO teamPOJO,
                                             @RequestPart String selectedCategory,
                                             @RequestPart String selectedSubCategory) {
        Team team;
        try {
            team = new Team(teamPOJO.getName(), teamPOJO.getLocation(), teamPOJO.getLatitude(), teamPOJO.getLongitude());
            if (!(teamPOJO.getIcon() == null)) team.setIcon(teamPOJO.getIcon());
        } catch (Exception exception) {
            logger.info(exception.getLocalizedMessage());
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body("Incorrect input data");
        }
        team.setAddedAt();
        if (teamService.teamByName(team.getName()) != null) {
            logger.warn("Team name must be unique: " + team);
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body("Team with name \"" + team.getName() + "\" already exists");
        } else if (teamService.teamByCoordinates(team.getLatitude(), team.getLongitude()) != null) {
            logger.warn("Latitude and longitude must be unique: " + team);
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body("Team with latitude " + team.getLatitude() + " and longitude " + team.getLongitude() + " already exists");
        }
        if (!selectedCategory.equals("All")) {
            Category category = categoryService.getCategoryByName(selectedCategory);
            if (category != null) team.setCategory(categoryService.getCategoryByName(selectedCategory));
        }
        if (!selectedSubCategory.equals("All")) {
            Category subcategory = categoryService.getCategoryByName(selectedSubCategory);
            if (subcategory != null) team.setSubCategory(categoryService.getCategoryByName(selectedSubCategory));
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
        return teamService.allTeams(count, page);
    }

    @GetMapping("/count")
    public ResponseEntity<Object> getTeamCount(){
        return ResponseEntity.ok().body(teamService.teamsCount());
    }

    @GetMapping("/locations")
    public ResponseEntity<Object> getAllLocations() {
        Set<String> locations = new HashSet<>();
        List<Team> allTeams = teamService.allTeams();
        allTeams.forEach(team -> locations.add(team.getLocation()));
        return ResponseEntity.ok().body(locations);
    }

    @GetMapping("/subscriptions")
    public ResponseEntity<Set<Team>> getUserSubscriptions() {
        UserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(userService.getUserWithSubscriptions(userDetails.getUsername()).getSubscriptions());
    }

    @GetMapping("/{subcategory}")
    public ResponseEntity<List<Team>> getTeamsBySubcategory(@PathVariable Long subcategory) {
        System.out.println(teamService.teamBySubcategory(subcategory));
        return ResponseEntity.ok(teamService.teamBySubcategory(subcategory));
    }

    @GetMapping("/team-hub/{page}")
    public @ResponseBody List<Team> allTeamsByArticlesSize(
            @PathVariable Integer page
    ) {
        return teamService.allTeamsByArticlesSize(page);
    }

    @GetMapping("/subscribe/{id}")
    public ResponseEntity<Object> addSubscriber(
            @PathVariable Integer id
    ) {
        UserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.getByEmail(userDetails.getUsername());
        Team team = teamService.teamById(id);
        if (team == null) return ResponseEntity.notFound().build();
        if (team.getSubscribers().contains(user)) return ResponseEntity.badRequest().build();
        user.addSubscription(team);
        userService.save(user);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/unsubscribe/{id}")
    public ResponseEntity<Object> deleteSubscriber(
            @PathVariable Integer id
    ) {
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        UserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(userDetails.getUsername());
        User user = userService.getByEmail(userDetails.getUsername());
        Team team = teamService.teamById(id);
        if (team == null) return ResponseEntity.notFound().build();
        if (!team.getSubscribers().contains(user)) return ResponseEntity.badRequest().build();
        user.removeSubscription(team);
        userService.save(user);
        return ResponseEntity.ok().build();
    }
}
