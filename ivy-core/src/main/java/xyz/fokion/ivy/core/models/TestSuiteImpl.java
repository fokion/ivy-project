package xyz.fokion.ivy.core.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Collections;
import java.util.List;


@Data
public class TestSuiteImpl implements TestSuite {

    @JsonProperty("name")
    private final String name;
    @JsonProperty("testcases")
    private List<TestCase> testCases;

    @Override
    public List<TestCase> getTestCases() {
        return Collections.unmodifiableList(testCases);
    }
}
