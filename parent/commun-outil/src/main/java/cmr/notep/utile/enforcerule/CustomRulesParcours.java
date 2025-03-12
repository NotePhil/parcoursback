package cmr.notep.utile.enforcerule;

import lombok.Getter;
import lombok.Setter;
import org.apache.maven.enforcer.rule.api.*;
import org.apache.maven.plugin.logging.Log;
import org.codehaus.plexus.component.configurator.expression.ExpressionEvaluationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

@Getter
@Setter
public class CustomRulesParcours implements EnforcerRule {
    private String location;
    public CustomRulesParcours() {
    }
    @Override
    public void execute(EnforcerRuleHelper helper) throws EnforcerRuleException {
        Log log = helper.getLog();

        try {
            // Add your custom logic here. For example, you can fail the build if a certain condition is not met:
            String someProperty = (String) helper.evaluate("${runTestsFromMaven}");
            if (someProperty == null || someProperty.isEmpty()) {
                throw new EnforcerRuleException("The property 'runTestsFromMaven' is missing or empty");
            }
            log.info("valeur de la propriété runTestsFromMaven : " + someProperty);
            findFailures(location, log);
        } catch (ExpressionEvaluationException | ParserConfigurationException | IOException | SAXException e) {
            throw new EnforcerRuleException("Failed to lookup an expression", e);
        }
    }

    public void findFailures(String directoryPath, Log log) throws EnforcerRuleException, ParserConfigurationException, IOException, SAXException {
        File directory = new File(directoryPath);
        File[] files = directory.listFiles((dir, name) -> name.toLowerCase().endsWith(".xml"));

        if (files != null) {
            for (File file : files) {
                try {
                    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                    Document doc = dBuilder.parse(file);
                    doc.getDocumentElement().normalize();

                    NodeList testSuites = doc.getElementsByTagName("testsuite");
                    for (int i = 0; i < testSuites.getLength(); i++) {
                        Node testSuite = testSuites.item(i);
                        if (testSuite.getNodeType() == Node.ELEMENT_NODE) {
                            NodeList testCases = ((Element) testSuite).getElementsByTagName("testcase");
                            for (int j = 0; j < testCases.getLength(); j++) {
                                Node testCase = testCases.item(j);
                                if (testCase.getNodeType() == Node.ELEMENT_NODE) {
                                    NodeList failures = ((Element) testCase).getElementsByTagName("failure");
                                    if (failures.getLength() > 0) {
                                        log.error("Failure found in file: " + file.getName());
                                        log.error("Failure details: " + failures.item(0).getTextContent());
                                        throw new EnforcerRuleException("Des tests ont échoué, Veuillez vérifier les logs");
                                    }
                                    NodeList errors = ((Element) testCase).getElementsByTagName("error");
                                    if (errors.getLength() > 0) {
                                        log.error("Error found in file: " + file.getName());
                                        log.error("Errors details: " + errors.item(0).getTextContent());
                                        throw new EnforcerRuleException("Des tests ont échoué, Veuillez vérifier les logs");
                                    }
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    if(e instanceof EnforcerRuleException)
                        throw e;
                   log.error("Erreur  " + e.getMessage());
                }
            }
        }
    }

    @Override
    public boolean isCacheable() {
        return false;
    }

    @Override
    public boolean isResultValid(EnforcerRule cachedRule) {
        return false;
    }

    @Override
    public String getCacheId() {
        return "false";
    }
}
