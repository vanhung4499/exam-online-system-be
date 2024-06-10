-- ----------------------------
-- Table structure for t_certificate
-- ----------------------------
DROP TABLE IF EXISTS `t_certificate`;
CREATE TABLE `t_certificate` (
                                 `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Certificate ID',
                                 `certificate_name` varchar(255) NOT NULL COMMENT 'Certificate Name',
                                 `image` varchar(255) DEFAULT NULL COMMENT 'Background Image',
                                 `certification_unit` varchar(50) NOT NULL COMMENT 'Certification Unit',
                                 `create_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT 'Creation Time',
                                 `is_deleted` int(11) NOT NULL DEFAULT '0' COMMENT 'Whether Deleted 0: No 1: Yes',
                                 PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;


-- ----------------------------
-- Table structure for t_certificate_user
-- ----------------------------
DROP TABLE IF EXISTS `t_certificate_user`;
CREATE TABLE `t_certificate_user` (
                                      `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Certificate and User Relationship Table ID',
                                      `user_id` int(11) DEFAULT NULL COMMENT 'User ID',
                                      `exam_id` int(11) DEFAULT NULL COMMENT 'Exam ID',
                                      `code` varchar(255) DEFAULT NULL COMMENT 'Certificate Number',
                                      `certificate_id` int(11) DEFAULT NULL COMMENT 'Certificate ID',
                                      `create_time` datetime DEFAULT NULL COMMENT 'Award Time      YYYY-MM-DD hh:mm:ss',
                                      PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;


-- ----------------------------
-- Table structure for t_exam
-- ----------------------------
DROP TABLE IF EXISTS `t_exam`;
CREATE TABLE `t_exam` (
                          `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Exam ID',
                          `title` varchar(255) NOT NULL COMMENT 'Exam Name',
                          `exam_duration` int(11) NOT NULL COMMENT 'Exam Duration',
                          `passed_score` int(11) NOT NULL COMMENT 'Passing Score',
                          `gross_score` int(11) NOT NULL COMMENT 'Total Score',
                          `max_count` int(11) DEFAULT NULL COMMENT 'Maximum Screen Cuts',
                          `user_id` int(11) DEFAULT NULL COMMENT 'Creator ID',
                          `certificate_id` int(11) DEFAULT NULL COMMENT 'Certificate ID',
                          `radio_count` int(11) DEFAULT NULL COMMENT 'Number of Single Choice Questions',
                          `radio_score` int(11) DEFAULT NULL COMMENT 'Single Choice Question Score     Stored in the database * 100, normal input and display in frontend / 100',
                          `multi_count` int(11) DEFAULT NULL COMMENT 'Number of Multiple Choice Questions',
                          `multi_score` int(11) DEFAULT NULL COMMENT 'Multiple Choice Question Score     Stored in the database * 100, normal input and display in frontend / 100',
                          `judge_count` int(11) DEFAULT NULL COMMENT 'Number of True/False Questions',
                          `judge_score` int(11) DEFAULT NULL COMMENT 'True/False Question Score     Stored in the database * 100, normal input and display in frontend / 100',
                          `saq_count` int(11) DEFAULT NULL COMMENT 'Number of Short Answer Questions',
                          `saq_score` int(11) DEFAULT NULL COMMENT 'Short Answer Question Score     Stored in the database * 100, normal input and display in frontend / 100',
                          `start_time` datetime DEFAULT NULL COMMENT 'Start Time     YYYY-MM-DD hh:mm:ss',
                          `end_time` datetime DEFAULT NULL COMMENT 'End Time     YYYY-MM-DD hh:mm:ss',
                          `create_time` datetime DEFAULT NULL COMMENT 'Creation Time     YYYY-MM-DD hh:mm:ss  ',
                          `is_deleted` int(11) NOT NULL DEFAULT '0' COMMENT 'Whether Deleted 0: No 1: Yes',
                          PRIMARY KEY (`id`,`passed_score`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;


-- ----------------------------
-- Table structure for t_exam_grade
-- ----------------------------
DROP TABLE IF EXISTS `t_exam_grade`;
CREATE TABLE `t_exam_grade` (
                                `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Exam and Grade Relationship Table ID',
                                `exam_id` int(11) DEFAULT NULL COMMENT 'Exam ID  Unique',
                                `grade_id` int(11) DEFAULT NULL COMMENT 'Grade ID  Unique',
                                PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=62 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;


-- ----------------------------
-- Table structure for t_exam_qu_answer
-- ----------------------------
DROP TABLE IF EXISTS `t_exam_qu_answer`;
CREATE TABLE `t_exam_qu_answer` (
                                    `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Exam Record Answers ID',
                                    `user_id` int(11) NOT NULL COMMENT 'User ID',
                                    `exam_id` int(11) NOT NULL COMMENT 'Exam ID',
                                    `question_id` int(11) NOT NULL COMMENT 'Question ID',
                                    `question_type` int(11) DEFAULT NULL COMMENT 'Question Type',
                                    `answer_id` varchar(10) DEFAULT NULL COMMENT 'Answer ID  Used for objective questions, multiple choice question IDs are separated by ","',
                                    `answer_content` varchar(255) DEFAULT NULL COMMENT 'Answer Content    Used for subjective questions',
                                    `checkout` int(11) DEFAULT NULL COMMENT 'Whether Selected   0 Not Selected  1 Selected',
                                    `is_sign` int(11) DEFAULT NULL COMMENT 'Whether Marked   0 Not Marked  1 Marked',
                                    `is_right` int(11) DEFAULT NULL COMMENT 'Whether Correct   Used for objective questions, 0 Incorrect 1 Correct',
                                    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for t_exam_question
-- ----------------------------
DROP TABLE IF EXISTS `t_exam_question`;
CREATE TABLE `t_exam_question` (
                                   `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Exam Question ID',
                                   `exam_id` int(11) NOT NULL COMMENT 'Exam ID',
                                   `question_id` int(11) NOT NULL COMMENT 'Question ID',
                                   `score` int(11) NOT NULL COMMENT 'Score',
                                   `sort` int(11) DEFAULT NULL COMMENT 'Sorting',
                                   `type` int(11) DEFAULT NULL COMMENT 'Question Type',
                                   PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=137 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;


-- ----------------------------
-- Table structure for t_exam_repo
-- ----------------------------
DROP TABLE IF EXISTS `t_exam_repo`;
CREATE TABLE `t_exam_repo` (
                               `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Exam and Repository ID',
                               `exam_id` int(11) NOT NULL COMMENT 'Exam ID  Unique',
                               `repo_id` int(11) DEFAULT NULL COMMENT 'Repository ID  Unique',
                               PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;


-- ----------------------------
-- Table structure for t_exercise_record
-- ----------------------------
DROP TABLE IF EXISTS `t_exercise_record`;
CREATE TABLE `t_exercise_record` (
                                     `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Exercise Record ID',
                                     `repo_id` int(11) NOT NULL COMMENT 'Repository ID',
                                     `question_id` int(11) NOT NULL COMMENT 'Question ID',
                                     `user_id` int(11) NOT NULL COMMENT 'User ID',
                                     `answer` varchar(255) DEFAULT NULL COMMENT 'Subjective Answer',
                                     `question_type` int(11) NOT NULL COMMENT 'Question Type',
                                     `options` varchar(255) DEFAULT NULL COMMENT 'Objective Answer Collection  Used for objective questions, multiple choice question IDs are separated by ","',
                                     `is_right` int(11) DEFAULT NULL COMMENT 'Whether Objective Question is Correct',
                                     PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;


-- ----------------------------
-- Table structure for t_grade
-- ----------------------------
DROP TABLE IF EXISTS `t_grade`;
CREATE TABLE `t_grade` (
                           `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Grade ID',
                           `grade_name` varchar(255) NOT NULL COMMENT 'Grade Name',
                           `user_id` int(11) DEFAULT NULL COMMENT 'Creator ID',
                           `code` varchar(255) NOT NULL COMMENT 'Class Code',
                           `is_deleted` int(11) NOT NULL DEFAULT '0' COMMENT 'Whether Deleted 0: No 1: Yes',
                           PRIMARY KEY (`id`) USING BTREE,
                           UNIQUE KEY `grade_name` (`grade_name`) USING BTREE,
                           UNIQUE KEY `code` (`code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=70 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;


-- ----------------------------
-- Table structure for t_grade_exercise
-- ----------------------------
DROP TABLE IF EXISTS `t_grade_exercise`;
CREATE TABLE `t_grade_exercise` (
                                    `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Exercise ID',
                                    `repo_id` int(11) DEFAULT NULL COMMENT 'Repository ID',
                                    `grade_id` int(11) DEFAULT NULL COMMENT 'Grade ID',
                                    `user_id` int(11) DEFAULT NULL COMMENT 'Creator ID',
                                    `create_time` datetime DEFAULT NULL COMMENT 'Creation Time',
                                    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;


-- ----------------------------
-- Table structure for t_manual_score
-- ----------------------------
DROP TABLE IF EXISTS `t_manual_score`;
CREATE TABLE `t_manual_score` (
                                  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Manual Scoring ID',
                                  `exam_qu_answer_id` int(11) DEFAULT NULL COMMENT 'Exam Record Answer ID',
                                  `user_id` int(11) DEFAULT NULL COMMENT 'Grader ID',
                                  `score` int(11) DEFAULT NULL COMMENT 'Score',
                                  `create_time` datetime DEFAULT NULL COMMENT 'Grading Time',
                                  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;


-- ----------------------------
-- Table structure for t_notice
-- ----------------------------
DROP TABLE IF EXISTS `t_notice`;
CREATE TABLE `t_notice` (
                            `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Notice ID',
                            `title` varchar(255) NOT NULL COMMENT 'Notice Title',
                            `image` varchar(255) DEFAULT NULL COMMENT 'Image URL',
                            `content` text NOT NULL COMMENT 'Notice Content',
                            `user_id` int(11) DEFAULT NULL COMMENT 'Creating User ID   Unique',
                            `create_time` datetime DEFAULT NULL COMMENT 'Creation Time',
                            `is_deleted` int(11) NOT NULL DEFAULT '0' COMMENT 'Whether Deleted 0: No 1: Yes',
                            PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for t_notice_grade
-- ----------------------------
DROP TABLE IF EXISTS `t_notice_grade`;
CREATE TABLE `t_notice_grade` (
                                  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Notice Grade Association ID',
                                  `notice_id` int(11) DEFAULT NULL COMMENT 'Notice ID',
                                  `grade_id` int(11) DEFAULT NULL COMMENT 'Grade ID',
                                  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for t_option
-- ----------------------------
DROP TABLE IF EXISTS `t_option`;
CREATE TABLE `t_option` (
                            `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Option Answer ID',
                            `qu_id` int(11) NOT NULL COMMENT 'Question ID',
                            `is_right` int(11) DEFAULT NULL COMMENT 'Whether Correct',
                            `image` varchar(255) DEFAULT NULL COMMENT 'Image URL   0: Incorrect, 1: Correct',
                            `content` varchar(255) NOT NULL COMMENT 'Option Content',
                            `sort` int(11) DEFAULT NULL COMMENT 'Sorting',
                            `is_deleted` int(11) NOT NULL DEFAULT '0' COMMENT 'Whether Deleted 0: No 1: Yes',
                            PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2244 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for t_question
-- ----------------------------
DROP TABLE IF EXISTS `t_question`;
CREATE TABLE `t_question` (
                              `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Question ID',
                              `qu_type` varchar(255) NOT NULL COMMENT 'Question Type',
                              `image` varchar(255) DEFAULT NULL COMMENT 'Question Image',
                              `content` varchar(255) NOT NULL COMMENT 'Question Content',
                              `create_time` datetime NOT NULL COMMENT 'Creation Time',
                              `analysis` varchar(255) DEFAULT NULL COMMENT 'Question Analysis',
                              `repo_id` int(11) NOT NULL COMMENT 'Repository ID',
                              `user_id` int(11) DEFAULT NULL COMMENT 'User ID',
                              `is_deleted` int(11) NOT NULL DEFAULT '0' COMMENT 'Whether Deleted 0: No 1: Yes',
                              PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=578 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for t_repo
-- ----------------------------
DROP TABLE IF EXISTS `t_repo`;
CREATE TABLE `t_repo` (
                          `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Repository ID',
                          `user_id` int(11) NOT NULL COMMENT 'Creator ID',
                          `title` varchar(255) NOT NULL COMMENT 'Repository Title',
                          `create_time` datetime DEFAULT NULL COMMENT 'Creation Time',
                          `is_deleted` int(11) NOT NULL DEFAULT '0' COMMENT 'Whether Deleted 0: No 1: Yes',
                          PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;


-- ----------------------------
-- Table structure for t_role
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role` (
                          `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Role ID',
                          `role_name` varchar(10) NOT NULL COMMENT 'Role Name',
                          `code` varchar(10) NOT NULL COMMENT 'Role Code',
                          PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

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
                          `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'User ID',
                          `user_name` varchar(255) NOT NULL COMMENT 'Username',
                          `real_name` varchar(255) DEFAULT NULL COMMENT 'Real Name',
                          `password` varchar(255) DEFAULT NULL COMMENT 'Password',
                          `avatar` varchar(255) DEFAULT NULL COMMENT 'Avatar URL',
                          `role_id` int(11) DEFAULT '1' COMMENT 'Role ID',
                          `grade_id` int(11) DEFAULT NULL COMMENT 'Grade ID',
                          `create_time` datetime DEFAULT NULL COMMENT 'Creation Time   YYYY-MM-DD hh:mm:ss',
                          `status` int(11) DEFAULT '1' COMMENT 'Status  1: Active, 0: Disabled',
                          `is_deleted` int(11) NOT NULL DEFAULT '0' COMMENT 'Whether Deleted 0: No 1: Yes',
                          PRIMARY KEY (`id`) USING BTREE,
                          UNIQUE KEY `user_name` (`user_name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;


-- ----------------------------
-- Table structure for t_user_book
-- ----------------------------
DROP TABLE IF EXISTS `t_user_book`;
CREATE TABLE `t_user_book` (
                               `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID: Question Bank',
                               `exam_id` int(11) DEFAULT NULL COMMENT 'Exam ID: Unique',
                               `user_id` int(11) DEFAULT NULL COMMENT 'User ID: Unique',
                               `qu_id` int(11) DEFAULT NULL COMMENT 'Question ID: Unique',
                               `create_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT 'Creation Time: YYYY-MM-DD hh:mm:ss',
                               PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=122 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;


-- ----------------------------
-- Table structure for t_user_daily_login_duration
-- ----------------------------
DROP TABLE IF EXISTS `t_user_daily_login_duration`;
CREATE TABLE `t_user_daily_login_duration` (
                                               `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Primary key, auto-incremented',
                                               `user_id` int(11) DEFAULT NULL COMMENT 'User ID, associated with the user table to ensure data consistency',
                                               `login_date` date DEFAULT NULL COMMENT 'Login date, records the login duration of users on a specific date',
                                               `total_seconds` int(11) DEFAULT NULL COMMENT 'Total accumulated online seconds, daily total login duration in seconds',
                                               PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;


-- ----------------------------
-- Table structure for t_user_exams_score
-- ----------------------------
DROP TABLE IF EXISTS `t_user_exams_score`;
CREATE TABLE `t_user_exams_score` (
                                      `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'User Exam Score ID',
                                      `user_id` int(11) DEFAULT NULL COMMENT 'User ID  Unique',
                                      `exam_id` int(11) DEFAULT NULL COMMENT 'Exam Paper ID  Unique',
                                      `total_time` int(11) DEFAULT NULL COMMENT 'Total Duration  YYYY-MM-DD hh:mm:ss',
                                      `user_time` int(11) DEFAULT NULL COMMENT 'User Time Spent  YYYY-MM-DD hh:mm:ss',
                                      `user_score` int(11) unsigned DEFAULT '0' COMMENT 'User Score',
                                      `limit_time` datetime DEFAULT NULL COMMENT 'Submission Time  YYYY-MM-DD hh:mm:ss',
                                      `count` int(11) DEFAULT NULL COMMENT 'Screen Switching Count',
                                      `state` int(11) DEFAULT NULL COMMENT 'State   0: In Progress 1: Completed',
                                      `create_time` datetime DEFAULT NULL COMMENT 'Creation Time  YYYY-MM-DD hh:mm:ss',
                                      `whether_mark` int(11) DEFAULT NULL COMMENT 'Whether Graded -1: No essay questions, 0: Not Graded, 1: Graded',
                                      PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=71 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;


-- ----------------------------
-- Table structure for t_user_exercise_record
-- ----------------------------
DROP TABLE IF EXISTS `t_user_exercise_record`;
CREATE TABLE `t_user_exercise_record` (
                                          `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'User Exercise Record ID',
                                          `user_id` int(11) DEFAULT NULL COMMENT 'User ID',
                                          `repo_id` int(11) DEFAULT NULL COMMENT 'Repository ID',
                                          `total_count` int(11) DEFAULT NULL COMMENT 'Total Questions',
                                          `exercise_count` int(11) DEFAULT NULL COMMENT 'Questions Attempted',
                                          `create_time` datetime DEFAULT NULL COMMENT 'Exercise Time',
                                          PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;
