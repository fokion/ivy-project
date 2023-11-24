package xyz.fokion.ivy.core.serialisers;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import xyz.fokion.ivy.core.models.Step;
import xyz.fokion.ivy.core.models.TestCase;
import xyz.fokion.ivy.core.models.TestCaseImpl;
import xyz.fokion.ivy.core.steps.Script;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TestCaseDeserializer extends StdDeserializer<TestCase> {
    public TestCaseDeserializer(Class<?> vc) {
        super(vc);
    }

    public TestCaseDeserializer() {
        this(null);
    }

    @Override
    public TestCase deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JacksonException {
        JsonNode node = jp.getCodec().readTree(jp);
        TestCaseImpl testCase = new TestCaseImpl(node.get("name").asText());
        List<Step> steps = new ArrayList<>();
        node.get("steps").forEach(n -> {
            try {
                n.get("type").asText();
                Class<? extends Step> type = getClassByType(n.get("type").asText());
                Step step = jp.getCodec().treeToValue(n, type);
                steps.add(step);
            } catch (Throwable e) {
                //ignore
            }
        });
        testCase.setSteps(steps);

        return testCase;
    }

    private Class<? extends Step> getClassByType(String type) {
        return switch (type) {
            case "exec" -> Script.class;
            default -> throw new RuntimeException("Unknown step type: " + type);
        };
    }
}
