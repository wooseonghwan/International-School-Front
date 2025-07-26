-- API 로그
CREATE TABLE tb_api_log (
	api_log_seq    BIGINT(20) UNSIGNED NOT NULL COMMENT '순서', -- 순서
	profile        VARCHAR(30)         NOT NULL COMMENT '접속 환경 유형(local, dev, prod)', -- 접속 환경 유형(local, dev, prod)
	host           VARCHAR(30)         NOT NULL COMMENT '호스트', -- 호스트
	url            VARCHAR(300)        NULL     COMMENT 'URL', -- URL
	request_header LONGTEXT            NULL     COMMENT '요청 헤더', -- 요청 헤더
	error_log      LONGTEXT            NULL     COMMENT '오류 로그', -- 오류 로그
	parameter      LONGTEXT            NULL     COMMENT '파라미터', -- 파라미터
	result         LONGTEXT            NULL     COMMENT '결과', -- 결과
	access_ip      VARCHAR(50)         NULL     COMMENT '접속 아이피', -- 접속 아이피
	process_time   INTEGER             NULL     COMMENT '수행 시간', -- 수행 시간
	create_dt      DATETIME            NULL     DEFAULT NOW() COMMENT '생성 일시' -- 생성 일시
)
DEFAULT CHARACTER SET = 'utf8mb4'
DEFAULT COLLATE = 'utf8mb4_unicode_ci'
COMMENT 'API 로그';

-- API 로그
ALTER TABLE tb_api_log
	ADD CONSTRAINT PK_tb_api_log -- API 로그 기본키
		PRIMARY KEY (
			api_log_seq -- 순서
		);

ALTER TABLE tb_api_log
	MODIFY COLUMN api_log_seq BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '순서';

-- 조직
CREATE TABLE tb_org (
	org_id    VARCHAR(30)         NOT NULL COMMENT '조직 아이디', -- 조직 아이디
	org_nm    VARCHAR(300)        NOT NULL COMMENT '조직 명', -- 조직 명
	use_flag  VARCHAR(1)          NULL     DEFAULT 'Y' COMMENT '사용여부', -- 사용여부
	create_no BIGINT(20) UNSIGNED NULL     COMMENT '생성자 번호', -- 생성 번호
	create_dt DATETIME            NULL     DEFAULT NOW() COMMENT '생성 일시', -- 생성 일시
	update_no BIGINT(20) UNSIGNED NULL     COMMENT '수정자 번호', -- 수정 번호
	update_dt DATETIME            NULL     DEFAULT NOW() COMMENT '수정 일시' -- 수정 일시
)
DEFAULT CHARACTER SET = 'utf8mb4'
DEFAULT COLLATE = 'utf8mb4_unicode_ci'
COMMENT '조직';

-- 조직
ALTER TABLE tb_org
	ADD CONSTRAINT PK_tb_org -- 조직 기본키
		PRIMARY KEY (
			org_id -- 조직 아이디
		);

-- 관리자
CREATE TABLE tb_admin (
	admin_no        BIGINT(20) UNSIGNED NOT NULL COMMENT '관리자 번호', -- 관리자 번호
	org_id          VARCHAR(30)         NOT NULL COMMENT '조직 아이디', -- 조직 아이디
	admin_id        VARCHAR(30)         NOT NULL COMMENT '관리자 아이디', -- 관리자 아이디
	admin_nm        VARCHAR(30)         NOT NULL COMMENT '관리자 명', -- 관리자 명
	email           VARCHAR(30)         NULL     COMMENT '이메일', -- 이메일
	admin_status_cd VARCHAR(30)         NOT NULL COMMENT '관리자 상태(공통코드-ADMIN_STATUS)', -- 관리자 상태 코드
	admin_password  VARCHAR(255)        NOT NULL COMMENT '관리자 비밀번호(암호화-bcrypt)', -- 관리자 비밀번호
	use_start_dt    DATETIME            NULL     DEFAULT NOW() COMMENT '사용 시작 일시', -- 사용 시작 일시
	use_end_dt      DATETIME            NULL     DEFAULT NOW() COMMENT '사용 종료 일시', -- 사용 종료 일시
	approval_no     BIGINT(20) UNSIGNED NULL     COMMENT '승인자 번호', -- 승인 번호
	approval_dt     DATETIME            NULL     DEFAULT NOW() COMMENT '승인 일시', -- 승인 일시
	create_no       BIGINT(20) UNSIGNED NULL     COMMENT '생성자 번호', -- 생성 번호
	create_dt       DATETIME            NULL     DEFAULT NOW() COMMENT '생성 일시', -- 생성 일시
	update_no       BIGINT(20) UNSIGNED NULL     COMMENT '수정자 번호', -- 수정 번호
	update_dt       DATETIME            NULL     DEFAULT NOW() COMMENT '수정 일시' -- 수정 일시
)
DEFAULT CHARACTER SET = 'utf8mb4'
DEFAULT COLLATE = 'utf8mb4_unicode_ci'
COMMENT '관리자';

