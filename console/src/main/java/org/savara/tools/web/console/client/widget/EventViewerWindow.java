/*
 * JBoss, Home of Professional Open Source
 * Copyright 2008-11, Red Hat Middleware LLC, and others contributors as indicated
 * by the @authors tag. All rights reserved.
 * See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 * This copyrighted material is made available to anyone wishing to use,
 * modify, copy, or redistribute it subject to the terms and conditions
 * of the GNU Lesser General Public License, v. 2.1.
 * This program is distributed in the hope that it will be useful, but WITHOUT A
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public License,
 * v.2.1 along with this distribution; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA  02110-1301, USA.
 */
package org.savara.tools.web.console.client.widget;

import com.smartgwt.client.types.VisibilityMode;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.SearchForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.SectionStack;
import com.smartgwt.client.widgets.layout.SectionStackSection;

import java.util.Date;

/**
 * @author: Jeff Yu
 * @date: 26/05/11
 */
public class EventViewerWindow extends DesktopWindow {

    private ListGrid grid;

    private SectionStack stack;

    public EventViewerWindow() {
        super();

        setTitle(AppMessages.EVENT_VIEWER_LABEL);
        setShortcutIcon(AppImages.EVENT_DETAIL_SHORTCUT);
        setHeaderIcon(AppImages.EVENT_DETAIL_ICON);

        stack = new SectionStack();
        stack.setVisibilityMode(VisibilityMode.MULTIPLE);

        SectionStackSection findSection = new SectionStackSection("Find");
        findSection.setExpanded(true);

        final DynamicForm form = new SearchForm();
        form.setNumCols(7);
        form.setHeight(35);
        form.setTop(15);
        form.setCellPadding(10);

        TextItem actIdItem = new TextItem();
        actIdItem.setTitle("Activity ID");
        TextItem contextItem = new TextItem();
        contextItem.setTitle("Context");
        ButtonItem findBtn = new ButtonItem("Search");
        findBtn.setStartRow(false);
        findBtn.setIcon(AppImages.SEARCH_ICON);
        findBtn.setWidth("125px");

        form.setFields(actIdItem, contextItem, findBtn);
        findSection.addItem(form);

        SectionStackSection resultSection = new SectionStackSection("Activities");
        resultSection.setExpanded(true);


        grid = new ListGrid();
        grid.setShowAllRecords(true);
        grid.setHeight(300);

        ListGridField actId = new ListGridField("activityId", "ID" , 40);
        ListGridField actDate = new ListGridField("activityDate", "Date", 150);
        ListGridField actCtx = new ListGridField("activityContext", "Context", 220);
        ListGridField actDesc = new ListGridField("activityDescription", "Description");

        grid.setFields(actId, actCtx, actDesc, actDate);
        grid.setData(getData());

        resultSection.addItem(grid);

        SectionStackSection detailSection = new SectionStackSection("Activity Detail");
        detailSection.setExpanded(true);

        stack.addSection(findSection);
        stack.addSection(resultSection);
        stack.addSection(detailSection);

        this.addItem(stack);
    }

    private ActivityRecord[] getData() {
        ActivityRecord[] data = new ActivityRecord[] {
            new ActivityRecord("1", new Date(), "context1", "description111"),
            new ActivityRecord("2", new Date(), "context2", "description222"),
            new ActivityRecord("3", new Date(), "context3", "description333")
        };
        return data;
    }

    private static class ActivityRecord extends ListGridRecord {

        public ActivityRecord (String id, Date timestamp, String context, String descrption) {
            setContext(context);
            setActivityId(id);
            setActivityDate(timestamp);
        }

        public void setActivityId(String id) {
            setAttribute("activityId", id);
        }

        public String getActivityId() {
            return getAttribute("activityId");
        }

        public void setActivityDate(Date date) {
            setAttribute("activityDate", date);
        }

        public Date getActivityDate() {
            return getAttributeAsDate("activityDate");
        }

        public void setContext(String context) {
            setAttribute("activityContext", context);
        }

        public String getContext() {
            return getAttribute("activityContext");
        }

        public String getDescription() {
            return getAttribute("activityDescription");
        }

        public void setDescription(String description) {
            setAttribute("activityDescription", description);
        }
    }
}
