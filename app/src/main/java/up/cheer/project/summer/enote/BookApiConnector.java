package up.cheer.project.summer.enote;

import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;

public class BookApiConnector {

    public BookApiConnector(){}

    // AsyncTask 를 이용 UI 처리 및 Background 작업 등을 하나의 클래스에서 작업 할 수 있도록 지원해준다.
    /*
     [보충]

     AsyncTask 클래스는 AsyncTask<Params, Progress, Result>와 같이 제네릭을 이용하여 작성

        Params은 실행 시에 작업에 전달되는 값의 타입
        Progress는 작업의 진행 정도를 나타내는 값의 타입
        Result는 작업의 결과 값를 나타내는 값의 타입.
        앞의 모든 타입을 모두 사용할 필요는 없다. 만약 필요하지 않은 타입 있다면 Void로 표시

     AsyncTask 클래스의 메소드

        doInBackground() 메소드는 작업 스레드에서 자동적으로 실행
        onPreExecute(), onPostExecute(), onProgressUpdate()는 UI쓰레드에서 실행
        doInBackground()가 반환하는 값은 onPostExecute()로 보내진다.
        doInBackground()도중에 언제든지 publishProgress()를 호출하여서 UI스레드에서 onProgressUpdate()를 실행가능
        언제든지 작업을 취소할수있다.

     */

    /**
     * 원격으로부터 JSON형태의 문서를 받아서
     * JSON 객체를 생성한 다음에 객체에서 필요한 데이터 추출
     */
    public String getJsonText() {

        StringBuffer sb = new StringBuffer();
        try {

            //주어진 URL 문서의 내용을 문자열로 얻는다.
            String jsonPage = getStringFromUrl("http://api.ibtk.kr/openapi/ksTerminology_api01.do");

            //읽어들인 JSON포맷의 데이터를 JSON객체로 변환
            JSONObject json = new JSONObject(jsonPage);

            //ksk_list의 값은 배열로 구성 되어있으므로 JSON 배열생성
            JSONArray jArr = json.getJSONArray("ksk_list");

            //배열의 크기만큼 반복하면서, ksNo과 korName의 값을 추출함
            for (int i=0; i<jArr.length(); i++){

                //i번째 배열 할당
                json = jArr.getJSONObject(i);

                //ksNo,korName의 값을 추출함
                String ksNo = json.getString("ksNo");
                String korName = json.getString("korName");
                System.out.println("ksNo:"+ksNo+"/korName:"+korName);

                //StringBuffer 출력할 값을 저장
                sb.append("[ "+ksNo+" ]\n");
                sb.append(korName+"\n");
                sb.append("\n");
            }

        } catch (Exception e) {
            // TODO: handle exception
        }

        return sb.toString() + "hello";
    }//getJsonText()-----------


    // getStringFromUrl : 주어진 URL의 문서의 내용을 문자열로 반환
    public String getStringFromUrl(String pUrl){

        BufferedReader bufreader=null;
        HttpURLConnection urlConnection = null;

        StringBuffer page=new StringBuffer(); //읽어온 데이터를 저장할 StringBuffer객체 생성

        String tmp = "";

        try {

            //[Type1]
            /*
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response = httpclient.execute(new HttpGet(pUrl));
            InputStream contentStream = response.getEntity().getContent();
            */

            //[Type2]
            URL url= new URL(pUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream contentStream = urlConnection.getInputStream();

            if(urlConnection.getResponseCode() == HttpURLConnection.HTTP_ACCEPTED) {
                tmp +="OK";
            }

            bufreader = new BufferedReader(new InputStreamReader(contentStream,"UTF-8"));
            String line = null;

            //버퍼의 웹문서 소스를 줄단위로 읽어(line), Page에 저장함
            while((line = bufreader.readLine())!=null){
                Log.d("line:",line);
                page.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();

            return tmp + "error";

        }finally{
            //자원해제


            try {
                bufreader.close();
                urlConnection.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        return tmp + "Nothing";
    }// getStringFromUrl()-------------------------



}
