DROP TABLE IF EXISTS permission;
CREATE TABLE permission (
  id                    BIGINT(64)    NOT NULL AUTO_INCREMENT COMMENT 'Primary key',
  name                  VARCHAR(50)   NOT NULL COMMENT 'Permission name',
  url                   VARCHAR(1000) DEFAULT NULL COMMENT 'When the type is page: it represents the front-end routing, when the type is button: it represents the back-end address',
  type                  INT(2)        NOT NULL COMMENT 'Permission type: page-1, button-2',
  expression            VARCHAR(50)   DEFAULT NULL COMMENT 'Permission expression',
  method                VARCHAR(50)   DEFAULT NULL COMMENT 'BackEnd interface access method',
  sort                  INT(11)       NOT NULL COMMENT 'Sort',
  parent_id             BIGINT(64)    NOT NULL COMMENT 'Parent id',
  CONSTRAINT pk_permission PRIMARY KEY (id)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT 'Table Permission';

INSERT INTO permission VALUES (1, 'Test page', '/test', 1, 'page:test', NULL, 1, 0);
INSERT INTO permission VALUES (2, 'Test button-list-query', '/**/test', 2, 'btn:test:query-list', 'GET', 1, 1);
INSERT INTO permission VALUES (3, 'Test button-query', '/**/test/{id}', 2, 'btn:test:query', 'GET', 2, 1);
INSERT INTO permission VALUES (4, 'Test button-add-query', '/**/test', 2, 'btn:test:insert', 'POST', 3, 1);
INSERT INTO permission VALUES (5, 'Test button-update-query', '/**/test/{id}', 2, 'btn:test:update', 'PUT', 4, 1);
INSERT INTO permission VALUES (6, 'Test button-delete-query', '/**/test/{id}', 2, 'btn:test:delete', 'DELETE', 5, 1);
INSERT INTO permission VALUES (7, 'Permission page', '/permissions', 1, 'page:permissions', NULL, 3, 0);
INSERT INTO permission VALUES (8, 'Permission button-list-permission', '/**/permissions', 2, 'btn:permissions:list', 'GET', 1, 10);
INSERT INTO permission VALUES (9, 'Permission button-permission', '/**/permissions/{id}', 2, 'btn:permissions:query', 'GET', 2, 10);
INSERT INTO permission VALUES (10, 'Permission button-add-permission', '/**/permissions', 2, 'btn:permissions:insert', 'POST', 3, 10);
INSERT INTO permission VALUES (11, 'Permission button-update-permission', '/**/permissions/{id}', 2, 'btn:permissions:update', 'PUT', 4, 10);
INSERT INTO permission VALUES (12, 'Permission button-delete-permission', '/**/permissions/{id}', 2, 'btn:permissions:delete', 'DELETE', 5, 10);

DROP TABLE IF EXISTS role;
CREATE TABLE role (
  id          BIGINT(64)   NOT NULL AUTO_INCREMENT COMMENT 'Primary key',
  name        VARCHAR(50)  NOT NULL COMMENT 'Role name',
  description VARCHAR(100) DEFAULT NULL COMMENT 'Description',
  create_time BIGINT(13)   NOT NULL COMMENT 'Create time',
  create_by   VARCHAR(50)  NOT NULL COMMENT 'Create by',
  update_time BIGINT(13)   NOT NULL COMMENT 'Update time',
  update_by   VARCHAR(50)  NOT NULL COMMENT 'Update by',
  CONSTRAINT pk_role PRIMARY KEY (id),
  UNIQUE KEY name (name)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT 'Table Role';

INSERT INTO role VALUES (1, 'Administrator', 'Super administrator', 1544611947239, 'SYSTEM', 1544611947239, 'SYSTEM');
INSERT INTO role VALUES (2, 'General user', 'General user', 1544611947246, 'SYSTEM', 1544611947246, 'SYSTEM');

DROP TABLE IF EXISTS role_permission;
CREATE TABLE role_permission (
  role_id       BIGINT(64) NOT NULL COMMENT 'Role primary key',
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
  id          BIGINT(64)   NOT NULL AUTO_INCREMENT COMMENT 'Primary key',
  username    VARCHAR(50)  NOT NULL COMMENT 'Username',
  password    VARCHAR(60)  NOT NULL COMMENT 'Password',
  nickname    VARCHAR(255) DEFAULT NULL COMMENT 'Nickname',
  phone       VARCHAR(11)  DEFAULT NULL COMMENT 'Phone numbers',
  email       VARCHAR(50)  DEFAULT NULL COMMENT 'Email address',
  birthday    BIGINT(13)   DEFAULT NULL COMMENT 'Birthday',
  gender      INT(2)       DEFAULT NULL COMMENT 'Gender, male-1, female-2',
  status      INT(2)       NOT NULL DEFAULT 1 COMMENT 'Status, enable -1, disable-0',
  create_time BIGINT(13)   NOT NULL COMMENT 'Create time',
  create_by   VARCHAR(50)  NOT NULL COMMENT 'Create by',
  update_time BIGINT(13)   NOT NULL COMMENT 'Update time',
  update_by   VARCHAR(50)  NOT NULL COMMENT 'Update by',
  CONSTRAINT pk_user PRIMARY KEY (id),
  UNIQUE KEY username (username),
  UNIQUE KEY phone (phone),
  UNIQUE KEY email (email)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT 'User table';

INSERT INTO user VALUES (1, 'admin', '$2a$10$64iuSLkKNhpTN19jGHs7xePvFsub7ZCcCmBqEYw8fbACGTE3XetYq', 'Admin', '17300000000', 'admin@gmail.com', 785433600000, 1, 1, 1544611947032, 'SYSTEM', 1544611947032, 'SYSTEM');
INSERT INTO user VALUES (2, 'user', '$2a$10$OUDl4thpcHfs7WZ1kMUOb.ZO5eD4QANW5E.cexBLiKDIzDNt87QbO', 'User', '17300001111', 'user@gmail.com', 785433600000, 1, 1, 1544611947234, 'SYSTEM', 1544611947234, 'SYSTEM');

DROP TABLE IF EXISTS user_role;
CREATE TABLE user_role (
  user_id BIGINT(64) NOT NULL COMMENT 'User primary key',
  role_id BIGINT(64) NOT NULL COMMENT 'Role primary key',
  CONSTRAINT pk_user_role PRIMARY KEY (user_id, role_id)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT 'User role relationship table';

INSERT INTO user_role VALUES (1, 1);
INSERT INTO user_role VALUES (2, 2);
