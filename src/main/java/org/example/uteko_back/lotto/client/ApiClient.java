package org.example.uteko_back.lotto.client;
import org.example.uteko_back.lotto.domain.Lotto;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Component
public class ApiClient{
    private final RestTemplate restTemplate;
    private static final String API_URL = "https://www.dhlottery.co.kr/common.do?method=getLottoNumber&drwNo={drwNo}";
    private static final LocalDate FIRST_DRAW_DATE = LocalDate.of(2002, 12, 7); // 1회차 추첨일

    public ApiClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public WinningLotto getLatestWinningLotto() {
        long latestDrwNo = calculateLatestDrwNo();
        LottoApiResponse response = callApi(latestDrwNo);

        // API 결과가 아직 없으면(fail) 이전 회차를 조회
        if (response == null || !"success".equals(response.getReturnValue())) {
            latestDrwNo--;
            response = callApi(latestDrwNo);
        }

        if (response == null || !"success".equals(response.getReturnValue())) {
            throw new RuntimeException("최신 당첨 번호 조회에 실패했습니다.");
        }
        return response.toWinningLotto();
    }

    private LottoApiResponse callApi(long drwNo) {
        try {
            return restTemplate.getForObject(API_URL, LottoApiResponse.class, drwNo);
        } catch (Exception e) {
            return null; // 네트워크 오류 등
        }
    }

    // 오늘 날짜 기준 최신 회차 계산
    private long calculateLatestDrwNo() {
        return ChronoUnit.WEEKS.between(FIRST_DRAW_DATE, LocalDate.now()) + 1;
    }

    // Service 레이어가 의존하는 내부 클래스
    public static class WinningLotto {
        private final Lotto winningLotto;
        private final int bonusNumber;

        public WinningLotto(List<Integer> numbers, int bonus) {
            this.winningLotto = new Lotto(numbers); // Lotto.java의 유효성 검사 재사용

            // 보너스 번호 유효성 검사
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
