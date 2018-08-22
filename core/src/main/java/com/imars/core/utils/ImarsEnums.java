package com.imars.core.utils;

import java.util.Arrays;

public class ImarsEnums {

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
    
    public enum Health{
    	GOOD(100,80),SICK(50,10),DYING(10,0);
    	
        private int maxThreshold,minThreshold;
        
        private Health(int min,int max){
        	this.setMinThreshold(min);
        	this.setMaxThreshold(max);
        }

		public int getMaxThreshold() {
			return maxThreshold;
		}

		public void setMaxThreshold(int threshold) {
			this.maxThreshold = threshold;
		}
		
		public int getMinThreshold() {
			return minThreshold;
		}

		public void setMinThreshold(int minThreshold) {
			this.minThreshold = minThreshold;
		}

		public static Health fromValue(int value) {
			for (Health health : values()) {
				if(value<=health.getMaxThreshold() && value>health.getMinThreshold()){
					return health;
				}
			}
			throw new IllegalArgumentException(
					"Unknown enum type " + value + ", Allowed values are " + Arrays.toString(values()));
		}
    	
    }
}
