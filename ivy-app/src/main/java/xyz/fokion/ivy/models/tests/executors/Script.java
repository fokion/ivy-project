package xyz.fokion.ivy.models.tests.executors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import xyz.fokion.ivy.models.tests.AbstractExecutor;
import xyz.fokion.ivy.models.tests.IResult;
import xyz.fokion.ivy.models.tests.Step;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

@Component
@Slf4j
public class Script extends AbstractExecutor {

    private final boolean IS_WINDOWS = System.getProperty("os.name").toLowerCase().startsWith("windows");
    private final String name;
    ProcessBuilder builder = new ProcessBuilder();

    public static final String SCRIPT_KEY = "script";
    public static final String DIRECTORY_KEY = "dir";
    public static final String ARGS_KEY = "args";

    public static final Map<String, String> KEYS = Map.of(
            NAME_KEY, "the name of the step",
            SCRIPT_KEY, "the script command",
            DIRECTORY_KEY, "the directory of execution",
            ARGS_KEY, "the arguments of the script",
            TIMEOUT_KEY, "the timeout of the script"
    );

    public Script(Map<String, Object> inputArgs) {
        super(inputArgs);

        name = String.valueOf(inputArgs.getOrDefault(NAME_KEY, "script"));

    }


    @Override
    protected Process run() throws IOException {
        if (inputArgs.containsKey(DIRECTORY_KEY)) {
            File directory = new File(String.valueOf(inputArgs.get(DIRECTORY_KEY)));
            if (directory.isDirectory() || !directory.exists()) {
                throw new IllegalArgumentException("Directory does not exist or is not a directory");
            }
            builder.directory(directory);
        }

        var args = inputArgs.getOrDefault(ARGS_KEY, new ArrayList<String>());
        List<String> executor = IS_WINDOWS ? List.of("cmd.exe", " /c") : List.of("/bin/sh", "-c");
        List<String> scriptWithArgs = new ArrayList<>(executor);
        scriptWithArgs.add((String) inputArgs.get(SCRIPT_KEY));

        if (!(args instanceof List)) {
            scriptWithArgs.add(args.toString());
        } else if (args instanceof Collection) {
            scriptWithArgs.addAll((Collection<? extends String>) args);
        }
        builder.command(scriptWithArgs);

        return builder.start();
    }

    @Override
    public List<Step> getSteps() {
        return List.of(this);
    }

    @Override
    public CompletableFuture<IResult> run(Optional<String> previousOutput, ExecutorService executorService) {
        log.debug("need to handle the previous output {}", previousOutput);
        return run(executorService);
    }

    @Override
    public String getName() {
        return name;
    }
}