-- 관리자
ALTER TABLE tb_admin
	ADD CONSTRAINT PK_tb_admin -- 관리자 기본키
		PRIMARY KEY (
			admin_no -- 관리자 번호
		);

-- 관리자 유니크 인덱스
CREATE UNIQUE INDEX UIX_tb_admin
	ON tb_admin ( -- 관리자
		admin_id ASC -- 관리자 아이디
	);

ALTER TABLE tb_admin
	MODIFY COLUMN admin_no BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '관리자 번호';

-- 공통코드
CREATE TABLE tb_common_cd (
	group_cd  VARCHAR(30)         NOT NULL COMMENT '분류 코드', -- 분류 코드
	cd        VARCHAR(30)         NOT NULL COMMENT '코드', -- 코드
	group_nm  VARCHAR(300)        NOT NULL COMMENT '분류 명', -- 분류 명
	upper_cd  VARCHAR(30)         NULL     COMMENT '상위 코드', -- 상위 코드
	cd_nm     VARCHAR(300)        NULL     COMMENT '코드 명', -- 코드 명
	cd_order  INTEGER             NULL     COMMENT '코드 순서', -- 코드 순서
	cd_desc   TEXT                NULL     COMMENT '코드 설명', -- 코드 설명
	cd_level  INTEGER             NULL     COMMENT '코드 레벨', -- 코드 레벨
	use_flag  VARCHAR(1)          NULL     DEFAULT 'Y' COMMENT '사용 여부', -- 사용 여부
	create_no BIGINT(20) UNSIGNED NULL     COMMENT '생성자 번호', -- 생성 번호
	create_dt DATETIME            NULL     DEFAULT NOW() COMMENT '생성 일시', -- 생성 일시
	update_no BIGINT(20) UNSIGNED NULL     COMMENT '수정자 번호', -- 수정 번호
	update_dt DATETIME            NULL     DEFAULT NOW() COMMENT '수정 일시' -- 수정 일시
)
DEFAULT CHARACTER SET = 'utf8mb4'
DEFAULT COLLATE = 'utf8mb4_unicode_ci'
COMMENT '공통코드';

-- 공통코드
ALTER TABLE tb_common_cd
	ADD CONSTRAINT PK_tb_common_cd -- 공통코드 기본키
		PRIMARY KEY (
			group_cd, -- 분류 코드
			cd        -- 코드
		);

-- 파일
CREATE TABLE tb_file (
	file_seq  BIGINT(20) UNSIGNED NOT NULL COMMENT '파일 순서', -- 파일 순서
	create_no BIGINT(20) UNSIGNED NULL     COMMENT '생성자 번호', -- 생성 번호
	create_dt DATETIME            NULL     DEFAULT NOW() COMMENT '생성 일시' -- 생성 일시
)
DEFAULT CHARACTER SET = 'utf8mb4'
DEFAULT COLLATE = 'utf8mb4_unicode_ci'
COMMENT '파일';

