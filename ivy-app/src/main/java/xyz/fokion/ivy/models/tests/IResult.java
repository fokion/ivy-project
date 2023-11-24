package xyz.fokion.ivy.models.tests;

import xyz.fokion.ivy.models.tests.Result;

import java.util.Map;
import java.util.Optional;

public sealed interface IResult permits Result {

    boolean isSuccessful();
    Optional<String> getOutput();
    Optional<String> getError();

    Map<String,Object> getInput();

}
