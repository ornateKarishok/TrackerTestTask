package com.example.trackertesttask.model;

import java.util.Date;

public class WorkSession {
    private final double duration;
    private final Date date;

    public WorkSession(double duration, Date date) {
        this.duration = duration;
        this.date = date;
    }

    public double getDuration() {
        return duration;
    }

    public Date getDate() {
        return date;
    }
}
