package de.applegreen.household.persistence;

import de.applegreen.household.model.GroceryList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroceryListRepository extends JpaRepository<GroceryList, Long> {

    @Query("SELECT g FROM GroceryList g WHERE g.done = FALSE AND g.current = TRUE ORDER BY createdOn DESC")
    List<GroceryList> findUndoneAndNewest();

    @Query("SELECT g FROM GroceryList g WHERE g.current = FALSE AND g.done = FALSE ORDER BY createdOn DESC")
    List<GroceryList> findNext();
}
