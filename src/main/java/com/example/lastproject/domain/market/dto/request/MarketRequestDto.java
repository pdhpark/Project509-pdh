package com.example.lastproject.domain.market.dto.request;

import com.example.lastproject.domain.market.entity.Market;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MarketRequestDto {

    @NotBlank(message = "마켓 이름은 필수 입력 항목입니다.")
    private String storeName;

    @NotBlank(message = "주소는 필수 입력 항목입니다.")
    private String address;

    public Market toEntity() {
        return new Market(this.storeName, this.address);
    }
}
