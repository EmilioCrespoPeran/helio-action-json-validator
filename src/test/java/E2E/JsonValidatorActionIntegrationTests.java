package E2E;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;

import helio.blueprints.components.ComponentType;
import helio.blueprints.components.Components;
import helio.blueprints.exceptions.ExtensionNotFoundException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import utils.E2EUtils;

import org.junit.Test;

/**
 * Set of test which validates JSON documents.
 * 
 * @author Emilio
 *
 */
public class JsonValidatorActionIntegrationTests {

	
	@BeforeClass
    public static void setup() throws ExtensionNotFoundException {
        Components.registerAndLoad(
            "https://github.com/helio-ecosystem/helio-action-json-validator/releases/download/v0.1.0/helio-action-json-validator-0.1.0.jar",
            "helio.actions.validator.JsonValidatorAction",
            ComponentType.ACTION);
        
        Components.registerAndLoad(
                "https://github.com/helio-ecosystem/helio-action-xml-validator/releases/download/v0.1.0/helio-action-xml-validator-0.1.0.jar",
                "helio.actions.validator.XmlValidatorAction",
                ComponentType.ACTION);
    }
	

	/**
	 * The JSON source is incorrect and the validator throws a JsonSyntaxException.
	 */
	@Test
	public void test01_ValidateInvalidJsonDataWithJsonSchema() {
		try {
			String expected = "error";
			JsonObject obtained = JsonParser.parseString(
				E2EUtils.executeTestWithTemplate("01_json-template.txt")).getAsJsonObject();

			assertTrue(obtained.has("status"));
			assertTrue(obtained.has("message"));
			assertEquals(expected, obtained.get("status").getAsString().strip().toLowerCase());
		} catch (Exception e) {
			System.out.println("Error al parsear el resultado");
			assertTrue(e.getMessage(), false);
		}
	}

	/**
	 * The JSON source is correct and the validator verifies it.
	 */
	@Test
	public void test02_ValidateCorrectJsonDataWithJsonSchema() {
		try {
			//String expected = TestUtils.readFile(ActionDirectiveTestUtils.DIR_VALIDATOR_RESOURCES + "json-valid.json");
			String expected = "ok";
			JsonObject obtained = JsonParser.parseString(
				E2EUtils.executeTestWithTemplate("02_json-template.txt")).getAsJsonObject();

			assertTrue(obtained.has("status"));
			assertTrue(!obtained.has("message"));
			assertEquals(expected, obtained.get("status").getAsString().strip().toLowerCase());
		} catch (Exception e) {
			assertTrue(e.getMessage(), false);
		}
	}

	/**
	 * The JSON source is correct but the validator expected a XML data.
	 */
	@Test
	public void test03_ValidateJsonDataWithXmlSchema() {
		try {
			String expected = "error";
			JsonObject obtained = JsonParser.parseString(
				E2EUtils.executeTestWithTemplate("03_json-template.txt")).getAsJsonObject();

			assertTrue(obtained.has("status"));
			assertTrue(obtained.has("message"));
			assertEquals(expected, obtained.get("status").getAsString().strip().toLowerCase());
		} catch (Exception e) {
			assertTrue(e.getMessage(), false);
		}
	}

}
