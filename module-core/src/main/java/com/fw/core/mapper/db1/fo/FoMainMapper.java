package com.fw.core.mapper.db1.fo;

import com.fw.core.dto.fo.FoGolfPriceDTO;
import com.fw.core.dto.fo.FoReservationDTO;
import com.fw.core.dto.fo.FoUserDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FoMainMapper {
    int countByCardId(@Param("cardId") String cardId);
    int countByWebId(@Param("webId") String webId);
    int getTodayUserSeq(@Param("today") String today); // 오늘날짜에 해당하는 일련번호
    int insertUser(FoUserDTO dto);
    FoUserDTO selectUserByCredentials(FoUserDTO foUserDTO);
}
