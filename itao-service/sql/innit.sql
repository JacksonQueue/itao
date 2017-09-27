CREATE TABLE `sm_user` (
  `user_id` varchar(32) NOT NULL COMMENT '用户ID',
  `user_name` varchar(100) NOT NULL COMMENT '用户名',
  `password` varchar(50) NOT NULL COMMENT '密码',
  `name` varchar(20) NOT NULL COMMENT '姓名',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `tel` varchar(20) DEFAULT NULL COMMENT '座机号码',
  `mobile` varchar(20) DEFAULT NULL COMMENT '手机号',
  `dept_id` varchar(50) NOT NULL COMMENT '所属部门',
  `user_status` varchar(20) NOT NULL COMMENT '当前状态',
  `is_locked` tinyint(1) NOT NULL COMMENT '是否锁住',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `creator` varchar(50) NOT NULL COMMENT '创建者',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `last_modifier` varchar(50) NOT NULL COMMENT '最后编辑者',
  `last_modify_time` datetime DEFAULT NULL COMMENT '最后编辑时间',
  `validity` varchar(20) NOT NULL COMMENT '数据有效性',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `IDX_SM_USER_USER_NAME` (`user_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统管理者用户信息表(system_manager_user)';

CREATE TABLE `sys_department` (
  `dept_id` varchar(32) NOT NULL COMMENT '部门ID',
  `dept_code` varchar(50) DEFAULT NULL COMMENT '部门编号/代码',
  `dept_name` varchar(50) NOT NULL COMMENT '部门名称',
  `dept_level` int(11) NOT NULL COMMENT '层级',
  `parent_id` varchar(32) NOT NULL COMMENT '父节点',
  PRIMARY KEY (`dept_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='部门表';

