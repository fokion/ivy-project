package xyz.fokion.ivy.core.models;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.junit.jupiter.api.Test;
import xyz.fokion.ivy.core.serialisers.TestCaseDeserializer;
import xyz.fokion.ivy.core.serialisers.TestSuiteDeserializer;

import java.io.IOException;
import java.util.List;

class ContextTest {


    private ObjectMapper mapper = new ObjectMapper(YAMLFactory.builder().build())
            .registerModules(getDeserialisers())
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);


    List<SimpleModule> getDeserialisers() {
        SimpleModule module = new SimpleModule();
        module.addDeserializer(TestSuite.class, new TestSuiteDeserializer());
        module.addDeserializer(TestCase.class, new TestCaseDeserializer());
        return List.of(module);
    }

    @Test
    void loadTestSuite() throws IOException {
        TestSuite testSuite = mapper.readValue(getClass().getResource("/test.yml"), TestSuite.class);
        System.out.println(testSuite);
    }

}