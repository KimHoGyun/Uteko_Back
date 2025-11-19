package org.example.uteko_back.lotto.dto;

import java.util.*;

public class LottoCheckRequestDto {
    private List<String> userLottoStrings;

    public List<String> getUserLottoStrings() {
        return userLottoStrings;
    }

    public void setUserLottoStrings(List<String> userLottoStrings) {
        this.userLottoStrings = userLottoStrings;
    }
}
