package xyz.fokion.ivy.core.models;

import java.time.Clock;
import java.time.OffsetDateTime;

public abstract sealed class AbstractResultHandler implements NamedEntity permits AbstractResult, Context, ContextResult, TestSuiteResult {
    protected boolean state;
    protected String output;
    protected String errorLog;
    protected OffsetDateTime endTime;

    private final String name;

    public AbstractResultHandler(String name) {
        this.name = name;
    }

    public final String getName() {
        return name;
    }

    protected final void handleResult(Result task) {
        state = state && task.isSuccessful();
        StringBuilder errorLogBuilder = new StringBuilder();
        StringBuilder outputBuilder = new StringBuilder();
        if (task.getOutput().isPresent()) {
            output = outputBuilder.append(output).append("\n").append(task.getOutput().get()).toString();
        }
        if (!task.isSuccessful() && task.getError().isPresent()) {
            errorLog = errorLogBuilder.append(errorLog).append("\n").append(task.getError().get()).toString();
        }
        endTime = OffsetDateTime.now(Clock.systemUTC());
    }
}
