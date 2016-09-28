package fr.istic.repository;

import fr.istic.domain.Enquete;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Enquete entity.
 */
@SuppressWarnings("unused")
public interface EnqueteRepository extends JpaRepository<Enquete,Long> {

}