-- 파일
ALTER TABLE tb_file
	ADD CONSTRAINT PK_tb_file -- 파일 기본키
		PRIMARY KEY (
			file_seq -- 파일 순서
		);

ALTER TABLE tb_file
	MODIFY COLUMN file_seq BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '파일 순서';

-- 파일 상세
CREATE TABLE tb_file_detail (
	file_detail_seq BIGINT(20) UNSIGNED NOT NULL COMMENT '파일 상세 순서', -- 파일 상세 순서
	file_seq        BIGINT(20) UNSIGNED NOT NULL COMMENT '파일 순서', -- 파일 순서
	ori_file_nm     VARCHAR(300)        NULL     COMMENT '원 파일 명', -- 원 파일 명
	save_file_nm    VARCHAR(300)        NULL     COMMENT '저장 파일 명', -- 저장 파일 명
	save_file_src   VARCHAR(300)        NULL     COMMENT '저장 파일 경로', -- 저장 파일 경로
	file_url        VARCHAR(300)        NULL     COMMENT '파일 URL', -- 파일 URL
	file_ext        VARCHAR(30)         NULL     COMMENT '파일 확장자', -- 파일 확장자
	file_size       INTEGER             NULL     COMMENT '파일 크기', -- 파일 크기
	del_flag        VARCHAR(1)          NULL     DEFAULT 'N' COMMENT '삭제 여부', -- 삭제 여부
	create_no       BIGINT(20) UNSIGNED NULL     COMMENT '생성 번호', -- 생성 번호
	create_group    VARCHAR(30)         NULL     COMMENT '생성 그룹(공통코드-CREATE_GROUP)', -- 생성 그룹
	create_dt       DATETIME            NULL     DEFAULT NOW() COMMENT '생성 일시' -- 생성 일시
)
DEFAULT CHARACTER SET = 'utf8mb4'
DEFAULT COLLATE = 'utf8mb4_unicode_ci'
COMMENT '파일 상세';

-- 파일 상세
ALTER TABLE tb_file_detail
	ADD CONSTRAINT PK_tb_file_detail -- 파일 상세 기본키
		PRIMARY KEY (
			file_detail_seq -- 파일 상세 순서
		);

-- 관리자 로그
CREATE TABLE tb_admin_log (
	admin_log_seq  BIGINT(20) UNSIGNED NOT NULL COMMENT '순서', -- 순서
	profile        VARCHAR(30)         NOT NULL COMMENT '접속 환경 유형(local, dev, prod)', -- 접속 환경 유형(local, dev, prod)
	host           VARCHAR(30)         NOT NULL COMMENT '호스트', -- 호스트
	url            VARCHAR(300)        NULL     COMMENT 'URL', -- URL
	request_header LONGTEXT            NULL     COMMENT '요청 헤더', -- 요청 헤더
	error_log      LONGTEXT            NULL     COMMENT '오류 로그', -- 오류 로그
	parameter      LONGTEXT            NULL     COMMENT '파라미터', -- 파라미터
	result         LONGTEXT            NULL     COMMENT '결과', -- 결과
	access_ip      VARCHAR(50)         NULL     COMMENT '접속 아이피', -- 접속 아이피
	process_time   INTEGER             NULL     COMMENT '수행 시간', -- 수행 시간
	create_dt      DATETIME            NULL     DEFAULT NOW() COMMENT '생성 일시' -- 생성 일시
)
DEFAULT CHARACTER SET = 'utf8mb4'
DEFAULT COLLATE = 'utf8mb4_unicode_ci'
COMMENT '관리자 로그';

-- 관리자 로그
ALTER TABLE tb_admin_log
	ADD CONSTRAINT PK_tb_admin_log -- 관리자 로그 기본키
		PRIMARY KEY (
			admin_log_seq -- 순서
		);

