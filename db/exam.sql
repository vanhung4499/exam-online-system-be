/*
 Navicat Premium Data Transfer

 Source Server         : exam
 Source Server Type    : MySQL
 Source Server Version : 80036 (8.0.36)
 Source Host           : localhost:3306
 Source Schema         : exam

 Target Server Type    : MySQL
 Target Server Version : 80036 (8.0.36)
 File Encoding         : 65001

 Date: 10/06/2024 21:58:02
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_certificate
-- ----------------------------
DROP TABLE IF EXISTS `t_certificate`;
CREATE TABLE `t_certificate` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'Certificate ID',
  `certificate_name` varchar(255) NOT NULL COMMENT 'Certificate Name',
  `image` varchar(255) DEFAULT NULL COMMENT 'Background Image',
  `certification_unit` varchar(50) NOT NULL COMMENT 'Certification Unit',
  `create_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT 'Creation Time',
  `is_deleted` int NOT NULL DEFAULT '0' COMMENT 'Whether Deleted 0: No 1: Yes',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of t_certificate
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for t_certificate_user
-- ----------------------------
DROP TABLE IF EXISTS `t_certificate_user`;
CREATE TABLE `t_certificate_user` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'Certificate and User Relationship Table ID',
  `user_id` int DEFAULT NULL COMMENT 'User ID',
  `exam_id` int DEFAULT NULL COMMENT 'Exam ID',
  `code` varchar(255) DEFAULT NULL COMMENT 'Certificate Number',
  `certificate_id` int DEFAULT NULL COMMENT 'Certificate ID',
  `create_time` datetime DEFAULT NULL COMMENT 'Award Time      YYYY-MM-DD hh:mm:ss',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of t_certificate_user
-- ----------------------------
BEGIN;
INSERT INTO `t_certificate_user` (`id`, `user_id`, `exam_id`, `code`, `certificate_id`, `create_time`) VALUES (1, 33, 49, 'JhXJsjuYFYp57wtQL9', NULL, '2024-06-09 18:03:56');
INSERT INTO `t_certificate_user` (`id`, `user_id`, `exam_id`, `code`, `certificate_id`, `create_time`) VALUES (2, 33, 49, 'R2nE57SIrmVN5HGIkG', NULL, '2024-06-09 18:10:59');
INSERT INTO `t_certificate_user` (`id`, `user_id`, `exam_id`, `code`, `certificate_id`, `create_time`) VALUES (3, 33, 50, 'w2P1dvDKAP4plrvBl8', NULL, '2024-06-09 18:25:24');
INSERT INTO `t_certificate_user` (`id`, `user_id`, `exam_id`, `code`, `certificate_id`, `create_time`) VALUES (4, 35, 49, 'KmMMmOJTnJrLm9O6aQ', NULL, '2024-06-10 16:53:36');
INSERT INTO `t_certificate_user` (`id`, `user_id`, `exam_id`, `code`, `certificate_id`, `create_time`) VALUES (5, 33, 51, '7lJYzfmst58cxQVCLy', NULL, '2024-06-10 17:59:41');
INSERT INTO `t_certificate_user` (`id`, `user_id`, `exam_id`, `code`, `certificate_id`, `create_time`) VALUES (6, 37, 52, 'm2bbKbvLaOy8quktKm', NULL, '2024-06-10 19:17:06');
INSERT INTO `t_certificate_user` (`id`, `user_id`, `exam_id`, `code`, `certificate_id`, `create_time`) VALUES (7, 33, 52, '9uis8rX5FJ6gCnLx75', NULL, '2024-06-10 21:22:28');
INSERT INTO `t_certificate_user` (`id`, `user_id`, `exam_id`, `code`, `certificate_id`, `create_time`) VALUES (8, 37, 53, 'Xr7qLjNQgkDkSQDRaG', NULL, '2024-06-10 21:35:10');
COMMIT;

-- ----------------------------
-- Table structure for t_exam
-- ----------------------------
DROP TABLE IF EXISTS `t_exam`;
CREATE TABLE `t_exam` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'Exam ID',
  `title` varchar(255) NOT NULL COMMENT 'Exam Name',
  `exam_duration` int NOT NULL COMMENT 'Exam Duration',
  `passed_score` int NOT NULL COMMENT 'Passing Score',
  `gross_score` int NOT NULL COMMENT 'Total Score',
  `max_count` int DEFAULT NULL COMMENT 'Maximum Screen Cuts',
  `user_id` int DEFAULT NULL COMMENT 'Creator ID',
  `certificate_id` int DEFAULT NULL COMMENT 'Certificate ID',
  `radio_count` int DEFAULT NULL COMMENT 'Number of Single Choice Questions',
  `radio_score` int DEFAULT NULL COMMENT 'Single Choice Question Score     Stored in the database * 100, normal input and display in frontend / 100',
  `multi_count` int DEFAULT NULL COMMENT 'Number of Multiple Choice Questions',
  `multi_score` int DEFAULT NULL COMMENT 'Multiple Choice Question Score     Stored in the database * 100, normal input and display in frontend / 100',
  `judge_count` int DEFAULT NULL COMMENT 'Number of True/False Questions',
  `judge_score` int DEFAULT NULL COMMENT 'True/False Question Score     Stored in the database * 100, normal input and display in frontend / 100',
  `saq_count` int DEFAULT NULL COMMENT 'Number of Short Answer Questions',
  `saq_score` int DEFAULT NULL COMMENT 'Short Answer Question Score     Stored in the database * 100, normal input and display in frontend / 100',
  `start_time` datetime DEFAULT NULL COMMENT 'Start Time     YYYY-MM-DD hh:mm:ss',
  `end_time` datetime DEFAULT NULL COMMENT 'End Time     YYYY-MM-DD hh:mm:ss',
  `create_time` datetime DEFAULT NULL COMMENT 'Creation Time     YYYY-MM-DD hh:mm:ss  ',
  `is_deleted` int NOT NULL DEFAULT '0' COMMENT 'Whether Deleted 0: No 1: Yes',
  PRIMARY KEY (`id`,`passed_score`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of t_exam
-- ----------------------------
BEGIN;
INSERT INTO `t_exam` (`id`, `title`, `exam_duration`, `passed_score`, `gross_score`, `max_count`, `user_id`, `certificate_id`, `radio_count`, `radio_score`, `multi_count`, `multi_score`, `judge_count`, `judge_score`, `saq_count`, `saq_score`, `start_time`, `end_time`, `create_time`, `is_deleted`) VALUES (49, 'Math Test 03', 5, 10, 20, 2, 33, NULL, 2, 10, 0, 0, 0, 0, 0, 0, '2024-06-08 17:00:00', '2024-06-10 17:00:00', '2024-06-09 18:01:49', 0);
INSERT INTO `t_exam` (`id`, `title`, `exam_duration`, `passed_score`, `gross_score`, `max_count`, `user_id`, `certificate_id`, `radio_count`, `radio_score`, `multi_count`, `multi_score`, `judge_count`, `judge_score`, `saq_count`, `saq_score`, `start_time`, `end_time`, `create_time`, `is_deleted`) VALUES (50, 'Math Test 01', 1, 5, 10, 1, 33, NULL, 2, 5, 0, 0, 0, 0, 0, 0, '2024-06-08 17:00:00', '2024-06-10 17:00:00', '2024-06-09 18:22:47', 0);
INSERT INTO `t_exam` (`id`, `title`, `exam_duration`, `passed_score`, `gross_score`, `max_count`, `user_id`, `certificate_id`, `radio_count`, `radio_score`, `multi_count`, `multi_score`, `judge_count`, `judge_score`, `saq_count`, `saq_score`, `start_time`, `end_time`, `create_time`, `is_deleted`) VALUES (51, 'English Test 01', 5, 5, 10, 1, 34, NULL, 2, 5, 0, 0, 0, 0, 0, 0, '2024-06-09 17:00:00', '2024-06-11 17:00:00', '2024-06-10 16:26:01', 0);
INSERT INTO `t_exam` (`id`, `title`, `exam_duration`, `passed_score`, `gross_score`, `max_count`, `user_id`, `certificate_id`, `radio_count`, `radio_score`, `multi_count`, `multi_score`, `judge_count`, `judge_score`, `saq_count`, `saq_score`, `start_time`, `end_time`, `create_time`, `is_deleted`) VALUES (52, 'Math Test 04', 5, 5, 10, 1, 33, NULL, 2, 5, 0, 0, 0, 0, 0, 0, '2024-06-09 17:00:00', '2024-06-14 17:00:00', '2024-06-10 19:15:41', 0);
INSERT INTO `t_exam` (`id`, `title`, `exam_duration`, `passed_score`, `gross_score`, `max_count`, `user_id`, `certificate_id`, `radio_count`, `radio_score`, `multi_count`, `multi_score`, `judge_count`, `judge_score`, `saq_count`, `saq_score`, `start_time`, `end_time`, `create_time`, `is_deleted`) VALUES (53, 'Test abcd', 5, 0, 10, 5, 33, NULL, 2, 5, 0, 0, 0, 0, 0, 0, '2024-06-09 17:00:00', '2024-06-14 17:00:00', '2024-06-10 21:26:19', 0);
COMMIT;

-- ----------------------------
-- Table structure for t_exam_grade
-- ----------------------------
DROP TABLE IF EXISTS `t_exam_grade`;
CREATE TABLE `t_exam_grade` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'Exam and Grade Relationship Table ID',
  `exam_id` int DEFAULT NULL COMMENT 'Exam ID  Unique',
  `grade_id` int DEFAULT NULL COMMENT 'Grade ID  Unique',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=69 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of t_exam_grade
-- ----------------------------
BEGIN;
INSERT INTO `t_exam_grade` (`id`, `exam_id`, `grade_id`) VALUES (62, 47, 70);
INSERT INTO `t_exam_grade` (`id`, `exam_id`, `grade_id`) VALUES (63, 48, 70);
INSERT INTO `t_exam_grade` (`id`, `exam_id`, `grade_id`) VALUES (64, 49, 70);
INSERT INTO `t_exam_grade` (`id`, `exam_id`, `grade_id`) VALUES (65, 50, 70);
INSERT INTO `t_exam_grade` (`id`, `exam_id`, `grade_id`) VALUES (66, 51, 72);
INSERT INTO `t_exam_grade` (`id`, `exam_id`, `grade_id`) VALUES (67, 52, 70);
INSERT INTO `t_exam_grade` (`id`, `exam_id`, `grade_id`) VALUES (68, 53, 70);
COMMIT;

-- ----------------------------
-- Table structure for t_exam_qu_answer
-- ----------------------------
DROP TABLE IF EXISTS `t_exam_qu_answer`;
CREATE TABLE `t_exam_qu_answer` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'Exam Record Answers ID',
  `user_id` int NOT NULL COMMENT 'User ID',
  `exam_id` int NOT NULL COMMENT 'Exam ID',
  `question_id` int NOT NULL COMMENT 'Question ID',
  `question_type` int DEFAULT NULL COMMENT 'Question Type',
  `answer_id` varchar(10) DEFAULT NULL COMMENT 'Answer ID  Used for objective questions, multiple choice question IDs are separated by ","',
  `answer_content` varchar(255) DEFAULT NULL COMMENT 'Answer Content    Used for subjective questions',
  `checkout` int DEFAULT NULL COMMENT 'Whether Selected   0 Not Selected  1 Selected',
  `is_sign` int DEFAULT NULL COMMENT 'Whether Marked   0 Not Marked  1 Marked',
  `is_right` int DEFAULT NULL COMMENT 'Whether Correct   Used for objective questions, 0 Incorrect 1 Correct',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of t_exam_qu_answer
-- ----------------------------
BEGIN;
INSERT INTO `t_exam_qu_answer` (`id`, `user_id`, `exam_id`, `question_id`, `question_type`, `answer_id`, `answer_content`, `checkout`, `is_sign`, `is_right`) VALUES (38, 33, 47, 578, 1, '2246', NULL, NULL, NULL, 1);
INSERT INTO `t_exam_qu_answer` (`id`, `user_id`, `exam_id`, `question_id`, `question_type`, `answer_id`, `answer_content`, `checkout`, `is_sign`, `is_right`) VALUES (39, 33, 48, 578, 1, '2247', NULL, NULL, NULL, 0);
INSERT INTO `t_exam_qu_answer` (`id`, `user_id`, `exam_id`, `question_id`, `question_type`, `answer_id`, `answer_content`, `checkout`, `is_sign`, `is_right`) VALUES (40, 33, 49, 579, 1, '2251', NULL, NULL, NULL, 1);
INSERT INTO `t_exam_qu_answer` (`id`, `user_id`, `exam_id`, `question_id`, `question_type`, `answer_id`, `answer_content`, `checkout`, `is_sign`, `is_right`) VALUES (41, 33, 49, 578, 1, '2246', NULL, NULL, NULL, 1);
INSERT INTO `t_exam_qu_answer` (`id`, `user_id`, `exam_id`, `question_id`, `question_type`, `answer_id`, `answer_content`, `checkout`, `is_sign`, `is_right`) VALUES (42, 33, 50, 578, 1, '2246', NULL, NULL, NULL, 1);
INSERT INTO `t_exam_qu_answer` (`id`, `user_id`, `exam_id`, `question_id`, `question_type`, `answer_id`, `answer_content`, `checkout`, `is_sign`, `is_right`) VALUES (43, 33, 50, 579, 1, '2249', NULL, NULL, NULL, 0);
INSERT INTO `t_exam_qu_answer` (`id`, `user_id`, `exam_id`, `question_id`, `question_type`, `answer_id`, `answer_content`, `checkout`, `is_sign`, `is_right`) VALUES (44, 35, 49, 579, 1, '2251', NULL, NULL, NULL, 1);
INSERT INTO `t_exam_qu_answer` (`id`, `user_id`, `exam_id`, `question_id`, `question_type`, `answer_id`, `answer_content`, `checkout`, `is_sign`, `is_right`) VALUES (45, 35, 49, 578, 1, '2245', NULL, NULL, NULL, 0);
INSERT INTO `t_exam_qu_answer` (`id`, `user_id`, `exam_id`, `question_id`, `question_type`, `answer_id`, `answer_content`, `checkout`, `is_sign`, `is_right`) VALUES (46, 37, 50, 578, 1, '2245', NULL, NULL, NULL, 0);
INSERT INTO `t_exam_qu_answer` (`id`, `user_id`, `exam_id`, `question_id`, `question_type`, `answer_id`, `answer_content`, `checkout`, `is_sign`, `is_right`) VALUES (47, 37, 50, 579, 1, '2251', NULL, NULL, NULL, 1);
INSERT INTO `t_exam_qu_answer` (`id`, `user_id`, `exam_id`, `question_id`, `question_type`, `answer_id`, `answer_content`, `checkout`, `is_sign`, `is_right`) VALUES (48, 37, 49, 579, 1, '2249', NULL, NULL, NULL, 0);
INSERT INTO `t_exam_qu_answer` (`id`, `user_id`, `exam_id`, `question_id`, `question_type`, `answer_id`, `answer_content`, `checkout`, `is_sign`, `is_right`) VALUES (49, 37, 49, 578, 1, '2246', NULL, NULL, NULL, 1);
INSERT INTO `t_exam_qu_answer` (`id`, `user_id`, `exam_id`, `question_id`, `question_type`, `answer_id`, `answer_content`, `checkout`, `is_sign`, `is_right`) VALUES (50, 33, 51, 581, 1, '2256', NULL, NULL, NULL, 1);
INSERT INTO `t_exam_qu_answer` (`id`, `user_id`, `exam_id`, `question_id`, `question_type`, `answer_id`, `answer_content`, `checkout`, `is_sign`, `is_right`) VALUES (51, 33, 51, 580, 1, '2252', NULL, NULL, NULL, 1);
INSERT INTO `t_exam_qu_answer` (`id`, `user_id`, `exam_id`, `question_id`, `question_type`, `answer_id`, `answer_content`, `checkout`, `is_sign`, `is_right`) VALUES (52, 37, 52, 578, 1, '2246', NULL, NULL, NULL, 1);
INSERT INTO `t_exam_qu_answer` (`id`, `user_id`, `exam_id`, `question_id`, `question_type`, `answer_id`, `answer_content`, `checkout`, `is_sign`, `is_right`) VALUES (53, 37, 52, 579, 1, '2251', NULL, NULL, NULL, 1);
INSERT INTO `t_exam_qu_answer` (`id`, `user_id`, `exam_id`, `question_id`, `question_type`, `answer_id`, `answer_content`, `checkout`, `is_sign`, `is_right`) VALUES (54, 33, 52, 578, 1, '2246', NULL, NULL, NULL, 1);
INSERT INTO `t_exam_qu_answer` (`id`, `user_id`, `exam_id`, `question_id`, `question_type`, `answer_id`, `answer_content`, `checkout`, `is_sign`, `is_right`) VALUES (55, 33, 52, 579, 1, '2251', NULL, NULL, NULL, 1);
INSERT INTO `t_exam_qu_answer` (`id`, `user_id`, `exam_id`, `question_id`, `question_type`, `answer_id`, `answer_content`, `checkout`, `is_sign`, `is_right`) VALUES (56, 37, 53, 578, 1, '2246', NULL, NULL, NULL, 1);
INSERT INTO `t_exam_qu_answer` (`id`, `user_id`, `exam_id`, `question_id`, `question_type`, `answer_id`, `answer_content`, `checkout`, `is_sign`, `is_right`) VALUES (57, 37, 53, 579, 1, '2248', NULL, NULL, NULL, 0);
COMMIT;

-- ----------------------------
-- Table structure for t_exam_question
-- ----------------------------
DROP TABLE IF EXISTS `t_exam_question`;
CREATE TABLE `t_exam_question` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'Exam Question ID',
  `exam_id` int NOT NULL COMMENT 'Exam ID',
  `question_id` int NOT NULL COMMENT 'Question ID',
  `score` int NOT NULL COMMENT 'Score',
  `sort` int DEFAULT NULL COMMENT 'Sorting',
  `type` int DEFAULT NULL COMMENT 'Question Type',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=149 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of t_exam_question
-- ----------------------------
BEGIN;
INSERT INTO `t_exam_question` (`id`, `exam_id`, `question_id`, `score`, `sort`, `type`) VALUES (137, 47, 578, 10, 0, 1);
INSERT INTO `t_exam_question` (`id`, `exam_id`, `question_id`, `score`, `sort`, `type`) VALUES (138, 48, 578, 10, 0, 1);
INSERT INTO `t_exam_question` (`id`, `exam_id`, `question_id`, `score`, `sort`, `type`) VALUES (139, 49, 578, 10, 1, 1);
INSERT INTO `t_exam_question` (`id`, `exam_id`, `question_id`, `score`, `sort`, `type`) VALUES (140, 49, 579, 10, 0, 1);
INSERT INTO `t_exam_question` (`id`, `exam_id`, `question_id`, `score`, `sort`, `type`) VALUES (141, 50, 578, 5, 0, 1);
INSERT INTO `t_exam_question` (`id`, `exam_id`, `question_id`, `score`, `sort`, `type`) VALUES (142, 50, 579, 5, 1, 1);
INSERT INTO `t_exam_question` (`id`, `exam_id`, `question_id`, `score`, `sort`, `type`) VALUES (143, 51, 580, 5, 1, 1);
INSERT INTO `t_exam_question` (`id`, `exam_id`, `question_id`, `score`, `sort`, `type`) VALUES (144, 51, 581, 5, 0, 1);
INSERT INTO `t_exam_question` (`id`, `exam_id`, `question_id`, `score`, `sort`, `type`) VALUES (145, 52, 578, 5, 0, 1);
INSERT INTO `t_exam_question` (`id`, `exam_id`, `question_id`, `score`, `sort`, `type`) VALUES (146, 52, 579, 5, 1, 1);
INSERT INTO `t_exam_question` (`id`, `exam_id`, `question_id`, `score`, `sort`, `type`) VALUES (147, 53, 578, 5, 0, 1);
INSERT INTO `t_exam_question` (`id`, `exam_id`, `question_id`, `score`, `sort`, `type`) VALUES (148, 53, 579, 5, 1, 1);
COMMIT;

-- ----------------------------
-- Table structure for t_exam_repo
-- ----------------------------
DROP TABLE IF EXISTS `t_exam_repo`;
CREATE TABLE `t_exam_repo` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'Exam and Repository ID',
  `exam_id` int NOT NULL COMMENT 'Exam ID  Unique',
  `repo_id` int DEFAULT NULL COMMENT 'Repository ID  Unique',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of t_exam_repo
-- ----------------------------
BEGIN;
INSERT INTO `t_exam_repo` (`id`, `exam_id`, `repo_id`) VALUES (35, 47, 26);
INSERT INTO `t_exam_repo` (`id`, `exam_id`, `repo_id`) VALUES (36, 48, 26);
INSERT INTO `t_exam_repo` (`id`, `exam_id`, `repo_id`) VALUES (37, 49, 26);
INSERT INTO `t_exam_repo` (`id`, `exam_id`, `repo_id`) VALUES (38, 50, 26);
INSERT INTO `t_exam_repo` (`id`, `exam_id`, `repo_id`) VALUES (39, 51, 27);
INSERT INTO `t_exam_repo` (`id`, `exam_id`, `repo_id`) VALUES (40, 52, 26);
INSERT INTO `t_exam_repo` (`id`, `exam_id`, `repo_id`) VALUES (41, 53, 26);
COMMIT;

-- ----------------------------
-- Table structure for t_exercise_record
-- ----------------------------
DROP TABLE IF EXISTS `t_exercise_record`;
CREATE TABLE `t_exercise_record` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'Exercise Record ID',
  `repo_id` int NOT NULL COMMENT 'Repository ID',
  `question_id` int NOT NULL COMMENT 'Question ID',
  `user_id` int NOT NULL COMMENT 'User ID',
  `answer` varchar(255) DEFAULT NULL COMMENT 'Subjective Answer',
  `question_type` int NOT NULL COMMENT 'Question Type',
  `options` varchar(255) DEFAULT NULL COMMENT 'Objective Answer Collection  Used for objective questions, multiple choice question IDs are separated by ","',
  `is_right` int DEFAULT NULL COMMENT 'Whether Objective Question is Correct',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of t_exercise_record
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for t_grade
-- ----------------------------
DROP TABLE IF EXISTS `t_grade`;
CREATE TABLE `t_grade` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'Grade ID',
  `grade_name` varchar(255) NOT NULL COMMENT 'Grade Name',
  `user_id` int DEFAULT NULL COMMENT 'Creator ID',
  `code` varchar(255) NOT NULL COMMENT 'Class Code',
  `is_deleted` int NOT NULL DEFAULT '0' COMMENT 'Whether Deleted 0: No 1: Yes',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `grade_name` (`grade_name`) USING BTREE,
  UNIQUE KEY `code` (`code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=75 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of t_grade
-- ----------------------------
BEGIN;
INSERT INTO `t_grade` (`id`, `grade_name`, `user_id`, `code`, `is_deleted`) VALUES (70, 'Basic Math', 33, '5TKoduqcKN1hmbgudz', 0);
INSERT INTO `t_grade` (`id`, `grade_name`, `user_id`, `code`, `is_deleted`) VALUES (71, 'English Basic', 33, 'qFWjXCwWw9E58QcUlL', 0);
INSERT INTO `t_grade` (`id`, `grade_name`, `user_id`, `code`, `is_deleted`) VALUES (72, 'English', 34, '1ftTR104r3PGCLQ7TK', 0);
INSERT INTO `t_grade` (`id`, `grade_name`, `user_id`, `code`, `is_deleted`) VALUES (74, 'English 01', 34, 'cQf28c5I30Cs60VsGr', 0);
COMMIT;

-- ----------------------------
-- Table structure for t_grade_exercise
-- ----------------------------
DROP TABLE IF EXISTS `t_grade_exercise`;
CREATE TABLE `t_grade_exercise` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'Exercise ID',
  `repo_id` int DEFAULT NULL COMMENT 'Repository ID',
  `grade_id` int DEFAULT NULL COMMENT 'Grade ID',
  `user_id` int DEFAULT NULL COMMENT 'Creator ID',
  `create_time` datetime DEFAULT NULL COMMENT 'Creation Time',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of t_grade_exercise
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for t_manual_score
-- ----------------------------
DROP TABLE IF EXISTS `t_manual_score`;
CREATE TABLE `t_manual_score` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'Manual Scoring ID',
  `exam_qu_answer_id` int DEFAULT NULL COMMENT 'Exam Record Answer ID',
  `user_id` int DEFAULT NULL COMMENT 'Grader ID',
  `score` int DEFAULT NULL COMMENT 'Score',
  `create_time` datetime DEFAULT NULL COMMENT 'Grading Time',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of t_manual_score
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for t_notice
-- ----------------------------
DROP TABLE IF EXISTS `t_notice`;
CREATE TABLE `t_notice` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'Notice ID',
  `title` varchar(255) NOT NULL COMMENT 'Notice Title',
  `image` varchar(255) DEFAULT NULL COMMENT 'Image URL',
  `content` text NOT NULL COMMENT 'Notice Content',
  `user_id` int DEFAULT NULL COMMENT 'Creating User ID   Unique',
  `create_time` datetime DEFAULT NULL COMMENT 'Creation Time',
  `is_deleted` int NOT NULL DEFAULT '0' COMMENT 'Whether Deleted 0: No 1: Yes',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of t_notice
-- ----------------------------
BEGIN;
INSERT INTO `t_notice` (`id`, `title`, `image`, `content`, `user_id`, `create_time`, `is_deleted`) VALUES (14, 'Middle Exam', NULL, '<p>Middle Exam</p>', 33, '2024-06-10 21:00:59', 0);
INSERT INTO `t_notice` (`id`, `title`, `image`, `content`, `user_id`, `create_time`, `is_deleted`) VALUES (15, 'Final Exam', NULL, '<p>final</p>', 33, '2024-06-10 21:28:59', 0);
COMMIT;

-- ----------------------------
-- Table structure for t_notice_grade
-- ----------------------------
DROP TABLE IF EXISTS `t_notice_grade`;
CREATE TABLE `t_notice_grade` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'Notice Grade Association ID',
  `notice_id` int DEFAULT NULL COMMENT 'Notice ID',
  `grade_id` int DEFAULT NULL COMMENT 'Grade ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of t_notice_grade
-- ----------------------------
BEGIN;
INSERT INTO `t_notice_grade` (`id`, `notice_id`, `grade_id`) VALUES (1, 14, 70);
INSERT INTO `t_notice_grade` (`id`, `notice_id`, `grade_id`) VALUES (2, 14, 71);
INSERT INTO `t_notice_grade` (`id`, `notice_id`, `grade_id`) VALUES (3, 15, 70);
INSERT INTO `t_notice_grade` (`id`, `notice_id`, `grade_id`) VALUES (4, 15, 71);
COMMIT;

-- ----------------------------
-- Table structure for t_option
-- ----------------------------
DROP TABLE IF EXISTS `t_option`;
CREATE TABLE `t_option` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'Option Answer ID',
  `qu_id` int NOT NULL COMMENT 'Question ID',
  `is_right` int DEFAULT NULL COMMENT 'Whether Correct',
  `image` varchar(255) DEFAULT NULL COMMENT 'Image URL   0: Incorrect, 1: Correct',
  `content` varchar(255) NOT NULL COMMENT 'Option Content',
  `sort` int DEFAULT NULL COMMENT 'Sorting',
  `is_deleted` int NOT NULL DEFAULT '0' COMMENT 'Whether Deleted 0: No 1: Yes',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2264 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of t_option
-- ----------------------------
BEGIN;
INSERT INTO `t_option` (`id`, `qu_id`, `is_right`, `image`, `content`, `sort`, `is_deleted`) VALUES (2244, 578, 0, NULL, '1', 0, 0);
INSERT INTO `t_option` (`id`, `qu_id`, `is_right`, `image`, `content`, `sort`, `is_deleted`) VALUES (2245, 578, 0, NULL, '2', 1, 0);
INSERT INTO `t_option` (`id`, `qu_id`, `is_right`, `image`, `content`, `sort`, `is_deleted`) VALUES (2246, 578, 1, NULL, '3', 2, 0);
INSERT INTO `t_option` (`id`, `qu_id`, `is_right`, `image`, `content`, `sort`, `is_deleted`) VALUES (2247, 578, 0, NULL, '4', 3, 0);
INSERT INTO `t_option` (`id`, `qu_id`, `is_right`, `image`, `content`, `sort`, `is_deleted`) VALUES (2248, 579, 0, NULL, '1', 0, 0);
INSERT INTO `t_option` (`id`, `qu_id`, `is_right`, `image`, `content`, `sort`, `is_deleted`) VALUES (2249, 579, 0, NULL, '2', 1, 0);
INSERT INTO `t_option` (`id`, `qu_id`, `is_right`, `image`, `content`, `sort`, `is_deleted`) VALUES (2250, 579, 0, NULL, '3', 2, 0);
INSERT INTO `t_option` (`id`, `qu_id`, `is_right`, `image`, `content`, `sort`, `is_deleted`) VALUES (2251, 579, 1, NULL, '4', 3, 0);
INSERT INTO `t_option` (`id`, `qu_id`, `is_right`, `image`, `content`, `sort`, `is_deleted`) VALUES (2252, 580, 1, NULL, 'I\'m fine.', 0, 0);
INSERT INTO `t_option` (`id`, `qu_id`, `is_right`, `image`, `content`, `sort`, `is_deleted`) VALUES (2253, 580, 0, NULL, 'OK!', 1, 0);
INSERT INTO `t_option` (`id`, `qu_id`, `is_right`, `image`, `content`, `sort`, `is_deleted`) VALUES (2254, 580, 0, NULL, 'Hi.', 2, 0);
INSERT INTO `t_option` (`id`, `qu_id`, `is_right`, `image`, `content`, `sort`, `is_deleted`) VALUES (2255, 580, 0, NULL, 'No!', 3, 0);
INSERT INTO `t_option` (`id`, `qu_id`, `is_right`, `image`, `content`, `sort`, `is_deleted`) VALUES (2256, 581, 1, NULL, '12', 0, 0);
INSERT INTO `t_option` (`id`, `qu_id`, `is_right`, `image`, `content`, `sort`, `is_deleted`) VALUES (2257, 581, 0, NULL, 'hi', 1, 0);
INSERT INTO `t_option` (`id`, `qu_id`, `is_right`, `image`, `content`, `sort`, `is_deleted`) VALUES (2258, 581, 0, NULL, 'fine', 2, 0);
INSERT INTO `t_option` (`id`, `qu_id`, `is_right`, `image`, `content`, `sort`, `is_deleted`) VALUES (2259, 581, 0, NULL, 'ok', 3, 0);
INSERT INTO `t_option` (`id`, `qu_id`, `is_right`, `image`, `content`, `sort`, `is_deleted`) VALUES (2260, 582, 1, NULL, '9', 0, 0);
INSERT INTO `t_option` (`id`, `qu_id`, `is_right`, `image`, `content`, `sort`, `is_deleted`) VALUES (2261, 582, 0, NULL, '8', 1, 0);
INSERT INTO `t_option` (`id`, `qu_id`, `is_right`, `image`, `content`, `sort`, `is_deleted`) VALUES (2262, 582, 0, NULL, '10', 2, 0);
INSERT INTO `t_option` (`id`, `qu_id`, `is_right`, `image`, `content`, `sort`, `is_deleted`) VALUES (2263, 582, 0, NULL, '5', 3, 0);
COMMIT;

-- ----------------------------
-- Table structure for t_question
-- ----------------------------
DROP TABLE IF EXISTS `t_question`;
CREATE TABLE `t_question` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'Question ID',
  `qu_type` varchar(255) NOT NULL COMMENT 'Question Type',
  `image` varchar(255) DEFAULT NULL COMMENT 'Question Image',
  `content` varchar(255) NOT NULL COMMENT 'Question Content',
  `create_time` datetime NOT NULL COMMENT 'Creation Time',
  `analysis` varchar(255) DEFAULT NULL COMMENT 'Question Analysis',
  `repo_id` int NOT NULL COMMENT 'Repository ID',
  `user_id` int DEFAULT NULL COMMENT 'User ID',
  `is_deleted` int NOT NULL DEFAULT '0' COMMENT 'Whether Deleted 0: No 1: Yes',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=583 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of t_question
-- ----------------------------
BEGIN;
INSERT INTO `t_question` (`id`, `qu_type`, `image`, `content`, `create_time`, `analysis`, `repo_id`, `user_id`, `is_deleted`) VALUES (578, '1', NULL, '1 + 2 = ?', '2024-06-09 14:52:18', NULL, 26, 33, 0);
INSERT INTO `t_question` (`id`, `qu_type`, `image`, `content`, `create_time`, `analysis`, `repo_id`, `user_id`, `is_deleted`) VALUES (579, '1', NULL, '2*2=?', '2024-06-09 16:18:21', NULL, 26, 33, 0);
INSERT INTO `t_question` (`id`, `qu_type`, `image`, `content`, `create_time`, `analysis`, `repo_id`, `user_id`, `is_deleted`) VALUES (580, '1', NULL, 'How are you?', '2024-06-09 20:04:14', NULL, 27, 34, 0);
INSERT INTO `t_question` (`id`, `qu_type`, `image`, `content`, `create_time`, `analysis`, `repo_id`, `user_id`, `is_deleted`) VALUES (581, '1', NULL, 'How old are you?', '2024-06-10 16:24:06', '', 27, 34, 0);
INSERT INTO `t_question` (`id`, `qu_type`, `image`, `content`, `create_time`, `analysis`, `repo_id`, `user_id`, `is_deleted`) VALUES (582, '1', NULL, '2 + 3 + 4 = ?', '2024-06-10 21:27:11', NULL, 26, 33, 0);
COMMIT;

-- ----------------------------
-- Table structure for t_repo
-- ----------------------------
DROP TABLE IF EXISTS `t_repo`;
CREATE TABLE `t_repo` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'Repository ID',
  `user_id` int NOT NULL COMMENT 'Creator ID',
  `title` varchar(255) NOT NULL COMMENT 'Repository Title',
  `create_time` datetime DEFAULT NULL COMMENT 'Creation Time',
  `is_deleted` int NOT NULL DEFAULT '0' COMMENT 'Whether Deleted 0: No 1: Yes',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of t_repo
-- ----------------------------
BEGIN;
INSERT INTO `t_repo` (`id`, `user_id`, `title`, `create_time`, `is_deleted`) VALUES (26, 33, 'Math', '2024-06-09 14:50:20', 0);
INSERT INTO `t_repo` (`id`, `user_id`, `title`, `create_time`, `is_deleted`) VALUES (27, 34, 'English', '2024-06-09 20:03:19', 0);
INSERT INTO `t_repo` (`id`, `user_id`, `title`, `create_time`, `is_deleted`) VALUES (28, 33, 'Test', '2024-06-10 20:58:15', 0);
COMMIT;

-- ----------------------------
-- Table structure for t_role
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'Role ID',
  `role_name` varchar(10) NOT NULL COMMENT 'Role Name',
  `code` varchar(10) NOT NULL COMMENT 'Role Code',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of t_role
-- ----------------------------
BEGIN;
INSERT INTO `t_role` (`id`, `role_name`, `code`) VALUES (1, 'Student', 'student');
INSERT INTO `t_role` (`id`, `role_name`, `code`) VALUES (2, 'Teacher', 'teacher');
INSERT INTO `t_role` (`id`, `role_name`, `code`) VALUES (3, 'Admin', 'admin');
COMMIT;

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'User ID',
  `user_name` varchar(255) NOT NULL COMMENT 'Username',
  `real_name` varchar(255) DEFAULT NULL COMMENT 'Real Name',
  `password` varchar(255) DEFAULT NULL COMMENT 'Password',
  `avatar` varchar(255) DEFAULT NULL COMMENT 'Avatar URL',
  `role_id` int DEFAULT '1' COMMENT 'Role ID',
  `grade_id` int DEFAULT NULL COMMENT 'Grade ID',
  `create_time` datetime DEFAULT NULL COMMENT 'Creation Time   YYYY-MM-DD hh:mm:ss',
  `status` int DEFAULT '1' COMMENT 'Status  1: Active, 0: Disabled',
  `is_deleted` int NOT NULL DEFAULT '0' COMMENT 'Whether Deleted 0: No 1: Yes',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `user_name` (`user_name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of t_user
-- ----------------------------
BEGIN;
INSERT INTO `t_user` (`id`, `user_name`, `real_name`, `password`, `avatar`, `role_id`, `grade_id`, `create_time`, `status`, `is_deleted`) VALUES (33, 'admin', 'admin', '$2a$10$JnIFDQtm5jFQopgS5LSQhedFCV82hf.Zf5LlSeVbZA1kxvA0gX3uu', NULL, 3, NULL, '2024-06-09 14:47:42', 1, 0);
INSERT INTO `t_user` (`id`, `user_name`, `real_name`, `password`, `avatar`, `role_id`, `grade_id`, `create_time`, `status`, `is_deleted`) VALUES (34, 'teacher', 'teacher', '$2a$10$WScUp1GdlaJiWoP3PMKzA.vTVYAguu79u/EWE8cFqCGyOnH45mqtq', NULL, 2, NULL, '2024-06-09 14:48:15', 1, 0);
INSERT INTO `t_user` (`id`, `user_name`, `real_name`, `password`, `avatar`, `role_id`, `grade_id`, `create_time`, `status`, `is_deleted`) VALUES (35, 'student', 'student', '$2a$10$yEWKkjaGt8fN8Vm8Wa2ms.Jhy6.PBMHBOy0NdpXtpKa3oFIsXFyFS', NULL, 1, 70, '2024-06-09 14:48:37', 1, 0);
INSERT INTO `t_user` (`id`, `user_name`, `real_name`, `password`, `avatar`, `role_id`, `grade_id`, `create_time`, `status`, `is_deleted`) VALUES (36, 'abcd', 'ABCD', '$2a$10$UArchBl21VCcvuxJITTcKuBs5qwdBSil1lgLnO0xwmkThyK5pQ2PC', NULL, 1, NULL, '2024-06-09 16:48:00', 1, 0);
INSERT INTO `t_user` (`id`, `user_name`, `real_name`, `password`, `avatar`, `role_id`, `grade_id`, `create_time`, `status`, `is_deleted`) VALUES (37, 'st1234', 'Nguyen Van Hung', '$2a$10$EBUPAzYEGK6hmGtvOzZLL.TNf8R.ITYBF0QvmwAYbBGNdmZSvioiq', NULL, 1, 70, '2024-06-10 17:17:01', 1, 0);
INSERT INTO `t_user` (`id`, `user_name`, `real_name`, `password`, `avatar`, `role_id`, `grade_id`, `create_time`, `status`, `is_deleted`) VALUES (38, 'test_user_01', 'abcdefacg', '$2a$10$OeXm19D5djq/8y5O9aAe7.QVRWzgect4LGPS66PNXUcQjMdWAPJPK', NULL, 1, NULL, '2024-06-10 21:20:56', 1, 0);
COMMIT;

-- ----------------------------
-- Table structure for t_user_book
-- ----------------------------
DROP TABLE IF EXISTS `t_user_book`;
CREATE TABLE `t_user_book` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID: Question Bank',
  `exam_id` int DEFAULT NULL COMMENT 'Exam ID: Unique',
  `user_id` int DEFAULT NULL COMMENT 'User ID: Unique',
  `qu_id` int DEFAULT NULL COMMENT 'Question ID: Unique',
  `create_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT 'Creation Time: YYYY-MM-DD hh:mm:ss',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=127 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of t_user_book
-- ----------------------------
BEGIN;
INSERT INTO `t_user_book` (`id`, `exam_id`, `user_id`, `qu_id`, `create_time`) VALUES (123, 49, 33, 579, '2024-06-09 18:15:56');
INSERT INTO `t_user_book` (`id`, `exam_id`, `user_id`, `qu_id`, `create_time`) VALUES (124, 49, 33, 578, '2024-06-09 18:15:56');
INSERT INTO `t_user_book` (`id`, `exam_id`, `user_id`, `qu_id`, `create_time`) VALUES (125, 49, 35, 578, '2024-06-10 16:53:37');
COMMIT;

-- ----------------------------
-- Table structure for t_user_daily_login_duration
-- ----------------------------
DROP TABLE IF EXISTS `t_user_daily_login_duration`;
CREATE TABLE `t_user_daily_login_duration` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'Primary key, auto-incremented',
  `user_id` int DEFAULT NULL COMMENT 'User ID, associated with the user table to ensure data consistency',
  `login_date` date DEFAULT NULL COMMENT 'Login date, records the login duration of users on a specific date',
  `total_seconds` int DEFAULT NULL COMMENT 'Total accumulated online seconds, daily total login duration in seconds',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of t_user_daily_login_duration
-- ----------------------------
BEGIN;
INSERT INTO `t_user_daily_login_duration` (`id`, `user_id`, `login_date`, `total_seconds`) VALUES (44, 33, '2024-06-09', 27426);
INSERT INTO `t_user_daily_login_duration` (`id`, `user_id`, `login_date`, `total_seconds`) VALUES (45, 34, '2024-06-09', 2097);
INSERT INTO `t_user_daily_login_duration` (`id`, `user_id`, `login_date`, `total_seconds`) VALUES (46, 33, '2024-06-10', 78176);
INSERT INTO `t_user_daily_login_duration` (`id`, `user_id`, `login_date`, `total_seconds`) VALUES (47, 34, '2024-06-10', 18418);
INSERT INTO `t_user_daily_login_duration` (`id`, `user_id`, `login_date`, `total_seconds`) VALUES (48, 35, '2024-06-10', 1499);
INSERT INTO `t_user_daily_login_duration` (`id`, `user_id`, `login_date`, `total_seconds`) VALUES (49, 37, '2024-06-10', 8742);
COMMIT;

-- ----------------------------
-- Table structure for t_user_exams_score
-- ----------------------------
DROP TABLE IF EXISTS `t_user_exams_score`;
CREATE TABLE `t_user_exams_score` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'User Exam Score ID',
  `user_id` int DEFAULT NULL COMMENT 'User ID  Unique',
  `exam_id` int DEFAULT NULL COMMENT 'Exam Paper ID  Unique',
  `total_time` int DEFAULT NULL COMMENT 'Total Duration  YYYY-MM-DD hh:mm:ss',
  `user_time` int DEFAULT NULL COMMENT 'User Time Spent  YYYY-MM-DD hh:mm:ss',
  `user_score` int unsigned DEFAULT '0' COMMENT 'User Score',
  `limit_time` datetime DEFAULT NULL COMMENT 'Submission Time  YYYY-MM-DD hh:mm:ss',
  `count` int DEFAULT NULL COMMENT 'Screen Switching Count',
  `state` int DEFAULT NULL COMMENT 'State   0: In Progress 1: Completed',
  `create_time` datetime DEFAULT NULL COMMENT 'Creation Time  YYYY-MM-DD hh:mm:ss',
  `whether_mark` int DEFAULT NULL COMMENT 'Whether Graded -1: No essay questions, 0: Not Graded, 1: Graded',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=95 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of t_user_exams_score
-- ----------------------------
BEGIN;
INSERT INTO `t_user_exams_score` (`id`, `user_id`, `exam_id`, `total_time`, `user_time`, `user_score`, `limit_time`, `count`, `state`, `create_time`, `whether_mark`) VALUES (90, 33, 49, 5, NULL, 0, NULL, NULL, 0, '2024-06-10 17:57:14', NULL);
INSERT INTO `t_user_exams_score` (`id`, `user_id`, `exam_id`, `total_time`, `user_time`, `user_score`, `limit_time`, `count`, `state`, `create_time`, `whether_mark`) VALUES (91, 33, 51, 5, 34, 10, '2024-06-10 17:59:42', NULL, 1, '2024-06-10 17:59:07', -1);
INSERT INTO `t_user_exams_score` (`id`, `user_id`, `exam_id`, `total_time`, `user_time`, `user_score`, `limit_time`, `count`, `state`, `create_time`, `whether_mark`) VALUES (92, 37, 52, 5, 11, 10, '2024-06-10 19:17:07', NULL, 1, '2024-06-10 19:16:55', -1);
INSERT INTO `t_user_exams_score` (`id`, `user_id`, `exam_id`, `total_time`, `user_time`, `user_score`, `limit_time`, `count`, `state`, `create_time`, `whether_mark`) VALUES (93, 33, 52, 5, 28, 10, '2024-06-10 21:22:29', NULL, 1, '2024-06-10 21:22:00', -1);
INSERT INTO `t_user_exams_score` (`id`, `user_id`, `exam_id`, `total_time`, `user_time`, `user_score`, `limit_time`, `count`, `state`, `create_time`, `whether_mark`) VALUES (94, 37, 53, 5, 33, 5, '2024-06-10 21:35:11', NULL, 1, '2024-06-10 21:34:37', -1);
COMMIT;

-- ----------------------------
-- Table structure for t_user_exercise_record
-- ----------------------------
DROP TABLE IF EXISTS `t_user_exercise_record`;
CREATE TABLE `t_user_exercise_record` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'User Exercise Record ID',
  `user_id` int DEFAULT NULL COMMENT 'User ID',
  `repo_id` int DEFAULT NULL COMMENT 'Repository ID',
  `total_count` int DEFAULT NULL COMMENT 'Total Questions',
  `exercise_count` int DEFAULT NULL COMMENT 'Questions Attempted',
  `create_time` datetime DEFAULT NULL COMMENT 'Exercise Time',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of t_user_exercise_record
-- ----------------------------
BEGIN;
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
