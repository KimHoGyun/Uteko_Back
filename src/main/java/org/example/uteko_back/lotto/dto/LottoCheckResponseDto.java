package org.example.uteko_back.lotto.dto;

import java.util.List;

public class LottoCheckResponseDto {
    private WinningNumbersDto winningNumbers;
    private List<LottoResultDto> results;

    public LottoCheckResponseDto(WinningNumbersDto winningNumbers, List<LottoResultDto> results) {
        this.winningNumbers = winningNumbers;
        this.results = results;
    }

    public WinningNumbersDto getWinningNumbers() { return winningNumbers; }
    public List<LottoResultDto> getResults() { return results; }
}