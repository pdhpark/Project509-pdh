package com.example.lastproject.domain.party.controller;


import com.example.lastproject.domain.party.dto.request.PartyCreateRequest;
import com.example.lastproject.domain.party.dto.response.PartyResponse;
import com.example.lastproject.domain.party.service.PartyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/parties")
public class PartyController {

    private final PartyService partyService;

    public PartyController(PartyService partyService) {
        this.partyService = partyService;
    }

    /**
     *파티 생성 메소드
     * @param partyCreateRequest 파티 생성 시 필요한 json body
     *        (marketName: 마켓 이름, marketAddress: 마켓 주소, itemId: 거래 품목, itemUnit: 거래 단위,
     *         startTime: 장보기 시작 시간, endTime: 장보기 종료 시간, maxMembers: 파티 인원)
     * @return ResponseEntity<PartyResponse>로 생성된 파티 정보 반환, HTTP 상태 코드 201 반환
     */
    @PostMapping
    public ResponseEntity<PartyResponse> createParty(
            @RequestBody PartyCreateRequest partyCreateRequest) {
        PartyResponse partyResponse = partyService.createParty(partyCreateRequest);
        return new ResponseEntity<>(partyResponse, HttpStatus.CREATED);
    }
}
