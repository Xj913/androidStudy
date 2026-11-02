package com.contents.entity;

public record MyRingtone(String id, String name, String path, String is_ringtone,
                         String is_notification) {

    @Override
    public String toString() {
        return "MyRingtone{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", is_ringtone='" + is_ringtone + '\'' +
                ", is_notification='" + is_notification + '\'' +
                '}';
    }
}
