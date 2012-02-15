package location;
import gps.Position;


public interface ReverseGeocoder {
	Location locationOf(Position position);
}
