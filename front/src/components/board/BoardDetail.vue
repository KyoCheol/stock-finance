<template>
  <v-container>
    <v-card elevation="10" outlined width="100%" class="mx-auto">
      <v-card-title>
        <span class="mr-2">Detail</span>
      </v-card-title>
      <v-card-text>
        <v-row>
          <v-col>
            <v-text-field label="Title" readonly :value="title" />
          </v-col>
        </v-row>
        <v-row>
          <v-col>
            <v-text-field label="Writer" readonly dense :value="writer" />
          </v-col>
          <v-col>
            <v-text-field
              label="Register Time"
              readonly
              dense
              :value="regDttm"
            />
          </v-col>
          <v-col>
            <v-text-field label="View" readonly dense :value="view" />
          </v-col>
        </v-row>
        Content<br />
        <div style="height: 300px"><Viewer ref="viewer" /><br /></div>
        Reply ({{ replyCount }})<br />
        <v-simple-table dense>
          <tbody>
            <tr v-for="(reply, index) in replies" :key="index">
              <td style="width: 110px; padding: 0" v-if="!isMobile()">
                <v-icon small> mdi-account </v-icon>
                {{ reply.writer }}
              </td>
              <td style="width: 40px; padding: 0" v-else>
                <Tooltip
                  bottom
                  iconName="mdi-account"
                  title="작성자"
                  :content="reply.writer"
                />
              </td>
              <td style="padding: 0">{{ reply.content }}</td>
              <td style="width: 140px; padding: 0" v-if="!isMobile()">
                {{ reply.regDttm }}
              </td>
              <td style="width: 40px; padding: 0" v-else>
                <Tooltip
                  bottom
                  iconName="mdi-clock-outline"
                  title="작성일시"
                  :content="reply.regDttm"
                />
              </td>
              <td style="width: 30px; padding: 0">
                <Button
                  @click="replyEdit(reply.replyNo, reply.content)"
                  color="grey"
                  icon
                  xsmall
                  iconName="mdi-pencil"
                />
              </td>
              <td style="width: 30px; padding: 0">
                <Button
                  @click="replyDel(reply.replyNo)"
                  color="red"
                  icon
                  xsmall
                  iconName="mdi-close"
                />
              </td>
            </tr>
          </tbody>
        </v-simple-table>
        <v-divider></v-divider>
        <v-row>
          <v-col cols="12" md="11" style="padding: 0 12px">
            <v-textarea
              clearable
              clear-icon="mdi-close-circle"
              rows="2"
              no-resize
              full-width
              v-model="comment"
            ></v-textarea>
          </v-col>
          <v-col md="1" align-self="center" style="padding: 0 10px">
            <Button
              @click="replySave"
              color="indigo"
              rounded
              small
              iconName="mdi-pencil"
              btnName="Save"
            ></Button>
          </v-col>
        </v-row>
      </v-card-text>
      <v-card-actions>
        <v-spacer></v-spacer>
        <Button
          @click="movePage('/edit?docNo=' + docNo)"
          color="warning"
          rounded
          small
          iconName="mdi-pencil"
          btnName="Edit"
        ></Button>
        <Button
          @click="del"
          color="error"
          rounded
          small
          iconName="mdi-delete-forever"
          btnName="Delete"
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
      <v-card-actions>
        <v-row align="center" justify="center">
          <Button
            @click="likeBtn"
            class="ma-2"
            v-bind:color="
              likeCheck === 0 ? 'white lighten-2' : 'blue lighten-2'
            "
            large
            iconName="mdi-thumb-up"
          ></Button>
          {{ likeCnt }}
          <Button
            @click="disLikeBtn"
            class="ma-2"
            v-bind:color="
              unLikeCheck === 0 ? 'white lighten-2' : 'red lighten-2'
            "
            large
            iconName="mdi-thumb-down"
          ></Button>
          {{ unLikeCnt }}
        </v-row>
      </v-card-actions>
    </v-card>
  </v-container>
</template>

<script>
import Viewer from "@/components/common/Viewer";
import Tooltip from "@/components/common/Tooltip";
import btnMixins from "@/config/common/btnMixins";

