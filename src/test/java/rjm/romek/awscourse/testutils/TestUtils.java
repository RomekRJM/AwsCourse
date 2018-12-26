package rjm.romek.awscourse.testutils;

import java.util.Map;

import com.amazonaws.services.ec2.model.IpPermission;
import com.amazonaws.services.ec2.model.IpRange;

import rjm.romek.awscourse.model.Task;
import rjm.romek.awscourse.model.UserTask;

public class TestUtils {
    public static UserTask createUserTask(String description, Map<String, String> answers) {
        Task task = new Task();
        task.setDescription(description);
        UserTask userTask = new UserTask();
        userTask.setAnswers(answers);
        userTask.setTask(task);

        return userTask;
    }

    public static IpPermission createIpPermission(String protocol, Integer fromPort, Integer toPort, String cidr) {
        return new IpPermission()
                .withToPort(toPort)
                .withFromPort(fromPort)
                .withIpProtocol(protocol)
                .withIpv4Ranges(
                        new IpRange().withCidrIp(cidr)
                );
    }
}
