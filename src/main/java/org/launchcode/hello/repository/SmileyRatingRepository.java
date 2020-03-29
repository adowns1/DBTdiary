package org.launchcode.hello.repository;

import org.launchcode.hello.domain.SmileyRating;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the SmileyRating entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SmileyRatingRepository extends JpaRepository<SmileyRating, Long> {

    @Query("select smileyRating from SmileyRating smileyRating where smileyRating.user.login = ?#{principal.username}")
    List<SmileyRating> findByUserIsCurrentUser();

}
