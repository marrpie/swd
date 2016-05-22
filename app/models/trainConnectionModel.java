package models;

import java.sql.Time;

public class trainConnectionModel {
    private String from;
    private String to;
    private Long fromTime;
    private Long toTime;

    public trainConnectionModel()
    {
    }

    public trainConnectionModel(String from, String to, Long fromTime, Long toTime)
    {
        this.from = from;
        this.to = to;
        this.fromTime = fromTime;
        this.toTime = toTime;
    }

    public String getFrom()
    {
        return from;
    }

    public void setFrom(String from)
    {
        this.from = from;
    }

    public String getTo()
    {
        return to;
    }

    public void setTo(String to)
    {
        this.to = to;
    }

    public Long getFromTime()
    {
        return fromTime;
    }

    public void setFromTime(Long fromTime)
    {
        this.fromTime = fromTime;
    }

    public Long getToTime()
    {
        return toTime;
    }

    public void setToTime(Long toTime)
    {
        this.toTime = toTime;
    }
}