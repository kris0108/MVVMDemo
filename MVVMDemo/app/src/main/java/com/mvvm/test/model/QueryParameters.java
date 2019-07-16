package com.mvvm.test.model;

public class QueryParameters {

    private String latitude = "37.422740";
    private String longitude = "-122.139956";
    private String offset = null;
    private String limit = null;

    public String getLatitude() {
        return latitude;
    }

    private void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    private void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getOffset() {
        return offset;
    }

    private void setOffset(String offset) {
        this.offset = offset;
    }

    public String getLimit() {
        return limit;
    }

    private void setLimit(String limit) {
        this.limit = limit;
    }
}
