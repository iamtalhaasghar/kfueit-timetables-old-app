package pk.edu.kfueit.timetable_app;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class TimeTable extends AsyncTask<String, Void, JSONObject> {

    private static String daysOfWeek[] = {"Mon","Tue","Wed","Thu","Fri","Sat","Sun"};
    private int toSkip[] = new int[7];

    public static final String TEACHER = "teacher";
    public static final String ROOM = "room";
    public static final String SUBJECT = "subject";
    public static final String START_TIME = "start";
    public static final String END_TIME = "end";

    private static final String TIME_SIDE = "timeside";
    private static final String FIXED_HEIGHT = "fixedheight";
    private static final String LIGHT_GREEN = "lightgreen";
    private static final String BREAK_TIME = "breaktime";



    private JSONObject scrapClassTimeTable(String url) {

        try {
            Document doc = Jsoup.connect(url).get();
            Element table = doc.getElementById("basic-table");

            Element tbody = table.getElementsByTag("tbody").last();

            JSONObject timeTable = new JSONObject();

            for (Element tr : tbody.getElementsByTag("tr")) {
                Elements elements = tr.getElementsByTag("td");
                for (int i = 0, counter = 0; i < elements.size();) {
                    Element td = elements.get(i);
                    String cellType = td.attr("class").trim();
                    if (toSkip[counter] == 0) {
                        if (cellType.equalsIgnoreCase(TIME_SIDE)) {
                            i++;
                        } else if (cellType.equalsIgnoreCase(FIXED_HEIGHT)) {
                            counter++;
                            i++;

                        } else if (cellType.equalsIgnoreCase(LIGHT_GREEN)) {
                            String lectureData = td.html().trim();
                            lectureData = lectureData.replaceAll("&amp;", "&");
                            lectureData = lectureData.replaceAll("\\u2013", "-");
                            Scanner sc = new Scanner(lectureData);
                            sc = sc.useDelimiter("<br>");
                            String subject = sc.next();
                            String teacher = sc.next();
                            String room = sc.next();
                            String time[] = sc.next().split("-");
                            String startTime = time[0].trim();
                            String endTime = time[1].trim();

                            JSONObject lecture = new JSONObject();
                            lecture.put(SUBJECT, subject);
                            lecture.put(TEACHER, teacher);
                            lecture.put(ROOM, room);
                            lecture.put(START_TIME, startTime);
                            lecture.put(END_TIME, endTime);

                            String key = daysOfWeek[counter];
                            if(!timeTable.has(key)){
                                JSONArray lecturesArray = new JSONArray();
                                lecturesArray.put(lecture);
                                timeTable.put(key, lecturesArray);
                            }
                            else{
                                timeTable.accumulate(key, lecture);
                            }

                            toSkip[counter] = getSpan(td) - 1;
                            counter++;
                            i++;

                        } else if (cellType.equalsIgnoreCase(BREAK_TIME)) {



                            toSkip[counter] = getSpan(td) - 1;
                            counter++;
                            i++;
                        }

                    } else {
                        toSkip[counter]--;
                        counter++;
                    }
                }

            }

            return timeTable;

        } catch (IOException ex) {
            System.out.println(ex);
        } catch (JSONException ex) {
            System.out.println(ex);
        }
        return null;
    }

    private int getSpan(Element td) {
        String rowspanText = td.attr("rowspan").trim();
        int rowspan = Integer.parseInt(rowspanText);
        return rowspan;
    }

    public static String getDay(int number){
        return daysOfWeek[number - 1];
    }

    @Override
    protected JSONObject doInBackground(String... urls) {
        // Create a trust manager that does not validate certificate chains
        TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
            }

            public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {
            }
        } };

        // Install the all-trusting trust manager
        try {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return scrapClassTimeTable(urls[0]);
    }
}
