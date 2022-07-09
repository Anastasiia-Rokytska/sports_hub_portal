package com.company.sportHubPortal.Services;

import com.company.sportHubPortal.Models.Likes;
import com.company.sportHubPortal.Repositories.LikesRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LikesService {

    private final LikesRepository likesRepository;

    public LikesService(LikesRepository likesRepository) {
        this.likesRepository = likesRepository;
    }

    public Likes saveLikes(Likes likes) {
        Likes temp = likesRepository.findAll().stream()
                        .filter(like -> like.getComment().getId().equals(likes.getComment().getId()))
                                .filter(like -> like.getUser().getId().equals(likes.getUser().getId()))
                                .findFirst().orElse(null);
        if (temp != null){
            temp.setLiked(!temp.isLiked());
            return likesRepository.save(temp);
        }
        else{
            return likesRepository.save(likes);
        }
    }

    public void deleteLikes(Long id) {
        likesRepository.deleteById(id);
    }

    public List<Likes> getAllLikes() {
        return likesRepository.findAll();
    }

    public Likes getById(Long id) {
        return likesRepository.findById(id).get();
    }

    public boolean isLiked(Long id) {
        return likesRepository.findById(id).get().isLiked();
    }
}