-- 관리자 메뉴
CREATE TABLE tb_admin_menu (
	menu_cd              VARCHAR(30)         NOT NULL COMMENT '메뉴 코드', -- 메뉴 코드
	upper_menu_cd        VARCHAR(30)         NOT NULL COMMENT '상위 메뉴 코드', -- 상위 메뉴 코드
	menu_nm              VARCHAR(300)        NOT NULL COMMENT '메뉴 명', -- 메뉴 명
	menu_icon_cd         VARCHAR(30)         NULL     COMMENT '메뉴 아이콘 코드(공통코드-MENU_ICON)', -- 메뉴 아이콘 코드
	menu_url             VARCHAR(300)        NULL     COMMENT '메뉴 URL', -- 메뉴 URL
	menu_open_type_cd    VARCHAR(30)         NULL     COMMENT '메뉴 오픈 타입 코드(공통코드-MENU_OPEN_TYPE)', -- 메뉴 오픈 타입 코드
	menu_order           INTEGER             NULL     COMMENT '메뉴 순서', -- 메뉴 순서
	menu_level           INTEGER             NOT NULL COMMENT '메뉴 레벨', -- 메뉴 레벨
	use_flag             VARCHAR(1)          NULL     DEFAULT 'Y' COMMENT '사용 여부', -- 사용 여부
	access_able_start_dt DATETIME            NULL     DEFAULT NOW() COMMENT '접근 가능 시작 일시', -- 접근 가능 시작 일시
	access_able_end_dt   DATETIME            NULL     DEFAULT NOW() COMMENT '접근 가능 종료 일시', -- 접근 가능 종료 일시
	block_start_dt       DATETIME            NULL     DEFAULT NOW() COMMENT '차단 시작 일시', -- 차단 시작 일시
	block_end_dt         DATETIME            NULL     DEFAULT NOW() COMMENT '차단 종료 일시', -- 차단 종료 일시
	create_no            BIGINT(20) UNSIGNED NULL     COMMENT '생성자 번호', -- 생성 번호
	create_dt            DATETIME            NULL     DEFAULT NOW() COMMENT '생성 일시', -- 생성 일시
	update_no            BIGINT(20) UNSIGNED NULL     COMMENT '수정자 번호', -- 수정 번호
	update_dt            DATETIME            NULL     DEFAULT NOW() COMMENT '수정 일시' -- 수정 일시
)
DEFAULT CHARACTER SET = 'utf8mb4'
DEFAULT COLLATE = 'utf8mb4_unicode_ci'
COMMENT '관리자 메뉴';

-- 관리자 메뉴
ALTER TABLE tb_admin_menu
	ADD CONSTRAINT PK_tb_admin_menu -- 관리자 메뉴 기본키
		PRIMARY KEY (
			menu_cd -- 메뉴 코드
		);

-- 관리자 메뉴 프로그램
CREATE TABLE tb_admin_menu_program (
	menu_cd         VARCHAR(30)         NOT NULL COMMENT '메뉴 코드', -- 메뉴 코드
	program_cd      VARCHAR(30)         NOT NULL COMMENT '프로그램 코드', -- 프로그램 코드
	program_nm      VARCHAR(300)        NOT NULL COMMENT '프로그램 명', -- 프로그램 명
	program_url     VARCHAR(300)        NOT NULL COMMENT '프로그램 URL', -- 프로그램 URL
	program_attr_cd VARCHAR(30)         NOT NULL COMMENT '프로그램 속성 코드(공통코드-PROGRAM_ATTR)', -- 프로그램 속성 코드
	create_no       BIGINT(20) UNSIGNED NULL     COMMENT '생성자 번호', -- 생성 번호
	create_dt       DATETIME            NULL     DEFAULT NOW() COMMENT '생성 일시', -- 생성 일시
	update_no       BIGINT(20) UNSIGNED NULL     COMMENT '수정자 번호', -- 수정 번호
	update_dt       DATETIME            NULL     DEFAULT NOW() COMMENT '수정 일시' -- 수정 일시
)
DEFAULT CHARACTER SET = 'utf8mb4'
DEFAULT COLLATE = 'utf8mb4_unicode_ci'
COMMENT '관리자 메뉴 프로그램';

