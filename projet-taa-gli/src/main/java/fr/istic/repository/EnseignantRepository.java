package fr.istic.repository;

import fr.istic.domain.Enseignant;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Enseignant entity.
 */
@SuppressWarnings("unused")
public interface EnseignantRepository extends JpaRepository<Enseignant,Long> {

}
