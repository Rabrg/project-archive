package com.runescape.media;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ColorModel;
import java.awt.image.DirectColorModel;
import java.awt.image.ImageConsumer;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;

public class ProducingGraphicsBuffer implements ImageProducer, ImageObserver {
	
	public boolean aBoolean391;
	public int anIntArray392[];
	public int anInt393;
	public int anInt394;
	public ColorModel aColorModel395;
	public ImageConsumer anImageConsumer396;
	public Image anImage397;
	
	public ProducingGraphicsBuffer(int i, byte byte0, Component component, int j) {
		aBoolean391 = true;
		anInt393 = j;
		anInt394 = i;
		anIntArray392 = new int[j * i];
		aColorModel395 = new DirectColorModel(32, 0xff0000, 65280, 255);
		anImage397 = component.createImage(this);
		method232();
		component.prepareImage(anImage397, this);
		method232();
		component.prepareImage(anImage397, this);
		if (byte0 != -12) {
			throw new NullPointerException();
		} else {
			method232();
			component.prepareImage(anImage397, this);
			method230(false);
			return;
		}
	}

	public void method230(boolean flag) {
		if (flag) {
			for (int i = 1; i > 0; i++) {
				;
			}
		}
		Rasterizer.method444(aBoolean391, anInt393, anInt394, anIntArray392);
	}

	public void method231(int i, int j, Graphics g, boolean flag) {
		if (flag) {
			return;
		} else {
			method232();
			g.drawImage(anImage397, j, i, this);
			return;
		}
	}

	@Override
	public synchronized void addConsumer(ImageConsumer imageconsumer) {
		anImageConsumer396 = imageconsumer;
		imageconsumer.setDimensions(anInt393, anInt394);
		imageconsumer.setProperties(null);
		imageconsumer.setColorModel(aColorModel395);
		imageconsumer.setHints(14);
	}

	@Override
	public synchronized boolean isConsumer(ImageConsumer imageconsumer) {
		return anImageConsumer396 == imageconsumer;
	}

	@Override
	public synchronized void removeConsumer(ImageConsumer imageconsumer) {
		if (anImageConsumer396 == imageconsumer) {
			anImageConsumer396 = null;
		}
	}

	@Override
	public void startProduction(ImageConsumer imageconsumer) {
		addConsumer(imageconsumer);
	}

	@Override
	public void requestTopDownLeftRightResend(ImageConsumer imageconsumer) {
		System.out.println("TDLR");
	}

	public synchronized void method232() {
		if (anImageConsumer396 == null) {
			return;
		} else {
			anImageConsumer396.setPixels(0, 0, anInt393, anInt394, aColorModel395, anIntArray392, 0, anInt393);
			anImageConsumer396.imageComplete(2);
			return;
		}
	}

	@Override
	public boolean imageUpdate(Image image, int i, int j, int k, int l, int i1) {
		return true;
	}
}
