package com.contents;

import  android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.util.Log;

import com.contents.entity.MyCallLog;
import com.contents.entity.MyRingtone;
import com.contents.entity.UploadPhone;
import com.xiajun.utils.FormatUtil;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import com.contents.entity.MediaBean;
import com.contents.entity.MediaFolder;

public class MyContentsData {
    private static final String TAG = "ContactHelper";

    private static final String[] RINGTONE_PROJECTION = {
            MediaStore.Audio.Media._ID
            , MediaStore.Audio.Media.TITLE
            , MediaStore.Audio.Media.DATA
            , MediaStore.Audio.Media.IS_RINGTONE
            , MediaStore.Audio.Media.IS_NOTIFICATION
    };

    public static ArrayList<MyRingtone> getRingtone(Context context) {
        ArrayList<MyRingtone> list = new ArrayList<>();
        ContentResolver cr = context.getContentResolver();
        String selection = "is_ringtone=1 or is_notification=1";
        Cursor cur = cr.query(MediaStore.Audio.Media.INTERNAL_CONTENT_URI, RINGTONE_PROJECTION, selection, null, null);
        if (cur != null && cur.getCount() > 0) {
                MyRingtone ringtone;
                while (cur.moveToNext()) {
                    String id = cur.getString(cur.getColumnIndexOrThrow(MediaStore.Audio.Media._ID));
                    String name = cur.getString(cur.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
                    String path = cur.getString(cur.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
                    String is_ringtone = cur.getString(cur.getColumnIndexOrThrow(MediaStore.Audio.Media.IS_RINGTONE));
                    String is_notification = cur.getString(cur.getColumnIndexOrThrow(MediaStore.Audio.Media.IS_NOTIFICATION));
                    ringtone = new MyRingtone(id, name, path, is_ringtone, is_notification);
                    Log.e(TAG, ringtone.toString());
                    list.add(ringtone);
                }
        }
        if (cur != null) cur.close();
        return list;
    }

    static String[] Contacts_PROJECTION = {
            ContactsContract.Contacts._ID
            , ContactsContract.Contacts.DISPLAY_NAME
            , ContactsContract.Contacts.HAS_PHONE_NUMBER
    };
    static String[] Phone_PROJECTION = {
            ContactsContract.CommonDataKinds.Phone.CONTACT_ID
            , ContactsContract.CommonDataKinds.Phone.NUMBER
    };

    public static ArrayList<UploadPhone> getContacts(Context context) {
        ArrayList<UploadPhone> contacts = new ArrayList<>();
        ContentResolver cr = context.getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, Contacts_PROJECTION, null, null, null);
        if (cur != null) {
            if (cur.getCount() > 0) {
                while (cur.moveToNext()) {
                    UploadPhone contact = new UploadPhone();
                    String id = cur.getString(cur.getColumnIndexOrThrow(ContactsContract.Contacts._ID));
                    String name = cur.getString(cur.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME));
                    contact.setName(name);
                    if (Integer.parseInt(cur.getString(cur.getColumnIndexOrThrow(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                        Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, Phone_PROJECTION, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{id}, null);
                        while (pCur.moveToNext()) {
                            String phone = pCur.getString(pCur.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            phone = phone.replace("-", "").replace("-", "").replace(" ", "").trim();
                            if (phone.length() >= 11) {
                                phone = phone.substring(phone.length() - 11);
                                if (FormatUtil.isMobileNum(phone)) {
                                    if (null == contact.getTelephone()) {
                                        boolean inResult = false;
                                        for (UploadPhone up : contacts) {
                                            if (phone.equals(up.getTelephone())) {
                                                inResult = true;
                                                break;
                                            }
                                        }
                                        if (!inResult) {
                                            contact.setTelephone(phone);
                                            contacts.add(contact);
                                        }
                                    }
                                }
                            }
                        }
                        pCur.close();
                    }
                }
            }
            cur.close();
        }
        return contacts;
    }

    public static List<MyCallLog> queryCalls(Context context) {
        List<MyCallLog> calls = new ArrayList<>();
       /* Cursor cursor = context.getContentResolver().query(CallLog.Calls.CONTENT_URI, null, null, null, CallLog.Calls.DEFAULT_SORT_ORDER);
        while (cursor.moveToNext()) {
            MyCallLog call = new MyCallLog();
            call.name = cursor.getString(cursor.getColumnIndexOrThrow(CallLog.Calls.CACHED_NAME)) + " ";
            String date = cursor.getString(cursor.getColumnIndexOrThrow(CallLog.Calls.DATE));
            call.date = formatTime(Long.parseLong(date));
            call.phone = cursor.getString(cursor.getColumnIndexOrThrow(CallLog.Calls.NUMBER));
            call.type = cursor.getString(cursor.getColumnIndexOrThrow(CallLog.Calls.TYPE));
            calls.add(call);
            if (call.name.equals("null ")) {
                call.setName(call.getPhone());
            }
        }
        cursor.close();*/
        return calls;
    }

    static String[] AUDIO_PROJECTION = {
            MediaStore.Files.FileColumns.DATA,
            MediaStore.Files.FileColumns.DISPLAY_NAME,
            MediaStore.Files.FileColumns.DATE_ADDED,
            MediaStore.Files.FileColumns.MEDIA_TYPE,
            MediaStore.Files.FileColumns.SIZE,
            MediaStore.Files.FileColumns._ID,
            MediaStore.Files.FileColumns.PARENT};

    public static ArrayList<MediaFolder> getAudios(Context context) {
        ArrayList<MediaFolder> folders = new ArrayList<>();
        Uri queryUri = MediaStore.Files.getContentUri("external");
        ContentResolver cr = context.getContentResolver();
        String selection = MediaStore.Files.FileColumns.MEDIA_TYPE + "=" + MediaStore.Files.FileColumns.MEDIA_TYPE_AUDIO;
        Cursor cursor = cr.query(queryUri, AUDIO_PROJECTION, selection, null, MediaStore.Files.FileColumns.DATE_ADDED + " DESC");
        if (cursor != null && cursor.getCount() > 0) {
            Log.e("getAudios ", cursor.getCount() + "数量数量");
            while (cursor.moveToNext()) {
                String path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATA));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DISPLAY_NAME));
                long dateTime = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATE_ADDED));
                int mediaType = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.MEDIA_TYPE));
                long size = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.SIZE));
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns._ID));

                if (size < 1) continue;
                String dirName = getParent(path);
                MediaBean media = new MediaBean(path, name, dateTime, mediaType, size, id, dirName);

                int index = hasDir(folders, dirName);
                if (index != -1) {
                    folders.get(index).addMedias(media);
                } else {
                    MediaFolder folder = new MediaFolder(dirName);
                    folder.addMedias(media);
                    folders.add(folder);
                }
            }
        }
        if (cursor != null) cursor.close();
        return folders;
    }

    public static String getParent(String path) {
        String sp[]=path.split("/");
        return sp[sp.length-2];
    }

    public static int hasDir(ArrayList<MediaFolder> folders, String dirName){
        for(int i = 0; i< folders.size(); i++){
            MediaFolder folder = folders.get(i);
            if( folder.name.equals(dirName)) {
                return i;
            }
        }
        return -1;
    }

    public String formatTime(long date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd   HH:mm:ss", Locale.getDefault());
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        // Date currentTime = new Date(java.lang.System.currentTimeMillis());
        return sdf.format(date);
    }
}
