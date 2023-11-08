package xyz.fokion.ivy.models.tests;

import lombok.extern.slf4j.Slf4j;
import xyz.fokion.ivy.models.tests.executors.StepExecutor;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
public non-sealed abstract class AbstractExecutor implements Step, StepExecutor {

    protected final Map<String, Object> inputArgs;

    public AbstractExecutor(Map<String,Object> inputArgs){
        this.inputArgs = inputArgs;
    }

    protected Process run() throws IOException {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public CompletableFuture<IResult> run(ExecutorService executorService) {
        return CompletableFuture.supplyAsync(() -> {
            try{
                Process process = run();
                int timeout = 1;
                try{
                    timeout = Integer.parseInt(String.valueOf(inputArgs.getOrDefault(TIMEOUT_KEY,1)));
                }catch (Exception e){
                    log.debug("Could not parse timeout",e);
                }
                boolean exited = process.waitFor(timeout, TimeUnit.MINUTES);
                int exitCode = exited?0:process.exitValue();
                String output = new String(process.getInputStream().readAllBytes());
                String errorLog = new String(process.getErrorStream().readAllBytes());
                return new Result(exitCode,output,errorLog,inputArgs);
            }catch (Exception e){
                log.warn("Could not run process",e);
                return new Result(1,"",e.getMessage(),inputArgs);
            }
        },executorService);
    }

    @Override
    public boolean isParallel() {
        return false;
    }

}
