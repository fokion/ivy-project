package xyz.fokion.ivy.core.models;

import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;


public final class ScriptResult extends AbstractResult {

    public ScriptResult(String name, String output, String errorLog, int exitCode, OffsetDateTime startTime, OffsetDateTime endTime) {
        super(name);
        this.output = output;
        this.errorLog = errorLog;
        this.state = exitCode == 0;
        this.endTime = endTime;
    }
}
