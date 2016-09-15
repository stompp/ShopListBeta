package com.example.josem.shoplistbesta.api;

/**
 * Created by josem on 20/07/2016.
 */
public final class Listra {
    public static final class STATUS{
        public static final String UNKNOWN = "unknown";
        public static final String PENDING = "pending";
        public static final String COMPLETE = "complete";
        public static final String CANCELED = "canceled";
        public static final String TRASH = "trash";
    }
    public static final class KEYS{
        public static final String STATUS = "status";
        public static final String TASK = "task";
        public static final String CREATION_TS = "creation_ts";
        public static final String CANCELED = "canceled";
        public static final String TRASH = "trash";
    }
}
