package com.example.lastproject.domain.item.service;

import com.example.lastproject.common.CustomException;
import com.example.lastproject.common.ErrorCode;
import com.example.lastproject.domain.item.entity.Item;
import com.example.lastproject.domain.item.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemOpenApiService {

    @Value("${ITEM_API_KEY}")
    private String apiKey;

    private final RestTemplate restTemplate;

    private final ItemRepository itemRepository;

    private final String API_PORT = "http://211.237.50.150:7080/openapi";

    /**
     * 오픈 API 요청후 응답받은 데이터를 item 엔티티에 저장
     */
    public String getItemFromOpenApi() {

        Map<String,String> values = getApiRangeIndexParameters(10000, 1000);

        // Open Api 데이터요청이 회당 1000건 제한이 있어 반복문 처리를 위해 메서드 분리
        for(Map.Entry<String,String> value : values.entrySet()){
            saveOpenApiRequest(value.getKey(), value.getValue());
        }
        return "품목데이터 업데이트완료";
    }

    private void saveOpenApiRequest(String startIndex, String endIndex) {
        // api 키가 주입되었을때만 실행
        if (!apiKey.isBlank()) {
            try {
                // 포트 설정
                StringBuilder urlBuilder = new StringBuilder(API_PORT);
                // api 인증키
                urlBuilder.append("/" + apiKey);
                // 데이터 응답 형태
                urlBuilder.append("/" + "json");
                // api URL
                urlBuilder.append("/" + "Grid_20151207000000000328_1");
                // 요청 데이터 인덱스 시작 위치
                urlBuilder.append("/" + startIndex);
                // 요청 데이터 인덱스 종료 위치
                urlBuilder.append("/" + endIndex);
                // 설정 완료된 주소값 문자열 변환
                String url = urlBuilder.toString();

                // url 문자열 uri 변환
                URI uri = UriComponentsBuilder
                        .fromUriString(url)
                        .encode()
                        .build()
                        .toUri();
                log.info("uri = " + uri);

                // 응답값을 제이슨데이터로 받기
                log.info("API 요청 시작");
                String jsonData = restTemplate.getForObject(uri, String.class);

                // 데이터 파싱을 위한 객체생성
                JSONParser jsonParser = new JSONParser();
                JSONObject jsonResponse = (JSONObject) jsonParser.parse(jsonData);

                // 제이슨 응답데이터를 제이슨 리스트로 파싱
                JSONObject jsonBody = (JSONObject) jsonResponse.get("Grid_20151207000000000328_1");
                JSONArray jsonRowList = (JSONArray) jsonBody.get("row");
                log.info("데이터 파싱 성공");

                // 파싱한 데이터를 담을 리스트
                List<Item> items = new ArrayList<>();

                // 제이슨리스트에서 Item 엔티티객체로 파싱
                for (Object row : jsonRowList) {
                    JSONObject jsonItem = (JSONObject) row;
                    Item item = new Item().builder()
                            // 카테고리추출후 아이템 category 매핑
                            .category((String) jsonItem.get("STD_PRDLST_NM"))
                            // 상세상품명 추출후 아이템 productName 매핑
                            .productName((String) jsonItem.get("STD_SPCIES_NM"))
                            .build();
                    items.add(item);
                }

                // 파싱된 엔티티리스트 jpa 저장
                itemRepository.saveAll(items);

            } catch (Exception e) {
                throw new CustomException(ErrorCode.API_CONNECTION_ERROR);

            } finally {
                log.info("API 요청 종료");
            }
        }
        // API 키가 없을경우 예외 발생
        else {
            throw new CustomException(ErrorCode.API_KEY_NOT_FOUND);
        }
    }

    /**
     * 조회할 최대 범위값과 조회범위 텀을 얼마나 정할지 입력시 범위 파라미터를 반환
     * 예) getApiRangeIndexParameters(1000, 100)
     * => 출력 <key: 1, value: 100>, <key: 101, value: 200>,... <key: 901, value: 1000>
     */
    private Map<String, String> getApiRangeIndexParameters(int maxRange, int termLength){
        Map<String,String> values = new HashMap<>();
        for(int i = 1; i < maxRange;){
            String startIndex = String.valueOf(i);
            i = i+termLength-1;
            String endIndex = String.valueOf(i);
            i++;
            values.put(startIndex, endIndex);
        }
        return values;
    }
}



