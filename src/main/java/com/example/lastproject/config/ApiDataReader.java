package com.example.lastproject.config;

import com.example.lastproject.common.CustomException;
import com.example.lastproject.common.enums.ErrorCode;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

/**
 * BatchConfig 설정에 들어가는 클래스로
 * api 요청후 응답데이터를 받아오는 Reader 클래스
 * null 을 리턴하면 요청종료
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ApiDataReader implements ItemStreamReader<String> {

    // 환경변수 설정값이 없으면 빈 문자열을 주입
    @Value("${ITEM_API_KEY:}")
    private String apiKey;

    @Value("${API_PORT:}")
    private String apiPort;

    @Value("${API_URL:}")
    private String apiUrl;

    private int startIndex = 1;
    private int endIndex = 1000;
    private int totalPage;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public String read() throws JsonProcessingException {

        String jsonData = itemOpenApiRequest(startIndex, endIndex);

        // 처음 실행시에만 총 페이지 계산
        if (startIndex == 1) {
            JsonNode nodeTemp = objectMapper.readTree(jsonData);
            // 총 페이지수 계산
            totalPage = nodeTemp.path(apiUrl).path("totalCnt").asInt();
        }

        // 마지막 페이지에 요청 종료 및 페이지 초기화
        if (totalPage < startIndex) {
            return null;
        }

        // 다음 요청페이지 파라미터값 설정
        startIndex = startIndex + 1000;
        if(startIndex > 1) {
            endIndex = Math.min(endIndex + 1000, totalPage);
        }
        return jsonData;
    }

    private String itemOpenApiRequest(int startIndex, int endIndex) {

        // api 키가 주입되었을때만 실행
        if (!apiKey.isBlank() && !apiPort.isBlank() && !apiUrl.isBlank()) {
            try {
                // 포트 설정
                StringBuilder urlBuilder = new StringBuilder(apiPort);
                // api 인증키
                urlBuilder.append("/" + apiKey);
                // 데이터 응답 형태
                urlBuilder.append("/" + "json");
                // api URL
                urlBuilder.append("/" + apiUrl);
                // 요청 데이터 인덱스 시작 위치
                urlBuilder.append("/" + startIndex);
                // 요청 데이터 인덱스 종료 위치
                urlBuilder.append("/" + endIndex);
                // 설정 완료된 주소값 문자열 변환
                String url = urlBuilder.toString();

                // url 문자열 uri 변환
                URI uri = UriComponentsBuilder.fromUriString(url).encode().build().toUri();
                log.info("uri = " + uri);

                // 응답값을 제이슨데이터로 받기
                log.info("API 요청 시작");
                String jsonData = restTemplate.getForObject(uri, String.class);

                // Json 데이터 반환
                return jsonData;
            } catch (RestClientException e) {
                throw new CustomException(ErrorCode.API_CONNECTION_ERROR);
            } finally {
                log.info("API 요청 종료");
            }
        } else {
            throw new CustomException(ErrorCode.API_CONFIGURATION_NOT_FOUND);
        }
    }

}
