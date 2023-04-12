package com.ll.gramgram.boundedContext.likeablePerson.repository;

import com.ll.gramgram.boundedContext.likeablePerson.entity.LikeablePerson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LikeablePersonRepository extends JpaRepository<LikeablePerson, Long> {
    List<LikeablePerson> findByFromInstaMemberId(Long fromInstaMemberId);

    boolean existsByToInstaMemberUsernameAndFromInstaMemberUsername(String toInstaMemberUsername,String FromInstaMemberUsername);

    Optional<LikeablePerson> findByToInstaMember_UsernameAndFromInstaMember_Username(String toInstaMemberUsername, String FromInstaMemberUsername);

}
