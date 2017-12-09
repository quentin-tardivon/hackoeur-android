package com.tardivon.quentin.hackoeur;

/**
 * Created by Admin on 23-11-2017.
 */

/**
 * Created by Admin on 23-11-2017.
 */

public class Eventss {
    String eventId;
    String eventname;
    String eventdescription;
    String eventlocation;
   String eventImage;
    String eventhour;
    String eventmin;
    String eventday;
    String eventmonth;
    String eventyear;
    String count;
    String clientId;
    String clients;
    public Eventss(){

    }
    public Eventss(String clientId, String clients, String count, String eventId,String eventImage, String eventday, String eventdescription, String eventhour, String eventlocation, String eventmin,String eventmonth,String eventname,String eventyear){
        this.eventId=eventId;
        this.eventname=eventname;
        this.eventdescription=eventdescription;
        this.eventlocation=eventlocation;
        this.eventImage=eventImage;
        this.eventhour=eventhour;
        this.eventmin=eventmin;
        this.eventday=eventday;
        this.eventmonth=eventmonth;
        this.eventyear=eventyear;
        this.count=count;
        this.clientId=clientId;
        this.clients=clients;
    }
    public String getEventId(){
        return eventId;
    }
    public void setEventId(String eventId){
this.eventId=eventId;
    }
    public String getEventname(){
        return eventname;
    }
    public void setEventname(String eventname){
        this.eventname=eventname;
    }
    public String getEventdescription(){
        return eventdescription;
    }
    public void setEventdescription(String eventdescription){
        this.eventdescription=eventdescription;
    }
    public String getEventlocation(){
        return eventlocation;
    }
    public void setEventlocation(String eventlocation){
        this.eventlocation=eventlocation;
    }
   public String getEventImage(){
        return eventImage;
    }
    public void setEventImage(String eventImage){
        this.eventImage=eventImage;
    }
    public String getEventhour(){
        return eventhour;
    }
    public void setEventhour(String eventhour){
        this.eventhour=eventhour;
    }
    public String getEventmin(){
        return eventmin;
    }
    public void setEventmin(String eventmin){
        this.eventmin=eventmin;
    }
    public String getEventday(){
        return eventday;
    }
    public void setEventday(String eventday){
        this.eventday=eventday;
    }
    public String getEventmonth(){
        return eventmonth;
    }
    public void setEventmonth(String eventmonth){
        this.eventmonth=eventmonth;
    }
    public String getEventyear(){
        return eventyear;
    }
    public void setEventyear(String eventyear){
        this.eventyear=eventyear;
    }
    public String getCount(){
        return count;
    }
    public void setCount(String count){
        this.count=count;
    }
    public String getClientId(){
        return clientId;
    }
    public void setClientId(String clientId){
        this.clientId=clientId;
    }
    public String getClients(){return clients;}
    public void setClients(String clients){
        this.clients=clients;
    }


}



