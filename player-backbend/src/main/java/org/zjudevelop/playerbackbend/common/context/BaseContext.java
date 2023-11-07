package org.zjudevelop.playerbackbend.common.context;

public class BaseContext {
    public static ThreadLocal<Long> threadLocal = new ThreadLocal();

    public static void setCurrentUserId(Long id) {
        threadLocal.set(id);
    }

    public static Long getCurrentUserId() {
        return threadLocal.get();
    }

    public static void removeCurrentUserId() {
        threadLocal.remove();
    }
}
