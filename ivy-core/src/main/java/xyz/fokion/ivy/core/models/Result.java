package xyz.fokion.ivy.core.models;

import java.time.OffsetDateTime;
import java.util.Optional;

public interface Result extends NamedEntity{
    boolean isSuccessful();
    Optional<String> getOutput();
    Optional<String> getError();

    OffsetDateTime getStartTime();
    OffsetDateTime getEndTime();

    Result complete(Result task);
}
