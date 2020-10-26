package com.bugull.android.ext.kotlin;

public class Utils {
    public boolean check(String user) {
        return user.length() > 6;
    }

    public Utils getInstance() {
        return Holder.INSTANCE;
    }

    private static final class Holder {
        private static final Utils INSTANCE = new Utils();
    }
}
