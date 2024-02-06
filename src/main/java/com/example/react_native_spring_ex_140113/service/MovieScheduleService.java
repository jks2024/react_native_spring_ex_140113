package com.example.react_native_spring_ex_140113.service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class MovieScheduleService {
    private final RestTemplate restTemplate;
    private final MovieService movieService;
    private final ObjectMapper objectMapper;

    @Scheduled(cron = "0 0 0/1 * * *") // 매 시간마다 실행
    //@Scheduled(cron = "0 * * * * *") // 매분마다 실행
    public void movieSchedule() throws JsonProcessingException {
        movieService.deleteAll();
        String URL = "http://127.0.0.1:5000/api/movie";
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(URL, String.class);
            String movieDataJson = response.getBody();
            System.out.println(movieDataJson);
            List<Map<String, String>> movieList = objectMapper.readValue(movieDataJson, new TypeReference<List<Map<String, String>>>() {});
            movieService.processAndSaveMovieData(movieList);
        } catch (Exception e) {
            log.error("영화 데이터 수집 실패");
        }
    }


}
