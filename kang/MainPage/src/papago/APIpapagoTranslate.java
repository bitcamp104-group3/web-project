package papago;
// 네이버 Papago NMT API 예제
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
    
    public String translate(String inputText) {
        String clientId = "FoBjSZSiiHDcHAIGjVPO";//애플리케이션 클라이언트 아이디값";
        String clientSecret = "E9MiOAdH5O";//애플리케이션 클라이언트 시크릿값";
        try {
            String text = URLEncoder.encode(inputText, "UTF-8");
            String apiURL = "https://openapi.naver.com/v1/papago/n2mt";
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("X-Naver-Client-Id", clientId);
            con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
            // post request
            String postParams = "source=ko&target=en&text=" + text;
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
            System.out.println(response.toString());
            //수정한 내용
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(response.toString());
            JSONObject jsonObj = (JSONObject) obj;
            JSONObject msgObj = (JSONObject) jsonObj.get("message");
            JSONObject resultObj = (JSONObject) msgObj.get("result");
            String translatedStr = (String) resultObj.get("translatedText");
            //System.out.println("번역된 문장: " + translatedStr); //확인용
            
            return translatedStr;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }
}