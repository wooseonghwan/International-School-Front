package com.fw.core.mapper.db1.fo;

import com.fw.core.dto.fo.FoGolfPriceDTO;
import com.fw.core.dto.fo.FoReservationDTO;
import com.fw.core.dto.fo.FoUserDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FoMainMapper {
    FoUserDTO selectUserByCardId(String cardId);
    int countByWebId(@Param("webId") String webId);
    int updateUserByCustNo(FoUserDTO foUserDTO);
    FoUserDTO selectUserByCredentials(FoUserDTO foUserDTO);
    FoUserDTO selectCardInfo(String cardId);
    FoUserDTO selectWebIdByNameAndCardId(FoUserDTO dto);
    FoUserDTO findPassword(FoUserDTO dto);
}
