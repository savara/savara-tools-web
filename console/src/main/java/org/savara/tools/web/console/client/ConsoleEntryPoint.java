package org.savara.tools.web.console.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.gwtplatform.mvp.client.DelayedBindRegistry;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class ConsoleEntryPoint implements EntryPoint {

    public final ConsoleUI consoleUI = GWT.create(ConsoleUI.class);

    public void onModuleLoad() {
        DelayedBindRegistry.bind(consoleUI);
        consoleUI.getPlaceManager().revealCurrentPlace();
    }
}
