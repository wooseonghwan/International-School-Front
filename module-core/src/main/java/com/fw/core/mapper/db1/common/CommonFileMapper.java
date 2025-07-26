package com.fw.core.mapper.db1.common;

import com.fw.core.dto.FileDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommonFileMapper {

	/** tb_file insert */
	void insertFile(FileDTO fileDTO);

	void insertFileSeq(FileDTO fileDTO);

	/** tb_detail_file insert */
	void insertFileDetail(FileDTO fileDTO);

	/** tb_detail_file delete */
	void updateFileDetail(FileDTO fileDTO);

	/** tb_detail_file delete /  use id */
	void updateFile(FileDTO fileDTO);

	/** tb_detail_file list */
	List<FileDTO> selectFileDetailList(String fileSeq);

	/** tb_detail_file list */
	FileDTO selectFileSeq(String fileId);

}