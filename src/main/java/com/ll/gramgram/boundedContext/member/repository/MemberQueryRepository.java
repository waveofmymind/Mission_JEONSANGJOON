package com.ll.gramgram.boundedContext.member.repository;

import com.ll.gramgram.boundedContext.member.entity.Member;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.ll.gramgram.boundedContext.instaMember.entity.QInstaMember.instaMember;
import static com.ll.gramgram.boundedContext.member.entity.QMember.member;

@Repository
@RequiredArgsConstructor
public class MemberQueryRepository {

    private final JPAQueryFactory queryFactory;

    public Optional<Member> findMemberByUsernameWithInstaMember(String username) {
        return Optional.ofNullable(queryFactory
                .selectFrom(member)
                .join(member.instaMember, instaMember)
                .fetchJoin()
                .where(member.username.eq(username))
                .fetchFirst());
    }

    public Optional<Member> findMemberByIdWithInstaMember(Long userId) {
        return Optional.ofNullable(queryFactory
                .selectFrom(member)
                .join(member.instaMember, instaMember)
                .fetchJoin()
                .where(member.id.eq(userId))
                .fetchFirst());
    }


}
