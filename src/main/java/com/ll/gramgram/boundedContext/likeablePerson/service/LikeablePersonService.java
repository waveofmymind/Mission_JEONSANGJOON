package com.ll.gramgram.boundedContext.likeablePerson.service;

import com.ll.gramgram.base.error.ErrorCode;
import com.ll.gramgram.base.error.exception.DuplicateUserException;
import com.ll.gramgram.base.error.exception.ExceedLikeablePersonLimitException;
import com.ll.gramgram.base.error.exception.UnAuthorizedException;
import com.ll.gramgram.base.rsData.RsData;
import com.ll.gramgram.boundedContext.instaMember.entity.InstaMember;
import com.ll.gramgram.boundedContext.instaMember.service.InstaMemberService;
import com.ll.gramgram.boundedContext.likeablePerson.entity.LikeablePerson;
import com.ll.gramgram.boundedContext.likeablePerson.repository.LikeablePersonRepository;
import com.ll.gramgram.boundedContext.member.entity.Member;
import com.ll.gramgram.boundedContext.member.service.MemberService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static com.ll.gramgram.base.error.ErrorCode.*;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class LikeablePersonService {
    private final LikeablePersonRepository likeablePersonRepository;
    private final InstaMemberService instaMemberService;

    @Transactional
    public RsData<LikeablePerson> like(Member member, String username, int attractiveTypeCode) {
        if (!member.hasConnectedInstaMember()) {
            return RsData.of(NOT_CONNECT.getResultCode(), NOT_CONNECT.getMessage());
        }
        if (member.getInstaMember().getUsername().equals(username)) {
            return RsData.of(SELF_LIKE_NOT_ALLOWED.getResultCode(), SELF_LIKE_NOT_ALLOWED.getMessage());
        }
        //10명이 넘으면 리턴
        if (member.getInstaMember().getLikeablePersonCount() >= 10) {
            return RsData.of(MAX_LIKEABLE_PERSON.getResultCode(), MAX_LIKEABLE_PERSON.getMessage());
        }

        Optional<LikeablePerson> findLikeablePerson = likeablePersonRepository.findByToInstaMember_UsernameAndFromInstaMember_Username(username, member.getInstaMember().getUsername());

        if (findLikeablePerson.isPresent()) {
            LikeablePerson existingLikeablePerson = findLikeablePerson.get();
            //존재할경우 사유를 비교한다.
            if (existingLikeablePerson.getAttractiveTypeCode() != attractiveTypeCode) {
                existingLikeablePerson.updateLikeablePerson(attractiveTypeCode);
            } else {
                //사유도 동일할 때에는 예외 발생
                throw new DuplicateUserException(ErrorCode.DUPLICATE_USER_LIKE);
            }
        } else {
            //새로운 호감 표시일 경우 등록한다.
            InstaMember toInstaMember = instaMemberService.findByUsernameOrCreate(username).getData();
            InstaMember fromInstaMember = member.getInstaMember();
            LikeablePerson likeablePerson = LikeablePerson
                    .builder()
                    .fromInstaMember(fromInstaMember)
                    .fromInstaMemberUsername(fromInstaMember.getUsername())
                    .toInstaMember(toInstaMember)
                    .toInstaMemberUsername(toInstaMember.getUsername())
                    .attractiveTypeCode(attractiveTypeCode)
                    .build();
            toInstaMember.addFromLikeablePerson(likeablePerson);
            fromInstaMember.addToLikeablePerson(likeablePerson);
            fromInstaMember.increaseLikeablePersonCount();
            likeablePersonRepository.save(likeablePerson);

            return RsData.of("S-1", "입력하신 인스타유저(%s)를 호감상대로 등록되었습니다.".formatted(username), likeablePerson);
        }
        //반드시 LikeablePerson이 존재하므로 get()을 통해 값 빼오기
        return RsData.of("S-2", "입력하신 인스타유저(%s)의 호감 사유가 변경되었습니다.".formatted(username), findLikeablePerson.get());
    }

    public List<LikeablePerson> findByFromInstaMemberId(Long fromInstaMemberId) {
        return likeablePersonRepository.findByFromInstaMemberId(fromInstaMemberId);
    }

    public LikeablePerson findlikeablePersonById(Long likeablePersonId) {
        return likeablePersonRepository.findById(likeablePersonId)
                .orElseThrow(EntityNotFoundException::new);
    }


    @Transactional
    public void deleteLikeablePerson(Long likeablePersonId) {

        likeablePersonRepository.deleteById(likeablePersonId);

    }

}
