/**
 * 
 */
package com.bupt.qrj.unifyum.dal.dataobject;

import java.util.Date;

/**
 * @author renjun.qrj  2015��12��29��:����7:30:12
 * com.bupt.qrj.unifyum.dal.dataobject.UserImageDO
 * unifyum-dal
 * ��;: 
 *
 */
public class UserImageDO {

    private Long   id;

    /**�û���*/
    private String userName;

    /**¼��ʱ��*/
    private Date   recordTime;

    /**base64 ���ַ��� */
    private String image;

    /**ͼ��������1*/
    private String moisten;

    /**ͼ��������2*/
    private String blood;

    /**ͼ��������3*/
    private String color;

    /**ͼ��������4*/
    private String texture;

    /**ͼ��������5*/
    private String satin;

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return the recordTime
     */
    public Date getRecordTime() {
        return recordTime;
    }

    /**
     * @param recordTime the recordTime to set
     */
    public void setRecordTime(Date recordTime) {
        this.recordTime = recordTime;
    }

    /**
     * @return the image
     */
    public String getImage() {
        return image;
    }

    /**
     * @param image the image to set
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * @return the moisten
     */
    public String getMoisten() {
        return moisten;
    }

    /**
     * @param moisten the moisten to set
     */
    public void setMoisten(String moisten) {
        this.moisten = moisten;
    }

    /**
     * @return the blood
     */
    public String getBlood() {
        return blood;
    }

    /**
     * @param blood the blood to set
     */
    public void setBlood(String blood) {
        this.blood = blood;
    }

    /**
     * @return the color
     */
    public String getColor() {
        return color;
    }

    /**
     * @param color the color to set
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * @return the texture
     */
    public String getTexture() {
        return texture;
    }

    /**
     * @param texture the texture to set
     */
    public void setTexture(String texture) {
        this.texture = texture;
    }

    /**
     * @return the satin
     */
    public String getSatin() {
        return satin;
    }

    /**
     * @param satin the satin to set
     */
    public void setSatin(String satin) {
        this.satin = satin;
    }

}
