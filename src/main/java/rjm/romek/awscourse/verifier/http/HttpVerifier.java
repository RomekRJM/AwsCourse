package rjm.romek.awscourse.verifier.http;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import rjm.romek.awscourse.model.UserTask;
import rjm.romek.awscourse.verifier.TaskVerifier;

@Service
public class HttpVerifier implements TaskVerifier {

    private static final String GET="GET";
    private static final Integer OK=200;
    private static final Integer TIMEOUT=1500;

    @Override
    public Boolean isCompleted(UserTask userTask) {
        Map<String, String> answers = userTask.getAnswers();
        String u = answers.getOrDefault("u", "");

        if(StringUtils.isBlank(u)) {
            return false;
        }

        return OK.equals(connectAndReturnResponseCode(u));
    }

    private Integer connectAndReturnResponseCode(String u) {
        try {
            URL url = new URL("http://" + u);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod(GET);
            con.setConnectTimeout(TIMEOUT);
            con.setConnectTimeout(TIMEOUT);
            con.connect();
            return con.getResponseCode();
        } catch (IOException exc) {
            throw new RuntimeException(exc);
        }
    }
}
