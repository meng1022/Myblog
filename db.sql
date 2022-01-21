/*
 Navicat Premium Data Transfer

 Source Server         : mydatabase
 Source Server Type    : MySQL
 Source Server Version : 80011
 Source Host           : localhost:3306
 Source Schema         : myblog

 Target Server Type    : MySQL
 Target Server Version : 80011
 File Encoding         : 65001

 Date: 21/01/2022 15:06:08
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for article_module
-- ----------------------------
DROP TABLE IF EXISTS `article_module`;
CREATE TABLE `article_module`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `articleid` bigint(11) NOT NULL,
  `moduleid` bigint(11) NULL DEFAULT NULL,
  `create_time` bigint(20) NULL DEFAULT NULL,
  `update_time` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `article_module_ibfk_1`(`articleid`) USING BTREE,
  INDEX `moduleid`(`moduleid`) USING BTREE,
  CONSTRAINT `article_module_ibfk_1` FOREIGN KEY (`articleid`) REFERENCES `articles` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `article_module_ibfk_2` FOREIGN KEY (`moduleid`) REFERENCES `modules` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 24 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of article_module
-- ----------------------------
INSERT INTO `article_module` VALUES (18, 20, 12, NULL, NULL);
INSERT INTO `article_module` VALUES (19, 20, 11, NULL, NULL);
INSERT INTO `article_module` VALUES (20, 21, 13, NULL, NULL);
INSERT INTO `article_module` VALUES (21, 21, 2, NULL, NULL);
INSERT INTO `article_module` VALUES (22, 22, 2, NULL, NULL);
INSERT INTO `article_module` VALUES (23, 26, 2, 1642632071035, NULL);

-- ----------------------------
-- Table structure for articles
-- ----------------------------
DROP TABLE IF EXISTS `articles`;
CREATE TABLE `articles`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `content` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_time` bigint(20) NULL DEFAULT NULL,
  `update_time` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 27 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of articles
-- ----------------------------
INSERT INTO `articles` VALUES (20, 'How to recover database using idb and frm files', '### For MYSQL database with innodb as the database engine, every time you create a table into databse, a frm file will be created to store the structure of that table, and idb will be generated to store the entries in this table. Thus, we could resume a database as long as we have the .frm and .idb files.\n\n### In this article, the whole process of restoring a database via frm and idb files will be demonstrated. The whold process comprises 3 steps:\n* ### restore table structure\n* ### restore data entries\n\n### Structure\n- To restore the table structure, we need to use .frm files. We could either execute this command in command line terminal for each .frm files and get the sql to regenerate the table structure:\n   ```j\n    mysqlfrm --diagnostic [.frm file path]\n   ```\n  or we could execute this .py program to output sql that restores table structe to a text file:\n   ```j\n    import sys\n    import os\n\n    if __name__ == \'__main__\':\n        # directory path of .frm files\n        filePath = \'D:/UndergraduateNotes/graduationProjects/exam_system/\'\n        files = []\n        for file in os.listdir(filePath):\n            if os.path.splitext(file)[1] == \'.frm\':\n                files.append(file)\n    \n        for file in files:\n            command = \'mysqlfrm --diagnostic D:/UndergraduateNotes/graduationProjects/exam_system/\' + file + \' >> D:/UndergraduateNotes/graduationProjects/test.txt\'\n        os.system(command)\n   ```\n    After doing that, you should execute sql files generate to restore the table structures.\n### Data\n- To restore the data, for example, a table named user. We need to execute this command in the corresponding database:\n    ```j\n    ALTER TABLE user DISCARD TABLESPACE\n    ```\n- And next, we copy and paste our original user.idb file to the folder of our new databse, and execute the command below:\n    ```j\n    ALTER TABLE user IMPORT TABLESPACE\n    ```\n- You will see the data has been recoverd in database.', 1642448195532, 1642448899125);
INSERT INTO `articles` VALUES (21, 'How to integrate GitHub login interface to your own blog (OAuth 2.0)', 'This article list principle steps to integrate GitHub login interface to self blog. Basically, the whole process consists of 3 steps:\n- Users consent to give authorization to blog to access users\' basic github profiles\n- Blog request access_token from GitHub\n- Using access_token to get users\' basic github information\n\n#### First, we need to register our application at GitHub, click the \"New OAuth App\" at settings/Developer settings/OAuth Apps. We need to input our application name, application homepage URL, and Authorization callback URL. The Authorization callback URL is the page which shows to users after they have consented to give authorizations to our blog.\n\n#### After we create our OAuth application, a client id and client secrets will be generated, which we need to remember.\n\n#### If you want your blog users to give authorizations after they click a button \"login\", you should configure this button with a href=\"https://github.com/login/oauth/authorize?client_id=[your_client_id]\". This is my login button:\n```j\n<Button href=\"https://github.com/login/oauth/authorize?client_id=***\">\n    <span >Sign In</span>\n</Button>\n```\n\n#### If the user agrees to give authorization to your application, the application will be redirected to your callback URL we mentioned before with a code parameter, which is similar to this:\n```j\nhttp://localhost:3000/?code=****\n```\n\n#### Now, we need to use this code to request access_token. What is important is that, we are not allowed to request the access_token at frontend directly, but we need to request from backend. The reason is that during this process we need to claim our client_id and client_secret in the URL, which is not secure. So we need to send POST Http Request from backend, code in java:\n```j\npublic String getToken(String code) throws Exception{\n        String get_token_url = \"https://github.com/login/oauth/access_token?client_id=***&\"\n                +\"client_secret=***&\"\n                +\"code=\"+code;\n        CloseableHttpClient httpClient = HttpClientBuilder.create().build();\n        HttpPost httpPost = new HttpPost(url);\n        CloseableHttpResponse response = httpClient.execute(httpPost);\n        HttpEntity responseEntity = response.getEntity();\n        String responseStr = EntityUtils.toString(responseEntity);\n        String[] responses = responseStr.split(\"&\");\n        int index = responses[0].indexOf(\"access_token\");\n        if(index==-1)\n            throw new Exception(\"no access_token\");\n        else{\n            String access_token = responses[0].substring(\"access_token=\".length());\n            return access_token;\n        }\n    }\n```\n\n#### Life will get easier after we get access_token, we could request user info by request the following URL, with corresponding header\n```j\nGET https://api.github.com/user\n\"Authorization\": token [access_token] \n```\n\n', 1642449277762, 1642472903065);
INSERT INTO `articles` VALUES (22, '“temporary upload location is not valid”', '[D:\\Blog\\springmvcblog\\backend\\tomcat.8080\\work\\Tomcat\\localhost\\ROOT\n\\backend\\src\\main\\webapp\\static\\images] is not valid', 1642525281811, 1642541088550);
INSERT INTO `articles` VALUES (26, 'title', 'content', 1642632070906, NULL);

-- ----------------------------
-- Table structure for gituser
-- ----------------------------
DROP TABLE IF EXISTS `gituser`;
CREATE TABLE `gituser`  (
  `userid` bigint(20) NOT NULL,
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`userid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of gituser
-- ----------------------------
INSERT INTO `gituser` VALUES (84921724, 'meng1022');

-- ----------------------------
-- Table structure for modules
-- ----------------------------
DROP TABLE IF EXISTS `modules`;
CREATE TABLE `modules`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `modulename` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_time` bigint(20) NULL DEFAULT NULL,
  `update_time` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `modulename`(`modulename`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of modules
-- ----------------------------
INSERT INTO `modules` VALUES (2, 'Java', 1642448195532, NULL);
INSERT INTO `modules` VALUES (11, 'Database', 1642449277762, NULL);
INSERT INTO `modules` VALUES (12, 'Mysql', 1642525281811, NULL);
INSERT INTO `modules` VALUES (13, 'OAuth 2.0', 1642632070906, NULL);
INSERT INTO `modules` VALUES (14, '111', 1642706057859, NULL);

SET FOREIGN_KEY_CHECKS = 1;
