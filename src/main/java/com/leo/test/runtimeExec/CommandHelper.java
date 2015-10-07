package com.leo.test.runtimeExec;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * User: Leo Date: 3/26/13 Time: 5:21 PM
 */
public class CommandHelper {
    /** millSeconds */
    public static int DEFAULT_TIMEOUT;
    /** millSeconds */
    public static final int DEFAULT_INTERVAL = 1000;
    public static long START;

    public static void main(String[] args) throws IOException, InterruptedException {
        DEFAULT_TIMEOUT = 5000;
        CommandResult exec = exec("http_load  -r 20 -s 300 hiholiday.txt");
        if (exec != null) {
            System.out.println(exec.getError());
            System.out.println(exec.getExitValue());
            System.out.println(exec.getOutput());
        }
    }

    public static CommandResult exec(String command) throws IOException, InterruptedException {
        Process process = Runtime.getRuntime().exec(command);
        CommandResult commandResult = wait(process);
        if (process != null) {
            process.destroy();
        }
        return commandResult;
    }

    private static boolean isOverTime() {
        return System.currentTimeMillis() - START >= DEFAULT_TIMEOUT;
    }

    private static CommandResult wait(Process process) throws InterruptedException, IOException {
        BufferedReader errorStreamReader = null;
        BufferedReader inputStreamReader = null;
        try {
            errorStreamReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            inputStreamReader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            // timeout control
            START = System.currentTimeMillis();
            boolean isFinished = false;

            for (;;) {
                if (isOverTime()) {
                    CommandResult result = new CommandResult();
                    result.setExitValue(CommandResult.EXIT_VALUE_TIMEOUT);
                    result.setOutput("Command process timeout");
                    process.destroy();
                    return result;
                }

                if (isFinished) {
                    CommandResult result = new CommandResult();
                    result.setExitValue(process.waitFor());

                    // parse error info
                    if (errorStreamReader.ready()) {
                        StringBuilder buffer = new StringBuilder();
                        String line;
                        while ((line = errorStreamReader.readLine()) != null) {
                            buffer.append(line);
                        }
                        result.setError(buffer.toString());
                    }

                    // parse info
                    if (inputStreamReader.ready()) {
                        StringBuilder buffer = new StringBuilder();
                        String line;
                        while ((line = inputStreamReader.readLine()) != null) {
                            buffer.append(line);
                        }
                        result.setOutput(buffer.toString());
                    }
                    return result;
                }

                try {
                    isFinished = true;
                    process.exitValue();
                } catch (IllegalThreadStateException e) {
                    // process hasn't finished yet
                    isFinished = false;
                    Thread.sleep(DEFAULT_INTERVAL);
                }
            }

        } finally {
            if (errorStreamReader != null) {
                try {
                    errorStreamReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (inputStreamReader != null) {
                try {
                    inputStreamReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
