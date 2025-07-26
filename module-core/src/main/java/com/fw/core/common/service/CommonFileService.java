package com.fw.core.common.service;

import com.fw.core.dto.FileDTO;
import com.fw.core.mapper.db1.common.CommonFileMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommonFileService {

    @Value("${file.upload.path}")
    private String FILE_UPLOAD_PATH;

    @Value("${service.upload.path}")
    private String SERVICE_UPLOAD_PATH;

    private final CommonFileMapper commonFileMapper;

    public Integer fileUpload(MultipartFile[] files) throws IOException {
        Integer rtnValue = null;

        if (files != null && files.length != 0) {
            FileDTO fd = FileDTO.builder().build();
            commonFileMapper.insertFile(fd);
            rtnValue =  Integer.parseInt(fd.getFileSeq());

            // 오늘날짜를 기준으로 폴더 생성
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
            String today = dateFormat.format(new Date());
            String folderPath = FILE_UPLOAD_PATH + File.separator + today;

            File folder = new File(folderPath);
            if (!folder.exists()) { // 폴더가 존재하지 않을경우 새로생성
                folder.mkdirs();
            }

            for (MultipartFile m : files) {

                if (m.isEmpty()) {
                    continue;
                }

                String origName = m.getOriginalFilename();
                String uuid = UUID.randomUUID().toString();
                String ext = Objects.requireNonNull(origName).toLowerCase().substring(origName.lastIndexOf(".")); // .ext
                String savedExt = Objects.requireNonNull(origName).toLowerCase().substring(origName.lastIndexOf(".") + 1); // ext
                String savedName = uuid + ext;

                String savedPath = folderPath + File.separator + savedName;

                String width = null;
                String height = null;
                int isImage = 0;

                List<String> imageExtensions = Arrays.asList("jpeg", "png", "jpg", "gif", "bmp");
                if(imageExtensions.contains(savedExt)) {
                    BufferedImage bimg = ImageIO.read(m.getInputStream());
                    width = String.valueOf(bimg.getWidth());
                    height = String.valueOf(bimg.getHeight());
                    isImage = 1;
                }

                m.transferTo(new File(savedPath));

                FileDTO fileDTO = FileDTO.builder().originName(origName)
                        .path(SERVICE_UPLOAD_PATH + "/" + today + "/" + savedName)
                        .fileSeq(fd.getFileSeq())
                        .size(String.valueOf(m.getSize()))
                        .name(savedName)
                        .ext(savedExt)
                        .width(width)
                        .height(height)
                        .isImage(String.valueOf(isImage))
                        .build();

                commonFileMapper.insertFileDetail(fileDTO);
            }
        }
        return rtnValue;
    }

    // 공통 파일 업로드 (파일 교체)
    public void fileUpdate(MultipartFile[] files, String rtnValue) throws Exception {
        if(files != null && files.length != 0){

            // 오늘날짜를 기준으로 폴더 생성
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
            String today = dateFormat.format(new Date());
            String folderPath = FILE_UPLOAD_PATH + File.separator + today;

            File folder = new File(folderPath);
            if (!folder.exists()) { // 폴더가 존재하지 않을경우 새로생성
                folder.mkdirs();
            }

            for(MultipartFile m : files){
                String origName = m.getOriginalFilename();
                String uuid = UUID.randomUUID().toString();
                String ext = Objects.requireNonNull(origName).toLowerCase().substring(origName.lastIndexOf(".")); // .ext
                String savedExt = Objects.requireNonNull(origName).toLowerCase().substring(origName.lastIndexOf(".") + 1); // ext
                String savedName = uuid + ext;

                String savedPath = folderPath + File.separator + savedName;

                String width = null;
                String height = null;
                int isImage = 0;

                List<String> imageExtensions = Arrays.asList("jpeg", "png", "jpg", "gif", "bmp");
                if(imageExtensions.contains(savedExt)) {
                    BufferedImage bimg = ImageIO.read(m.getInputStream());
                    width = String.valueOf(bimg.getWidth());
                    height = String.valueOf(bimg.getHeight());
                    isImage = 1;
                }

                m.transferTo(new File(savedPath));

                FileDTO fileDTO = FileDTO.builder().originName(origName)
                        .path(SERVICE_UPLOAD_PATH + "/" + today + "/" + savedName)
                        .fileSeq(rtnValue)
                        .size(String.valueOf(m.getSize()))
                        .name(savedName)
                        .ext(savedExt)
                        .width(width)
                        .height(height)
                        .isImage(String.valueOf(isImage))
                        .build();

                commonFileMapper.insertFileDetail(fileDTO);
            }
        }
    }

    public List<FileDTO> selectFileDetailList(String fileSeq) {
        return commonFileMapper.selectFileDetailList(fileSeq);
    }

    public void updateFileDetail(String fileSeq){
        FileDTO fileDTO = new FileDTO();
        fileDTO.setFileSeq(fileSeq);
        commonFileMapper.updateFileDetail(fileDTO);
    }

    public void updateFile(FileDTO fileDTO){
        commonFileMapper.updateFile(fileDTO);
    }

    /*public void deleteFile(String fileName) {
        log.info("delete target file name :: {}", fileName);
        try {
            File file = new File(FILE_UPLOAD_PATH + fileName);
            if (file.exists()) {
                if (file.delete()) {
                    log.info("delete success");
                } else {
                    log.info("error occured");
                }
            } else {
                log.info("target file doesn't exists");
            }
        } catch (Exception e) {
            log.error("error", e);
        }
    }*/
}
