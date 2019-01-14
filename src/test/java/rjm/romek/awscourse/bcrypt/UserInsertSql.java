package rjm.romek.awscourse.bcrypt;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import lombok.Data;

@Data
class UserInsertSql {
    private static final String SQL = "INSERT INTO user(user_id, username, password, enabled) \n" +
            "VALUES (%d, '%s', '%s', true);\n";
    private String userName;
    private String password;
    private int userNo;


    private UserInsertSql(String userName, String password, int userNo) {
        this.userName = userName;
        this.password = password;
        this.userNo = userNo;
    }

    public String convertToSqlInsert() {
        return String.format(SQL, userNo, userName, password);
    }

    public static UserInsertSqlBuilder builder() {
        return new UserInsertSqlBuilder();
    }

    public static class UserInsertSqlBuilder {

        private String userName;
        private String password;
        private String encryptedPassword;
        private BCryptPasswordEncoder passwordEncoder;
        private static int globalUserNo = 1000;

        private UserInsertSqlBuilder() {
            this.passwordEncoder = new BCryptPasswordEncoder(12);
        }

        public UserInsertSqlBuilder withUserName(String userName) {
            this.userName = userName;
            return this;
        }

        public UserInsertSqlBuilder withPassword(String password) {
            this.password = password;
            return this;
        }

        public UserInsertSql build() {
            this.encryptedPassword = passwordEncoder.encode(password);
            return new UserInsertSql(this.userName, this.encryptedPassword, globalUserNo++);
        }
    }
}
