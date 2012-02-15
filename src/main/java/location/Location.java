package location;


public class Location {
	public final String name;
	
	public Location(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Location that = (Location) obj;
		
		return this.name.equals(that.name);
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName() + "[" + name + "]";
	}
}
