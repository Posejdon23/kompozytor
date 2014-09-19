package com.kamilu.kompozytor;

import com.kamilu.kompozytor.views.EditView;
import com.kamilu.kompozytor.views.MainView;
import com.kamilu.kompozytor.views.ShowView;
import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
@Theme("kompozytor")
public class KompozytorUI extends UI {
	private VerticalLayout mainLayout;

	@Override
	protected void init(VaadinRequest request) {
		mainLayout = new VerticalLayout();
		Navigator mainNavigator = new Navigator(this, this);
		EditView editView = new EditView();
		MainView mainView = new MainView();
		ShowView showView = new ShowView();
		mainNavigator.addView(EditView.VIEW, editView);
		mainNavigator.addView(MainView.VIEW, mainView);
		mainNavigator.addView(ShowView.VIEW, showView);
		mainNavigator.navigateTo(MainView.VIEW);
		setContent(mainLayout);
	}
}