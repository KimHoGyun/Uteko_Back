package org.example.uteko_back.lotto.service;

import org.example.uteko_back.lotto.client.ApiClient;
import org.example.uteko_back.lotto.domain.Lotto;
import org.example.uteko_back.lotto.domain.Rank;
import org.example.uteko_back.lotto.dto.LottoCheckResponseDto;
import org.example.uteko_back.lotto.dto.LottoResultDto;
import org.example.uteko_back.lotto.dto.WinningNumbersDto;
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

    public LottoCheckResponseDto checkLottos(List<String> userLottoStrings) {
        ApiClient.WinningLotto latestWinning = apiClient.getLatestWinningLotto();
        Lotto winningLotto = latestWinning.getWinningLotto();
        int bonusNumber = latestWinning.getBonusNumber();
        long firstPrize = latestWinning.getFirstPrize();
        long drwNo = latestWinning.getDrwNo();

        // 당첨번호 정보 생성
        List<Integer> winningNumbersList = latestWinning.getWinningNumbersList();
        WinningNumbersDto winningNumbersDto = new WinningNumbersDto(
                winningNumbersList,
                bonusNumber,
                drwNo,
                firstPrize
        );

        // 사용자 로또 검사
        List<LottoResultDto> results = userLottoStrings.stream()
                .map(lottoString -> {
                    try {
                        Lotto userLotto = parseLottoString(lottoString);
                        int matchCount = userLotto.countMatch(winningLotto);
                        boolean bonusMatch = userLotto.contains(bonusNumber);
                        Rank rank = Rank.valueOf(matchCount, bonusMatch);
                        long prize = rank.getPrizeMoney(firstPrize);

                        return new LottoResultDto(lottoString, rank, prize, null);
                    } catch (IllegalArgumentException e) {
                        return new LottoResultDto(lottoString, Rank.MISS, 0L, e.getMessage());
                    }
                })
                .collect(Collectors.toList());

        return new LottoCheckResponseDto(winningNumbersDto, results);
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