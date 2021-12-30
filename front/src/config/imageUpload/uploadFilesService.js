import axios from "axios";
import COMMON_CONST from "@/config/common/commonConst";

class uploadFilesService {
  upload(file, onUploadProgress) {
    let formData = new FormData();

    formData.append("file", file);

    let apiUrl = COMMON_CONST.API_URL + "/api/file/upload";

    console.log("apiURL > > > >" + apiUrl);
    return axios.post(apiUrl, formData, {
      headers: { "content-type": "application/json" },
      onUploadProgress,
    });
  }

  getFiles() {
    let apiUrl = COMMON_CONST.API_URL + "/api/file/files";
    return axios.get(apiUrl);
  }
}

export default new uploadFilesService();
