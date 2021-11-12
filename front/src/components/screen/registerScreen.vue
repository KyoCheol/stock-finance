<template>
  <v-app id="inspire">
    <v-content>
      <v-container fluid fill-height>
        <v-layout align-center justify-center>
          <v-flex xs12 sm8 md4>
            <v-form ref="registerForm" v-model="valid" lazy-validation>
              <v-row>
                <v-col cols="12" sm="6" md="6">
                  <v-text-field
                    v-model="firstName"
                    :rules="[rules.required]"
                    label="First Name"
                    maxlength="20"
                    required
                  ></v-text-field>
                </v-col>
                <v-col cols="12" sm="6" md="6">
                  <v-text-field
                    v-model="lastName"
                    :rules="[rules.required]"
                    label="Last Name"
                    maxlength="20"
                    required
                  ></v-text-field>
                </v-col>
                <!--                <v-col cols="12">-->
                <!--                  <v-text-field-->
                <!--                    v-model="streetAddress"-->
                <!--                    :rules="[rules.required]"-->
                <!--                    label="Street Address"-->
                <!--                    required-->
                <!--                  ></v-text-field>-->
                <!--                </v-col>-->
                <!--                <v-col cols="12">-->
                <!--                  <v-text-field-->
                <!--                    v-model="city"-->
                <!--                    :rules="[rules.required]"-->
                <!--                    label="City"-->
                <!--                    required-->
                <!--                  ></v-text-field>-->
                <!--                </v-col>-->
                <!--                <v-col cols="6">-->
                <!--                  <v-text-field-->
                <!--                    v-model="state"-->
                <!--                    :rules="[rules.required]"-->
                <!--                    label="State"-->
                <!--                    required-->
                <!--                  ></v-text-field>-->
                <!--                </v-col>-->
                <!--                <v-col cols="6">-->
                <!--                  <v-text-field-->
                <!--                    v-model="zipCode"-->
                <!--                    :rules="zipCodeRules"-->
                <!--                    label="ZIP Code"-->
                <!--                    required-->
                <!--                  ></v-text-field>-->
                <!--                </v-col>-->
                <v-col cols="12">
                  <v-text-field v-model="id" label="ID" required></v-text-field>
                </v-col>
                <v-col cols="12">
                  <v-text-field
                    v-model="nickname"
                    label="NICKNAME"
                    required
                  ></v-text-field>
                </v-col>
                <v-col cols="12">
                  <v-text-field
                    v-model="mobile"
                    label="MOBILE"
                    required
                  ></v-text-field>
                </v-col>
                <v-col cols="12">
                  <v-text-field
                    v-model="email"
                    :rules="emailRules"
                    label="E-mail"
                    required
                  ></v-text-field>
                </v-col>
                <v-col cols="12">
                  <v-text-field
                    v-model="password"
                    :append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'"
                    :rules="[rules.required, rules.min]"
                    :type="show1 ? 'text' : 'password'"
                    name="input-10-1"
                    label="Password"
                    hint="At least 8 characters"
                    counter
                    @click:append="show1 = !show1"
                  ></v-text-field>
                </v-col>
                <v-col cols="12">
                  <v-text-field
                    block
                    v-model="cpassword"
                    :append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'"
                    :rules="[rules.required, passwordMatch]"
                    :type="show1 ? 'text' : 'password'"
                    name="input-10-1"
                    label="Confirm Password"
                    counter
                    @click:append="show1 = !show1"
                  ></v-text-field>
                </v-col>
                <v-col cols="12" sm="6" md="6">
                  <v-checkbox
                    v-model="role"
                    label="user"
                    color="indigo"
                    value="ROLE_USER"
                    hide-details
                  ></v-checkbox>
                </v-col>
                <v-col cols="12" sm="6" md="6">
                  <v-checkbox
                    v-model="role"
                    label="admin"
                    color="indigo"
                    value="ROLE_USER,ROLE_ADMIN"
                    hide-details
                  ></v-checkbox>
                </v-col>
                <v-spacer></v-spacer>
                <v-col class="d-flex" cols="12" align="center">
                  <v-btn
                    block
                    :disabled="!valid"
                    color="success"
                    class="mr-4"
                    @click="register"
                  >
                    Register
                  </v-btn>
                </v-col>
              </v-row>
            </v-form>
          </v-flex>
        </v-layout>
      </v-container>
    </v-content>
  </v-app>
</template>

<script>
export default {
  name: "register",
  computed: {
    passwordMatch() {
      return () => this.password === this.cpassword || "Password must match";
    },
  },
  data: () => ({
    valid: true,
    firstName: "",
    lastName: "",
    email: "",
    cpassword: "",
    loginPassword: "",
    loginEmail: "",
    id: "",
    nickname: "",
    mobile: "",
    role: "",
    loginEmailRules: [
      (v) => !!v || "Required",
      (v) => /.+@.+\..+/.test(v) || "E-mail must be valid",
    ],
    emailRules: [
      (v) => !!v || "Required",
      (v) => /.+@.+\..+/.test(v) || "E-mail must be valid",
    ],
    zipCodeRules: [
      (v) => !!v || "Required",
      (v) => /^\d+$/.test(v) || "Zipcode contains only digits",
    ],
    show1: false,
    password: "",
    rules: {
      required: (value) => !!value || "Required.",
      min: (v) => (v && v.length >= 8) || "Min 8 characters",
    },
    url: {
      register: "/api/register",
    },
  }),
  methods: {
    validate() {
      if (this.$refs.registerForm.validate()) {
        this.snackbar = true;
      }
    },
    register() {
      if (this.$refs.registerForm.validate()) {
        const params = {
          email: this.email,
          password: this.password,
          name: this.firstName + this.lastName,
          nickname: this.nickname,
          mobile: this.mobile,
          auth: this.role,
        };

        let url = this.url.register;
        let param = JSON.parse(JSON.stringify(params));
        this.$http
          .post(url, param)
          .then((res) => {
            console.log(" r e s > > > > > > ");
            console.log(res);
            let loginId = this.id;
            let loginPw = this.password;
            // 로그인 API 통신요청
            this.$store
              .dispatch("login", { loginId, loginPw })
              .then(() => {
                this.router.push("/");
              })
              .catch((e) => {
                alert(
                  "로그인 요청에 문제가 발생했습니다.\nmsg:" + e.response.data
                );
              });
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
