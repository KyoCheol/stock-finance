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
};
