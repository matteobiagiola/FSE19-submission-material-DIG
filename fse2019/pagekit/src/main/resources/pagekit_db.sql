# ************************************************************
# Sequel Pro SQL dump
# Version 4541
#
# http://www.sequelpro.com/
# https://github.com/sequelpro/sequelpro
#
# Host: 127.0.0.1 (MySQL 5.7.20-0ubuntu0.16.04.1)
# Database: pagekit
# Generation Time: 2017-12-29 10:57:23 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table pk_system_auth
# ------------------------------------------------------------

DROP TABLE IF EXISTS `pk_system_auth`;

CREATE TABLE `pk_system_auth` (
  `id` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `user_id` int(10) unsigned NOT NULL DEFAULT '0',
  `access` datetime DEFAULT NULL,
  `status` smallint(6) NOT NULL,
  `data` longtext COLLATE utf8_unicode_ci COMMENT '(DC2Type:json_array)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

LOCK TABLES `pk_system_auth` WRITE;
/*!40000 ALTER TABLE `pk_system_auth` DISABLE KEYS */;

INSERT INTO `pk_system_auth` (`id`, `user_id`, `access`, `status`, `data`)
VALUES
	('254bec04da70235386e7196b25bddd04dd914271',1,'2017-12-28 22:29:42',1,'{\"ip\":\"10.0.2.2\",\"user-agent\":\"Mozilla\\/5.0 (Macintosh; Intel Mac OS X 10_12_6) AppleWebKit\\/537.36 (KHTML, like Gecko) Chrome\\/63.0.3239.84 Safari\\/537.36\"}');

/*!40000 ALTER TABLE `pk_system_auth` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table pk_system_config
# ------------------------------------------------------------

DROP TABLE IF EXISTS `pk_system_config`;

CREATE TABLE `pk_system_config` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `value` longtext COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `pk_SYSTEM_CONFIG_NAME` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

LOCK TABLES `pk_system_config` WRITE;
/*!40000 ALTER TABLE `pk_system_config` DISABLE KEYS */;

INSERT INTO `pk_system_config` (`id`, `name`, `value`)
VALUES
	(1,'system/dashboard','{\"55dda578e93b5\":{\"type\":\"location\",\"column\":1,\"idx\":0,\"units\":\"metric\",\"id\":\"55dda578e93b5\",\"uid\":2911298,\"city\":\"Hamburg\",\"country\":\"DE\",\"coords\":{\"lon\":10,\"lat\":53.549999}},\"55dda581d5781\":{\"type\":\"feed\",\"column\":2,\"idx\":0,\"count\":5,\"content\":\"1\",\"id\":\"55dda581d5781\",\"title\":\"Pagekit News\",\"url\":\"http:\\/\\/pagekit.com\\/blog\\/feed\"},\"55dda6e3dd661\":{\"type\":\"user\",\"column\":0,\"idx\":100,\"show\":\"registered\",\"display\":\"thumbnail\",\"total\":\"1\",\"count\":12,\"id\":\"55dda6e3dd661\"}}'),
	(2,'system/site','{\"menus\":{\"main\":{\"id\":\"main\",\"label\":\"Main\"}},\"title\":\"Testing\",\"frontpage\":1,\"view\":{\"logo\":\"storage\\/pagekit-logo.svg\"}}'),
	(3,'system','{\"admin\":{\"locale\":\"en_GB\"},\"site\":{\"locale\":\"en_GB\"},\"version\":\"1.0.13\"}'),
	(6,'theme-one','{\"logo_contrast\":\"storage\\/pagekit-logo-contrast.svg\",\"_menus\":{\"main\":\"main\",\"offcanvas\":\"main\"},\"_positions\":{\"hero\":[1],\"footer\":[2]},\"_widgets\":{\"1\":{\"title_hide\":true,\"title_size\":\"uk-panel-title\",\"alignment\":true,\"html_class\":\"\",\"panel\":\"\"},\"2\":{\"title_hide\":true,\"title_size\":\"uk-panel-title\",\"alignment\":\"true\",\"html_class\":\"\",\"panel\":\"\"}},\"_nodes\":{\"1\":{\"title_hide\":true,\"title_large\":false,\"alignment\":true,\"html_class\":\"\",\"sidebar_first\":false,\"hero_image\":\"storage\\/home-hero.jpg\",\"hero_viewport\":true,\"hero_contrast\":true,\"navbar_transparent\":true}}}');

/*!40000 ALTER TABLE `pk_system_config` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table pk_system_node
# ------------------------------------------------------------

DROP TABLE IF EXISTS `pk_system_node`;

CREATE TABLE `pk_system_node` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `parent_id` int(10) unsigned NOT NULL DEFAULT '0',
  `priority` int(11) NOT NULL DEFAULT '0',
  `status` smallint(6) NOT NULL,
  `title` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `slug` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `path` varchar(1023) COLLATE utf8_unicode_ci NOT NULL,
  `link` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `type` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `menu` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `roles` longtext COLLATE utf8_unicode_ci COMMENT '(DC2Type:simple_array)',
  `data` longtext COLLATE utf8_unicode_ci COMMENT '(DC2Type:json_array)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

LOCK TABLES `pk_system_node` WRITE;
/*!40000 ALTER TABLE `pk_system_node` DISABLE KEYS */;

INSERT INTO `pk_system_node` (`id`, `parent_id`, `priority`, `status`, `title`, `slug`, `path`, `link`, `type`, `menu`, `roles`, `data`)
VALUES
	(1,0,1,1,'Home','home','/home','@page/1','page','main',NULL,'{\"defaults\":{\"id\":1}}'),
	(2,0,2,1,'Blog','blog','/blog','@blog','blog','main',NULL,NULL);

/*!40000 ALTER TABLE `pk_system_node` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table pk_system_page
# ------------------------------------------------------------

DROP TABLE IF EXISTS `pk_system_page`;

CREATE TABLE `pk_system_page` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `content` longtext COLLATE utf8_unicode_ci NOT NULL,
  `data` longtext COLLATE utf8_unicode_ci COMMENT '(DC2Type:json_array)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

LOCK TABLES `pk_system_page` WRITE;
/*!40000 ALTER TABLE `pk_system_page` DISABLE KEYS */;

INSERT INTO `pk_system_page` (`id`, `title`, `content`, `data`)
VALUES
	(1,'Home','<div class=\"uk-width-medium-3-4 uk-container-center\">\n    \n<h3 class=\"uk-h1 uk-margin-large-bottom\">Uniting fresh design and clean code<br class=\"uk-hidden-small\"> to create beautiful websites.</h3>\n\n<p class=\"uk-width-medium-4-6 uk-container-center\">Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.</p>\n\n</div>','{\"title\":true}');

/*!40000 ALTER TABLE `pk_system_page` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table pk_system_role
# ------------------------------------------------------------

DROP TABLE IF EXISTS `pk_system_role`;

CREATE TABLE `pk_system_role` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `priority` int(11) NOT NULL DEFAULT '0',
  `permissions` longtext COLLATE utf8_unicode_ci COMMENT '(DC2Type:simple_array)',
  PRIMARY KEY (`id`),
  UNIQUE KEY `pk_SYSTEM_ROLE_NAME` (`name`),
  KEY `pk_SYSTEM_ROLE_NAME_PRIORITY` (`name`,`priority`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

LOCK TABLES `pk_system_role` WRITE;
/*!40000 ALTER TABLE `pk_system_role` DISABLE KEYS */;

INSERT INTO `pk_system_role` (`id`, `name`, `priority`, `permissions`)
VALUES
	(1,'Anonymous',0,NULL),
	(2,'Authenticated',1,'blog: post comments'),
	(3,'Administrator',2,NULL);

/*!40000 ALTER TABLE `pk_system_role` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table pk_system_session
# ------------------------------------------------------------

DROP TABLE IF EXISTS `pk_system_session`;

CREATE TABLE `pk_system_session` (
  `id` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `time` datetime NOT NULL,
  `data` text COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

LOCK TABLES `pk_system_session` WRITE;
/*!40000 ALTER TABLE `pk_system_session` DISABLE KEYS */;

INSERT INTO `pk_system_session` (`id`, `time`, `data`)
VALUES
	('6odhvul3e3ermtifa14fh9gfb6','2017-12-28 22:21:41','X3NmMl9hdHRyaWJ1dGVzfGE6MTp7czo1OiJfY3NyZiI7czo0MDoiNWU2YzIyYTIxNzRmNjE0YWE3M2ZmYTg2Y2UzNzQyMWJjY2E5NTQ4ZiI7fV9zZjJfZmxhc2hlc3xhOjA6e31fcGtfbWVzc2FnZXN8YToyOntzOjc6ImRpc3BsYXkiO2E6MDp7fXM6MzoibmV3IjthOjA6e319X3NmMl9tZXRhfGE6Mzp7czoxOiJ1IjtpOjE1MTQ0OTk3MDE7czoxOiJjIjtpOjE1MTQ0OTk2ODk7czoxOiJsIjtzOjE6IjAiO30='),
	('ibjga4v8mtp7eivt8t6si72sg1','2017-12-28 22:12:41','X3NmMl9hdHRyaWJ1dGVzfGE6MTp7czo1OiJfY3NyZiI7czo0MDoiMTQyYTk0OWY5NzBlYTBkNjgwODFhOTZiZTU0NDJmN2JhYmQ1ZWQwYyI7fV9zZjJfZmxhc2hlc3xhOjA6e31fcGtfbWVzc2FnZXN8YToyOntzOjc6ImRpc3BsYXkiO2E6MDp7fXM6MzoibmV3IjthOjA6e319X3NmMl9tZXRhfGE6Mzp7czoxOiJ1IjtpOjE1MTQ0OTkxNjA7czoxOiJjIjtpOjE1MTQ0OTkxNjA7czoxOiJsIjtzOjE6IjAiO30='),
	('omohf01di89hjpvehu603g0gp1','2017-12-28 22:29:43','X3NmMl9hdHRyaWJ1dGVzfGE6MTp7czo1OiJfY3NyZiI7czo0MDoiNWU2YzIyYTIxNzRmNjE0YWE3M2ZmYTg2Y2UzNzQyMWJjY2E5NTQ4ZiI7fV9zZjJfZmxhc2hlc3xhOjA6e31fcGtfbWVzc2FnZXN8YToyOntzOjc6ImRpc3BsYXkiO2E6MDp7fXM6MzoibmV3IjthOjA6e319X3NmMl9tZXRhfGE6Mzp7czoxOiJ1IjtpOjE1MTQ1MDAxODI7czoxOiJjIjtpOjE1MTQ0OTk2ODk7czoxOiJsIjtzOjE6IjAiO30=');

/*!40000 ALTER TABLE `pk_system_session` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table pk_system_user
# ------------------------------------------------------------

DROP TABLE IF EXISTS `pk_system_user`;

CREATE TABLE `pk_system_user` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `username` varchar(255) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `email` varchar(255) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `password` varchar(255) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `url` varchar(255) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `status` smallint(6) NOT NULL DEFAULT '0',
  `registered` datetime NOT NULL,
  `login` datetime DEFAULT NULL,
  `activation` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `roles` longtext COLLATE utf8_unicode_ci COMMENT '(DC2Type:simple_array)',
  `data` longtext COLLATE utf8_unicode_ci COMMENT '(DC2Type:json_array)',
  PRIMARY KEY (`id`),
  UNIQUE KEY `pk_SYSTEM_USER_USERNAME` (`username`),
  UNIQUE KEY `pk_SYSTEM_USER_EMAIL` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

LOCK TABLES `pk_system_user` WRITE;
/*!40000 ALTER TABLE `pk_system_user` DISABLE KEYS */;

INSERT INTO `pk_system_user` (`id`, `name`, `username`, `email`, `password`, `url`, `status`, `registered`, `login`, `activation`, `roles`, `data`)
VALUES
	(1,'admin','admin','asd@asd.com','$2y$10$E8s6xVTyZIRaImVidx9TkOeQvmjp1Mk15D1/2GYRITKhQqdwLKXdW','',1,'2017-12-28 22:12:35','2017-12-28 22:21:41',NULL,'2,3',NULL);

/*!40000 ALTER TABLE `pk_system_user` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table pk_system_widget
# ------------------------------------------------------------

DROP TABLE IF EXISTS `pk_system_widget`;

CREATE TABLE `pk_system_widget` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `type` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `status` smallint(6) NOT NULL,
  `nodes` longtext COLLATE utf8_unicode_ci COMMENT '(DC2Type:simple_array)',
  `roles` longtext COLLATE utf8_unicode_ci COMMENT '(DC2Type:simple_array)',
  `data` longtext COLLATE utf8_unicode_ci COMMENT '(DC2Type:json_array)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

LOCK TABLES `pk_system_widget` WRITE;
/*!40000 ALTER TABLE `pk_system_widget` DISABLE KEYS */;

INSERT INTO `pk_system_widget` (`id`, `title`, `type`, `status`, `nodes`, `roles`, `data`)
VALUES
	(1,'Hello, I\'m Pagekit','system/text',1,'1',NULL,'{\"content\":\"<h1 class=\\\"uk-heading-large uk-margin-large-bottom\\\">Hello, I\'m Pagekit,<br class=\\\"uk-hidden-small\\\"> your new favorite CMS.<\\/h1>\\n\\n<a class=\\\"uk-button uk-button-large\\\" href=\\\"http:\\/\\/www.pagekit.com\\\">Get started<\\/a>\"}'),
	(2,'Powered by Pagekit','system/text',1,NULL,NULL,'{\"content\":\"<ul class=\\\"uk-grid uk-grid-medium uk-flex uk-flex-center\\\">\\n    <li><a href=\\\"https:\\/\\/github.com\\/pagekit\\\" class=\\\"uk-icon-hover uk-icon-small uk-icon-github\\\"><\\/a><\\/li>\\n    <li><a href=\\\"https:\\/\\/twitter.com\\/pagekit\\\" class=\\\"uk-icon-hover uk-icon-small uk-icon-twitter\\\"><\\/a><\\/li>\\n    <li><a href=\\\"https:\\/\\/gitter.im\\/pagekit\\/pagekit\\\" class=\\\"uk-icon-hover uk-icon-small uk-icon-comment-o\\\"><\\/a><\\/li>\\n    <li><a href=\\\"https:\\/\\/plus.google.com\\/communities\\/104125443335488004107\\\" class=\\\"uk-icon-hover uk-icon-small uk-icon-google-plus\\\"><\\/a><\\/li>\\n<\\/ul>\\n\\n<p>Powered by <a href=\\\"https:\\/\\/pagekit.com\\\">Pagekit<\\/a><\\/p>\"}');

/*!40000 ALTER TABLE `pk_system_widget` ENABLE KEYS */;
UNLOCK TABLES;



/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
