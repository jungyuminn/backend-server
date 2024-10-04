CREATE TABLE `club`
(
    `club_id`               CHAR(26) PRIMARY KEY COMMENT 'UUID',
    `club_name`             CHAR(26) NOT NULL,
    `club_category`         VARCHAR(255) NOT NULL,
    `club_short_description` VARCHAR(255) NOT NULL,
    `club_description`      TEXT NOT NULL,
    `club_image_url`        VARCHAR(255),
    `recruiting_status`     BOOLEAN NOT NULL,
    `club_established_at`   TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at`            TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT '동아리 테이블';