package login;

import java.util.Arrays;

public class LoginEnums {

    public enum Gender {
        MALE("male"), FEMALE("female");
        
        private String value;
        
        private Gender(String value){
        	this.setValue(value);
        }

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}
		
		public static Gender fromValue(String value) {
			for (Gender category : values()) {
				if (category.value.equalsIgnoreCase(value)) {
					return category;
				}
			}
			throw new IllegalArgumentException(
					"Unknown enum type " + value + ", Allowed values are " + Arrays.toString(values()));
		}
    }
    
    public enum Instrument{
    	GUITAR("guitar"),DRUM("drum"),BASS("bass"),KEYBOARD("keyboard"),VOCAL("vocal");
    	
        private String value;
        
        private Instrument(String value){
        	this.setValue(value);
        }

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}
		
		public static Instrument fromValue(String value) {
			for (Instrument category : values()) {
				if (category.value.equalsIgnoreCase(value)) {
					return category;
				}
			}
			throw new IllegalArgumentException(
					"Unknown enum type " + value + ", Allowed values are " + Arrays.toString(values()));
		}
    	
    }
    
    public enum MusicStyle{
    	ROCK("rock"),POP("pop"),METAL("metal"),HIPHOP("hiphop"),
    	JAZZ("jazz"),RnB("RnB"),BLUES("blues"),COUNTRY("country"),FUNK("funk"),PUNK("punk"),
    	CLASSICAL("classical"),SOUL("soul");
    	
        private String value;
        
        private MusicStyle(String value){
        	this.setValue(value);
        }

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}
		
		
		public static MusicStyle fromValue(String value) {
			for (MusicStyle category : values()) {
				if (category.value.equalsIgnoreCase(value)) {
					return category;
				}
			}
			throw new IllegalArgumentException(
					"Unknown enum type " + value + ", Allowed values are " + Arrays.toString(values()));
		}
    	
    }
}
