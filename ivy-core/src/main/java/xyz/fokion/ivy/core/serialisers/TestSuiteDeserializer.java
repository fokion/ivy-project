package xyz.fokion.ivy.core.serialisers;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.ArrayNode;
import xyz.fokion.ivy.core.models.TestCase;
import xyz.fokion.ivy.core.models.TestCaseImpl;
import xyz.fokion.ivy.core.models.TestSuite;
import xyz.fokion.ivy.core.models.TestSuiteImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TestSuiteDeserializer extends StdDeserializer<TestSuite> {

    public TestSuiteDeserializer() {
        this(null);
    }

    public TestSuiteDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public TestSuite deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        //deserialize the test suite
        JsonNode node = jp.getCodec().readTree(jp);
        TestSuiteImpl testSuite = new TestSuiteImpl(node.get("name").asText());
        List<TestCase> testCases = new ArrayList<>();
        if(node.hasNonNull("testcases") && node.get("testcases").isArray()) {
            for (JsonNode n : (ArrayNode) node.get("testcases")) {
                try {
                    TestCase testCase = jp.getCodec().treeToValue(n, TestCase.class);
                    testCases.add(testCase);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        testSuite.setTestCases(testCases);
        return testSuite;
    }
}
