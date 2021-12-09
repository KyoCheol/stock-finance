<template>
  <div>
    <Viewer ref="toastViewer" height="500px" />
  </div>
</template>

<script>
import { Viewer } from "@toast-ui/vue-editor";
import "codemirror/lib/codemirror.css";
import "@toast-ui/editor/dist/toastui-editor.css";

export default {
  components: {
    Viewer,
  },
  methods: {
    setContent(content) {
      this.$refs.toastViewer.invoke("setMarkdown", content);
    },
  },
};
</script>
