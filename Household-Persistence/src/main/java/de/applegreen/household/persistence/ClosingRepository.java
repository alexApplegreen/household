package de.applegreen.household.persistence;

import de.applegreen.household.model.Closing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClosingRepository extends JpaRepository<Closing, Long> {

    @Query("SELECT c from Closing c WHERE c.month = :month AND c.year = :year")
    public List<Closing> findRecent(@Param("month") int month, @Param("year") int year);
}
