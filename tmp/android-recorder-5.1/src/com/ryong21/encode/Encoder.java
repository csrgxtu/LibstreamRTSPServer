package com.ryong21.encode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ryong21.io.Consumer;

public class Encoder implements Runnable {

	private Logger log = LoggerFactory.getLogger(Encoder.class);
	private volatile int leftSize = 0;
	private final Object mutex = new Object();
	private Speex speex = new Speex();
	private long ts;
	private Consumer consumer;
	private byte[] processedData = new byte[1024];
	private short[] rawdata = new short[1024];
	private volatile boolean isRecording;

	public Encoder(Consumer consumer) {
		super();
		this.consumer = consumer;
		speex.init();
	}

	public void run() {

		android.os.Process
				.setThreadPriority(android.os.Process.THREAD_PRIORITY_URGENT_AUDIO);

		int getSize = 0;
		while (this.isRecording()) {
			if (isIdle()) {
				try {
					Thread.sleep(20);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				continue;
			}
			synchronized (mutex) {
				getSize = speex.encode(rawdata, 0, processedData, leftSize);
				setIdle();
			}
			if (getSize > 0) {
				consumer.putData(ts, processedData, getSize);
			}
		}
		log.info("encode thread exit");
	}

	public void putData(long ts, short[] data, int size) {
		synchronized (mutex) {
			this.ts = ts;
			System.arraycopy(data, 0, rawdata, 0, size);
			this.leftSize = size;
		}
	}

	public boolean isIdle() {
		synchronized (mutex) {
			return leftSize == 0 ? true : false;
		}
	}

	public void setIdle() {
		leftSize = 0;
	}

	public void setRecording(boolean isRecording) {
		synchronized (mutex) {
			this.isRecording = isRecording;
		}
	}

	public boolean isRecording() {
		synchronized (mutex) {
			return isRecording;
		}
	}
}
