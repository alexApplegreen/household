package de.applegreen.household.persistence;

import de.applegreen.household.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author [ATE] Alexander Tepe | alexander.tepe@lmis.de
 **/
public interface UserRepository extends JpaRepository<User, Long> {
}
