package com.example.administrator.signagesetting.bean;

/**
 * Created by Administrator on 2017/11/7.
 */

public class Properties
{
    private String server_url;
    private String application_root;
    private String usb_root;
    private String get_schedule;
    private String notify_status;
    private String schedule_download;

    public String getServer_url() {
        return server_url;
    }

    public void setServer_url(String server_url) {
        this.server_url = server_url;
    }

    public String getApplication_root() {
        return application_root;
    }

    public void setApplication_root(String application_root) {
        this.application_root = application_root;
    }

    public String getUsb_root() {
        return usb_root;
    }

    public void setUsb_root(String usb_root) {
        this.usb_root = usb_root;
    }

    public String getGet_schedule() {
        return get_schedule;
    }

    public void setGet_schedule(String get_schedule) {
        this.get_schedule = get_schedule;
    }

    public String getNotify_status() {
        return notify_status;
    }

    public void setNotify_status(String notify_status) {
        this.notify_status = notify_status;
    }

    public String getSchedule_download() {
        return schedule_download;
    }

    public void setSchedule_download(String schedule_download) {
        this.schedule_download = schedule_download;
    }
}

