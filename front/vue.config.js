module.exports = {
  transpileDependencies: ["vuetify"],
  runtimeCompiler: true,
  devServer: {
    proxy: {
      "/api": {
        target: "http://localhost:8080",
        changeOrigin: true,
      },
    },
  },
  chainWebpack: config => {
    config.plugin("html").tap(args => {
      args[0].title = "Board"
      return args
    })
  },
};
