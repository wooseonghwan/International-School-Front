package com.fw.core.mapper.db1.fo;

import com.fw.core.dto.fo.FoNoticeBoardDTO;
import com.fw.core.dto.fo.FoPaymentDTO;
import com.fw.core.dto.fo.FoPaymentDescDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FoPaymentMapper {
    void registerPayment(FoPaymentDTO foPaymentDTO);
    void registerPaymentDesc(FoPaymentDescDTO foPaymentDescDTO);
    String getIlnoForRegisterPayment();
    String getSnnoForRegisterPaymentDesc();
    List<FoPaymentDTO> selectPaymentChargeList(FoPaymentDTO foPaymentDTO);
    int selectPaymentChargeListCnt(FoPaymentDTO foPaymentDTO);
    List<FoPaymentDTO> selectPaymentUseList(FoPaymentDTO foPaymentDTO);
    int selectPaymentUseListCnt(FoPaymentDTO foPaymentDTO);
    List<FoPaymentDTO> getTargeListtForSyncBalance();
    void syncBalance(FoPaymentDTO foPaymentDTO);
}
