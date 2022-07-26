package com.company.sportHubPortal.Repositories;

import com.company.sportHubPortal.Models.Team;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends PagingAndSortingRepository<Team, Integer> {

  Team findByName(String name);

  Team findByLatitudeAndLongitude(Double latitude, Double longitude);

  List<Team> findAllByOrderByAddedAtDesc(Pageable pageable);

  @Query("SELECT t FROM Team t WHERE t.subCategory.id = :#{#id}")
  List<Team> findAllTeamsBySubcategoryId(Long id);

  @Query("SELECT t FROM Team t LEFT JOIN FETCH t.subscribers ORDER BY t.articles.size DESC")
  List<Team> findAllByArticlesSize(Pageable pageable);

  @Query("SELECT t FROM Team t LEFT JOIN FETCH t.subscribers WHERE t = :#{#team}")
  Team findTeamWithSubscribers(Team team);
}
