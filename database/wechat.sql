/*
Navicat MySQL Data Transfer

Source Server         : docker
Source Server Version : 50642
Source Host           : 192.168.0.69:12345
Source Database       : wechat

Target Server Type    : MYSQL
Target Server Version : 50642
File Encoding         : 65001

Date: 2019-02-20 17:05:00
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `userName` varchar(128) NOT NULL,
  `password` varchar(128) NOT NULL,
  `tokenStartTime` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO user VALUES ('1', 'u1', '111111', '123');
INSERT INTO user VALUES ('2', 'user2', '111', '12321423');
