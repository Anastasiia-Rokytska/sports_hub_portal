package com.company.sportHubPortal.Repositories;

import com.company.sportHubPortal.Models.Category;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

  List<Category> findAllByParentId(Long parentId);

  List<Category> findAllByParentIdNot(Long parentId);

  Category findByName(String name);
}
