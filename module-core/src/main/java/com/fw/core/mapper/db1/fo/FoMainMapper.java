package com.fw.core.mapper.db1.fo;

import com.fw.core.dto.fo.FoGolfPriceDTO;
import com.fw.core.dto.fo.FoReservationDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FoMainMapper {
    void insertReservation(FoReservationDTO foReservationDTO);
    void insertUser(FoReservationDTO foReservationDTO);
    List<FoGolfPriceDTO> selectDateList(FoGolfPriceDTO foGolfPriceDTO);
    String selectTotalPrice(FoGolfPriceDTO foGolfPriceDTO);
}
