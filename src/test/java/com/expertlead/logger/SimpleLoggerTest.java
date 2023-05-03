package com.expertlead.logger;

import com.expertlead.logger.target.impl.AsyncLogTarget;
import com.expertlead.logger.target.impl.ConsoleLogTarget;
import com.expertlead.logger.target.impl.EmailLogTarget;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class SimpleLoggerTest {

    @Test
    public void when_target_with_error_level() {

        ConsoleLogTarget consoleTarget = spy(new ConsoleLogTarget());
        consoleTarget.setMinLogLevel(Level.ERROR);

        SimpleLogger logger = new SimpleLogger();
        logger.addLogTarget("CONSOLE", consoleTarget);

        logger.error("This is error log");
        logger.warn("This is warn log");
        logger.info("This is info log");
        logger.debug("This is debug log");

        verify(consoleTarget, times(1)).log(any(Message.class));
    }

    @Test
    public void when_target_with_warn_level() {

        ConsoleLogTarget consoleTarget = spy(new ConsoleLogTarget());
        consoleTarget.setMinLogLevel(Level.WARN);

        SimpleLogger logger = new SimpleLogger();
        logger.addLogTarget("CONSOLE", consoleTarget);

        logger.error("This is error log");
        logger.warn("This is warn log");
        logger.info("This is info log");
        logger.debug("This is debug log");

        verify(consoleTarget, times(2)).log(any(Message.class));
    }

    @Test
    public void when_target_with_info_level() {

        ConsoleLogTarget consoleTarget = spy(new ConsoleLogTarget());
        consoleTarget.setMinLogLevel(Level.INFO);

        SimpleLogger logger = new SimpleLogger();
        logger.addLogTarget("CONSOLE", consoleTarget);

        logger.error("This is error log");
        logger.warn("This is warn log");
        logger.info("This is info log");
        logger.debug("This is debug log");

        verify(consoleTarget, times(3)).log(any(Message.class));
    }

    @Test
    public void when_target_with_debug_level() {

        ConsoleLogTarget consoleTarget = spy(new ConsoleLogTarget());
        consoleTarget.setMinLogLevel(Level.DEBUG);

        SimpleLogger logger = new SimpleLogger();
        logger.addLogTarget("CONSOLE", consoleTarget);

        logger.error("This is error log");
        logger.warn("This is warn log");
        logger.info("This is info log");
        logger.debug("This is debug log");

        verify(consoleTarget, times(4)).log(any(Message.class));
    }

    @Test
    public void when_multiple_targets() {

        ConsoleLogTarget consoleTarget = spy(new ConsoleLogTarget());
        consoleTarget.setMinLogLevel(Level.DEBUG);

        EmailLogTarget emailLogTarget = spy(new EmailLogTarget());
        emailLogTarget.setMinLogLevel(Level.ERROR);

        SimpleLogger logger = new SimpleLogger();
        logger.addLogTarget("CONSOLE", consoleTarget);
        logger.addLogTarget("EMAIL", emailLogTarget);

        logger.error("This is error log");
        logger.warn("This is warn log");
        logger.info("This is info log");
        logger.debug("This is debug log");

        verify(consoleTarget, times(4)).log(any(Message.class));
        verify(emailLogTarget, times(1)).log(any(Message.class));
    }

    @Test
    public void async_target() {

        EmailLogTarget emailLogTarget = new EmailLogTarget();
        emailLogTarget.setMinLogLevel(Level.INFO);
        AsyncLogTarget asyncLogTarget = spy(new AsyncLogTarget(emailLogTarget, 3));

        SimpleLogger logger = new SimpleLogger();
        logger.addLogTarget("ASYNC EMAIL", asyncLogTarget);

        logger.error("This is error log");
        logger.warn("This is warn log");
        logger.info("This is info log");
        logger.debug("This is debug log");

        verify(asyncLogTarget, times(3)).log(any(Message.class));
    }
}
