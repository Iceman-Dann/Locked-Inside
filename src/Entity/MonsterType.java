package Entity;
public enum MonsterType {
	TALL, CRY, SUS;
	
	public String toString() {
		switch(this) {
			case TALL: return "tall";
			case CRY: return "cry";
			case SUS: return "sus";
		}
		return "n/a";
	}
}