package org.example.uteko_back.lotto.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.example.uteko_back.lotto.domain.Rank;

@JsonInclude(JsonInclude.Include.NON_NULL) //에러 메시지가 널일경우 제외해주는 옵션
public class LottoResultDto {
    private String userLottoNumbers; //
    private Rank rank;
    private long prize;
    private String errorMessage;

    public LottoResultDto(String userLottoNumbers,Rank rank,long prize,String errorMessage) { //각 로또 결과값을 인증해주는 함수
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
