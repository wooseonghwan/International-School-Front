package com.fw.fo.main.service;

import com.fw.core.dto.fo.FoUserDTO;
import com.fw.core.mapper.db1.fo.FoMainMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Slf4j
@RequiredArgsConstructor
@Service
public class FoMainService {
    private final FoMainMapper foMainMapper;
    public boolean checkDuplicateCardId(String cardId) {
        return foMainMapper.countByCardId(cardId) > 0;
    }

    public boolean checkDuplicateWebId(String webId) {
        return foMainMapper.countByWebId(webId) > 0;
    }
    public boolean registerUser(FoUserDTO dto) {
        // 중복 체크
        if (checkDuplicateWebId(dto.getWebId())) {
            throw new IllegalArgumentException("이미 사용 중인 아이디입니다.");
        }
        if (checkDuplicateCardId(dto.getCardId())) {
            throw new IllegalArgumentException("이미 등록된 카드번호입니다.");
        }

        // 고객번호 생성: 오늘날짜 + 일련번호
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyMMdd")); // e.g. "250730"
        String seq = String.format("%04d", foMainMapper.getTodayUserSeq(today));       // e.g. "0001"
        dto.setCustNo(today + seq);  // e.g. "2507300001"

        return foMainMapper.insertUser(dto) > 0;
    }
    public FoUserDTO login(FoUserDTO foUserDTO) {
        return foMainMapper.selectUserByCredentials(foUserDTO);
    }

}
