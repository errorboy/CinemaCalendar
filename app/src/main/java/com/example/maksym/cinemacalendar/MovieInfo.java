package com.example.maksym.cinemacalendar;

import android.os.Parcel;
import android.os.Parcelable;

public class MovieInfo implements Parcelable {
    private String name;
    public static final Creator<MovieInfo> CREATOR = new Creator<MovieInfo>() {
        @Override
        public MovieInfo createFromParcel(Parcel in) {
            return new MovieInfo(in);
        }

        @Override
        public MovieInfo[] newArray(int size) {
            return new MovieInfo[size];
        }
    };
    private String date;
    private String imgUrl;
    private String url;
    private String rate;
    private String info;
    private String actors;

    public MovieInfo(String name, String rate, String date, String imgUrl, String url, String info, String actors) {
        this.name = name;
        this.rate = rate;
        this.date = date;
        this.imgUrl = imgUrl;
        this.url = url;
        this.info = info;
        this.actors = actors;
    }

    protected MovieInfo(Parcel in) {
        String[] data = new String[7];
        in.readStringArray(data);
        name = data[0];
        rate = data[1];
        date = data[2];
        imgUrl = data[3];
        url = data[4];
        info = data[5];
        actors = data[6];
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[]{name, rate, date, imgUrl, url, info, actors});

    }
}
