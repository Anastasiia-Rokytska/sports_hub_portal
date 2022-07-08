package com.company.sportHubPortal.Repositories;

import com.company.sportHubPortal.Database.Team;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamRepository extends PagingAndSortingRepository<Team, Integer> {

    Team findByName(String name);
    Team findByLatitudeAndLongitude(Double latitude, Double longitude);
    List<Team> findAllByOrderByAddedAtDesc(Pageable pageable);
}
