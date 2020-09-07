package com.carrot.restaurant_vote.repositories;

import com.carrot.restaurant_vote.models.Vote;
import com.carrot.restaurant_vote.models.VoteCount;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

@Repository
public interface VoteRepository extends CrudRepository<Vote, Integer> {
    Iterable<Vote> findAllByUserId(Integer userId);

    Optional<Vote> findByDateAndUserId(LocalDate date, Integer userId);

    //    @Query("SELECT new com.carrot.restaurant_vote.models.VoteCount(v.menu, COUNT(v)) FROM Vote as v " +
//            "WHERE v.date = :date GROUP BY v.menu ORDER BY 2 DESC")
    @Query(nativeQuery = true, value = "SELECT v.menu_id as menuId, COUNT(v.menu_id) as count FROM votes as v " +
            "WHERE v.date = :date GROUP BY menuId ORDER BY count DESC")
    Set<VoteCount> findTotalVotesByDate(LocalDate date);

    Integer countAllByMenuId(Integer menuId);
}
