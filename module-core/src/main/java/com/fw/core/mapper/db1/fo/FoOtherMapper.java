package com.fw.core.mapper.db1.fo;

import com.fw.core.dto.fo.FoNoticeBoardDTO;
import com.fw.core.dto.fo.FoNoticeDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FoOtherMapper {
    List<FoNoticeDTO> selectNoticeListMain(FoNoticeDTO foNoticeDTO);
    List<FoNoticeBoardDTO> selectNoticeBoardListMain(FoNoticeBoardDTO foNoticeBoardDTO);
    List<FoNoticeDTO> selectNoticeListEnMain(FoNoticeDTO foNoticeDTO);
    List<FoNoticeBoardDTO> selectNoticeBoardListEnMain(FoNoticeBoardDTO foNoticeBoardDTO);
    List<FoNoticeDTO> selectNoticeList(FoNoticeDTO foNoticeDTO);
    int selectNoticeListCnt(FoNoticeDTO foNoticeDTO);
    List<FoNoticeBoardDTO> selectNoticeBoardList(FoNoticeBoardDTO foNoticeBoardDTO);
    int selectNoticeBoardListCnt(FoNoticeBoardDTO foNoticeBoardDTO);
    List<FoNoticeDTO> selectNoticeListEn(FoNoticeDTO foNoticeDTO);
    int selectNoticeListEnCnt(FoNoticeDTO foNoticeDTO);
    List<FoNoticeBoardDTO> selectNoticeBoardListEn(FoNoticeBoardDTO foNoticeBoardDTO);
    int selectNoticeBoardListEnCnt(FoNoticeBoardDTO foNoticeBoardDTO);
    FoNoticeBoardDTO selectNoticeBoardDetail(String qnaId);
    FoNoticeBoardDTO selectNoticeBoardDetailEn(String qnaId);
    void insertNoticeBoard(FoNoticeBoardDTO foNoticeBoardDTO);
    String selectPasswordById(@Param("qnaId") Integer qnaId);
    void updateViewCount(@Param("qnaId") String qnaId);
    FoNoticeDTO selectNoticeDetail(String noticeId);
    FoNoticeDTO selectNoticeDetailEn(String noticeId);
    void updatePasswordByWebId(@Param("custNo") String custNo, @Param("webPw") String webPw);
}