export default {
  mixins: [btnMixins],
  components: {
    Viewer,
    Tooltip,
  },
  data: () => ({
    docNo: 0,
    title: "",
    writer: "",
    regDttm: "",
    view: 0,
    replyCount: 0,
    replies: null,
    comment: "",
    likeCnt: "",
    likeCheck: "",
    unLikeCnt: "",
    unLikeCheck: "",
    colorCheck: "blue",
    info: {
      url: {
        boardDelete: "/api/board/delete",
        boardReplyInsert: "/api/board/reply/insert",
        boardReplyUpdate: "/api/board/reply/update",
        boardReplyDelete: "/api/board/reply/delete",
        boardReplyList: "/api/board/reply/list/{docNo}",
        boardDetail: "/api/board/boardDetail/{docNo}/{userNo}",
        clickLike: "/api/board/like/{docNo}/{userNo}",
        clickUnLike: "/api/board/unLike/{docNo}/{userNo}",
      },
    },
  }),
  async mounted() {
    await this.getBoardDetail();
    await this.getReplyList();
  },
  watch: {
    options: {
      handler: "getBoardDetail",
      deep: true,
    },
  },
  methods: {
    async del() {
      let res = await this.confirmDialog(
        "Confirm Delete",
        "Are you sure you want to delete it?"
      );
      if (res) {
        let params = {
          docNo: this.docNo,
        };
        let url = this.info.url.boardDelete;
        let param = JSON.parse(JSON.stringify(params));

        this.$http
          .post(url, param)
          .then((res) => {
            if (res.data > 0) {
              this.$store.commit("SET_SNACKBAR", {
                show: true,
                msg: "Delete Complete",
                color: "error",
              });
              this.movePage("/list");
            }
          })
          .catch((e) => {
            console.log("eeeeeeeeeeeeeeeeeee");
            console.log(e);
          });
      }
    },
    replySave() {
      if (this.comment !== null) {
        let params = {
          docNo: this.docNo,
          content: this.comment,
          writer: "seochu",
        };
        let url = this.info.url.boardReplyInsert;
        let param = JSON.parse(JSON.stringify(params));

        this.$http
          .post(url, param)
          .then((res) => {
            if (res.data > 0) {
              this.refresh();
            }
          })
          .catch((e) => {
            console.log("eeeeeeeeeeeeeeeeeee");
            console.log(e);
          });
      }
    },
    async replyEdit(replyNo, comment) {
      let res = await this.promptDialog("Edit Reply", "Comment", comment);
      if (res) {
        let params = {
          replyNo: replyNo,
          docNo: this.docNo,
          content: res,
        };
        let url = this.info.url.boardReplyUpdate;
        let param = JSON.parse(JSON.stringify(params));

        this.$http
          .post(url, param)
          .then((res) => {
            if (res.data > 0) {
              this.refresh();
            }
          })
          .catch((e) => {
            console.log("eeeeeeeeeeeeeeeeeee");
            console.log(e);
          });
      }
    },
    async replyDel(replyNo) {
      let res = await this.confirmDialog(
        "Confirm Reply Delete",
        "Are you sure you want to delete it?"
      );
      if (res) {
        let params = {
          replyNo: replyNo,
          docNo: this.docNo,
        };
        let url = this.info.url.boardReplyDelete;
        let param = JSON.parse(JSON.stringify(params));

        this.$http
          .post(url, param)
          .then((res) => {
            if (res.data > 0) {
              this.refresh();
            }
          })
          .catch((e) => {
            console.log("eeeeeeeeeeeeeeeeeee");
            console.log(e);
          });
      }
    },
    getReplyList() {
      // let params = {
      //   docNo: this.$route.query.docNo,
      // };

      let url = this.info.url.boardReplyList;
      url = url.replace("{docNo}", this.$route.query.docNo);
      // let param = JSON.parse(JSON.stringify(params));

      this.$http
        .get(url)
        .then((res) => {
          this.replies = res.data;
        })
        .catch((e) => {
          console.log("eeeeeeeeeeeeeeeeeee");
          console.log(e);
        });
    },
    getBoardDetail() {
      let url = this.info.url.boardDetail;
      let docNo = this.$route.query.docNo;
      let userNo = this.$store.getters.user.data.id;

      url = url.replace("{docNo}", docNo);
      url = url.replace("{userNo}", userNo);

      this.$http
        .get(url)
        .then((res) => {
          console.log("res > > > > > ");
          console.log(res.data);

          this.docNo = res.data.data.docNo;
          this.title = res.data.data.title;
          this.$refs.viewer.setContent(res.data.data.content);
          this.writer = res.data.data.writer;
          this.regDttm = res.data.data.regDttm;
          this.view = res.data.data.view;
          this.likeCnt = res.data.data.like_cnt;
          this.unLikeCnt = res.data.data.unLike_cnt;
          this.replyCount = res.data.reply;
          this.likeCheck = res.data.likeCheck;
          this.unLikeCheck = res.data.unLikeCheck;
        })
        .catch((e) => {
          console.log("eeeeeeeeeeeeeeeeeee");
          console.log(e);
        });
    },
    likeBtn() {
      let url = this.info.url.clickLike;
      let docNo = this.$route.query.docNo;
      let userNo = this.$store.getters.user.data.id;

      url = url.replace("{docNo}", docNo);
      url = url.replace("{userNo}", userNo);

      this.$http
        .post(url)
        .then((res) => {
          this.likeCheck = res.data.likeCheck;
          this.likeCnt = res.data.likeCnt;
        })
        .catch((e) => {
          console.log("eeeeeeeeeeeeeeeeeee");
          console.log(e);
        });
    },
    disLikeBtn() {
      let url = this.info.url.clickUnLike;
      let docNo = this.$route.query.docNo;
      let userNo = this.$store.getters.user.data.id;

      url = url.replace("{docNo}", docNo);
      url = url.replace("{userNo}", userNo);

      this.$http
        .post(url)
        .then((res) => {
          console.log("181818");
          console.log(res.data);
          this.unLikeCheck = res.data.unLikeCheck;
          this.unLikeCnt = res.data.unLikeCnt;
        })
        .catch((e) => {
          console.log("eeeeeeeeeeeeeeeeeee");
          console.log(e);
        });
    },
  },
};
</script>
