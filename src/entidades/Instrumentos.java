package entidades;

public enum Instrumentos {
	PIANO(0), 
	ACOUSTIC_GRAND(0), 
	BRIGHT_ACOUSTIC(1),
	ELECTRIC_GRAND(2), 
	HONKEY_TONK(3),
	ELECTRIC_PIANO(	4), 
	ELECTRIC_PIANO_1(4),
	ELECTRIC_PIANO_2(5),
	HARPISCHORD(6),
	CLAVINET(7),
	CELESTA(8), 
	GLOCKENSPIEL(9),
	MUSIC_BOX(10),
	VIBRAPHONE(11),
	MARIMBA(12),
	XYLOPHONE(13),
	TUBULAR_BELLS(14),
	DULCIMER(15),
	DRAWBAR_ORGAN(16),
	PERCUSSIVE_ORGAN(17),
	ROCK_ORGAN(18), 
	CHURCH_ORGAN(19), 
	REED_ORGAN(20), 
	ACCORIDAN(21),
	HARMONICA(22), 
	TANGO_ACCORDIAN(23),
	GUITAR(24),
	NYLON_STRING_GUITAR(24),
	STEEL_STRING_GUITAR(25),
	ELECTRIC_JAZZ_GUITAR(26),
	ELECTRIC_CLEAN_GUITAR(27),
	ELECTRIC_MUTED_GUITAR(28),
	OVERDRIVEN_GUITAR(29),
	DISTORTION_GUITAR(30),
	GUITAR_HARMONICS(31),
	ACOUSTIC_BASS(32),
	ELECTRIC_BASS_FINGER(33),
	ELECTRIC_BASS_PICK(	34),
	FRETLESS_BASS(35),
	SLAP_BASS_1(36),
	SLAP_BASS_2(37),
	SYNTH_BASS_1(38),
	SYNTH_BASS_2(39),
	VIOLIN(40),
	VIOLA(41),
	CELLO(42),
	CONTRABASS(43),
	TREMOLO_STRINGS(44),
	PIZZICATO_STRINGS(45),
	ORCHESTRAL_STRINGS(46),
	TIMPANI(47),
	STRING_ENSEMBLE_1(48),
	STRING_ENSEMBLE_2(49);
	
	public int getValue() {
		return value;
	}
	private int value;
	Instrumentos(int value)
	{
		this.value = value;
	}
}
