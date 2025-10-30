package Item;

public enum RoomType {
	BEDROOMS, BEDROOML, BEDROOMR, OFFICE, OUT;

	public String toString() {
		switch(this) {
			case BEDROOMS: return "bedroom map";
			case BEDROOML: return "bedroom left";
			case BEDROOMR: return "bedroom right";
			case OFFICE: return "office";
			case OUT: return "Out of the house";
		}
		return "n/a";
	}
}
