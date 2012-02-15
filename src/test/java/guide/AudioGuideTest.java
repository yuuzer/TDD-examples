package guide;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import location.Location;
import media.MediaPlayerControl;
import media.Track;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class AudioGuideTest {

	private AudioGuide audioGuide;
	@Mock
	private MediaLibrary mediaLibrary;
	@Mock
	private MediaPlayerControl mediaPlayer;
	@Mock
	private Track firstTrack;
	private final Location firstLocation = new Location("first");
	private final Location secondLocation = new Location("second");
	@Mock
	private Track secondTrack;

	@BeforeMethod
	public void setUpAudioGuideTest() {
		MockitoAnnotations.initMocks(this);

		audioGuide = new AudioGuide(mediaLibrary, mediaPlayer);
	}

	@Test(groups = "unit")
	public void playsTrackAtTheFirstLocation() throws Exception {
		when(mediaLibrary.getTrackForLocation(firstLocation)).thenReturn(firstTrack);

		audioGuide.locationChanged(firstLocation);

		verify(mediaPlayer).play(firstTrack);
	}

	@Test(groups = "unit")
	public void playsNextTrackIfPreviousTrackHasFinishedAtNewLocation() throws Exception {
		when(mediaLibrary.getTrackForLocation(firstLocation)).thenReturn(firstTrack);
		audioGuide.locationChanged(firstLocation);
		verify(mediaPlayer).play(firstTrack);

		when(mediaLibrary.getTrackForLocation(secondLocation)).thenReturn(secondTrack);
		audioGuide.locationChanged(secondLocation);

		audioGuide.trackFinished();
		verify(mediaPlayer).play(secondTrack);

	}

	@Test(groups = "unit")
	public void doesntPlayTrackForNewLocationIfPreviousTrackHasntFinished() throws Exception {
		when(mediaLibrary.getTrackForLocation(firstLocation)).thenReturn(firstTrack);
		audioGuide.locationChanged(firstLocation);
		verify(mediaPlayer).play(firstTrack);

		when(mediaLibrary.getTrackForLocation(secondLocation)).thenReturn(secondTrack);
		audioGuide.locationChanged(secondLocation);

		verify(mediaPlayer, never()).play(secondTrack);

	}

	@Test(groups = "unit")
	public void playsNextTrackAtNewLocationImmedietlyIfNotPlayingYet() throws Exception {
		when(mediaLibrary.getTrackForLocation(firstLocation)).thenReturn(firstTrack);
		audioGuide.locationChanged(firstLocation);
		verify(mediaPlayer).play(firstTrack);

		audioGuide.trackFinished();

		when(mediaLibrary.getTrackForLocation(secondLocation)).thenReturn(secondTrack);
		audioGuide.locationChanged(secondLocation);

		verify(mediaPlayer).play(secondTrack);
	}

	@Test(groups = "unit")
	public void dosntPlayAnythingIfNoTrackAssociatedForTheLocation() throws Exception {
		final Location locationWithoutTrack = mock(Location.class);
		when(mediaLibrary.getTrackForLocation(locationWithoutTrack)).thenReturn(null);
		audioGuide.locationChanged(locationWithoutTrack);

		final Location secondLocationWithoutTrack = mock(Location.class);
		when(mediaLibrary.getTrackForLocation(secondLocationWithoutTrack)).thenReturn(null);
		audioGuide.locationChanged(secondLocationWithoutTrack);

		audioGuide.trackFinished();

		verify(mediaPlayer, never()).play(any(Track.class));
	}

	@Test(groups = "unit")
	public void playsEachTrackOnlyOnce() throws Exception {
		when(mediaLibrary.getTrackForLocation(firstLocation)).thenReturn(firstTrack);
		audioGuide.locationChanged(firstLocation);

		audioGuide.trackFinished();

		audioGuide.locationChanged(firstLocation);

		verify(mediaPlayer).play(firstTrack);
	}

}
