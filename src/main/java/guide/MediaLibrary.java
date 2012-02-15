package guide;

import location.Location;
import media.Track;


public interface MediaLibrary {

	Track getTrackForLocation(Location firstLocation);
	// Add methods to this interface as you discover the AudioGuide needs them
}
