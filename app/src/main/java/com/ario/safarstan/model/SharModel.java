package com.ario.safarstan.model;

public class SharModel {

  private String name;
  private String picUrl;
  private int id;
  private String detail;

  public void setName(String name) {
    this.name = name;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public int getId() {
    return id;
  }

  public void setDetail(String detail) {
    this.detail = detail;
  }

  public String getDetail() {
    return detail;
  }



  public String getPicUrl() {
    return picUrl;
  }

  public void setPicUrl(String picUrl) {
    this.picUrl = picUrl;
  }
}