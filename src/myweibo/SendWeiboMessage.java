package myweibo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;

import weibo4j.Timeline;
import weibo4j.examples.oauth2.Log;
import weibo4j.http.ImageItem;
import weibo4j.model.Status;
import weibo4j.model.WeiboException;

public class SendWeiboMessage {


	public static void main(String[] args) throws WeiboException, IOException {
		String access_token = "2.00FoNgQGFO64ED8f9b11ed9fG2AMvD";
		String content = URLEncoder.encode(
				"#酷云大数据#1月1日全天（00:00-24:00）卫视频道排行榜：NO.1#湖南卫视# NO.2#东方卫视# NO.3#浙江卫视# 【自助获取更多免费数据】http://pro.eye.kuyun.com/mobile/mobile_rank.html?_=0.75438000444759",
				"utf-8");
		String filePath = "/Users/baoming/Documents/workspace/git_work/video-dot/src/main/webapp/assets/images/thumb-1.png";
		ImageItem item = new ImageItem(getContent(filePath));
		Timeline tm = new Timeline(access_token);
		try {
			Status status = tm.uploadStatus(content, item);
			Log.logInfo(status.toString());
		} catch (WeiboException e) {
			Log.logInfo("发送微博信息失败");
			throw new RuntimeException(e);
		}
	}

	public static void sendWeibo(String access_token, String content, String filePath) {
		try {
			content = URLEncoder.encode(content, "utf-8");
			ImageItem item = new ImageItem(getContent(filePath));
			Timeline tm = new Timeline(access_token);
			Status status = tm.uploadStatus(content, item);
			Log.logInfo(status.toString());
		} catch (Exception e) {
			Log.logInfo("发送微博信息失败");
			throw new RuntimeException(e);
		}
	}

	public static byte[] getContent(String filePath) throws IOException {
		File file = new File(filePath);
		long fileSize = file.length();
		if (fileSize > 2147483647L) {
			System.out.println("file too big...");
			return null;
		}
		FileInputStream fi = new FileInputStream(file);
		byte[] buffer = new byte[(int) fileSize];
		int offset = 0;
		int numRead = 0;
		while ((offset < buffer.length) && ((numRead = fi.read(buffer, offset, buffer.length - offset)) >= 0)) {
			offset += numRead;
		}

		if (offset != buffer.length) {
			throw new IOException("Could not completely read file " + file.getName());
		}
		fi.close();
		return buffer;
	}
}
