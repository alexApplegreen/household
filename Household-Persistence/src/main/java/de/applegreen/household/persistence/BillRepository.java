package de.applegreen.household.persistence;

import de.applegreen.household.model.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {

    @Query("SELECT b FROM Bill b WHERE b.user = :username AND b.month = :month")
    List<Bill> findAllByUser(@Param("username") String username, @Param("month") int month);
}
