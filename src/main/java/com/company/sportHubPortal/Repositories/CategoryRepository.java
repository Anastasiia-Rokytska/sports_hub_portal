package com.company.sportHubPortal.Repositories;

import com.company.sportHubPortal.Database.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findAllByParentId(Long parentId);
    List<Category> findAllByParentIdNot(Long parentId);
    Category findByName(String name);
}
