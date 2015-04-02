package com.ryong21.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ryong21.encode.Encoder;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;

public class PcmRecorder implements Runnable {

	private Logger log = LoggerFactory.getLogger(PcmRecorder.class);
	private volatile boolean isRecording;
	private final Object mutex = new Object();
	private static final int frequency = 8000;
	private static final int audioEncoding = AudioFormat.ENCODING_PCM_16BIT;
	private Consumer consumer;
	public PcmRecorder(Consumer consumer) {
		super();
		this.consumer = consumer;
	}

	public void run() {

		// 启动编码线程
		Encoder encoder = new Encoder(this.consumer);
		Thread encodeThread = new Thread(encoder);
		encoder.setRecording(true);
		encodeThread.start();

		android.os.Process
				.setThreadPriority(android.os.Process.THREAD_PRIORITY_URGENT_AUDIO);

		int bufferRead = 0;
		int bufferSize = AudioRecord.getMinBufferSize(frequency,
				AudioFormat.CHANNEL_IN_MONO, audioEncoding);

		short[] tempBuffer = new short[bufferSize];
		AudioRecord recordInstance = new AudioRecord(
				MediaRecorder.AudioSource.MIC, frequency,
				AudioFormat.CHANNEL_IN_MONO, audioEncoding, bufferSize);

		recordInstance.startRecording();

		while (this.isRecording) {

			//bufferRead = recordInstance.read(tempBuffer, 0, bufferSize);
			bufferRead = recordInstance.read(tempBuffer, 0, 640);
			if (bufferRead == AudioRecord.ERROR_INVALID_OPERATION) {
				throw new IllegalStateException(
						"read() returned AudioRecord.ERROR_INVALID_OPERATION");
			} else if (bufferRead == AudioRecord.ERROR_BAD_VALUE) {
				throw new IllegalStateException(
						"read() returned AudioRecord.ERROR_BAD_VALUE");
			} else if (bufferRead == AudioRecord.ERROR_INVALID_OPERATION) {
				throw new IllegalStateException(
						"read() returned AudioRecord.ERROR_INVALID_OPERATION");
			}

			if (encoder.isIdle()) {
				encoder.putData(System.currentTimeMillis(), tempBuffer,
						bufferRead);
			} else {
				// 认为编码处理不过来，直接丢掉这次读到的数据
				log.error("drop data!");
			}

		}
		recordInstance.stop();
		encoder.setRecording(false);
	}

	public void stop(){
		setRecording(false);
	}
	
	public void setRecording(boolean isRecording) {
		synchronized (mutex) {
			this.isRecording = isRecording;
			if (this.isRecording) {
				mutex.notify();
			}
		}
	}

	public boolean isRecording() {
		synchronized (mutex) {
			return isRecording;
		}
	}
}
