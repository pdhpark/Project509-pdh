package com.example.lastproject.domain.item.batch;

import com.example.lastproject.config.ApiDataReader;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ApiDataReaderTest {

    @Mock
    RestTemplate restTemplate;

    @Mock
    ObjectMapper objectMapper;

    @InjectMocks
    ApiDataReader apiDataReader;

    @BeforeEach
    void setUp() throws NoSuchMethodException {
        // api 설정값 주입(공통값)
        ReflectionTestUtils.setField(apiDataReader, "apiKey", "apikey");
        ReflectionTestUtils.setField(apiDataReader, "apiPort", "apiPort");
        ReflectionTestUtils.setField(apiDataReader, "apiUrl", "apiUrl");
    }

    /**
     * api 요청 응답 테스트
     */
    @Test
    @DisplayName("품목업데이트 API 요청 테스트")
    void 정상적으로_Json_데이터를_가져온다() throws Exception {

        // given
        String jsonData = "{\"data\":\"jsonData\"}";
        given(restTemplate.getForObject(any(URI.class), eq(String.class))).willReturn(jsonData);
        given(objectMapper.readTree(jsonData)).willReturn(new ObjectMapper().readTree(jsonData));

        // when
        String result = apiDataReader.read();

        // then
        assertEquals(jsonData, result, "Json 문자열 데이터 반환 성공");
    }

    /**
     * api 요청시 총페이지수에 맞게 요청이 반복되는지 테스트
     */
    @Test
    @DisplayName("read 메서드 호출회수 테스트")
    void Reader가_예상수만큼_요청되는지_확인한다() throws Exception {

        // given
        String jsonData = "{\"apiUrl\":{\"totalCnt\":10000}}"; // 총 페이지수
        given(restTemplate.getForObject(any(URI.class), eq(String.class))).willReturn(jsonData);
        given(objectMapper.readTree(jsonData)).willReturn(new ObjectMapper().readTree(jsonData));

        // when
        int count = 0;
        // 총 페이지수 범위만큼 요청을 보내는지 카운트
        while (apiDataReader.read() != null) {
            count++;
        }

        // then
        assertEquals(10, count);  // 10000개의 데이터, 1000개씩 10회 호출 예상
    }

}
