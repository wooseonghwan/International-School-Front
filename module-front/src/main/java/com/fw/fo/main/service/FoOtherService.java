package com.fw.fo.main.service;

import com.fw.core.dto.fo.FoNoticeBoardDTO;
import com.fw.core.dto.fo.FoNoticeDTO;
import com.fw.core.mapper.db1.fo.FoOtherMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class FoOtherService {
    private final FoOtherMapper foOtherMapper;

    public List<FoNoticeDTO> selectNoticeListMain(FoNoticeDTO foNoticeDTO){
        return foOtherMapper.selectNoticeListMain(foNoticeDTO);
    }
    public List<FoNoticeBoardDTO> selectNoticeBoardListMain(FoNoticeBoardDTO foNoticeBoardDTO){
        return foOtherMapper.selectNoticeBoardListMain(foNoticeBoardDTO);
    }
    public List<FoNoticeDTO> selectNoticeListEnMain(FoNoticeDTO foNoticeDTO){
        return foOtherMapper.selectNoticeListEnMain(foNoticeDTO);
    }
    public List<FoNoticeBoardDTO> selectNoticeBoardListEnMain(FoNoticeBoardDTO foNoticeBoardDTO){
        return foOtherMapper.selectNoticeBoardListEnMain(foNoticeBoardDTO);
    }
    public List<FoNoticeDTO> selectNoticeList(FoNoticeDTO foNoticeDTO){
        return foOtherMapper.selectNoticeList(foNoticeDTO);
    }
    public int selectNoticeListCnt(FoNoticeDTO foNoticeDTO){
        return foOtherMapper.selectNoticeListCnt(foNoticeDTO);
    }
    public List<FoNoticeBoardDTO> selectNoticeBoardList(FoNoticeBoardDTO foNoticeBoardDTO){
        return foOtherMapper.selectNoticeBoardList(foNoticeBoardDTO);
    }
    public int selectNoticeBoardListCnt(FoNoticeBoardDTO foNoticeBoardDTO){
        return foOtherMapper.selectNoticeBoardListCnt(foNoticeBoardDTO);
    }
    public List<FoNoticeDTO> selectNoticeListEn(FoNoticeDTO foNoticeDTO){
        return foOtherMapper.selectNoticeListEn(foNoticeDTO);
    }
    public int selectNoticeListEnCnt(FoNoticeDTO foNoticeDTO){
        return foOtherMapper.selectNoticeListEnCnt(foNoticeDTO);
    }
    public List<FoNoticeBoardDTO> selectNoticeBoardListEn(FoNoticeBoardDTO foNoticeBoardDTO){
        return foOtherMapper.selectNoticeBoardListEn(foNoticeBoardDTO);
    }
    public int selectNoticeBoardListEnCnt(FoNoticeBoardDTO foNoticeBoardDTO){
        return foOtherMapper.selectNoticeBoardListEnCnt(foNoticeBoardDTO);
    }
    public FoNoticeBoardDTO selectNoticeBoardDetail(String qnaId){
        return foOtherMapper.selectNoticeBoardDetail(qnaId);
    }
    public FoNoticeBoardDTO selectNoticeBoardDetailEn(String qnaId){
        return foOtherMapper.selectNoticeBoardDetailEn(qnaId);
    }
    public void increaseViewCount(String qnaId) {
        foOtherMapper.updateViewCount(qnaId);
    }
    public void insertNoticeBoard(FoNoticeBoardDTO foNoticeBoardDTO){
        foOtherMapper.insertNoticeBoard(foNoticeBoardDTO);
    }
    public boolean checkPassword(FoNoticeBoardDTO foNoticeBoardDTO) {
        String savedPassword = foOtherMapper.selectPasswordById(foNoticeBoardDTO.getQnaId());
        return savedPassword != null && savedPassword.equals(foNoticeBoardDTO.getPassword());
    }
    public FoNoticeDTO selectNoticeDetail(String noticeId){
        return foOtherMapper.selectNoticeDetail(noticeId);
    }
    public FoNoticeDTO selectNoticeDetailEn(String noticeId){
        return foOtherMapper.selectNoticeDetailEn(noticeId);
    }
}
