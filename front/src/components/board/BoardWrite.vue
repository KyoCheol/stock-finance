<template>
  <v-container>
    <v-card elevation="10" outlined width="100%" class="mx-auto">
      <v-card-title>
        <span class="mr-2">Write</span>
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
          @click="save"
          color="success"
          rounded
          small
          iconName="mdi-content-save-all"
          btnName="Save"
        ></Button>
        <Button
          @click="movePage('/list')"
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
      (value) => (value && value.length - 1) || "Min 3 characters",
    ],
    title: "",
    info: {
      urlInfo: {
        boardWrite: "/api/board/boardWrite",
      },
    },
  }),
  methods: {
    async save() {
      let content = this.$refs.editor.getContent();
      let title = this.title;

      let res = await this.confirmDialog(
        "Confirrm Write",
        "Do you want to save it?"
      );

      if (res) {
        let url = this.info.urlInfo.boardWrite;
        const params = {
          title: title,
          content: content,
        };
        let param = JSON.parse(JSON.stringify(params));
        this.$http
          .post(url, param)
          .then((res) => {
            console.log(" r e s == > > > > >");
            console.log(res.data);
            if (res.data > 0) {
              this.$store.commit("SET_SNACKBAR", {
                show: true,
                msg: "Insert Complete",
                color: "success",
              });

              this.movePage("/detail?docNo=" + res.data);
            }
          })
          .catch((e) => {
            console.log("eeeeeeeeeeeeeeeeeee");
            console.log(e);
          });
      }
    },
  },
};
</script>
