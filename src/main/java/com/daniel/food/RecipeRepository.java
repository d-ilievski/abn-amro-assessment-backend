package com.daniel.food;



import org.springframework.data.domain.Page;	
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

public interface RecipeRepository extends PagingAndSortingRepository<Recipe, Long> {
    
    @Query(value = "select * from recipes where name like %?1% order by id desc", nativeQuery = true)
    Page<Recipe> searchResult(Pageable pageable, String searchText);
    
    @Modifying
    @Transactional
    @Query(value = "update Recipe r set r.hasImage = 1 where r.id = ?1")
    void updateHasImage(long id);
    
}
