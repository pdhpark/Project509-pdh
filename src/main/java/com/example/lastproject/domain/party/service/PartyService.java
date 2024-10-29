package com.example.lastproject.domain.party.service;

import com.example.lastproject.common.CustomException;
import com.example.lastproject.common.enums.ErrorCode;
import com.example.lastproject.domain.item.entity.Item;
import com.example.lastproject.domain.item.repository.ItemRepository;
import com.example.lastproject.domain.market.repository.MarketRepository;
import com.example.lastproject.domain.party.dto.request.PartyCreateRequest;
import com.example.lastproject.domain.party.dto.request.PartyUpdateRequest;
import com.example.lastproject.domain.party.dto.response.PartyResponse;
import com.example.lastproject.domain.party.entity.Party;
import com.example.lastproject.domain.party.enums.PartyStatus;
import com.example.lastproject.domain.party.repository.PartyRepository;
import com.example.lastproject.domain.partymember.entity.PartyMember;
import com.example.lastproject.domain.partymember.enums.PartyMemberRole;
import com.example.lastproject.domain.partymember.repository.PartyMemberRepository;
import com.example.lastproject.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PartyService {

    private final PartyRepository partyRepository;
    private final MarketRepository marketRepository;
    private final ItemRepository itemRepository;
    private final PartyMemberRepository partyMemberRepository;
    private final UserRepository userRepository;

    /**
     * 파티 생성
     * @param request 파티 생성 시 필요한 정보 (마켓 이름, 마켓 주소, 거래 품목, 거래 단위, 장보기 시작&종료 시간, 파티 인원)
     * @return PartyResponse 생성된 파티 정보
     */
    @Transactional
    public PartyResponse createParty(PartyCreateRequest request, Long userId) {

        // 거래 품목 조회
        Item item = itemRepository.findById(request.getItemId())
                .orElseThrow(() -> new CustomException(ErrorCode.ITEM_NOT_FOUND));

        // 품목 개수 확인
        if(request.getItemCount() < 1) {
            throw new CustomException(ErrorCode.INVALID_ITEM_COUNT);
        }

        // 시간 검증
        if(request.getStartTime().isAfter(request.getEndTime())) {
            throw new CustomException(ErrorCode.INVALID_TIME_RANGE);
        }

        // 인원 수 검증
        if(request.getMembersCount() < 1) {
            throw new CustomException(ErrorCode.INVALID_MEMBERS_COUNT);
        }

        // 파티 생성
        Party party = new Party(
                request.getMarketName(),
                request.getMarketAddress(),
                item,
                request.getItemUnit(),
                request.getItemCount(),
                request.getStartTime(),
                request.getEndTime(),
                request.getMembersCount()
        );

        // 파티 저장
        partyRepository.save(party);

        // 파티 멤버 리더 역할
        PartyMember partyMember = new PartyMember(
                userRepository.findById(userId)
                        .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND)),
                party,
                PartyMemberRole.LEADER // 생성 시 자동으로 리더(파티장)로 설정
        );

        // 파티 멤버 저장
        partyMemberRepository.save(partyMember);

        // 생성된 파티 정보 반환
        return new PartyResponse(party);
    }

    // 파티 조회
    @Transactional
    public List<PartyResponse> getAllActiveParties() {
        List<Party> parties = partyRepository.findAllByPartyStatus(PartyStatus.OPEN);
        return parties.stream()
                .map(PartyResponse::new)
                .collect(Collectors.toList());
    }

    // 파티 수정
    @Transactional
    public PartyResponse updateParty(Long partyId, PartyUpdateRequest request, Long userId) {
        // 파티 조회
        Party party = partyRepository.findById(partyId)
                .orElseThrow(() -> new CustomException(ErrorCode.ITEM_NOT_FOUND));

        // 파티장 확인
        PartyMember partyLeader = partyMemberRepository.findByPartyIdAndUserId(partyId, userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_PARTY_LEADER));

        // 거래 품목 업데이트
        Item item = itemRepository.findById(request.getItemId())
                .orElseThrow(() -> new CustomException(ErrorCode.ITEM_NOT_FOUND));

        // 수정할 정보 업데이트
        party.updateDetails(item, request.getItemUnit(), request.getItemCount(), request.getStartTime(), request.getEndTime(), request.getMembersCount());

        return new PartyResponse(party);
    }

    // 장보기 완료: 파티 상태를 DONE으로 변경
    @Transactional
    public void completeParty(Long partyId) {
        Party party = partyRepository.findById(partyId)
                .orElseThrow(() -> new CustomException(ErrorCode.PARTY_NOT_FOUND));

        party.completeParty();
    }

    // 파티 취소: 파티 상태를 CANCELED로 변경
    @Transactional
    public void cancelParty(Long partyId) {
        Party party = partyRepository.findById(partyId)
                .orElseThrow(() -> new CustomException(ErrorCode.PARTY_NOT_FOUND));

        party.cancelParty();
    }

}
