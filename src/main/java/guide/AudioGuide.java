package guide;

import java.util.HashSet;
import java.util.Set;

import location.Location;
import location.LocationAware;
import media.MediaPlayerControl;
import media.MediaPlayerListener;
import media.Track;

public class AudioGuide implements LocationAware, MediaPlayerListener {

	private final MediaLibrary mediaLibrary;
	private final MediaPlayerControl mediaPlayer;
	private boolean playing;
	private Location currentLocation;
	private Location previousLocation;
	private final Set<Track> playedTracks = new HashSet<Track>();

	public AudioGuide(final MediaLibrary mediaLibrary, final MediaPlayerControl mediaPlayer) {
		this.mediaLibrary = mediaLibrary;
		this.mediaPlayer = mediaPlayer;
	}

	public void locationChanged(final Location newLocation) {
		if (previousLocation == null) {
			previousLocation = newLocation;
		} else {
			previousLocation = currentLocation;
		}
		currentLocation = newLocation;
		if (!playing) {
			playTrackForCurrentLocation();
		}

	}

	public void trackFinished() {
		playing = false;
		if (isLocationChanged()) {
			playTrackForCurrentLocation();
		}
	}

	private boolean isLocationChanged() {
		return !currentLocation.equals(previousLocation);
	}

	private void playTrackForCurrentLocation() {
		final Track track = mediaLibrary.getTrackForLocation(currentLocation);
		if (shouldPlay(track)) {
			mediaPlayer.play(track);
			playing = true;
			playedTracks.add(track);
		}
	}

	private boolean shouldPlay(final Track track) {
		return track != null && !playedTracks.contains(track);
	}

}
