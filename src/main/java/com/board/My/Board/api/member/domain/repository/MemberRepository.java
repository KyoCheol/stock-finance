package com.board.seochu.finance.api.member.domain.repository;

import com.board.seochu.finance.api.member.domain.entity.Members;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Members, Long> {

    Optional<Members> findByEmail(String email);

    @Query(value =
            "SELECT COUNT(*) " +
                  "FROM MEMBERS " +
                  "WHERE EMAIL = :email ", nativeQuery = true)
    Integer countByEmail(@Param("email") String email);

    @Query(value =
            "select count(*) " +
                    "from members " +
                    "where mobile = :mobile ", nativeQuery = true)
    Integer countByMobile(@Param("mobile") String mobile);
}
