package com.example.lastproject.domain.party.service;

import com.example.lastproject.common.CustomException;
import com.example.lastproject.common.ErrorCode;
import com.example.lastproject.domain.item.entity.Item;
import com.example.lastproject.domain.item.repository.ItemRepository;
import com.example.lastproject.domain.market.repository.MarketRepository;
import com.example.lastproject.domain.party.dto.request.PartyCreateRequest;
import com.example.lastproject.domain.party.dto.request.PartyUpdateRequest;
import com.example.lastproject.domain.party.dto.response.PartyResponse;
import com.example.lastproject.domain.party.entity.Party;
import com.example.lastproject.domain.party.enums.PartyStatus;
import com.example.lastproject.domain.party.repository.PartyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PartyService {
    private final PartyRepository partyRepository;
    private final MarketRepository marketRepository;
    private final ItemRepository itemRepository;

    /**
     * 파티 생성
     * @param request 파티 생성 시 필요한 정보 (마켓 이름, 마켓 주소, 거래 품목, 거래 단위, 장보기 시작&종료 시간, 파티 인원)
     * @return PartyResponse 생성된 파티 정보
     */
    @Transactional
    public PartyResponse createParty(PartyCreateRequest request) {
        Item item = itemRepository.findById(request.getItemId())
                .orElseThrow(() -> new CustomException(ErrorCode.ITEM_NOT_FOUND));

        if(request.getStartTime().isAfter(request.getEndTime())) {
            throw new CustomException(ErrorCode.INVALID_TIME_RANGE);
        }
        if(request.getMaxMembers() <=2) {
            throw new CustomException(ErrorCode.INVALID_MAX_MEMBERS);
        }

        Party party = new Party(
                request.getMarketName(),
                request.getMarketAddress(),
                item,
                request.getItemUnit(),
                request.getStartTime(),
                request.getEndTime(),
                request.getMaxMembers()
        );

        partyRepository.save(party);
        return new PartyResponse(party);
    }

    public PartyResponse getParty(Long partyId) {
        Party party = partyRepository.findById(partyId)
                .orElseThrow(() -> new CustomException(ErrorCode.PARTY_NOT_FOUND));

        if(party.getPartyStatus() == PartyStatus.DONE) {
            throw new CustomException(ErrorCode.PARTY_NOT_FOUND);
        }
        return new PartyResponse(party);
    }

    @Transactional
    public PartyResponse updateParty(Long partyId, PartyUpdateRequest request) {
        Party party = partyRepository.findById(partyId)
                .orElseThrow(() -> new CustomException(ErrorCode.PARTY_NOT_FOUND));

        if(party.getPartyStatus() == PartyStatus.DONE) {
            throw new CustomException(ErrorCode.INVALID_PARTY_STATUS);
        }

        if(request.getMaxMembers() != null) {
            party.updateMaxMembers(request.getMaxMembers());
        }
        return new PartyResponse(party);
    }
}
