package com.example.lastproject.domain.item.dto.request;

import com.example.lastproject.domain.item.entity.Item;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ItemRequestDto {
    @NotBlank(message = "카테고리는 필수 입력 항목입니다.")
    private String category;

    @NotBlank(message = "품목명은 필수 입력 항목입니다.")
    private String productName;

    public Item toEntity(){
        return new Item(this.category,this.productName);
    }
}
