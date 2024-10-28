package com.example.lastproject.domain.item.dto.request;

import com.example.lastproject.domain.item.entity.Item;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ItemRequestDto {
    @NotBlank(message = "카테고리는 필수 입력 항목입니다.")
    private String category;

    public Item toEntity(){
        return new Item(this.category);
    }
}
