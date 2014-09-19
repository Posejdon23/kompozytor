package test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.kamilu.kompozytor.drawers.DrawCursor;
import com.kamilu.kompozytor.drawers.NoteDrawer;
import com.kamilu.kompozytor.entities.Note;
import com.kamilu.kompozytor.entities.NoteGroup;
import com.kamilu.kompozytor.mycomponent.DrawingCanvas;
import com.kamilu.kompozytor.propenums.Metrum;
import com.kamilu.kompozytor.propenums.NoteValue;

@RunWith(MockitoJUnitRunner.class)
public class NoteDrawerTest {

	@Mock
	private Note halfNote;
	@Mock
	private Note quarterNote;
	@Mock
	private Note eigthNote;
	@Mock
	private Note eigthNoteDotted;
	@Mock
	private Note sixteenthNote;
	@Mock
	private DrawingCanvas canvas;
	@Mock
	private DrawCursor drawCursor;

	@Before
	public void setUp() throws Exception {
		when(halfNote.getValue()).thenReturn(NoteValue.HalfNote);
		when(quarterNote.getValue()).thenReturn(NoteValue.QuarterNote);
		when(eigthNote.getValue()).thenReturn(NoteValue.EighthNote);
		when(eigthNoteDotted.getValue()).thenReturn(NoteValue.EighthNote);
		when(sixteenthNote.getValue()).thenReturn(NoteValue.SixteenthNote);
		when(quarterNote.isDot()).thenReturn(false);
		when(eigthNote.isDot()).thenReturn(false);
		when(halfNote.isDot()).thenReturn(false);
		when(eigthNoteDotted.isDot()).thenReturn(true);
		when(sixteenthNote.isDot()).thenReturn(false);
	}

	@Test
	public void testGroupOfNotes() throws Exception {
		// when
		Metrum metrum = Metrum.Common;
		NoteDrawer noteDrawer = new NoteDrawer(canvas, drawCursor);
		List<Note> notes = new ArrayList<Note>();
		notes.add(halfNote);
		notes.add(quarterNote);
		notes.add(quarterNote);
		List<NoteGroup> notesGroup = noteDrawer.divideToGroupOfNotes(notes,
				metrum);
		// then
		assertEquals(3, notesGroup.size());
		for (NoteGroup group : notesGroup) {
			assertEquals(true, group.isFull());
		}

	}
}
