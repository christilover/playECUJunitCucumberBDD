package io.github.xyz.util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogUtil {
    private static final Logger log = LoggerFactory.getLogger(LogUtil.class);

    // Method to log entry dynamically with class name and method name
    public static void logMethodEntry() {
        StackWalker stackWalker = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE);
        String methodName = stackWalker.walk(stream -> stream.skip(1) // Skip the `logMethodEntry` frame
                .findFirst()
                .map(StackWalker.StackFrame::getMethodName)
                .orElse("unknown"));

        String className = stackWalker.walk(stream -> stream.skip(1) // Skip the `logMethodEntry` frame
                .findFirst()
                .map(StackWalker.StackFrame::getClassName)
                .orElse("unknown"));

        log.info("Entering method: {}.{}()", className, methodName);
    }

    // Method to log exit dynamically with class name and method name
    public static void logMethodExit() {
        StackWalker stackWalker = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE);
        String methodName = stackWalker.walk(stream -> stream.skip(1) // Skip the `logMethodExit` frame
                .findFirst()
                .map(StackWalker.StackFrame::getMethodName)
                .orElse("unknown"));

        String className = stackWalker.walk(stream -> stream.skip(1) // Skip the `logMethodExit` frame
                .findFirst()
                .map(StackWalker.StackFrame::getClassName)
                .orElse("unknown"));

        log.info("Exiting method: {}.{}()", className, methodName);
    }
}
