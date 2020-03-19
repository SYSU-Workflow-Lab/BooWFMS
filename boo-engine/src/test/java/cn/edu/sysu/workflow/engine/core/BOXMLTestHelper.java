package cn.edu.sysu.workflow.engine.core;

import cn.edu.sysu.workflow.engine.core.env.SimpleDispatcher;
import cn.edu.sysu.workflow.engine.core.env.Tracer;
import cn.edu.sysu.workflow.engine.core.io.BOXMLReader;
import cn.edu.sysu.workflow.engine.core.model.CustomAction;
import cn.edu.sysu.workflow.engine.core.model.EnterableState;
import cn.edu.sysu.workflow.engine.core.model.SCXML;
import cn.edu.sysu.workflow.engine.core.model.TransitionTarget;
import org.junit.Assert;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * Helper methods for running SCXML unit tests.
 */
public class BOXMLTestHelper {

    /**
     * Serialized Commons SCXML object model temporary store.
     * Assumes the default build artifacts are generated in the
     * "target" directory (so it can be removed via a clean build).
     */
    public static final String SERIALIZATION_DIR = "target/serialization";
    public static final String SERIALIZATION_FILE_PREFIX = SERIALIZATION_DIR + "/scxml";
    public static final String SERIALIZATION_FILE_SUFFIX = ".ser";

    // Generate a unique sequence number for the serialization files
    private static int sequence = 0;

    private synchronized static String getSequenceNumber() {
        return Integer.toString(++sequence);
    }

    public static URL getResource(String name) {
        return BOXMLTestHelper.class.getClassLoader().getResource(name);
    }

    public static SCXML parse(final String scxmlResource) throws Exception {
        Assert.assertNotNull(scxmlResource);
        return parse(getResource(scxmlResource), null);
    }

    public static SCXML parse(final URL url) throws Exception {
        return parse(url, null);
    }

    public static SCXML parse(final String scxmlResource, final List<CustomAction> customActions) throws Exception {
        Assert.assertNotNull(scxmlResource);
        return parse(getResource(scxmlResource), customActions);
    }

    public static SCXML parse(final URL url, final List<CustomAction> customActions) throws Exception {
        Assert.assertNotNull(url);
        BOXMLReader.Configuration configuration = new BOXMLReader.Configuration(null, null, customActions);
        SCXML scxml = BOXMLReader.read(url, configuration);
        Assert.assertNotNull(scxml);
        SCXML roundtrip = testModelSerializability(scxml);
        return roundtrip;
    }

    public static SCXML parse(final Reader scxmlReader, final List<CustomAction> customActions) throws Exception {
        Assert.assertNotNull(scxmlReader);
        BOXMLReader.Configuration configuration = new BOXMLReader.Configuration(null, null, customActions);
        SCXML scxml = BOXMLReader.read(scxmlReader, configuration);
        Assert.assertNotNull(scxml);
        SCXML roundtrip = testModelSerializability(scxml);
        return roundtrip;
    }

    public static BOXMLExecutor getExecutor(final URL url) throws Exception {
        return getExecutor(parse(url), null);
    }

    public static BOXMLExecutor getExecutor(final String scxmlResource) throws Exception {
        return getExecutor(parse(scxmlResource), null);
    }

    public static BOXMLExecutor getExecutor(final SCXML scxml) throws Exception {
        return getExecutor(scxml, null);
    }

    public static BOXMLExecutor getExecutor(final URL url, final Evaluator evaluator) throws Exception {
        return getExecutor(parse(url), evaluator);
    }

    public static BOXMLExecutor getExecutor(final SCXML scxml, final Evaluator evaluator) throws Exception {
        return getExecutor(scxml, evaluator, new SimpleDispatcher());
    }

    public static BOXMLExecutor getExecutor(final SCXML scxml, final Evaluator evaluator, final EventDispatcher eventDispatcher) throws Exception {
        Tracer trc = new Tracer();
        BOXMLExecutor exec = new BOXMLExecutor(evaluator, eventDispatcher, trc);
        exec.setStateMachine(scxml);
        exec.addListener(scxml, trc);
        return exec;
    }

    public static TransitionTarget lookupTransitionTarget(BOXMLExecutor exec, String id) {
        return exec.getStateMachine().getTargets().get(id);
    }

    public static Context lookupContext(BOXMLExecutor exec, String id) {
        TransitionTarget tt = lookupTransitionTarget(exec, id);
        if (tt == null || !(tt instanceof EnterableState)) {
            return null;
        }
        return exec.getSCInstance().lookupContext((EnterableState) tt);
    }

    public static void assertState(BOXMLExecutor exec, String expectedStateId) {
        Set<EnterableState> currentStates = exec.getStatus().getStates();
        Assert.assertEquals("Expected 1 simple (leaf) state with id '"
                        + expectedStateId + "' but found " + currentStates.size() + " states instead.",
                1, currentStates.size());
        Assert.assertEquals(expectedStateId, currentStates.iterator().
                next().getId());
    }

    public static Set<EnterableState> fireEvent(BOXMLExecutor exec, String name) throws Exception {
        return fireEvent(exec, name, null);
    }

