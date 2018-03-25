package papago;
//네이버 Papago NMT API (Reference: https://developers.naver.com/docs/nmt/reference/)
/*
파일명: APIpapagoTranslate.java
작성일시: 2018-03-25
작성내용: 네이버 Papago NMT API활용 번역 페이지 작성
작성자: 강성훈
 */
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

//from json-simple-1.1.1.jar
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser; 

public class APIpapagoTranslate {
    
    public String translate(String inputText, String source, String target) {
    	//inputText: 번역할 내용(1회 최대 5,000자), source: 번역 대상 언어 코드, target: 번역 결과 언어 코드
        String clientId = "FoBjSZSiiHDcHAIGjVPO";//애플리케이션 클라이언트 아이디값;
        String clientSecret = "E9MiOAdH5O";//애플리케이션 클라이언트 시크릿값;
        try {
            String text = URLEncoder.encode(inputText, "UTF-8");
            String apiURL = "https://openapi.naver.com/v1/papago/n2mt";
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("X-Naver-Client-Id", clientId);
            con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
            // post request
            String postParams = "source=" + source + "&target=" + target + "&text=" + text;
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(postParams);
            wr.flush();
            wr.close();
            int responseCode = con.getResponseCode();
            BufferedReader br;
            if(responseCode==200) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {  // 에러 발생
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();
            //System.out.println(response.toString()); //확인용
            //수정한 내용
            JSONParser parser = new JSONParser(); //JSONParser 객체 생성
            Object obj = parser.parse(response.toString()); //문자열 결과를 객체로 받음 (String to Object)
            JSONObject jsonObj = (JSONObject) obj; //결과 객체를 JSON객체로 캐스팅 (Object to JSONObject)
            JSONObject msgObj = (JSONObject) jsonObj.get("message"); //해당 JSON객체의 key가 message인 value를 다시 JSON 캐스팅
            JSONObject resultObj = (JSONObject) msgObj.get("result"); //앞의 JSON객체의 key가 result인 value를 다시 JSON 캐스팅
            String translatedText = (String) resultObj.get("translatedText"); //최종적으로 앞의 JSON객체의 key가 translatedText인 value를 String 캐스팅 >> 번역된 문장
            //System.out.println("번역된 문장: " + translatedStr); // 최종 확인용
            
            return translatedText; //최종적으로 번역된 내용의 String값을 return
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }
}