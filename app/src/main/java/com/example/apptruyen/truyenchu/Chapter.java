package com.example.apptruyen.truyenchu;

import java.io.Serializable;

class Chapter implements Serializable {
    private int idChapter;
    private int idStory;
    private String chapterName;
    private String uploader;
    private String content;

    public Chapter() {
    }

    public Chapter(int idChapter, String chapterName) {
        this.idChapter = idChapter;
        this.chapterName = chapterName;
    }

    public Chapter(int idChapter, int idStory, String chapterName) {
        this.idChapter = idChapter;
        this.idStory = idStory;
        this.chapterName = chapterName;
    }

    public Chapter(int idChapter, int idStory, String chapterName, String uploader, String content) {
        this.idChapter = idChapter;
        this.idStory = idStory;
        this.chapterName = chapterName;
        this.uploader = uploader;
        this.content = content;
    }

    public int getIdChapter() {
        return idChapter;
    }

    public void setIdChapter(int idChapter) {
        this.idChapter = idChapter;
    }

    public int getIdStory() {
        return idStory;
    }

    public void setIdStory(int idStory) {
        this.idStory = idStory;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public String getUploader() {
        return uploader;
    }

    public void setUploader(String uploader) {
        this.uploader = uploader;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
