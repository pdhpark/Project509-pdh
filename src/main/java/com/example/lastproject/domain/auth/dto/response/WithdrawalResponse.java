package com.example.lastproject.domain.auth.dto.response;

import com.example.lastproject.common.enums.CustomMessage;
import lombok.Getter;

@Getter
public class WithdrawalResponse {

    private final String msg;

    public WithdrawalResponse(String nickname) {
        this.msg = nickname + CustomMessage.WITHDRAWAL_SUCCESS.getMessage();
    }

}