-- 관리자 메뉴 프로그램
ALTER TABLE tb_admin_menu_program
	ADD CONSTRAINT PK_tb_admin_menu_program -- 관리자 메뉴 프로그램 기본키
		PRIMARY KEY (
			menu_cd,    -- 메뉴 코드
			program_cd  -- 프로그램 코드
		);

-- 관리자 조직별 권한
CREATE TABLE tb_admin_org_auth (
	org_id     VARCHAR(30)         NULL COMMENT '조직 아이디', -- 조직 아이디
	menu_cd    VARCHAR(30)         NULL COMMENT '메뉴 코드', -- 메뉴 코드
	program_cd VARCHAR(30)         NULL COMMENT '프로그램 코드', -- 프로그램 코드
	create_no  BIGINT(20) UNSIGNED NULL COMMENT '생성자 번호', -- 생성 번호
	create_dt  DATETIME            NULL DEFAULT NOW() COMMENT '생성 일시' -- 생성 일시
)
DEFAULT CHARACTER SET = 'utf8mb4'
DEFAULT COLLATE = 'utf8mb4_unicode_ci'
COMMENT '관리자 조직별 권한';

-- 관리자 그룹별 권한
CREATE TABLE tb_admin_dept_auth (
	group_id   VARCHAR(30)         NULL COMMENT '그룹 아이디', -- 그룹 아이디
	menu_cd    VARCHAR(30)         NULL COMMENT '메뉴 코드', -- 메뉴 코드
	program_cd VARCHAR(30)         NULL COMMENT '프로그램 코드', -- 프로그램 코드
	create_no  BIGINT(20) UNSIGNED NULL COMMENT '생성자 번호', -- 생성 번호
	create_dt  DATETIME            NULL DEFAULT NOW() COMMENT '생성 일시' -- 생성 일시
)
DEFAULT CHARACTER SET = 'utf8mb4'
DEFAULT COLLATE = 'utf8mb4_unicode_ci'
COMMENT '관리자 그룹별 권한';

-- 그룹
CREATE TABLE tb_group (
	group_id  VARCHAR(30)         NOT NULL COMMENT '그룹 아이디', -- 그룹 아이디
	group_nm  VARCHAR(300)        NOT NULL COMMENT '그룹 명', -- 그룹 명
	use_flag  VARCHAR(1)          NULL     DEFAULT 'Y' COMMENT '사용여부', -- 사용여부
	create_no BIGINT(20) UNSIGNED NULL     COMMENT '생성자 번호', -- 생성 번호
	create_dt DATETIME            NULL     DEFAULT NOW() COMMENT '생성 일시', -- 생성 일시
	update_no BIGINT(20) UNSIGNED NULL     COMMENT '수정자 번호', -- 수정 번호
	update_dt DATETIME            NULL     DEFAULT NOW() COMMENT '수정 일시' -- 수정 일시
)
DEFAULT CHARACTER SET = 'utf8mb4'
DEFAULT COLLATE = 'utf8mb4_unicode_ci'
COMMENT '그룹';

-- 그룹
ALTER TABLE tb_group
	ADD CONSTRAINT PK_tb_group -- 그룹 기본키
		PRIMARY KEY (
			group_id -- 그룹 아이디
		);

-- 관리자 그룹
CREATE TABLE tb_admin_group (
	admin_no  BIGINT(20) UNSIGNED NULL COMMENT '관리자 번호', -- 관리자 번호
	group_id  VARCHAR(30)         NULL COMMENT '그룹 아이디', -- 그룹 아이디
	create_no BIGINT(20) UNSIGNED NULL COMMENT '생성자 번호', -- 생성 번호
	create_dt DATETIME            NULL DEFAULT NOW() COMMENT '생성 일시' -- 생성 일시
)
DEFAULT CHARACTER SET = 'utf8mb4'
DEFAULT COLLATE = 'utf8mb4_unicode_ci'
COMMENT '관리자 그룹';

