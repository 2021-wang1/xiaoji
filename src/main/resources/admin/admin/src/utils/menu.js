const menu = {
  list() {
    return [
      {
        backMenu: [
          {
            child: [{ buttons: ['新增', '查看', '修改', '删除'], menu: '学生', menuJump: '列表', tableName: 'xuesheng' }],
            menu: '学生管理',
          },
          {
            child: [{ buttons: ['新增', '查看', '修改', '删除'], menu: '教师', menuJump: '列表', tableName: 'jiaoshi' }],
            menu: '教师管理',
          },
          {
            child: [{ buttons: ['新增', '查看', '修改', '删除'], menu: '班级信息', menuJump: '列表', tableName: 'banjixinxi' }],
            menu: '班级信息管理',
          },
          {
            child: [{ buttons: ['查看', '修改', '删除'], menu: '作业信息', menuJump: '列表', tableName: 'zuoyexinxi' }],
            menu: '作业信息管理',
          },
          {
            child: [{ buttons: ['查看', '修改', '删除'], menu: '作业提交', menuJump: '列表', tableName: 'zuoyetijiao' }],
            menu: '作业提交管理',
          },
          {
            child: [{ buttons: ['查看', '修改', '删除'], menu: '作业点评', menuJump: '列表', tableName: 'zuoyedianping' }],
            menu: '作业点评管理',
          },
          {
            child: [{ buttons: ['查看', '修改', '删除'], menu: '题库', menuJump: '列表', tableName: 'questionBank' }],
            menu: '题库',
          },
        ],
        frontMenu: [],
        hasBackLogin: '是',
        hasBackRegister: '否',
        hasFrontLogin: '否',
        hasFrontRegister: '否',
        roleName: '管理员',
        tableName: 'users',
      },
      {
        backMenu: [
          {
            child: [{ buttons: ['查看', '提交作业'], menu: '作业信息', menuJump: '列表', tableName: 'zuoyexinxi' }],
            menu: '作业信息管理',
          },
          {
            child: [{ buttons: ['查看', '删除', '修改'], menu: '作业提交', menuJump: '列表', tableName: 'zuoyetijiao' }],
            menu: '作业提交管理',
          },
          {
            child: [{ buttons: ['查看'], menu: '作业点评', menuJump: '列表', tableName: 'zuoyedianping' }],
            menu: '作业点评管理',
          },
        ],
        frontMenu: [],
        hasBackLogin: '是',
        hasBackRegister: '是',
        hasFrontLogin: '是',
        hasFrontRegister: '是',
        roleName: '学生',
        tableName: 'xuesheng',
      },
      {
        backMenu: [
          {
            child: [{ buttons: ['新增', '查看', '修改', '删除'], menu: '作业信息', menuJump: '列表', tableName: 'zuoyexinxi' }],
            menu: '作业信息管理',
          },
          {
            child: [{ buttons: ['查看', '删除', '点评'], menu: '作业提交', menuJump: '列表', tableName: 'zuoyetijiao' }],
            menu: '作业提交管理',
          },
          {
            child: [{ buttons: ['查看', '修改', '删除'], menu: '作业点评', menuJump: '列表', tableName: 'zuoyedianping' }],
            menu: '作业点评管理',
          },
          {
            child: [{ buttons: ['查看', '修改', '删除'], menu: '题库', menuJump: '列表', tableName: 'questionBank' }],
            menu: '题库',
          },
        ],
        frontMenu: [],
        hasBackLogin: '是',
        hasBackRegister: '是',
        hasFrontLogin: '是',
        hasFrontRegister: '是',
        roleName: '教师',
        tableName: 'jiaoshi',
      },
    ]
  },
}
export default menu
