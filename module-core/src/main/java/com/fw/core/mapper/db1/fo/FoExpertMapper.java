package com.fw.core.mapper.db1.fo;

import com.fw.core.dto.fo.FoExpertDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FoExpertMapper {
    int selectIdCheck(String id);
    void inserExpert(FoExpertDTO foExpertDTO);
    void inserExpertTemp(FoExpertDTO foExpertDTO);
    void inserExpertCareer(FoExpertDTO foExpertDTO);
    void inserAdvisory(FoExpertDTO foExpertDTO);
    void insertJobCategoryDetail(FoExpertDTO foExpertDTO);
}
