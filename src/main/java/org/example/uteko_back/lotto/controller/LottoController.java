package org.example.uteko_back.lotto.controller;

import org.example.uteko_back.lotto.domain.Lotto;
import org.example.uteko_back.lotto.dto.LottoCheckRequestDto;
import org.example.uteko_back.lotto.dto.LottoResultDto;
import org.example.uteko_back.lotto.service.LottoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lotto")
// uteko_front의 기본 포트 3000번에서 오는 요청을 허용(C
@CrossOrigin(origins = "http://localhost:3000")

public class LottoController {
    private final LottoService lottoService;

    public LottoController(LottoService lottoService) {
        this.lottoService = lottoService;
    }

    @PostMapping("/check")
    public ResponseEntity<List<LottoResultDto>> checkLottos(@RequestBody LottoCheckRequestDto requestDto){
        List<LottoResultDto> result = lottoService.checkLottos(requestDto.getUserLottoStrings());
        return ResponseEntity.ok(result);
    }

}
