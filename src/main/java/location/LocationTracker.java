package location;

import gps.Position;
import gps.PositionListener;

public class LocationTracker implements PositionListener {
	private final ReverseGeocoder reverseGeocoder;
	private final LocationAware listener;

	private Location lastLocation = null;

	public LocationTracker(final ReverseGeocoder reverseGeocoder, final LocationAware listener) {
		this.reverseGeocoder = reverseGeocoder;
		this.listener = listener;
	}

	public void positionChanged(final Position newPosition) {
		final Location location = reverseGeocoder.locationOf(newPosition);
		if (!location.equals(lastLocation)) {
			listener.locationChanged(location);
			lastLocation = location;
		}
	}
}
