CREATE DATABASE IF NOT EXISTS bus_reservation CHARACTER SET = utf8 COLLATE = utf8_general_ci;
USE bus_reservation;

DROP TABLE IF EXISTS permission;
CREATE TABLE permission (
  id BIGINT(64) NOT NULL AUTO_INCREMENT COMMENT 'Permission primary key',
  name VARCHAR(50) NOT NULL COMMENT 'Permission name',
  url VARCHAR(1000) DEFAULT NULL COMMENT 'When the type is page: it represents the front-end routing; When the type is button: it represents the back-end address',
  type INT(2) NOT NULL COMMENT 'Permission type: page-1, button-2',
  expression VARCHAR(50) DEFAULT NULL COMMENT 'Permission expression',
  method VARCHAR(50) DEFAULT NULL COMMENT 'HTTP request methods',
  sort INT(11) NOT NULL COMMENT 'Sort order',
  parent_id BIGINT(64) NOT NULL COMMENT 'Parent id',
  CONSTRAINT pk_permission PRIMARY KEY (id)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT 'Table Permission';
INSERT INTO permission(id, name, url, type, expression, method, sort, parent_id) VALUES (1, 'Test page', '/test', 1, 'page:test', NULL, 1, 0);
INSERT INTO permission(id, name, url, type, expression, method, sort, parent_id) VALUES (2, 'Test button-list-query', '/**/test', 2, 'btn:test:query-list', 'GET', 1, 1);
INSERT INTO permission(id, name, url, type, expression, method, sort, parent_id) VALUES (3, 'Test button-query', '/**/test/{id}', 2, 'btn:test:query', 'GET', 2, 1);
INSERT INTO permission(id, name, url, type, expression, method, sort, parent_id) VALUES (4, 'Test button-add-query', '/**/test', 2, 'btn:test:insert', 'POST', 3, 1);
INSERT INTO permission(id, name, url, type, expression, method, sort, parent_id) VALUES (5, 'Test button-update-query', '/**/test/{id}', 2, 'btn:test:update', 'PUT', 4, 1);
INSERT INTO permission(id, name, url, type, expression, method, sort, parent_id) VALUES (6, 'Test button-delete-query', '/**/test/{id}', 2, 'btn:test:delete', 'DELETE', 5, 1);
INSERT INTO permission(id, name, url, type, expression, method, sort, parent_id) VALUES (7, 'Permission page', '/permissions', 1, 'page:permissions', NULL, 3, 0);
INSERT INTO permission(id, name, url, type, expression, method, sort, parent_id) VALUES (8, 'Permission button-list-permission', '/**/permissions', 2, 'btn:permissions:list', 'GET', 1, 10);
INSERT INTO permission(id, name, url, type, expression, method, sort, parent_id) VALUES (9, 'Permission button-permission', '/**/permissions/{id}', 2, 'btn:permissions:query', 'GET', 2, 10);
INSERT INTO permission(id, name, url, type, expression, method, sort, parent_id) VALUES (10, 'Permission button-add-permission', '/**/permissions', 2, 'btn:permissions:insert', 'POST', 3, 10);
INSERT INTO permission(id, name, url, type, expression, method, sort, parent_id) VALUES (11, 'Permission button-update-permission', '/**/permissions/{id}', 2, 'btn:permissions:update', 'PUT', 4, 10);
INSERT INTO permission(id, name, url, type, expression, method, sort, parent_id) VALUES (12, 'Permission button-delete-permission', '/**/permissions/{id}', 2, 'btn:permissions:delete', 'DELETE', 5, 10);

DROP TABLE IF EXISTS role;
CREATE TABLE role (
  id BIGINT(64) NOT NULL AUTO_INCREMENT COMMENT 'Role primary key',
  name VARCHAR(50) NOT NULL COMMENT 'Role name',
  description VARCHAR(100) DEFAULT NULL COMMENT 'Role Description',
  create_at DATETIME DEFAULT NULL COMMENT 'Create time',
  create_by VARCHAR(50) DEFAULT NULL COMMENT 'Create by',
  update_at DATETIME DEFAULT NULL COMMENT 'Update time',
  update_by VARCHAR(50) DEFAULT NULL COMMENT 'Update by',
  CONSTRAINT pk_role PRIMARY KEY (id),
  UNIQUE KEY uniq_name (name)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT 'Table Role';
INSERT INTO role(id, name, description, create_at, create_by, update_at, update_by) VALUES ( 1, 'Administrator', 'Super administrator', CURRENT_TIMESTAMP(), 'SYSTEM', CURRENT_TIMESTAMP(), 'SYSTEM' );
INSERT INTO role(id, name, description, create_at, create_by, update_at, update_by) VALUES ( 2, 'General user', 'General user', CURRENT_TIMESTAMP(), 'SYSTEM', CURRENT_TIMESTAMP(), 'SYSTEM' );

DROP TABLE IF EXISTS role_permission;
CREATE TABLE role_permission (
  role_id BIGINT(64) NOT NULL COMMENT 'Role primary key',
  permission_id BIGINT(64) NOT NULL COMMENT 'Permission primary key',
  CONSTRAINT pk_role_permission PRIMARY KEY (role_id, permission_id)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT 'Role permission relationship table';
INSERT INTO role_permission VALUES (1, 1);
INSERT INTO role_permission VALUES (1, 2);
INSERT INTO role_permission VALUES (1, 3);
INSERT INTO role_permission VALUES (1, 4);
INSERT INTO role_permission VALUES (1, 5);
INSERT INTO role_permission VALUES (1, 6);
INSERT INTO role_permission VALUES (1, 7);
INSERT INTO role_permission VALUES (1, 8);
INSERT INTO role_permission VALUES (1, 9);
INSERT INTO role_permission VALUES (1, 10);
INSERT INTO role_permission VALUES (1, 11);
INSERT INTO role_permission VALUES (1, 12);
INSERT INTO role_permission VALUES (2, 1);
INSERT INTO role_permission VALUES (2, 2);

DROP TABLE IF EXISTS user;
CREATE TABLE user (
  id BIGINT(64) NOT NULL AUTO_INCREMENT COMMENT 'User primary key',
  username VARCHAR(50) NOT NULL COMMENT 'Username',
  password VARCHAR(60) NOT NULL COMMENT 'Password',
  first_name VARCHAR(255) DEFAULT NULL COMMENT 'First name',
  last_name VARCHAR(255) DEFAULT NULL COMMENT 'Last name',
  phone VARCHAR(11) NOT NULL COMMENT 'Phone numbers',
  email VARCHAR(50) NOT NULL COMMENT 'Email address',
  birthday DATETIME DEFAULT NULL COMMENT 'Birthday',
  gender INT(2) DEFAULT NULL COMMENT 'Gender: male-1, female-2',
  status INT(2) NOT NULL DEFAULT 1 COMMENT 'Status: enable-1, disable-0',
  create_at DATETIME DEFAULT NULL COMMENT 'Create time',
  create_by VARCHAR(50) DEFAULT NULL COMMENT 'Create by',
  update_at DATETIME DEFAULT NULL COMMENT 'Update time',
  update_by VARCHAR(50) DEFAULT NULL COMMENT 'Update by',
  CONSTRAINT pk_user PRIMARY KEY (id),
  UNIQUE KEY uniq_username (username),
  UNIQUE KEY uniq_phone (phone),
  UNIQUE KEY uniq_email (email)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT 'User table';
INSERT INTO user(id, username, password, first_name, phone, email, birthday, gender, status, create_at, create_by, update_at, update_by)
VALUES (1, 'admin', '$2a$10$64iuSLkKNhpTN19jGHs7xePvFsub7ZCcCmBqEYw8fbACGTE3XetYq', 'Admin', '17300000000', 'admin@gmail.com', DATE('1994-08-06'), 1, 1, CURRENT_TIMESTAMP(), 'SYSTEM', CURRENT_TIMESTAMP(), 'SYSTEM' );
INSERT INTO user(id, username, password, first_name, phone, email, birthday, gender, status, create_at, create_by, update_at, update_by)
VALUES (2, 'user', '$2a$10$OUDl4thpcHfs7WZ1kMUOb.ZO5eD4QANW5E.cexBLiKDIzDNt87QbO', 'User', '17300001111', 'user@gmail.com', DATE('1988-03-20'), 1, 1, CURRENT_TIMESTAMP(), 'SYSTEM', CURRENT_TIMESTAMP(), 'SYSTEM' );

DROP TABLE IF EXISTS user_role;
CREATE TABLE user_role (
  user_id BIGINT(64) NOT NULL COMMENT 'User primary key',
  role_id BIGINT(64) NOT NULL COMMENT 'Role primary key',
  CONSTRAINT pk_user_role PRIMARY KEY (user_id, role_id)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT 'User role relationship table';
INSERT INTO user_role VALUES (1, 1);
INSERT INTO user_role VALUES (2, 2);

DROP TABLE IF EXISTS article;
CREATE TABLE article (
  id BIGINT(64) NOT NULL AUTO_INCREMENT COMMENT 'Article primary key',
  user_id BIGINT(64) NOT NULL COMMENT 'User primary key',
  slug VARCHAR(255) NOT NULL COMMENT 'Article Slug',
  title VARCHAR(255) DEFAULT NULL COMMENT 'Article Title',
  description VARCHAR(255) DEFAULT NULL COMMENT 'Article Description',
  body VARCHAR(255) DEFAULT NULL COMMENT 'Article body',
  created_at DATETIME DEFAULT NULL COMMENT 'Created time',
  updated_at DATETIME DEFAULT NULL COMMENT 'Updated time',
  CONSTRAINT pk_article PRIMARY KEY (id),
  UNIQUE KEY uniq_slug (slug)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT 'Article table';

DROP TABLE IF EXISTS article_favorites;
CREATE TABLE article_favorites (
  article_id BIGINT(64) NOT NULL COMMENT 'Article primary key',
  user_id BIGINT(64) NOT NULL COMMENT 'User primary key',
  CONSTRAINT pk_article_favorites PRIMARY KEY (article_id, user_id)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT 'Article favorites relationship table';

DROP TABLE IF EXISTS follow;
CREATE TABLE follow (
  user_id BIGINT(64) NOT NULL COMMENT 'User primary key',
  follow_id BIGINT(64) NOT NULL COMMENT 'User primary key',
  CONSTRAINT pk_follow PRIMARY KEY (user_id, follow_id)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT 'Follow table';

DROP TABLE IF EXISTS tag;
CREATE TABLE tag (
  id BIGINT(64) NOT NULL AUTO_INCREMENT COMMENT 'Tag primary key',
  name VARCHAR(255) NOT NULL COMMENT 'Tag name',
  CONSTRAINT pk_tag PRIMARY KEY (id)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT 'Tag table';

DROP TABLE IF EXISTS article_tag;
CREATE TABLE article_tag (
  article_id BIGINT(64) NOT NULL COMMENT 'Article primary key',
  tag_id BIGINT(64) NOT NULL COMMENT 'Tag primary key',
  CONSTRAINT pk_article_tag PRIMARY KEY (article_id, tag_id)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT 'Article tag relationship table';

DROP TABLE IF EXISTS comment;
CREATE TABLE comment (
  id BIGINT(64) NOT NULL AUTO_INCREMENT COMMENT 'Comment primary key',
  body VARCHAR(255) DEFAULT NULL COMMENT 'Comment body',
  article_id BIGINT(64) NOT NULL COMMENT 'Article primary key',
  user_id BIGINT(64) NOT NULL COMMENT 'User primary key',
  created_at DATETIME DEFAULT NULL COMMENT 'Created time',
  updated_at DATETIME DEFAULT NULL COMMENT 'Updated time',
  CONSTRAINT pk_comment PRIMARY KEY (id)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT 'Comment table';