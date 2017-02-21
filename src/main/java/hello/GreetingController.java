package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Controller
public class GreetingController {
    private final SimpMessagingTemplate messagingTemplates;

    @Autowired
    public GreetingController(SimpMessagingTemplate messagingTemplates) {
        this.messagingTemplates = messagingTemplates;
    }

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    ExecutorService executor = Executors.newSingleThreadExecutor();
    @MessageMapping("/trade")
    @SendToUser("/queue/position-updates")
        public Greeting executeGreeting(HelloMessage helloMessage, SimpMessageHeaderAccessor accessor) throws Exception {
        Thread.sleep(1000); // simulated delay
        System.out.println("before get session=============================");
        String applicantId=accessor.getSessionId();
        System.out.println("after get session, and ID is ===========" + applicantId);
        executor.submit(() -> {
            jobEnd(applicantId, helloMessage.getName());
        });
        return null;
    }

    private void jobEnd(String sessionId, String message) {
        System.out.println("session=============================" + sessionId);
        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor
                .create(SimpMessageType.MESSAGE);
        headerAccessor.setSessionId(sessionId);
        headerAccessor.setLeaveMutable(true);

        messagingTemplate.convertAndSendToUser(sessionId,"/queue/position-updates", "http://tsn.baidu.com/text2audio?tex=" + message + "&lan=zh&cuid=1&ctp=1&tok=24.2beb0786a12a2b365a92239414f5b6db.2592000.1488864448.282335-9247277%22",
                headerAccessor.getMessageHeaders());
    }

}
