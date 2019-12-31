package cn.edu.sysu.workflow.engine.core.model;

import java.util.ArrayList;
import java.util.List;

/**
 * An abstract base class for state elements in SCXML that can be transitioned out from, such as State or Parallel.
 */
public abstract class TransitionalState extends EnterableState {

    /**
     * A list of outgoing Transitions from this state, by document order.
     * 一系列的转移出口，文档顺序排列
     */
    private List<Transition> transitions;

    /**
     * Optional property holding the data model for this state.
     * 状态元素的数据模型
     */
    private Datamodel datamodel;

    /**
     * List of history states owned by a given state (applies to non-leaf
     * states).
     * 一系列的历史状态
     */
    private List<History> history;

    /**
     * The Invoke children, each which defines an external process that should
     * be invoked, immediately after the onentry executable content,
     * and the transitions become candidates after the invoked
     * process has completed its execution.
     * May occur 0 or more times.
     */
    private List<Invoke> invokes;

    /**
     * The set of EnterableState children contained in this TransitionalState
     * 一系列的孩子状态
     */
    private List<EnterableState> children;

    public TransitionalState() {
        super();
        transitions = new ArrayList<Transition>();
        history = new ArrayList<History>();
        children = new ArrayList<EnterableState>();
        invokes = new ArrayList<Invoke>();
    }

    /**
     * Update TransitionTarget descendants their ancestors
     */
    protected void updateDescendantsAncestors() {
        super.updateDescendantsAncestors();
        for (History h : history) {
            // reset ancestors
            h.updateDescendantsAncestors();
        }
        for (TransitionTarget child : children) {
            child.updateDescendantsAncestors();
        }
    }

    /**
     * Get the TransitionalState (State or Parallel) parent.
     *
     * @return Returns the parent.
     */
    @Override
    public TransitionalState getParent() {
        return (TransitionalState) super.getParent();
    }

    /**
     * Set the TransitionalState parent
     *
     * @param parent The parent to set.
     */
    public final void setParent(final TransitionalState parent) {
        super.setParent(parent);
    }

    /**
     * Get the ancestor of this TransitionalState at specified level
     *
     * @param level the level of the ancestor to return, zero being top
     * @return the ancestor at specified level
     */
    @Override
    public TransitionalState getAncestor(int level) {
        return (TransitionalState) super.getAncestor(level);
    }

    /**
     * Get the list of all outgoing transitions from this state, that
     * will be candidates for being fired on the given event.
     *
     * @param event The event
     * @return List Returns the candidate transitions for given event
     */
    public final List<Transition> getTransitionsList(final String event) {
        List<Transition> matchingTransitions = null; // since we returned null upto v0.6
        for (Transition t : transitions) {
            if ((event == null && t.getEvent() == null)
                    || (event != null && event.equals(t.getEvent()))) {
                if (matchingTransitions == null) {
                    matchingTransitions = new ArrayList<Transition>();
                }
                matchingTransitions.add(t);
            }
        }
        return matchingTransitions;
    }

    /**
     * Add a transition to the map of all outgoing transitions for
     * this state.
     *
     * @param transition The transitions to set.
     */
    public final void addTransition(final Transition transition) {
        transitions.add(transition);
        transition.setParent(this);
    }

    /**
     * Get the outgoing transitions for this state as a java.utility.List.
     *
     * @return List Returns the transitions list.
     */
    public final List<Transition> getTransitionsList() {
        return transitions;
    }

    /**
     * Get the data model for this transition target.
     *
     * @return Returns the data model.
     */
    public final Datamodel getDatamodel() {
        return datamodel;
    }

    /**
     * Set the data model for this transition target.
     *
     * @param datamodel The Datamodel to set.
     */
    public final void setDatamodel(final Datamodel datamodel) {
        this.datamodel = datamodel;
    }

    /**
     * @param h History pseudo state
     * @since 0.7
     */
    public final void addHistory(final History h) {
        history.add(h);
        h.setParent(this);
    }

    /**
     * 当前状态是不是有你是伪状态
     *
     * @return boolean true if a given state contains at least one
     * history pseudo state
     * @since 0.7
     */
    public final boolean hasHistory() {
        return (!history.isEmpty());
    }

    /**
     * Get the list of history pseudo states for this state.
     *
     * @return a list of all history pseudo states contained by a given state
     * (can be empty)
     * @see #hasHistory()
     * @since 0.7
     */
    public final List<History> getHistory() {
        return history;
    }

    /**
     * Get the Invoke children (may be empty).
     * 得到调用的孩子，可能是空的。
     *
     * @return Invoke Returns the invoke.
     */
    public final List<Invoke> getInvokes() {
        return invokes;
    }

    /**
     * Set the Invoke child.
     *
     * @param invoke The invoke to set.
     */
    public final void addInvoke(final Invoke invoke) {
        this.invokes.add(invoke);
        invoke.setParentEnterableState(this);
    }

    /**
     * Get the set of child transition targets (may be empty).
     *
     * @return Returns the children.
     * @since 0.7
     */
    public final List<EnterableState> getChildren() {
        return children;
    }

    /**
     * Add a child.
     *
     * @param es A child enterable state.
     * @since 0.7
     */
    protected void addChild(final EnterableState es) {
        children.add(es);
        es.setParent(this);
    }
}