    public static Set<EnterableState> fireEvent(BOXMLExecutor exec, String name, Object payload) throws Exception {
        TriggerEvent[] evts = {new TriggerEvent(name, TriggerEvent.SIGNAL_EVENT, payload)};
        exec.triggerEvents(evts);
        return exec.getStatus().getStates();
    }

    public static Set<EnterableState> fireEvent(BOXMLExecutor exec, TriggerEvent te) throws Exception {
        exec.triggerEvent(te);
        return exec.getStatus().getStates();
    }

    public static Set<EnterableState> fireEvents(BOXMLExecutor exec, TriggerEvent[] evts) throws Exception {
        exec.triggerEvents(evts);
        return exec.getStatus().getStates();
    }

    public static void assertPostTriggerState(BOXMLExecutor exec,
                                              String triggerEventName, String expectedStateId) throws Exception {
        assertPostTriggerState(exec, triggerEventName, null, expectedStateId);
    }

    public static void assertPostTriggerState(BOXMLExecutor exec,
                                              String triggerEventName, Object payload, String expectedStateId) throws Exception {
        assertPostTriggerState(exec, new TriggerEvent(triggerEventName,
                TriggerEvent.SIGNAL_EVENT, payload), expectedStateId);
    }

    public static void assertPostTriggerStates(BOXMLExecutor exec,
                                               String triggerEventName, String[] expectedStateIds) throws Exception {
        assertPostTriggerStates(exec, triggerEventName, null, expectedStateIds);
    }

    public static void assertPostTriggerStates(BOXMLExecutor exec,
                                               String triggerEventName, Object payload, String[] expectedStateIds) throws Exception {
        assertPostTriggerStates(exec, new TriggerEvent(triggerEventName,
                TriggerEvent.SIGNAL_EVENT, payload), expectedStateIds);
    }

    public static void assertPostTriggerState(BOXMLExecutor exec,
                                              TriggerEvent triggerEvent, String expectedStateId) throws Exception {
        Set<EnterableState> currentStates = fireEvent(exec, triggerEvent);
        Assert.assertEquals("Expected 1 simple (leaf) state with id '"
                        + expectedStateId + "' on firing event " + triggerEvent
                        + " but found " + currentStates.size() + " states instead.",
                1, currentStates.size());
        Assert.assertEquals(expectedStateId, currentStates.iterator().
                next().getId());
    }

    public static void assertPostTriggerStates(BOXMLExecutor exec,
                                               TriggerEvent triggerEvent, String[] expectedStateIds) throws Exception {
        if (expectedStateIds == null || expectedStateIds.length == 0) {
            Assert.fail("Must specify an array of one or more "
                    + "expected state IDs");
        }
        Set<EnterableState> currentStates = fireEvent(exec, triggerEvent);
        int n = expectedStateIds.length;
        Assert.assertEquals("Expected " + n + " simple (leaf) state(s) "
                        + " on firing event " + triggerEvent + " but found "
                        + currentStates.size() + " states instead.",
                n, currentStates.size());
        List<String> expectedStateIdList = new ArrayList<String>(Arrays.asList(expectedStateIds));
        for (TransitionTarget tt : currentStates) {
            String stateId = tt.getId();
            if (!expectedStateIdList.remove(stateId)) {
                Assert.fail("Expected state with id '" + stateId
                        + "' in current states on firing event "
                        + triggerEvent);
            }
        }
        Assert.assertEquals("More states in current configuration than those"
                + "specified in the expected state ids '" + expectedStateIds
                + "'", 0, expectedStateIdList.size());
    }

    public static SCXML testModelSerializability(final SCXML scxml) throws Exception {
        File fileDir = new File(SERIALIZATION_DIR);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        String filename = SERIALIZATION_FILE_PREFIX
                + getSequenceNumber() + SERIALIZATION_FILE_SUFFIX;
        SCXML roundtrip = null;
        ObjectOutputStream out =
                new ObjectOutputStream(new FileOutputStream(filename));
        out.writeObject(scxml);
        out.close();
        ObjectInputStream in =
                new ObjectInputStream(new FileInputStream(filename));
        roundtrip = (SCXML) in.readObject();
        in.close();
        return roundtrip;
    }

    public static BOXMLExecutor testInstanceSerializability(final BOXMLExecutor exec) throws Exception {
        File fileDir = new File(SERIALIZATION_DIR);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        String filename = SERIALIZATION_FILE_PREFIX
                + getSequenceNumber() + SERIALIZATION_FILE_SUFFIX;
        ObjectOutputStream out =
                new ObjectOutputStream(new FileOutputStream(filename));
        out.writeObject(exec.detachInstance());
        out.close();
        ObjectInputStream in =
                new ObjectInputStream(new FileInputStream(filename));
        exec.attachInstance((BOInstance) in.readObject());
        in.close();
        return exec;
    }

    /**
     * Parses a String containing XML source into a {@link Document}.
     *
     * @param xml The XML source as a String.
     * @return The parsed {@link Document}.
     */
    public static Document stringToXMLDocument(final String xml) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setNamespaceAware(true);
            return dbf.newDocumentBuilder().parse(new InputSource(new StringReader(xml)));
        } catch (Exception e) {
            throw new RuntimeException("Exception parsing String to Node:\n" + xml);
        }
    }

    /**
     * Discourage instantiation.
     */
    private BOXMLTestHelper() {
        super();
    }
}
