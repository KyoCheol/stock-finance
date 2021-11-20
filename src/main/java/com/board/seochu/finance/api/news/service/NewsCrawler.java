package com.board.seochu.finance.api.news.service;

import com.board.seochu.finance.api.news.dto.NewsVO;
import com.board.seochu.finance.common.consts.StcConst;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Slf4j
@Service
@Transactional
public class NewsCrawler {

    private Gson gson;

    @PostConstruct
    private void setUp() {
        gson = new Gson();
    }

    public List<NewsVO> searchPlance() {
        String keyword;

        try {
            keyword = URLEncoder.encode("주식", "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("fail", e);
        }

        log.info("S T E P 1 > > > > > > > ");

        String clientId = StcConst.CLIENT_ID;
        String clientSecret = StcConst.CLIENT_SECRET;

        String apiURL = "https://openapi.naver.com/v1/search/local.json?query=" + keyword + "&display=20&start=1&sort=random";    // json 결과
        log.info("S T E P 2 > > > > > > > ");
        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("X-Naver-Client-Id", clientId);
        requestHeaders.put("X-Naver-Client-Secret", clientSecret);
        String responseBody = get(apiURL, requestHeaders);

        System.out.println("네이버에서 받은 결과 = " + responseBody);
        System.out.println("-----------------------------------------");

        List<NewsVO> newsList = new ArrayList<>();
        return newsList;
        //return convertData(responseBody);
    }

    private String get(String apiUrl, Map<String, String> requestHeaders) {
        HttpURLConnection con = connect(apiUrl);
        try {
            con.setRequestMethod("GET");
            for (Map.Entry<String, String> header : requestHeaders.entrySet()) {
                con.setRequestProperty(header.getKey(), header.getValue());
            }

            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
                return readBody(con.getInputStream());
            } else { // 에러 발생
                return readBody(con.getErrorStream());
            }
        } catch (IOException e) {
            throw new RuntimeException("API 요청과 응답 실패", e);
        } finally {
            con.disconnect();
        }
    }

    private HttpURLConnection connect(String apiUrl){
        try {
            URL url = new URL(apiUrl);
            return (HttpURLConnection)url.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
        } catch (IOException e) {
            throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
        }
    }

    private String readBody(InputStream body) {
        InputStreamReader streamReader = new InputStreamReader(body, StandardCharsets.UTF_8);

        try (
                BufferedReader lineReader = new BufferedReader(streamReader)
        ) {
            StringBuilder responseBody = new StringBuilder();

            String line;
            while ((line = lineReader.readLine()) != null) {
                responseBody.append(line);
            }

            return responseBody.toString();
        } catch (IOException e) {
            throw new RuntimeException("API 응답을 읽는데 실패했습니다.", e);
        }
    }
//    private List<NewsVO> convertData(String responseBody) {
//
//    }
}
