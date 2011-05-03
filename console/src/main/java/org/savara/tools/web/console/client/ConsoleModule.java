package org.savara.tools.web.console.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.TextItem;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class ConsoleModule implements EntryPoint {

    public void onModuleLoad() {
        final DynamicForm form = new DynamicForm();
        final TextItem textItem = new TextItem();
        textItem.setTitle("Name");
        form.setFields(textItem);

        final IButton button = new IButton("Hello");
        button.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent clickEvent) {
                String name = textItem.getValue().toString();
                SC.say("Hello " + name);
            }
        });

        RootPanel.get("formContainer").add(form);
        RootPanel.get("buttonContainer").add(button);

    }
}
