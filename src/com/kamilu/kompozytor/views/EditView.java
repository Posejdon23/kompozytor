package com.kamilu.kompozytor.views;

import static com.vaadin.ui.Alignment.*;

import com.google.appengine.api.datastore.Entity;
import com.kamilu.kompozytor.DataStoreWrapper;
import com.kamilu.kompozytor.components.CompositorEditor;
import com.kamilu.kompozytor.entities.Song;
import com.kamilu.kompozytor.utils.SongFactory;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

@SuppressWarnings("serial")
public class EditView extends VerticalLayout implements View {

	public static final String VIEW = "edit";
	private TextField name;
	private TextField author;
	private Button create;
	private Button cancel;
	private HorizontalLayout butLay;
	private CompositorEditor editor;

	public EditView() {
		name = new TextField("Nazwa");
		author = new TextField("Autor");
		create = new Button("Stwórz");
		cancel = new Button("Anuluj");
		butLay = new HorizontalLayout(create, cancel);
		prepareListeners();
	}

	@Override
	public void enter(ViewChangeEvent event) {
		removeAllComponents();
		if (!event.getParameters().isEmpty()) {
			String parameters = event.getParameters();
			Entity songEntity = DataStoreWrapper.getById(Song.KIND,
					Long.valueOf(parameters));
			Song song = new Song(songEntity);
			song.setName(name.getValue());
			song.setAuthor(author.getValue());
			editor = new CompositorEditor(song);
			addComponent(editor);
			setComponentAlignment(editor, Alignment.MIDDLE_CENTER);
			editor.drawSong();
		} else {
			addComponents(name, author, butLay);
			setComponentAlignment(name, MIDDLE_CENTER);
			setComponentAlignment(author, MIDDLE_CENTER);
			setComponentAlignment(butLay, MIDDLE_CENTER);
		}
	}

	private void prepareListeners() {
		create.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				removeAllComponents();
				Song song = SongFactory.createNewSong(name.getValue(),
						author.getValue());
				getUI().getNavigator().navigateTo(
						VIEW + "/" + song.getKey().getId());
			}
		});
		cancel.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				getUI().getNavigator().navigateTo(MainView.VIEW);
			}
		});
	}

}
