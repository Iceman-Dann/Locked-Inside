package Item;
public enum ItemType {
	WEAPON, KEY, HEALING, GUN;

	public String toString() {
		switch(this) {
			case WEAPON: return "weapon";
			case KEY: return "key";
			case HEALING: return "healing";
			case GUN: return "gun";
		}
		return "n/a";
	}
}
