package Item;
public enum MapType {
	LIVINGR, BEDR, OFFICE;

	public String toString() {
		switch(this) {
			case LIVINGR: return "living room";
			case BEDR: return "bed room";
			case OFFICE: return "office";
		}
		return "n/a";
	}
}

