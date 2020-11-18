package de.applegreen.household.persistence;

import de.applegreen.household.model.ListElement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ListElementRepository extends JpaRepository<ListElement, Long> {
}
