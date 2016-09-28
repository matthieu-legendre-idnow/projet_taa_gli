package fr.istic.repository;

import fr.istic.domain.Partenaire;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Partenaire entity.
 */
@SuppressWarnings("unused")
public interface PartenaireRepository extends JpaRepository<Partenaire,Long> {

}
