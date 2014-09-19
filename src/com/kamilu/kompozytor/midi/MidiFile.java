package com.kamilu.kompozytor.midi;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Vector;

import com.google.appengine.api.files.AppEngineFile;
import com.google.appengine.api.files.FileService;
import com.google.appengine.api.files.FileServiceFactory;
import com.google.appengine.api.files.FileWriteChannel;

public class MidiFile {
	public static final int SEMIQUAVER = 4;
	public static final int QUAVER = 8;
	public static final int CROTCHET = 16;
	public static final int MINIM = 32;
	public static final int SEMIBREVE = 64;
	static final int header[] = new int[] { 0x4d, 0x54, 0x68, 0x64, 0x00, 0x00,
			0x00, 0x06, 0x00, 0x00, // single-track format
			0x00, 0x01, // one track
			0x00, 0x10, // 16 ticks per quarter
			0x4d, 0x54, 0x72, 0x6B };
	static final int footer[] = new int[] { 0x01, 0xFF, 0x2F, 0x00 };
	static final int tempoEvent[] = new int[] { 0x00, 0xFF, 0x51, 0x03, 0x0F,
			0x42, 0x40 };
	static final int keySigEvent[] = new int[] { 0x00, 0xFF, 0x59, 0x02, 0x00, // C
			0x00 // major
	};
	static final int timeSigEvent[] = new int[] { 0x00, 0xFF, 0x58, 0x04, 0x04, // numerator
			0x02, // denominator (2==4, because it's a power of 2)
			0x30, // ticks per click (not used)
			0x08 // 32nd notes per crotchet
	};
	protected Vector<int[]> playEvents;

	public MidiFile() {
		playEvents = new Vector<int[]>();
	}

	/**
	 * Convert an array of integers which are assumed to contain unsigned bytes
	 * into an array of bytes
	 */
	protected static byte[] intArrayToByteArray(int[] ints) {
		int l = ints.length;
		byte[] out = new byte[ints.length];
		for (int i = 0; i < l; i++) {
			out[i] = (byte) ints[i];
		}
		return out;
	}

	/** Store a note-on event */
	public void noteOn(int delta, int note, int velocity) {
		int[] data = new int[4];
		data[0] = delta;
		data[1] = 0x90;
		data[2] = note;
		data[3] = velocity;
		playEvents.add(data);
	}

	/** Store a note-off event */
	public void noteOff(int delta, int note) {
		int[] data = new int[4];
		data[0] = delta;
		data[1] = 0x80;
		data[2] = note;
		data[3] = 0;
		playEvents.add(data);
	}

	/** Store a program-change event at current position */
	public void progChange(int prog) {
		int[] data = new int[3];
		data[0] = 0;
		data[1] = 0xC0;
		data[2] = prog;
		playEvents.add(data);
	}

	/**
	 * Store a note-on event followed by a note-off event a note length later.
	 * There is no delta value — the note is assumed to follow the previous one
	 * with no gap.
	 */
	public void noteOnOffNow(int duration, int note, int velocity) {
		noteOn(0, note, velocity);
		noteOff(duration, note);
	}

	public void noteSequenceFixedVelocity(int[] sequence, int velocity) {
		boolean lastWasRest = false;
		int restDelta = 0;
		for (int i = 0; i < sequence.length; i += 2) {
			int note = sequence[i];
			int duration = sequence[i + 1];
			if (note < 0) {
				// This is a rest
				restDelta += duration;
				lastWasRest = true;
			} else {
				// A note, not a rest
				if (lastWasRest) {
					noteOn(restDelta, note, velocity);
					noteOff(duration, note);
				} else {
					noteOn(0, note, velocity);
					noteOff(duration, note);
				}
				restDelta = 0;
				lastWasRest = false;
			}
		}
	}

	/** Write the stored MIDI events to a file */
	@SuppressWarnings("deprecation")
	public AppEngineFile writeToFile() throws IOException {
		FileService fileService = FileServiceFactory.getFileService();
		AppEngineFile file = fileService.createNewBlobFile("text/plain");
		FileWriteChannel writeChannel = fileService
				.openWriteChannel(file, true);
		writeChannel.write(ByteBuffer.wrap(intArrayToByteArray(header)));
		writeChannel.write(ByteBuffer.wrap(intArrayToByteArray(header)));
		int size = tempoEvent.length + keySigEvent.length + timeSigEvent.length
				+ footer.length;
		for (int i = 0; i < playEvents.size(); i++)
			size += playEvents.elementAt(i).length;
		int high = size / 256;
		int low = size - (high * 256);
		writeChannel.write(ByteBuffer.wrap(new byte[] { (byte) 0 }));
		writeChannel.write(ByteBuffer.wrap(new byte[] { (byte) 0 }));
		writeChannel.write(ByteBuffer.wrap(new byte[] { (byte) high }));
		writeChannel.write(ByteBuffer.wrap(new byte[] { (byte) low }));
		writeChannel.write(ByteBuffer.wrap(intArrayToByteArray(tempoEvent)));
		writeChannel.write(ByteBuffer.wrap(intArrayToByteArray(keySigEvent)));
		writeChannel.write(ByteBuffer.wrap(intArrayToByteArray(timeSigEvent)));
		for (int i = 0; i < playEvents.size(); i++) {
			writeChannel.write(ByteBuffer.wrap(intArrayToByteArray(playEvents
					.elementAt(i))));
		}
		writeChannel.write(ByteBuffer.wrap(intArrayToByteArray(footer)));
		writeChannel.closeFinally();
		return file;
	}
}
