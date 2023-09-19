package com.rumpus.common.Controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;

import com.rumpus.common.AbstractCommonObject;

/**
 * Abstract class for common websocket controllers.
 * TODO: Implement this class. started below with greeting...
 */
public class AbstractWebSocketController extends AbstractCommonObject {

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public String greeting(String message) throws Exception {
        return "Hello, " + message + "!";
    }
    
}
