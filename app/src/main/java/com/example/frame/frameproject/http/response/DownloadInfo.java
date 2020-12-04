package com.example.frame.frameproject.http.response;

public class DownloadInfo {
	private int downloadProgress;
	private String file;

	public int getDownloadProgress() {
		return downloadProgress;
	}

	public void setDownloadProgress(int downloadProgress) {
		this.downloadProgress = downloadProgress;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}
}
