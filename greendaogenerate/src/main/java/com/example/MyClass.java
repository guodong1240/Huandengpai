package com.example;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class MyClass {
    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(1, "db.bean");
        schema.setDefaultJavaPackageDao("db.dao");
        addNote(schema);
        new DaoGenerator().generateAll(schema, "e:/aa/ZPai/app/src/main/greendao");

    }

    private static void addNote(Schema schema) {
        Entity note = schema.addEntity("LocalPPT");
        note.addIdProperty();
        note.addStringProperty("title");
        note.addStringProperty("path");
        note.addStringProperty("status");
        note.addStringProperty("time");

        Entity voice = schema.addEntity("SbumitVoice");
        voice.addIdProperty();
        voice.addStringProperty("nid");
        voice.addStringProperty("title");
        voice.addStringProperty("path");

    }
}
