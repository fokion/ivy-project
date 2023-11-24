package xyz.fokion.ivy.core.models;

import lombok.extern.slf4j.Slf4j;

import java.time.Clock;
import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;


public final class Context extends AbstractResultHandler implements TestVisitor,Result {
    private OffsetDateTime startTime;

    private Result testResult;
    private final ExecutorService executorService;


    public Context(String name, ExecutorService executorService) {
        super(name);
        testResult = new ContextResult(name);
        this.executorService = executorService;
    }

    @Override
    public Result visitTestSuite(TestSuite testSuite) {
        startTime = OffsetDateTime.now(Clock.systemUTC());
        Result testSuiteResult = testSuite.run(this);
        handleResult(testSuiteResult);
        return testResult;
    }

    @Override
    public Result getInitialResult() {
        return testResult;
    }

    @Override
    public ExecutorService getExecutor() {
        return executorService;
    }

    @Override
    public boolean isSuccessful() {
        return testResult.isSuccessful();
    }

    @Override
    public Optional<String> getOutput() {
        return Optional.ofNullable(output);
    }

    @Override
    public Optional<String> getError() {
        return Optional.ofNullable(errorLog);
    }

    @Override
    public OffsetDateTime getStartTime() {
        return startTime;
    }

    @Override
    public OffsetDateTime getEndTime() {
        return endTime;
    }

    @Override
    public Result complete(Result task) {
       return this;
    }

}
