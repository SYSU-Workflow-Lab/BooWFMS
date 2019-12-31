package cn.edu.sysu.workflow.engine.core.model;

import java.io.Serializable;

/**
 * An abstract base class for elements in SCXML that can serve as a
 * &lt;target&gt; for a &lt;transition&gt;, such as State or Parallel.
 */
public abstract class TransitionTarget implements Serializable, Observable {

    private static final EnterableState[] ZERO_ANCESTORS = new EnterableState[0];

    /**
     * The id for this {@link Observable} which is unique within the SCXML state machine
     */
    private Integer observableId;

    /**
     * Identifier for this transition target. Other parts of the SCXML
     * document may refer to this &lt;state&gt; using this ID.
     */
    private String id;

    /**
     * The parent of this transition target (may be null, if the parent
     * is the SCXML document root).
     */
    private EnterableState parent;

    private EnterableState[] ancestors = ZERO_ANCESTORS;

    /**
     * Constructor.
     */
    public TransitionTarget() {
        super();
        parent = null;
    }

    /**
     * {@inheritDoc}
     */
    public final Integer getObservableId() {
        return observableId;
    }

    /**
     * Sets the observableId for this Observable, which must be unique within the SCXML state machine
     *
     * @param observableId the observableId
     */
    public final void setObservableId(Integer observableId) {
        this.observableId = observableId;
    }

    /**
     * Get the identifier for this transition target (may be null).
     *
     * @return Returns the id.
     */
    public final String getId() {
        return id;
    }

    /**
     * Set the identifier for this transition target.
     *
     * @param id The id to set.
     */
    public final void setId(final String id) {
        this.id = id;
    }

    /**
     * @return the number of TransitionTarget ancestors
     */
    public int getNumberOfAncestors() {
        return ancestors.length;
    }

    /**
     * Get the ancestor of this TransitionTarget at specified level
     *
     * @param level the level of the ancestor to return, zero being top
     * @return the ancestor at specified level
     */
    public EnterableState getAncestor(int level) {
        return ancestors[level];
    }

    /**
     * Get the parent TransitionTarget.
     *
     * @return Returns the parent state
     * (null if parent is &lt;scxml&gt; element)
     */
    public EnterableState getParent() {
        return parent;
    }

    /**
     * Set the parent EnterableState.
     * <p>
     * The parent of a TransitionTarget must be of type EnterableState as a History (as only non-EnterableState)
     * TransitionTarget cannot have children.
     * </p>
     *
     * @param parent The parent state to set
     */
    protected void setParent(final EnterableState parent) {
        if (parent == null) {
            throw new IllegalArgumentException("Parent parameter cannot be null");
        }
        if (parent == this) {
            throw new IllegalArgumentException("Cannot set self as parent");
        }
        if (this.parent != parent) {
            this.parent = parent;
            updateDescendantsAncestors();
        }
    }

    /**
     * Update TransitionTarget descendants their ancestors
     */
    protected void updateDescendantsAncestors() {
        TransitionTarget ttParent = parent;
        ancestors = new EnterableState[ttParent.ancestors.length + 1];
        System.arraycopy(ttParent.ancestors, 0, ancestors, 0, ttParent.ancestors.length);
        ancestors[ttParent.ancestors.length] = parent;
    }

    /**
     * Checks whether this transition target (State or Parallel) is a
     * descendant of the transition target context.
     *
     * @param context TransitionTarget context - a potential ancestor
     * @return true if this is a descendant of context, false otherwise
     */
    public final boolean isDescendantOf(TransitionTarget context) {
        return getNumberOfAncestors() > context.getNumberOfAncestors()
                && getAncestor(context.getNumberOfAncestors()) == context;
    }

    /**
     * Enforce identity equality only
     *
     * @param other other object to compare with
     * @return this == other
     */
    @Override
    public final boolean equals(final Object other) {
        return this == other;
    }

    /**
     * Enforce returning identity based hascode
     *
     * @return {@link System#identityHashCode(Object) System.identityHashCode(this)}
     */
    @Override
    public final int hashCode() {
        return System.identityHashCode(this);
    }
}

