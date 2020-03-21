package cn.edu.sysu.workflow.resource.core.plugin;

/**
 * Author: Rinkako
 * Date  : 2018/2/3
 * Usage : Basic class for running asynchronously plugins.
 */
public abstract class AsyncRunnablePlugin extends TrackablePlugin implements Runnable {

    /**
     * Run plugin asynchronously.
     *
     * @see Thread#run()
     */
    @Override
    public abstract void run();
}
