package com.expertlead.logger.target;

import com.expertlead.logger.Level;
import com.expertlead.logger.Message;

import java.util.List;

public interface Target {

    void log(Message message);

    void log(List<Message> message);

    Level getLogLevel();
}
