package com.languagematters.tessta.service;

import org.jetbrains.annotations.Contract;

public class OsServices {
    private static final String OS = System.getProperty("os.name").toLowerCase();

    @Contract(pure = true)
    public static boolean isWindows() {
        return (OS.contains("win"));
    }

    @Contract(pure = true)
    public static boolean isMac() {
        return (OS.contains("mac"));
    }

    public static boolean isUnix() {
        return (OS.contains("nix") || OS.contains("nux") || OS.indexOf("aix") > 0);
    }

    @Contract(pure = true)
    public static boolean isSolaris() {
        return (OS.contains("sunos"));
    }
}
