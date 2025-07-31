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
    public FoUserDTO getUserByCardId(String cardId) {
        return foMainMapper.selectUserByCardId(cardId);
    }

    public boolean checkDuplicateWebId(String webId) {
        return foMainMapper.countByWebId(webId) > 0;
    }
    public boolean updateUserByCustNo(FoUserDTO foUserDTO) {
        return foMainMapper.updateUserByCustNo(foUserDTO) > 0;
    }
    public FoUserDTO login(FoUserDTO foUserDTO) {
        return foMainMapper.selectUserByCredentials(foUserDTO);
    }
    public FoUserDTO getCardInfo(String cardId) {
        return foMainMapper.selectCardInfo(cardId);
    }
    public FoUserDTO findWebIdByNameAndCardId(FoUserDTO dto) {
        return foMainMapper.selectWebIdByNameAndCardId(dto);
    }
    public FoUserDTO findPassword(FoUserDTO dto) {
        return foMainMapper.findPassword(dto);
    }
}
