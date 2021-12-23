package com.chipviet.project.service;

import com.chipviet.project.domain.Device;
import com.chipviet.project.domain.User;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class PushNotificationService {

    public static final String API_KEY = "MTczZjMwMGMtMGYzNi00ZmU3LTk3YzQtZTBlYzg5M2NlMTRm";
    public static final String APP_ID = "787049c3-eea5-4a60-ba0a-aee3830063ec";

    /**
     * Default PushNotificationService constructor
     */
    public PushNotificationService() {}

    public static void sendMessageToAllUsers(String message) {
        try {
            String jsonResponse;

            URL url = new URL("https://onesignal.com/api/v1/notifications");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setUseCaches(false);
            con.setDoOutput(true);
            con.setDoInput(true);

            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            con.setRequestProperty("Authorization", "Basic " + API_KEY); //REST API
            con.setRequestMethod("POST");

            String strJsonBody =
                "{" +
                "\"app_id\": \"" +
                APP_ID +
                "\"," +
                "\"included_segments\": [\"All\"]," +
                "\"data\": {\"foo\": \"bar\"}," +
                "\"contents\": {\"en\": \"" +
                message +
                "\"}" +
                "}";

            System.out.println("strJsonBody:\n" + strJsonBody);

            byte[] sendBytes = strJsonBody.getBytes("UTF-8");
            con.setFixedLengthStreamingMode(sendBytes.length);

            OutputStream outputStream = con.getOutputStream();
            outputStream.write(sendBytes);

            int httpResponse = con.getResponseCode();
            System.out.println("httpResponse: " + httpResponse);

            jsonResponse = mountResponseRequest(con, httpResponse);
            System.out.println("jsonResponse:\n" + jsonResponse);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    private static String mountResponseRequest(HttpURLConnection con, int httpResponse) throws IOException {
        String jsonResponse;
        if (httpResponse >= HttpURLConnection.HTTP_OK && httpResponse < HttpURLConnection.HTTP_BAD_REQUEST) {
            Scanner scanner = new Scanner(con.getInputStream(), "UTF-8");
            jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
            scanner.close();
        } else {
            Scanner scanner = new Scanner(con.getErrorStream(), "UTF-8");
            jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
            scanner.close();
        }
        return jsonResponse;
    }

    public static void sendMessageToUser(Long id, String message, List<Device> devices, Optional<User> user) {
        try {
            String jsonResponse;

            URL url = new URL("https://onesignal.com/api/v1/notifications");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setUseCaches(false);
            con.setDoOutput(true);
            con.setDoInput(true);

            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            con.setRequestProperty("Authorization", "Basic " + API_KEY);
            con.setRequestMethod("POST");

            List<String> deviceUuids = devices.stream().map(Device::getDeviceUuid).collect(Collectors.toList());

            String[] array = deviceUuids.toArray(new String[0]);
            System.out.println("REST request to update device cc " + converToJsonStringify(deviceUuids));
            System.out.println("User in push notification " + user);
            String strJsonBody =
                "{" +
                "\"app_id\": \"" +
                APP_ID +
                "\"," +
                "\"include_player_ids\":" +
                converToJsonStringify(deviceUuids) +
                "," +
                "\"data\": {\"title\": \"" +
                id +
                "\"}," +
                "\"contents\": {\"en\": \"" +
                message +
                "\"}" +
                "}";

            byte[] sendBytes = strJsonBody.getBytes("UTF-8");
            con.setFixedLengthStreamingMode(sendBytes.length);

            OutputStream outputStream = con.getOutputStream();
            outputStream.write(sendBytes);

            int httpResponse = con.getResponseCode();

            jsonResponse = mountResponseRequest(con, httpResponse);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    private static String converToJsonStringify(List<String> deviceUuids) {
        String result = "[";
        for (String s : deviceUuids) {
            result = result + "\"" + s + "\",";
        }
        result = result.substring(0, result.length() - 1);
        result = result + "]";
        return result;
    }
}
