package org.example.uteko_back.lotto.client;

import java.util.*;
import org.example.uteko_back.lotto.domain.Lotto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LottoApiResponse {
    private String returnValue;
    private Integer drwtNo1,drwtNo2,drwtNo3,drwtNo4,drwtNo5,drwtNo6;
    private Integer bnusNo;

    public String getReturnValue() {return  returnValue;}
    public void setReturnValue(String returnValue) {this.returnValue = returnValue;}
    public Integer getDrwtNo1() {return drwtNo1;}
    public void setDrwtNo1(Integer drwtNo1) {this.drwtNo1 = drwtNo1;}
    public Integer getDrwtNo2() {return drwtNo2;}
    public void setDrwtNo2(Integer drwtNo2) {this.drwtNo2 = drwtNo2;}
    public Integer getDrwtNo3() {return drwtNo3;}
    public void setDrwtNo3(Integer drwtNo3) {this.drwtNo3 = drwtNo3;}
    public Integer getDrwtNo4() {return drwtNo4;}
    public void setDrwtNo4(Integer drwtNo4) {this.drwtNo4 = drwtNo4;}
    public Integer getDrwtNo5() {return drwtNo5;}
    public void setDrwtNo5(Integer drwtNo5) {this.drwtNo5 = drwtNo5;}
    public Integer getDrwtNo6() {return drwtNo6;}
    public void setDrwtNo6(Integer drwtNo6) {this.drwtNo6 = drwtNo6;}
    public Integer getBnusNo() {return bnusNo;}
    public void setBnusNo(Integer bnusNo) {this.bnusNo = bnusNo;}

    public ApiClient.WinningLotto toWinningLotto() {
        if (!"success".equals(this.returnValue)) {
            throw new RuntimeException("API 응답실패: " + this.returnValue);
        }
        List<Integer> numbers = List.of(drwtNo1, drwtNo2, drwtNo3, drwtNo4, drwtNo5, drwtNo6);
        return new ApiClient.WinningLotto(numbers, this.bnusNo);
    }
}