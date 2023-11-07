CREATE DATABASE `videoplayer` /*!40100 DEFAULT CHARACTER SET utf8 */;

-- videoplayer.video definition

CREATE TABLE `video` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `title` varchar(100) NOT NULL,
  `category_id` bigint(20) NOT NULL,
  `like_count` bigint(20) NOT NULL DEFAULT '0',
  `description` varchar(100) DEFAULT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `video_url` varchar(800) DEFAULT NULL,
  `cover_url` varchar(800) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `video_FK` (`category_id`),
  CONSTRAINT `video_FK` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=56 DEFAULT CHARSET=utf8;


-- videoplayer.`user` definition

CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `username` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '用户名',
  `password` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '密码',
  `avatar_path` varchar(255) DEFAULT NULL COMMENT '头像存储路径',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UNIQUE` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=69 DEFAULT CHARSET=utf8;


-- videoplayer.category definition

CREATE TABLE `category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `category_name` varchar(100) NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;


-- videoplayer.collection definition

CREATE TABLE `collection` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `collection_name` varchar(25) NOT NULL COMMENT '收藏夹名称',
  `user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;


-- videoplayer.collection_video definition

CREATE TABLE `collection_video` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `collection_id` bigint(20) NOT NULL COMMENT '收藏夹id',
  `video_id` bigint(20) NOT NULL COMMENT '收藏的视频id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uni_col_video` (`collection_id`,`video_id`),
  KEY `collection_video_video_id_fk` (`video_id`),
  CONSTRAINT `collection_video_collection_id_fk` FOREIGN KEY (`collection_id`) REFERENCES `collection` (`id`),
  CONSTRAINT `collection_video_video_id_fk` FOREIGN KEY (`video_id`) REFERENCES `video` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COMMENT='收藏夹与视频关系表';


-- videoplayer.comment definition

CREATE TABLE `comment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `content` varchar(800) DEFAULT NULL,
  `user_id` bigint(20) NOT NULL,
  `entity_type` varchar(100) NOT NULL,
  `entity_id` bigint(20) NOT NULL,
  `target_id` bigint(20) NOT NULL DEFAULT '-1',
  `status` bigint(20) NOT NULL DEFAULT '0',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `comment_FK` (`user_id`),
  CONSTRAINT `comment_FK` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;


-- videoplayer.creates definition

CREATE TABLE `creates` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `video_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `userid_fk` (`user_id`),
  KEY `videoid_fk` (`video_id`),
  CONSTRAINT `creates_userid_fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `creates_videoid_fk` FOREIGN KEY (`video_id`) REFERENCES `video` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8;


-- videoplayer.follows definition

CREATE TABLE `follows` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `follower_id` bigint(20) NOT NULL,
  `following_id` bigint(20) NOT NULL COMMENT '被关注的人',
  PRIMARY KEY (`id`),
  KEY `follower_pk` (`follower_id`),
  KEY `following_fk` (`following_id`),
  CONSTRAINT `follower_pk` FOREIGN KEY (`follower_id`) REFERENCES `user` (`id`),
  CONSTRAINT `following_fk` FOREIGN KEY (`following_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=67 DEFAULT CHARSET=utf8;


-- videoplayer.likes definition

CREATE TABLE `likes` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `video_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `userid_fk` (`user_id`),
  KEY `videoid_fk` (`video_id`),
  CONSTRAINT `userid_fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `videoid_fk` FOREIGN KEY (`video_id`) REFERENCES `video` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=59 DEFAULT CHARSET=utf8;


-- videoplayer.message definition

CREATE TABLE `message` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `from_id` bigint(20) NOT NULL,
  `to_id` bigint(20) NOT NULL,
  `conversation_type` varchar(100) NOT NULL,
  `content` varchar(800) DEFAULT NULL,
  `status` bigint(20) NOT NULL DEFAULT '0',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `message_FK` (`from_id`),
  KEY `message_FK_1` (`to_id`),
  CONSTRAINT `message_FK` FOREIGN KEY (`from_id`) REFERENCES `user` (`id`),
  CONSTRAINT `message_FK_1` FOREIGN KEY (`to_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;
