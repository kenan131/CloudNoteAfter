package com.bin.model.Enum;

public enum BookStatus {
    UNSHARE(0),SHARE(1),SHARESUCCESS(2),UNDELETE(-1),RUBBISH(999),MD(1),RICH(2);

    private int code;

    public int getCode() {
        return code;
    }

    BookStatus(int code) {
        this.code = code;
    }
}
