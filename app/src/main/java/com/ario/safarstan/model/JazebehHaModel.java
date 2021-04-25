package com.ario.safarstan.model;

public class JazebehHaModel {

  private String picUrl;
  private String address;
  private String tozihat;
  private int like;
  private String film;
  private int id;

  private String title;

  public void setTitle(String title) {
    this.title = title;
  }

  public String getTitle() {
    return title;
  }
  public void setId(int id) {
    this.id = id;
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

  private String detail;

  public String getPicUrl() {
    return picUrl;
  }

  public void setPicUrl(String picUrl) {
    this.picUrl = picUrl;
  }

  public String getAddress() {
    return address;
  }

  public String getTozihat() {
    return tozihat;
  }

  public int getLike() {
    return like;
  }

  public String getFilm() {
    return film;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public void setTozihat(String tozihat) {
    this.tozihat = tozihat;
  }

  public void setLike(int like) {
    this.like = like;
  }

  public void setFilm(String film) {
    this.film = film;
  }
}