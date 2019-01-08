package rjm.romek.awscourse.verifier.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import rjm.romek.awscourse.model.UserTask;
import rjm.romek.awscourse.verifier.TaskVerifier;

@Service
public class DBConnectionVerifier implements TaskVerifier {

    private static final String SQL = "SELECT 'I am in.'";

    @Value("${dbUrlFormat}")
    private String urlFormat;

    @Override
    public Boolean isCompleted(UserTask userTask) {
        Map<String, String> answers = userTask.getAnswers();
        String user = answers.getOrDefault("user", "");
        String password = answers.getOrDefault("password", "");
        String endpoint = answers.getOrDefault("endpoint", "");
        String database = answers.getOrDefault("database", "");
        String url = String.format(urlFormat, endpoint, database);

        Boolean queryExecuted = Boolean.FALSE;

        try {
            queryExecuted = testConnection(url, user, password);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        return queryExecuted;
    }

    private Boolean testConnection(String url, String user, String password) throws SQLException {
        DriverManager.setLoginTimeout(5);
        Connection con = DriverManager.getConnection(url, user, password);
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(SQL);

        return rs.next();
    }
}
