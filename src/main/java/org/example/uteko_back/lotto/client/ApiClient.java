package org.example.uteko_back.lotto.client;

import org.example.uteko_back.lotto.domain.Lotto;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Component
public class ApiClient {
    private static final Logger log = LoggerFactory.getLogger(ApiClient.class);

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private static final String API_URL = "https://www.dhlottery.co.kr/common.do?method=getLottoNumber&drwNo={drwNo}";
    private static final LocalDate FIRST_DRAW_DATE = LocalDate.of(2002, 12, 7);

    public ApiClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.objectMapper = new ObjectMapper();
    }

    public WinningLotto getLatestWinningLotto() {
        long latestDrwNo = calculateLatestDrwNo();
        log.info("계산된 최신 회차: {}회", latestDrwNo);

        LottoApiResponse response = callApi(latestDrwNo);

        if (response == null || !"success".equals(response.getReturnValue())) {
            log.info("{}회차 결과 없음. 이전 회차 조회 시도", latestDrwNo);
            latestDrwNo--;
            response = callApi(latestDrwNo);
        }

        if (response == null) {
            log.error("API 호출 실패: 응답이 null입니다.");
            throw new RuntimeException("로또 API 호출에 실패했습니다. 네트워크 연결을 확인해주세요.");
        }

        if (!"success".equals(response.getReturnValue())) {
            log.error("API 응답 실패: returnValue={}", response.getReturnValue());
            throw new RuntimeException("최신 당첨 번호 조회에 실패했습니다. (응답: " + response.getReturnValue() + ")");
        }

        log.info("{}회차 당첨 번호 조회 성공", latestDrwNo);
        return response.toWinningLotto();
    }

    private LottoApiResponse callApi(long drwNo) {
        try {
            log.info("API 호출 시작: {}회차", drwNo);

            String responseBody = restTemplate.getForObject(API_URL, String.class, drwNo);

            if (responseBody == null || responseBody.isEmpty()) {
                log.warn("API 응답이 비어있습니다.");
                return null;
            }

            log.info("API 원본 응답: {}", responseBody.substring(0, Math.min(200, responseBody.length())));

            LottoApiResponse response = objectMapper.readValue(responseBody, LottoApiResponse.class);

            if (response != null) {
                log.info("API 응답 성공: returnValue={}", response.getReturnValue());
            }

            return response;

        } catch (Exception e) {
            log.error("API 호출 중 예외 발생: {}", e.getMessage(), e);
            return null;
        }
    }

    private long calculateLatestDrwNo() {
        return ChronoUnit.WEEKS.between(FIRST_DRAW_DATE, LocalDate.now()) + 1;
    }

    public static class WinningLotto {
        private final Lotto winningLotto;
        private final int bonusNumber;

        public WinningLotto(List<Integer> numbers, int bonus) {
            this.winningLotto = new Lotto(numbers);

            if (bonus < 1 || bonus > 45) {
                throw new IllegalArgumentException("보너스 번호는 1~45 사이여야 합니다.");
            }
            if (this.winningLotto.contains(bonus)) {
                throw new IllegalArgumentException("보너스 번호는 당첨 번호와 중복될 수 없습니다.");
            }
            this.bonusNumber = bonus;
        }

        public Lotto getWinningLotto() { return winningLotto; }
        public int getBonusNumber() { return bonusNumber; }
    }
}