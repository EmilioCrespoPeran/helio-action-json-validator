package actions;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import helio.actions.validator.JsonValidatorAction;

public class JsonValidatorTests {

    private JsonValidatorAction validator = new JsonValidatorAction();

    @Test
    public void test01_validateJsonObject() throws Exception {
        String json = "{\"telephone\":\"123456789\",\"name\":\"John Doe\",\"jobTitle\":\"Mechanic\",\"id\":\"AMKo1IQBTwbSPbB5JMEc\"}";

        JsonObject r = JsonParser.parseString(validator.run(json)).getAsJsonObject();

        assertEquals("ok", r.get("status").getAsString());
    }

    @Test
    public void test02_validateJsonArray() throws Exception {
        String json = "[{\"telephone\":\"123456789\",\"name\":\"John Doe\",\"jobTitle\":\"Mechanic\",\"id\":\"AMKo1IQBTwbSPbB5JMEc\"}]";

        JsonObject r = JsonParser.parseString(validator.run(json)).getAsJsonObject();

        assertEquals("ok", r.get("status").getAsString());
    }

    @Test
    public void test03_validateInvalidJson() throws Exception {
        String json = "[\"telephone\":\"123456789\",\"name\":\"John Doe\",\"jobTitle\":\"Mechanic\",\"id\":\"AMKo1IQBTwbSPbB5JMEc\"]";

        JsonObject r = JsonParser.parseString(validator.run(json)).getAsJsonObject();

        assertEquals("error", r.get("status").getAsString());
    }

}