-- 관리자 개인별 권한
CREATE TABLE tb_admin_person_auth (
	admin_no   BIGINT(20) UNSIGNED NULL COMMENT '관리자 번호', -- 관리자 번호
	menu_cd    VARCHAR(30)         NULL COMMENT '메뉴 코드', -- 메뉴 코드
	program_cd VARCHAR(30)         NULL COMMENT '프로그램 코드', -- 프로그램 코드
	create_no  BIGINT(20) UNSIGNED NULL COMMENT '생성자 번호', -- 생성 번호
	create_dt  DATETIME            NULL DEFAULT NOW() COMMENT '생성 일시' -- 생성 일시
)
DEFAULT CHARACTER SET = 'utf8mb4'
DEFAULT COLLATE = 'utf8mb4_unicode_ci'
COMMENT '관리자 개인별 권한';

-- 배너
CREATE TABLE tb_banner (
	banner_seq      BIGINT(20) UNSIGNED NOT NULL COMMENT '배너 순서', -- 배너 순서
	banner_type_cd  VARCHAR(30)         NOT NULL COMMENT '배너 타입 코드(공통코드-BANNER_TYPE)', -- 배너 타입 코드
	banner_nm       VARCHAR(300)        NOT NULL COMMENT '배너 명', -- 배너 명
	banner_order    INTEGER             NULL     COMMENT '메뉴 순서', -- 메뉴 순서
	use_flag        VARCHAR(1)          NULL     DEFAULT 'Y' COMMENT '사용 여부', -- 사용 여부
	banner_start_dt DATETIME            NULL     DEFAULT NOW() COMMENT '배너 시작 일시', -- 배너 시작 일시
	banner_end_dt   DATETIME            NULL     DEFAULT NOW() COMMENT '배너 종료 일시', -- 배너 종료 일시
	link_url        VARCHAR(30)         NULL     COMMENT '메뉴 아이콘 코드(공통코드-MENU_ICON)', -- 링크 URL
	create_no       BIGINT(20) UNSIGNED NULL     COMMENT '생성자 번호', -- 생성 번호
	create_dt       DATETIME            NULL     DEFAULT NOW() COMMENT '생성 일시', -- 생성 일시
	update_no       BIGINT(20) UNSIGNED NULL     COMMENT '수정자 번호', -- 수정 번호
	update_dt       DATETIME            NULL     DEFAULT NOW() COMMENT '수정 일시' -- 수정 일시
)
DEFAULT CHARACTER SET = 'utf8mb4'
DEFAULT COLLATE = 'utf8mb4_unicode_ci'
COMMENT '배너';

-- 배너
ALTER TABLE tb_banner
	ADD CONSTRAINT PK_tb_banner -- 배너 기본키
		PRIMARY KEY (
			banner_seq -- 배너 순서
		);

-- 팝업
CREATE TABLE tb_popup (
	popup_seq      BIGINT(20) UNSIGNED NOT NULL COMMENT '팝업 순서', -- 팝업 순서
	title          VARCHAR(300)        NOT NULL COMMENT '제목', -- 제목
	content        TEXT                NULL     COMMENT '내용', -- 내용
	popup_order    INTEGER             NULL     COMMENT '메뉴 순서', -- 메뉴 순서
	use_flag       VARCHAR(1)          NULL     DEFAULT 'Y' COMMENT '사용 여부', -- 사용 여부
	popup_start_dt DATETIME            NULL     DEFAULT NOW() COMMENT '팝업 시작 일시', -- 팝업 시작 일시
	popup_end_dt   DATETIME            NULL     DEFAULT NOW() COMMENT '팝업 종료 일시', -- 팝업 종료 일시
	link_url       VARCHAR(300)        NULL     COMMENT '링크 URL', -- 링크 URL
	offset_x       INTEGER             NULL     COMMENT '오프셋 X', -- 오프셋 X
	offset_y       INTEGER             NULL     COMMENT '오프셋 Y', -- 오프셋 Y
	create_no      BIGINT(20) UNSIGNED NULL     COMMENT '생성자 번호', -- 생성 번호
	create_dt      DATETIME            NULL     DEFAULT NOW() COMMENT '생성 일시', -- 생성 일시
	update_no      BIGINT(20) UNSIGNED NULL     COMMENT '수정자 번호', -- 수정 번호
	update_dt      DATETIME            NULL     DEFAULT NOW() COMMENT '수정 일시' -- 수정 일시
)
DEFAULT CHARACTER SET = 'utf8mb4'
DEFAULT COLLATE = 'utf8mb4_unicode_ci'
COMMENT '팝업';

