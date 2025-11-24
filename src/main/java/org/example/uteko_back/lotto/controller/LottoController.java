package org.example.uteko_back.lotto.controller;

import org.example.uteko_back.lotto.dto.LottoCheckRequestDto;
import org.example.uteko_back.lotto.dto.LottoCheckResponseDto;
import org.example.uteko_back.lotto.service.LottoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/lotto")
@CrossOrigin(origins = "http://localhost:3000")
public class LottoController {
    private final LottoService lottoService;

    public LottoController(LottoService lottoService) {
        this.lottoService = lottoService;
    }

    @PostMapping("/check")
    public ResponseEntity<LottoCheckResponseDto> checkLottos(@RequestBody LottoCheckRequestDto requestDto){
        LottoCheckResponseDto response = lottoService.checkLottos(requestDto.getUserLottoStrings());
        return ResponseEntity.ok(response);
    }
}