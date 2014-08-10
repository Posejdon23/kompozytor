package com.kamilu.kompozytor.components;

import static com.kamilu.kompozytor.KompozytorUI.*;
import static com.vaadin.ui.Alignment.*;

import com.kamilu.kompozytor.KompozytorUI;
import com.kamilu.kompozytor.drawers.SongDrawer;
import com.kamilu.kompozytor.entities.Song;
import com.kamilu.kompozytor.utils.SongFactory;
import com.kamilu.kompozytor.views.MainView;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.TabSheet.SelectedTabChangeEvent;
import com.vaadin.ui.TabSheet.SelectedTabChangeListener;
import com.vaadin.ui.TabSheet.Tab;

@SuppressWarnings("serial")
public class Kompozytor extends VerticalLayout {

	private final SongMenu songMenu;
	private final VoiceMenu voiceMenu;
	private final TactMenu tactMenu;
	private final NoteMenu noteMenu;
	private final TabSheet tabs;
	private SongDrawer songDrawer;
	private Song utwor;
	private final HorizontalLayout fileLayout;
	private Tab fileTab;
	private Tab songTab;
	private Tab voiceTab;
	private Tab tactTab;
	private Tab noteTab;

	public Kompozytor(Song song) {

		Button back = new Button("Zapisz i WyjdŸ", new SaveAndQuit());
		makeNewIfNull(song);
		songDrawer = new SongDrawer();
		songMenu = new SongMenu(song, songDrawer);
		voiceMenu = new VoiceMenu(song, songDrawer);
		tactMenu = new TactMenu(song, songDrawer);
		noteMenu = new NoteMenu(song, songDrawer);
		tabs = new TabSheet();
		fileLayout = new HorizontalLayout(back);
		fileTab = tabs.addTab(fileLayout, "Plik");
		songTab = tabs.addTab(songMenu, "Utwór");
		voiceTab = tabs.addTab(voiceMenu, "G³osy");
		tactTab = tabs.addTab(tactMenu, "Takty");
		noteTab = tabs.addTab(noteMenu, "Nuty");
		fileTab.setStyleName(MENU_CLEAR);
		tabs.addSelectedTabChangeListener(new ChangeTab());
		reloadCanvas(true);
	}

	private void makeNewIfNull(Song utwor) {
		if (utwor == null) {
			this.utwor = SongFactory.createNewUtwor("Nowy Utwór", "Gal Anonim");
		} else {
			this.utwor = utwor;
		}
	}

	public void reloadCanvas(boolean withCanvas) {
		removeAllComponents();
		voiceMenu.loadVoicesToTable();
		addComponent(tabs);
		setComponentAlignment(tabs, TOP_CENTER);
		if (withCanvas) {
			addComponent(songDrawer);
			setComponentAlignment(songDrawer, TOP_CENTER);
			songDrawer.drawSong(utwor);
		}
	}

	private final class ChangeTab implements SelectedTabChangeListener {
		@Override
		public void selectedTabChange(SelectedTabChangeEvent event) {
			if (event.getTabSheet().getSelectedTab().equals(voiceMenu)) {

				reloadCanvas(false);
			} else {
				reloadCanvas(true);
			}
			fileTab.setStyleName(MENU_CLEAR);
			songTab.setStyleName(MENU_CLEAR);
			voiceTab.setStyleName(MENU_CLEAR);
			tactTab.setStyleName(MENU_CLEAR);
			noteTab.setStyleName(MENU_CLEAR);
			fileTab.setStyleName(MENU_CLEAR);
			tabs.getSelectedTab().setStyleName(MENU_SELECTED);

		}
	}

	private final class SaveAndQuit implements Button.ClickListener {
		@Override
		public void buttonClick(ClickEvent event) {
			getUI().getNavigator().navigateTo(MainView.VIEW);
		}
	}
}
