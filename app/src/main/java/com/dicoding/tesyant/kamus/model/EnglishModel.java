package com.dicoding.tesyant.kamus.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by tesyant on 10/4/17.
 */

public class EnglishModel implements Parcelable{

    private int id;
    private String vocab;
    private String means;

    public EnglishModel() {
    }

    public EnglishModel(String vocab, String means) {
        this.vocab = vocab;
        this.means = means;
    }

    public EnglishModel (int id, String vocab, String means) {
        this.id = id;
        this.vocab = vocab;
        this.means = means;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVocab() {
        return vocab;
    }

    public void setVocab(String vocab) {
        this.vocab = vocab;
    }

    public String getMeans() {
        return means;
    }

    public void setMeans(String means) {
        this.means = means;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.vocab);
        dest.writeString(this.means);
    }

    protected EnglishModel(Parcel in) {
        this.id = in.readInt();
        this.vocab = in.readString();
        this.means = in.readString();
    }

    public static final Creator<EnglishModel> CREATOR = new Creator<EnglishModel>() {
        @Override
        public EnglishModel createFromParcel(Parcel source) {
            return new EnglishModel(source);
        }

        @Override
        public EnglishModel[] newArray(int size) {
            return new EnglishModel[size];
        }
    };
}