-- 팝업
ALTER TABLE tb_popup
	ADD CONSTRAINT PK_tb_popup -- 팝업 기본키
		PRIMARY KEY (
			popup_seq -- 팝업 순서
		);

-- 도움말
CREATE TABLE tb_help (
	menu_cd   VARCHAR(30)         NOT NULL COMMENT '메뉴 코드', -- 메뉴 코드
	content   LONGTEXT            NOT NULL COMMENT '내용', -- 내용
	create_no BIGINT(20) UNSIGNED NULL     COMMENT '생성자 번호', -- 생성 번호
	create_dt DATETIME            NULL     DEFAULT NOW() COMMENT '생성 일시', -- 생성 일시
	update_no BIGINT(20) UNSIGNED NULL     COMMENT '수정자 번호', -- 수정 번호
	update_dt DATETIME            NULL     DEFAULT NOW() COMMENT '수정 일시' -- 수정 일시
)
DEFAULT CHARACTER SET = 'utf8mb4'
DEFAULT COLLATE = 'utf8mb4_unicode_ci'
COMMENT '도움말';

-- 도움말
ALTER TABLE tb_help
	ADD CONSTRAINT PK_tb_help -- 도움말 기본키
		PRIMARY KEY (
			menu_cd -- 메뉴 코드
		);

-- 즐겨찾기
CREATE TABLE tb_favorite (
	admin_no  BIGINT(20) UNSIGNED NOT NULL COMMENT '관리자 번호', -- 관리자 번호
	menu_cd   VARCHAR(30)         NOT NULL COMMENT '메뉴 코드', -- 메뉴 코드
	create_no BIGINT(20) UNSIGNED NULL     COMMENT '생성자 번호', -- 생성 번호
	create_dt DATETIME            NULL     DEFAULT NOW() COMMENT '생성 일시' -- 생성 일시
)
DEFAULT CHARACTER SET = 'utf8mb4'
DEFAULT COLLATE = 'utf8mb4_unicode_ci'
COMMENT '즐겨찾기';

-- 즐겨찾기
ALTER TABLE tb_favorite
	ADD CONSTRAINT PK_tb_favorite -- 즐겨찾기 기본키
		PRIMARY KEY (
			admin_no, -- 관리자 번호
			menu_cd   -- 메뉴 코드
		);

-- 관리자
ALTER TABLE tb_admin
	ADD CONSTRAINT FK_tb_org_TO_tb_admin -- 조직 -> 관리자
		FOREIGN KEY (
			org_id -- 조직 아이디
		)
		REFERENCES tb_org ( -- 조직
			org_id -- 조직 아이디
		);

-- 파일 상세
ALTER TABLE tb_file_detail
	ADD CONSTRAINT FK_tb_file_TO_tb_file_detail -- 파일 -> 파일 상세
		FOREIGN KEY (
			file_seq -- 파일 순서
		)
		REFERENCES tb_file ( -- 파일
			file_seq -- 파일 순서
		);

-- 관리자 메뉴 프로그램
ALTER TABLE tb_admin_menu_program
	ADD CONSTRAINT FK_tb_admin_menu_TO_tb_admin_menu_program -- 관리자 메뉴 -> 관리자 메뉴 프로그램
		FOREIGN KEY (
			menu_cd -- 메뉴 코드
		)
		REFERENCES tb_admin_menu ( -- 관리자 메뉴
			menu_cd -- 메뉴 코드
		);

