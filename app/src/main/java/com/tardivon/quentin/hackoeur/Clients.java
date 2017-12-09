
/**
 * Created by Admin on 08-12-2017.
 */

package com.tardivon.quentin.hackoeur;

/**
 * Created by Admin on 01-12-2017.
 */

public class Clients {
    public String ClientId;
    public String ClientEmail;
    public String Events;
    public Clients(){

    }

    public Clients(String clientId, String clientEmail,String events) {
        ClientId = clientId;
        ClientEmail = clientEmail;
        Events=events;
    }

    public String getClientId() {
        return ClientId;
    }
    public void setClientId(String clientId){
        this.ClientId=clientId;
    }


    public String getClientEmail() {
        return ClientEmail;
    }

    public void setClientEmail(String clientEmail){
        this.ClientEmail=clientEmail;
    }
    public String getEvents() {
        return Events;
    }
    public void setEvents(String events){
        this.Events=events;
    }


}
