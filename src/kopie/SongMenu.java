package kopie;

import com.kamilu.kompozytor.entities.Song;
import com.vaadin.event.FieldEvents.BlurEvent;
import com.vaadin.event.FieldEvents.BlurListener;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.HasComponents.ComponentAttachEvent;

public class SongMenu extends VerticalLayout {

	private static final long serialVersionUID = 1L;
	private TextField songName, songAuthor;

	public SongMenu(final Song song) {
		songName = new TextField("Nazwa utworu");
		songName.setValue(song.getName());
		songAuthor = new TextField("Autor utworu");
		songAuthor.setValue(song.getAuthor());
		songName.addBlurListener(new ChangeName(song));
		songAuthor.addBlurListener(new ChangeAuthor(song));
		addComponents(songName, songAuthor);
	}
	

	private final class ChangeName implements BlurListener {

		private static final long serialVersionUID = 1L;
		private Song song;

		public ChangeName(Song song) {
			this.song = song;
		}

		@Override
		public void blur(BlurEvent event) {
			song.setName(songName.getValue());
		}
	}

	private final class ChangeAuthor implements BlurListener {

		private static final long serialVersionUID = 1L;
		private Song song;

		public ChangeAuthor(Song song) {
			this.song = song;
		}

		@Override
		public void blur(BlurEvent event) {
			song.setAuthor(songAuthor.getValue());
		}
	}
}
