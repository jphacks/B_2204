package com.example.cardgame;

import android.provider.BaseColumns;

public final class FeedReaderContract {
    // FeedReaderContractをprivateで宣言
    private FeedReaderContract() {}

    /* テーブルのコンテンツをインナーで定義する */
    // テーブル名だからStringで定義 //
    public static class StudyEntry implements BaseColumns {
        public static final String TABLE_NAME = "Studies";
        public static final String COLUMN_NAME_SUBJECT = "subject";
        public static final String COLUMN_NAME_DATE = "date";
        public static final String COLUMN_NAME_TIME = "hour";
    }
}