-- 관리자 조직별 권한
ALTER TABLE tb_admin_org_auth
	ADD CONSTRAINT FK_tb_org_TO_tb_admin_org_auth -- 조직 -> 관리자 조직별 권한
		FOREIGN KEY (
			org_id -- 조직 아이디
		)
		REFERENCES tb_org ( -- 조직
			org_id -- 조직 아이디
		);

-- 관리자 조직별 권한
ALTER TABLE tb_admin_org_auth
	ADD CONSTRAINT FK_tb_admin_menu_program_TO_tb_admin_org_auth -- 관리자 메뉴 프로그램 -> 관리자 조직별 권한
		FOREIGN KEY (
			menu_cd,    -- 메뉴 코드
			program_cd  -- 프로그램 코드
		)
		REFERENCES tb_admin_menu_program ( -- 관리자 메뉴 프로그램
			menu_cd,    -- 메뉴 코드
			program_cd  -- 프로그램 코드
		);

-- 관리자 그룹별 권한
ALTER TABLE tb_admin_dept_auth
	ADD CONSTRAINT FK_tb_group_TO_tb_admin_dept_auth -- 그룹 -> 관리자 그룹별 권한
		FOREIGN KEY (
			group_id -- 그룹 아이디
		)
		REFERENCES tb_group ( -- 그룹
			group_id -- 그룹 아이디
		);

-- 관리자 그룹별 권한
ALTER TABLE tb_admin_dept_auth
	ADD CONSTRAINT FK_tb_admin_menu_program_TO_tb_admin_dept_auth -- 관리자 메뉴 프로그램 -> 관리자 그룹별 권한
		FOREIGN KEY (
			menu_cd,    -- 메뉴 코드
			program_cd  -- 프로그램 코드
		)
		REFERENCES tb_admin_menu_program ( -- 관리자 메뉴 프로그램
			menu_cd,    -- 메뉴 코드
			program_cd  -- 프로그램 코드
		);

-- 관리자 그룹
ALTER TABLE tb_admin_group
	ADD CONSTRAINT FK_tb_group_TO_tb_admin_group -- 그룹 -> 관리자 그룹
		FOREIGN KEY (
			group_id -- 그룹 아이디
		)
		REFERENCES tb_group ( -- 그룹
			group_id -- 그룹 아이디
		);

-- 관리자 그룹
ALTER TABLE tb_admin_group
	ADD CONSTRAINT FK_tb_admin_TO_tb_admin_group -- 관리자 -> 관리자 그룹
		FOREIGN KEY (
			admin_no -- 관리자 번호
		)
		REFERENCES tb_admin ( -- 관리자
			admin_no -- 관리자 번호
		);

-- 관리자 개인별 권한
ALTER TABLE tb_admin_person_auth
	ADD CONSTRAINT FK_tb_admin_TO_tb_admin_person_auth -- 관리자 -> 관리자 개인별 권한
		FOREIGN KEY (
			admin_no -- 관리자 번호
		)
		REFERENCES tb_admin ( -- 관리자
			admin_no -- 관리자 번호
		);

-- 관리자 개인별 권한
ALTER TABLE tb_admin_person_auth
	ADD CONSTRAINT FK_tb_admin_menu_program_TO_tb_admin_person_auth -- 관리자 메뉴 프로그램 -> 관리자 개인별 권한
		FOREIGN KEY (
			menu_cd,    -- 메뉴 코드
			program_cd  -- 프로그램 코드
		)
		REFERENCES tb_admin_menu_program ( -- 관리자 메뉴 프로그램
			menu_cd,    -- 메뉴 코드
			program_cd  -- 프로그램 코드
		);

-- 즐겨찾기
ALTER TABLE tb_favorite
	ADD CONSTRAINT FK_tb_admin_TO_tb_favorite -- 관리자 -> 즐겨찾기
		FOREIGN KEY (
			admin_no -- 관리자 번호
		)
		REFERENCES tb_admin ( -- 관리자
			admin_no -- 관리자 번호
		);