package com.fw.core.mapper.db1.fo;

import com.fw.core.dto.fo.FoPaymentDTO;
import com.fw.core.dto.fo.FoPaymentDescDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FoPaymentMapper {
    void registerPayment(FoPaymentDTO foPaymentDTO);
    void registerPaymentDesc(FoPaymentDescDTO foPaymentDescDTO);
    String getIlnoForRegisterPayment();
    String getSnnoForRegisterPaymentDesc();
}
