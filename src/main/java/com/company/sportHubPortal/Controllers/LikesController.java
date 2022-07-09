package com.company.sportHubPortal.Controllers;

import com.company.sportHubPortal.Models.Likes;
import com.company.sportHubPortal.Services.LikesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/likes")
public class LikesController {
    private final LikesService likesService;

    public LikesController(LikesService likesService) {
        this.likesService = likesService;
    }

    @GetMapping()
    public List<Likes> getAllLikes() {
        return likesService.getAllLikes();
    }

    @GetMapping("/{id}")
    public Likes getById(@PathVariable Long id) {
        return likesService.getById(id);
    }

    @PostMapping()
    public ResponseEntity<Likes> saveLikes(@RequestBody Likes likes) {
        return ResponseEntity.ok(likesService.saveLikes(likes));
    }

    @DeleteMapping("/{id}")
    public void deleteLikes(@PathVariable Long id) {
        likesService.deleteLikes(id);
    }


}
