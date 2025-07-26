package com.fw.core.mapper.db1.fo;

import com.fw.core.dto.fo.FoClientDTO;
import com.fw.core.dto.fo.FoExpertDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FoClientMapper {
    int selectIdCheck(String id);
    void insertClient(FoClientDTO foClientDTO);
    void insertClientTemp(FoClientDTO foClientDTO);
    void inserConsultingCategory(FoClientDTO foClientDTO);
    void inserExpertNeed(FoClientDTO foClientDTO);
    void inserConsultingMethod(FoClientDTO foClientDTO);
    void inserEtcRequest(FoClientDTO foClientDTO);
}
