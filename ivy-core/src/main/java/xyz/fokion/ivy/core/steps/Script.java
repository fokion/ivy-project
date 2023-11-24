package xyz.fokion.ivy.core.steps;

import com.fasterxml.jackson.annotation.JsonProperty;
import xyz.fokion.ivy.core.models.Result;
import xyz.fokion.ivy.core.models.ScriptResult;
import xyz.fokion.ivy.core.models.Step;
import xyz.fokion.ivy.core.models.TestVisitor;

import java.time.Clock;
import java.time.OffsetDateTime;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Script implements Step {

    private final boolean IS_WINDOWS = System.getProperty("os.name").toLowerCase().startsWith("windows");
    @JsonProperty("name")
    private String name;

    @JsonProperty("type")
    private String type = "exec";

    @JsonProperty("script")
    private String script;

    @JsonProperty("timeout")
    private long timeout = 1;

    ProcessBuilder builder = new ProcessBuilder();


    @Override
    public Result run(TestVisitor executor) {
        OffsetDateTime startTime = OffsetDateTime.now(Clock.systemUTC());
        try {
            return CompletableFuture.supplyAsync(() -> {

                try {
                    List<String> processExecutor = IS_WINDOWS ? List.of("cmd.exe", " /c") : List.of("/bin/sh", "-c");
                    List<String> scriptWithArgs = new ArrayList<>(processExecutor);
                    scriptWithArgs.add(script);
                    builder.command(scriptWithArgs);
                    Process process = builder.start();
                    boolean exited = process.waitFor(timeout, TimeUnit.MINUTES);
                    int exitCode = exited ? 0 : process.exitValue();
                    String output = new String(process.getInputStream().readAllBytes());
                    String errorLog = new String(process.getErrorStream().readAllBytes());
                    return new ScriptResult(name, output, errorLog, exitCode, startTime, OffsetDateTime.now(Clock.systemUTC()));
                } catch (Exception e) {
                    return new ScriptResult(name, "", e.getMessage(), 1, startTime, OffsetDateTime.now(Clock.systemUTC()));
                }
            }, executor.getExecutor()).get(timeout, TimeUnit.MINUTES);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            return new ScriptResult(name, "", e.getMessage(), 1, startTime, OffsetDateTime.now(Clock.systemUTC()));
        }
    }

}
