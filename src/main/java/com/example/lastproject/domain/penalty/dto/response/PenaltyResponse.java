package com.example.lastproject.domain.penalty.dto.response;

import com.example.lastproject.common.enums.CustomMessage;
import lombok.Getter;

@Getter
public class PenaltyResponse {

    private final String msg;

    public PenaltyResponse() {
        this.msg = CustomMessage.PENALTY_SEND_SUCCESS.getMessage();
    }

}
