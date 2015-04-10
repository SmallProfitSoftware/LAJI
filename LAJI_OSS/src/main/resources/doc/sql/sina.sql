##建库
create database app_heyang DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

##后台账号
drop table if exists app_heyang.`t_user`;
CREATE TABLE app_heyang.`t_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增长ID',
  `role` char(4) NOT NULL COMMENT '角色编码',
  `userId` varchar(32) NOT NULL COMMENT '账号ID',
  `userPwd` varchar(32) NOT NULL COMMENT '账号密码',
  `userName` varchar(12) NOT NULL COMMENT '账号姓名',
  `isLock` tinyint(1) DEFAULT 0 COMMENT '锁定状态(1-锁定)',
  `loginCount` int(8) DEFAULT 0 COMMENT '登陆次数',
  `lastLoginIp` varchar(15) COMMENT '最后登录IP',
  `lastLoginDate` datetime COMMENT '最后登录时间',
  `createDate` datetime NOT NULL COMMENT '创建时间',
  `creater` varchar(32) NOT NULL COMMENT '创建人',
  `updateDate` datetime NOT NULL COMMENT '创建时间',
  `updater` varchar(32) NOT NULL COMMENT '创建人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

##超级管理员
insert into app_heyang.`t_user`(`role`, `userId`, `userPwd`, `userName`, `isLock`, `loginCount`, `lastLoginIp`, `lastLoginDate`, `createDate`, `creater`, `updateDate`, `updater`)
values('R001', 'superadmin', '96e79218965eb72c92a549dd5a330112', '超级管理员', 0, 1, null, null, now(), 'superadmin', now(), 'superadmin');

##后台角色
drop table if exists app_heyang.`t_role`;
CREATE TABLE app_heyang.`t_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增长ID',
  `code` char(4) NOT NULL COMMENT '角色编码(系统自动生成,以R开头，如：R001,R002...)',
  `name` varchar(32) NOT NULL COMMENT '角色名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

##超级管理员
insert into app_heyang.`t_role`(code, name) values('R001', '超级管理员');

##后台权限
drop table if exists app_heyang.`t_auth`;
CREATE TABLE app_heyang.`t_auth` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增长ID',
  `code` varchar(6)  NOT NULL COMMENT '权限编码(一级2位,如01，二级4位，如0101，三级6位，如010101)，1级指模块，2级指目录，3级指功能点)',
  `str` varchar(64) NOT NULL COMMENT '权限字符串(一级module_开头，二级dir_开头，三级func_开头)',
  `name` varchar(64) NOT NULL COMMENT '权限码名称',
  `url` varchar(128) NOT NULL COMMENT '目录地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

insert into app_heyang.`t_auth`(`code`, `str`, `name`, `url`) values('01','m_system','系统设置', 'views/system');
insert into app_heyang.`t_auth`(`code`, `str`, `name`, `url`) values('0101','d_dir','目录管理', 'views/system/dir.jsp');
insert into app_heyang.`t_auth`(`code`, `str`, `name`, `url`) values('0102','d_auth','权限管理', 'views/system/auth.jsp');
insert into app_heyang.`t_auth`(`code`, `str`, `name`, `url`) values('0103','d_role','角色管理', 'views/system/role.jsp');
insert into app_heyang.`t_auth`(`code`, `str`, `name`, `url`) values('0104','d_user','账号管理', 'views/system/user.jsp');
insert into app_heyang.`t_auth`(`code`, `str`, `name`, `url`) values('0105','d_syslog','日志管理', 'views/system/syslog.jsp');
insert into app_heyang.`t_auth`(`code`, `str`, `name`, `url`) values('010101','f_dir_add','目录添加', 'views/system/dir_add.json');
insert into app_heyang.`t_auth`(`code`, `str`, `name`, `url`) values('010102','f_dir_edit','目录修改', 'views/system/dir_edit.json');
insert into app_heyang.`t_auth`(`code`, `str`, `name`, `url`) values('010103','f_dir_del','目录删除', 'views/system/dir_del.json');
insert into app_heyang.`t_auth`(`code`, `str`, `name`, `url`) values('010201','f_auth_import','权限导入', 'views/system/auth_import.json');
insert into app_heyang.`t_auth`(`code`, `str`, `name`, `url`) values('010301','f_role_add','角色添加', 'views/system/role_add.json');
insert into app_heyang.`t_auth`(`code`, `str`, `name`, `url`) values('010302','f_role_edit','角色修改', 'views/system/role_edit.json');
insert into app_heyang.`t_auth`(`code`, `str`, `name`, `url`) values('010303','f_role_auth_setting','角色权限设置', 'views/system/role_auth_setting.json');
insert into app_heyang.`t_auth`(`code`, `str`, `name`, `url`) values('010401','f_user_add','帐号添加', 'views/system/user_add.json');
insert into app_heyang.`t_auth`(`code`, `str`, `name`, `url`) values('010402','f_user_edit','帐号修改', 'views/system/user_edit.json');
insert into app_heyang.`t_auth`(`code`, `str`, `name`, `url`) values('010403','f_user_lock','帐号锁定/解锁', 'views/system/user_lock.json');
insert into app_heyang.`t_auth`(`code`, `str`, `name`, `url`) values('010404','f_user_pwd_reset','帐号密码重置', 'views/system/user_pwd_reset.json');


##后台角色权限关联表
drop table if exists app_heyang.`r_role_auth`;
CREATE TABLE app_heyang.`r_role_auth` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增长ID',
  `roleCode` char(4) NOT NULL COMMENT '角色编码',
  `authCode` varchar(6) NOT NULL COMMENT '权限编码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

##后台日志
drop table if exists app_heyang.`t_sysLog`;
CREATE TABLE `t_sysLog` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增长ID',
  `module` varchar(32) NOT NULL COMMENT '操作目录',
  `act` varchar(32) NOT NULL COMMENT '操作行为',
  `ip` varchar(15) NOT NULL COMMENT 'IP地址',
  `status` varchar(7) DEFAULT 'success' COMMENT '状态(success/error)',
  `message` varchar(100) DEFAULT '' COMMENT '日志信息',
  `operator` varchar(32) NOT NULL COMMENT '操作人',
  `operateDate` datetime NOT NULL COMMENT '操作时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;