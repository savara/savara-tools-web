package org.savara.tools.web.console.client.icons;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

/**
 * @author: Jeff Yu
 * @date: 13/05/11
 */
public interface ConsoleIconBundle extends ClientBundle{

    public static final ConsoleIconBundle INSTANCE = GWT.create(ConsoleIconBundle.class);

    @Source("logout.png")
    ImageResource logoutIcon();

    @Source("event_detail.png")
    ImageResource eventDetailIcon();

    @Source("transaction_view.png")
    ImageResource transactionViewIcon();

    @Source("savara_icon.png")
    ImageResource savaraIcon();

}
