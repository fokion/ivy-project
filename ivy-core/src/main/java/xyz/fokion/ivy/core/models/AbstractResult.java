package xyz.fokion.ivy.core.models;

import java.time.Clock;
import java.time.OffsetDateTime;
import java.util.Optional;


public sealed class AbstractResult extends AbstractResultHandler implements Result permits ScriptResult, TestCaseResult {

    private final OffsetDateTime startTime;


    public AbstractResult(String name) {
        this(name, OffsetDateTime.now(Clock.systemUTC()));
    }

    public AbstractResult(String name, OffsetDateTime startTime) {
        super(name);
        this.startTime = startTime;
    }


    @Override
    public boolean isSuccessful() {
        return state;
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
    public final Result complete(Result task) {
        if (task != null && task instanceof ScriptResult) {
            this.handleResult(task);
        }
        return this;
    }

}
