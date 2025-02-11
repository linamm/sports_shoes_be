package com.sportsshoes.exceptions;

import java.util.Date;

public class ErrorMessage {

        private Date timestamp;
        private String errorMsg;
        private String path;


        public Date getTimestamp() {
            return timestamp;
        }


        public void setTimestamp(Date timestamp) {
            this.timestamp = timestamp;
        }


        public String getErrorMsg() {
            return errorMsg;
        }


        public void setErrorMsg(String errorMsg) {
            this.errorMsg = errorMsg;
        }


        public String getPath() {
            return path;
        }


        public void setPath(String path) {
            this.path = path;
        }


        public ErrorMessage(Date timestamp, String errorMsg, String path) {
            this.timestamp = timestamp;
            this.errorMsg = errorMsg;
            this.path = path;
        }
    }
