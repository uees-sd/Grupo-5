/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos_juego_sin_nombre;

import java.io.Serializable;

/**
 *
 * @author aleja
 */
public class Usuario implements Serializable{
    private String userName;
    private String Avatar;
    private String ip;
    
    public Usuario() {}

    public Usuario(String userName, String Avatar, String ip) {
        this.userName = userName;
        this.Avatar = Avatar;
        this.ip = ip;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAvatar() {
        return Avatar;
    }

    public void setAvatar(String Avatar) {
        this.Avatar = Avatar;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    
}
