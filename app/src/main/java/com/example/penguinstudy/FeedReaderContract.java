package com.example.penguinstudy;

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
        public static final String COLUMN_NAME_TODO = "todo";
    }

    public static class AccountEntry implements BaseColumns {
        public static final String TABLE_NAME = "Account";
        public static final String COLUMN_NAME_ACCOUNT = "account";
        public static final String COLUMN_NAME_PASS = "password";
    }

    // 餌テーブル
    public static class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "Feed";
        public static final String COLUMN_NAME_FEED = "feeds";
    }

    // ペンギンテーブル
    public static class PenguinEntry implements BaseColumns {
        public static final String TABLE_NAME = "Penguin";
        public static final String COLUMN_NAME_STOMACH = "stomach"; // 空腹具合
        public static final String COLUMN_NAME_GENERATION = "generation"; // 世代
        public static final String COLUMN_NAME_FIRST = "first_open"; // 最初のオープン
        public static final String COLUMN_NAME_LAST = "last_open"; // 最後のオープン
    }

    // タグテーブル
    public static class TagEntry implements BaseColumns {
        public static final String TABLE_NAME = "Tags";
        public static final String COLUMN_NAME_FEED = "tag_name";
        public static final String COLUMN_NAME_COLOR = "tag_color";
    }
}
