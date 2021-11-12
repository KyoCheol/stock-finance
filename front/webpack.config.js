const path = require("path");

let webpackCommonConfig = {
  name: "commonConfig",
  resolve: {
    extensions: [".js", ".vue", ".json", ".scss"],
    // modules: [path.resolve(__dirname, "src"), "node_modules"],
    alias: {
      "@": resolve("src/"),
      "@page": resolve("src/pages/"),
    },
  },
  rules: [
    {
      test: /\.s(c|a)ss$/,
      use: [
        "vue-style-loader",
        "css-loader",
        {
          loader: "sass-loader",
          // Requires sass-loader@^7.0.0
          options: {
            implementation: require("sass"),
            fiber: require("fibers"),
            indentedSyntax: true, // optional
          },
        },
      ],
    },
  ],
};

module.exports = webpackCommonConfig;

function resolve(dir) {
  return path.join(__dirname, ".", dir);
}
