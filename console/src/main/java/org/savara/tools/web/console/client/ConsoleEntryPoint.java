package org.savara.tools.web.console.client;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.gwtplatform.mvp.client.DelayedBindRegistry;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class ConsoleEntryPoint implements EntryPoint {

    public static final ConsoleUI MODULE = GWT.create(ConsoleUI.class);

    public void onModuleLoad() {

        Log.setUncaughtExceptionHandler();

        Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand(){

            public void execute() {
                actualModuleLoad();
            }
        });
    }


    public void actualModuleLoad() {
        DelayedBindRegistry.bind(MODULE);
        MODULE.getPlaceManager().revealCurrentPlace();

    }
}
