<template>
  <v-container>
    <v-card elevation="10" outlined width="100%" class="mx-auto">
      <v-card-title>
        <span class="mr-2">Edit</span>
      </v-card-title>
      <v-card-text>
        <v-text-field
          label="Title"
          :rules="rules"
          :counter="100"
          v-model="title"
        ></v-text-field>
        <Editor ref="editor" />
      </v-card-text>
      <v-card-actions>
        <v-spacer></v-spacer>
        <Button
          @click="edit"
          color="warning"
          rounded
          small
          iconName="mdi-pencil"
          btnName="Edit"
        ></Button>
        <Button
          @click="movePage('/detail?docNo=' + docNo)"
          color="grey darken-1"
          rounded
          small
          iconName="mdi-arrow-left"
          btnName="Back"
        ></Button>
      </v-card-actions>
    </v-card>
  </v-container>
</template>

<script>
import Editor from "@/components/common/Editor";

export default {
  components: {
    Editor,
  },
  data: () => ({
    rules: [
      (value) => !!value || "Required.",
      (value) => (value && value.length >= 3) || "Min 3 characters",
    ],
    docNo: "",
    title: "",
    writer: "",
    info: {
      url: {
        boardEdit: "/api/board/boardEdit",
      },
    },
  }),
  mounted() {
    this.getBoardDetail();
  },
  methods: {
    async edit() {
      let params = {
        docNo: this.docNo,
        title: this.title,
        writer: this.writer,
      };

      let url = this.info.url.boardEdit;
      let param = JSON.parse(JSON.stringify(params));

      this.$http
        .get(url, param)
        .then((res) => {
          if (res.data > 0) {
            this.$store.commit("SET_SNACKBAR", {
              show: true,
              msg: "Edit Complete",
              color: "warning",
            });
            this.movePage("/detail?docNo=" + this.docNo);
          }
        })
        .catch((e) => {
          console.log("eeeeeeeeeeeeeeeeeee");
          console.log(e);
        });
    },
    getBoardDetail() {
      let params = {
        docNo: this.$route.query.docNo,
      };
      let url = this.info.url.boardDetail;
      let param = JSON.parse(JSON.stringify(params));

      this.$http
        .get(url, param)
        .then((res) => {
          this.docNo = res.data.docNo;
          this.title = res.data.title;
          this.$refs.viewer.setContent(res.data.content);
        })
        .catch((e) => {
          console.log("eeeeeeeeeeeeeeeeeee");
          console.log(e);
        });
    },
  },
};
</script>
