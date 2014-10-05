package kopie;

import static com.vaadin.server.Sizeable.Unit.*;

import com.kamilu.kompozytor.drawers.*;
import com.kamilu.kompozytor.entities.Song;
import com.kamilu.kompozytor.entities.Voice;
import com.kamilu.kompozytor.mycomponent.DrawingCanvas;
import com.kamilu.kompozytor.views.MainView;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TabSheet;

@SuppressWarnings("serial")
public class CompositorEditor extends HorizontalLayout {
	public static final float WIDTH = 850.0f;
	public static final float HEIGHT = 200.0f;
	private final DrawingCanvas canvas;
	private final SongMenu songMenu;
	private final VoiceMenu voiceMenu;
	private final TactMenu tactMenu;
	private final NoteMenu noteMenu;
	private final TabSheet tabs;
	private final SongDrawer songDrawer;
	private final VoiceDrawer voiceDrawer;
	private final TactDrawer tactDrawer;
	private final Song song;

	public CompositorEditor(Song song) {
		this.song = song;
		setImmediate(true);
		canvas = new DrawingCanvas();
		prepareCanvas();
		songDrawer = new SongDrawer(canvas);
		voiceDrawer = new VoiceDrawer(canvas);
		tactDrawer = new TactDrawer(canvas);
		songMenu = new SongMenu(song);
		voiceMenu = new VoiceMenu(song);
		voiceMenu.loadVoicesToTable();
		tactMenu = new TactMenu(song, this);
		noteMenu = new NoteMenu(song, this);
		tabs = new TabSheet();
		Button save = new Button("Zapisz", new Save());
		Button quit = new Button("Wróæ do listy", new Quit());
		tabs.addTab(new HorizontalLayout(save, quit), "Plik");
		tabs.addTab(songMenu, "Utwór");
		tabs.addTab(voiceMenu, "G³osy");
		tabs.addTab(tactMenu, "Takty");
		tabs.addTab(noteMenu, "Nuty");
		addComponents(tabs, canvas);
		setHeight(100, PERCENTAGE);
	}

	public TactDrawer getTactDrawer() {
		return tactDrawer;
	}

	private void prepareCanvas() {
		canvas.setWidth(WIDTH, PIXELS);
		canvas.setHeight(HEIGHT, PIXELS);
		canvas.setImmediate(true);
		// canvas.addListener(new CanvasClick());
	}

	// private final class CanvasClick implements CanvasClickListener,
	// Serializable {
	// @Override
	// public void clicked(MouseEventDetails event) {
	// drawSong();
	// setCurrentCursorPosition(event.getRelativeX(), event.getRelativeY());
	// Notification.show("Tact: " + tactDrawer.getSelectedTactIndex()
	// + ", Note: " + tactDrawer.getSelectedNoteIndex(),
	// TRAY_NOTIFICATION);
	// }
	// }

	public void drawSong() {
		cursor.reset();
		songDrawer.drawSong(song);
		Voice voice = song.getVoice(0);
		voiceDrawer.drawVoice(voice);
		tactDrawer.drawTacts(voice.getTacts());
		markAsDirtyRecursive();
	}

	public void setCurrentCursorPosition(int mouseX, int mouseY) {
		TactField tactField = tactDrawer.getSelectedTactField(mouseX, mouseY);
		NoteField noteField = tactDrawer.getSelectedNoteField(mouseX, mouseY);
		if (tactField != null) {
			cursor.drawTactSelectBox(tactField, mouseX, mouseY);
		} else {
			cursor.setCurrTactField(null);
		}
		if (noteField != null) {
			cursor.drawNoteSelectBox(noteField, mouseX, mouseY);
		} else {
			cursor.setCurrNoteField(null);
		}
	}

	private final class Save implements Button.ClickListener {
		@Override
		public void buttonClick(ClickEvent event) {
			song.persist();
		}
	}

	private final class Quit implements Button.ClickListener {
		@Override
		public void buttonClick(ClickEvent event) {
			getUI().getNavigator().navigateTo(MainView.VIEW);
		}
	}
}
