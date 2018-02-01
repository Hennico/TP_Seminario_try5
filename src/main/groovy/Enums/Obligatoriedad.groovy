package organizadordemetas

public enum Obligatoriedad {
	NECESARIO ("Necesario"),
	OPCIONAL ("Opcional")

	final String value

	Obligatoriedad(String value) { this.value = value }

	@Override
	String toString() { value }
	String getKey() { name() }

	static Obligatoriedad convertirEnEnum( String value ) {
		values().find { it.value == value }
	}
}
