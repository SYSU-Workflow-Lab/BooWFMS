package cn.edu.sysu.workflow.engine.core.model;

import org.w3c.dom.Node;

import java.io.Serializable;
import java.util.Map;

/**
 * The class in this SCXML object model that corresponds to the SCXML
 * &lt;data&gt; child element of the &lt;datamodel&gt; element.
 */
public class Data implements NamespacePrefixesHolder, Serializable {

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The identifier of this data instance.
     * For backwards compatibility this is also the name.
     */
    private String id;

    /**
     * The URL to get the XML data tree from.
     */
    private String src;

    /**
     * The expression that evaluates to the value of this data instance.
     */
    private String expr;

    /**
     * The child XML data tree, parsed as a Node, cloned per execution
     * instance.
     */
    private Node node;

    /**
     * The current XML namespaces in the SCXML document for this action node,
     * preserved for deferred XPath evaluation. Easier than to scrape node
     * above, given the Builtin API.
     */
    private Map<String, String> namespaces;

    /**
     * Constructor.
     */
    public Data() {
        this.id = null;
        this.src = null;
        this.expr = null;
        this.node = null;
    }

    /**
     * Get the id.
     *
     * @return String An identifier.
     */
    public final String getId() {
        return id;
    }

    /**
     * Set the id.
     *
     * @param id The identifier.
     */
    public final void setId(final String id) {
        this.id = id;
    }

    /**
     * Get the URL where the XML data tree resides.
     *
     * @return String The URL.
     */
    public final String getSrc() {
        return src;
    }

    /**
     * Set the URL where the XML data tree resides.
     *
     * @param src The source URL.
     */
    public final void setSrc(final String src) {
        this.src = src;
    }

    /**
     * Get the expression that evaluates to the value of this data instance.
     *
     * @return String The expression.
     */
    public final String getExpr() {
        return expr;
    }

    /**
     * Set the expression that evaluates to the value of this data instance.
     *
     * @param expr The expression.
     */
    public final void setExpr(final String expr) {
        this.expr = expr;
    }

    /**
     * Get the XML data tree.
     *
     * @return Node The XML data tree, parsed as a <code>Node</code>.
     */
    public final Node getNode() {
        return node;
    }

    /**
     * Set the XML data tree.
     *
     * @param node The XML data tree, parsed as a <code>Node</code>.
     */
    public final void setNode(final Node node) {
        this.node = node;
    }

    /**
     * Get the XML namespaces at this action node in the SCXML document.
     *
     * @return Returns the map of namespaces.
     */
    public final Map<String, String> getNamespaces() {
        return namespaces;
    }

    /**
     * Set the XML namespaces at this action node in the SCXML document.
     *
     * @param namespaces The document namespaces.
     */
    public final void setNamespaces(final Map<String, String> namespaces) {
        this.namespaces = namespaces;
    }

}

