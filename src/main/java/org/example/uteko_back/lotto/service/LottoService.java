package org.example.uteko_back.lotto.service;

import org.example.uteko_back.lotto.client.ApiClient;
import org.example.uteko_back.lotto.domain.Lotto;
import org.example.uteko_back.lotto.domain.Rank;
import org.example.uteko_back.lotto.dto.LottoResultDto;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LottoService {

    private final ApiClient apiClient;

    public LottoService(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public List<LottoResultDto> checkLottos(List<String> userLottoStrings) {
        ApiClient.WinningLotto latestWinning = apiClient.getLatestWinningLotto();
        Lotto winningLotto = latestWinning.getWinningLotto();
        int bonusNumber = latestWinning.getBonusNumber();

        return userLottoStrings.stream()
                .map(lottoString -> {
                    try {
                        Lotto userLotto = parseLottoString(lottoString);

                        int matchCount = userLotto.countMatch(winningLotto);
                        boolean bonusMatch = userLotto.contains(bonusNumber);
                        Rank rank = Rank.valueOf(matchCount, bonusMatch);

                        return new LottoResultDto(lottoString, rank, rank.getPrizeMoney(), null);
                    } catch (IllegalArgumentException e) {
                        return new LottoResultDto(lottoString, Rank.MISS, 0L, e.getMessage());
                    }
                })
                .collect(Collectors.toList());
    }

    private Lotto parseLottoString(String lottoString) {
        if (lottoString == null || lottoString.isBlank()) {
            throw new IllegalArgumentException("입력값이 비어있습니다.");
        }

        try {
            List<Integer> numbers = Arrays.stream(lottoString.split(","))
                    .map(String::trim)
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());

            return new Lotto(numbers);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("로또 번호는 숫자여야 합니다.");
        }
    }
}