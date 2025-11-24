package org.example.uteko_back.lotto.dto;

import java.util.List;

public class WinningNumbersDto {
    private List<Integer> winningNumbers;
    private int bonusNumber;
    private long drwNo;
    private long firstPrize;

    public WinningNumbersDto(List<Integer> winningNumbers, int bonusNumber, long drwNo, long firstPrize) {
        this.winningNumbers = winningNumbers;
        this.bonusNumber = bonusNumber;
        this.drwNo = drwNo;
        this.firstPrize = firstPrize;
    }

    public List<Integer> getWinningNumbers() { return winningNumbers; }
    public int getBonusNumber() { return bonusNumber; }
    public long getDrwNo() { return drwNo; }
    public long getFirstPrize() { return firstPrize; }
}