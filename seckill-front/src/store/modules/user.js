const user = {
  state: {
    login: false, //判断是否登录
    dialogVisible: false, //是否弹出登录框
    loading: false
  },

  mutations: {
    setIsLogin: (state) => {
      state.login = !state.login
    },
    setDialogVisible: (state) => {
      state.dialogVisible =  !state.dialogVisible
    },
    setLoading: (state,token) => {
      state.loading = token
    }
  }
}

export default user
