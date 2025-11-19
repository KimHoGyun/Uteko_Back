package org.example.uteko_back.lotto.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.example.uteko_back.lotto.domain.Rank;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class LottoResultDto {
    private String userLottoNumbers; //
    private Rank rank;
    private long prize;
    private String errorMessage;

    public LottoResultDto(String userLottoNumbers,Rank rank,long prize,String errorMessage) {
        this.userLottoNumbers = userLottoNumbers;
        this.rank = rank;
        this.prize = prize;
        this.errorMessage = errorMessage;
    }
    public String getUserLottoNumbers() {return userLottoNumbers;}
    public Rank getRank() {return rank;}
    public long getPrize() {return prize;}
    public String getErrorMessage() {return errorMessage;}
}
