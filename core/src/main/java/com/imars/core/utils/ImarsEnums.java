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
    
    public enum Wealth{
    	HOUSE("house"),CAR("car"),PLANE("plane"),YATCH("yatch"),MONEY("money"),INSTRUMENTS("instruments");
    	
    	private String value;
        
        private Wealth(String value){
        	this.setValue(value);
        }

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}
		
		
		public static Wealth fromValue(String value) {
			for (Wealth category : values()) {
				if (category.value.equalsIgnoreCase(value)) {
					return category;
				}
			}
			throw new IllegalArgumentException(
					"Unknown enum type " + value + ", Allowed values are " + Arrays.toString(values()));
		}
    	
    }
    
    public enum ScheduleType{
    	PRACTICEINSTRUMENT("practiceinstrument"),
    	AUDITION("audition"),
    	REHERSAL("rehersal"),
    	CONCERT("concert"),
    	MEETING("meeting"),
    	ALBUMMAKING("albummaking");
    	
    	private String value;
        
        private ScheduleType(String value){
        	this.setValue(value);
        }

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}
		
		
		public static ScheduleType fromValue(String value) {
			for (ScheduleType category : values()) {
				if (category.value.equalsIgnoreCase(value)) {
					return category;
				}
			}
			throw new IllegalArgumentException(
					"Unknown enum type " + value + ", Allowed values are " + Arrays.toString(values()));
		}
    }

    public enum ScheduleActivityType{
    	INDIVIDUAL("individual"),
    	BAND("band");
    	
    	private String value;
        
        private ScheduleActivityType(String value){
        	this.setValue(value);
        }

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}
		
		
		public static ScheduleActivityType fromValue(String value) {
			for (ScheduleActivityType category : values()) {
				if (category.value.equalsIgnoreCase(value)) {
					return category;
				}
			}
			throw new IllegalArgumentException(
					"Unknown enum type " + value + ", Allowed values are " + Arrays.toString(values()));
		}
    }
    
    public enum ScheduleActivityStatus{
    	SCHEDULED("scheduled"),
    	INPROGRESS("inProgress"),
    	COMPLETED("completed"),
    	NOTCOMPLETED("notCompleted"),
    	CANCELLED("cancelled"),
    	EXPIRED("expired"),
    	ERROR("error");
    	
    	private String value;
        
        private ScheduleActivityStatus(String value){
        	this.setValue(value);
        }

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}
		
		
		public static ScheduleActivityStatus fromValue(String value) {
			for (ScheduleActivityStatus category : values()) {
				if (category.value.equalsIgnoreCase(value)) {
					return category;
				}
			}
			throw new IllegalArgumentException(
					"Unknown enum type " + value + ", Allowed values are " + Arrays.toString(values()));
		}
    }
    
    public enum MessageStatus{
    	READ("read"),
    	UNREAD("unread");
    	
    	private String value;
        
        private MessageStatus(String value){
        	this.setValue(value);
        }

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}
		
		
		public static MessageStatus fromValue(String value) {
			for (MessageStatus status : values()) {
				if (status.value.equalsIgnoreCase(value)) {
					return status;
				}
			}
			throw new IllegalArgumentException(
					"Unknown enum type " + value + ", Allowed values are " + Arrays.toString(values()));
		}
    }
    
}
