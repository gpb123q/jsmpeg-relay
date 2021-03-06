package fun.stgoder.jsmpeg_relay.common;

import fun.stgoder.jsmpeg_relay.common.exception.ExecException;
import fun.stgoder.jsmpeg_relay.ps.Cmd;
import fun.stgoder.jsmpeg_relay.ps.Out;
import fun.stgoder.jsmpeg_relay.ps.Ps;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public enum OS {
    LINUX, WIN, MAC;
    private static OS os;

    static {
        String osName = System.getProperty("os.name").toLowerCase();
        if (osName.indexOf("linux") >= 0)
            os = OS.LINUX;
        if (osName.indexOf("windows") >= 0)
            os = OS.WIN;
        if (osName.indexOf("mac") >= 0)
            os = OS.MAC;
    }

    public static List<Integer> pid(String name) throws ExecException {
        long s = System.currentTimeMillis();
        List<Integer> pids = new ArrayList<>();
        Out out = new Ps(
                new Cmd()
                        .add("/bin/bash")
                        .add("-c")
                        .add("pgrep -f " + name))
                .exec();
        int exitValue = out.getExitValue();
        if (exitValue != 0 && StringUtils.isBlank(out.getOutput())) {
            return pids;
        }
        String outputStr = StringUtils.trim(out.getOutput());
        outputStr = StringUtils.strip(outputStr);
        String[] pidStrs = outputStr.split("\n");
        for (String pidStr : pidStrs) {
            if (StringUtils.isNotBlank(pidStr)) {
                try {
                    int pid = Integer.valueOf(pidStr);
                    pids.add(pid);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        long e = System.currentTimeMillis();
        return pids;
    }

    public static boolean isLINUX() {
        return os == OS.LINUX ? true : false;
    }

    public static boolean isWIN() {
        return os == OS.WIN ? true : false;
    }

    public static boolean isMAC() {
        return os == OS.MAC ? true : false;
    }
}
