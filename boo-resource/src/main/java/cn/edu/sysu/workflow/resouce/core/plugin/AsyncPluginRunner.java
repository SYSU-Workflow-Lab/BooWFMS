package cn.edu.sysu.workflow.resouce.core.plugin;

/**
 * Author: Rinkako
 * Date  : 2018/2/8
 * Usage : A simple runner for plugins which can run asynchronously.
 */
public class AsyncPluginRunner {

    /**
     * Run a plugin asynchronously.
     *
     * @param plugin plugin to launch
     */
    public static void AsyncRun(AsyncRunnablePlugin plugin) {
        new Thread(plugin).start();
    }
}
