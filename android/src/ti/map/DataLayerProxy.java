/**
 * Appcelerator Titanium Mobile
 * Copyright (c) 2013-2016 by Appcelerator, Inc. All Rights Reserved.
 * Licensed under the terms of the Apache Public License
 * Please see the LICENSE included with this distribution for details.
 */
package ti.map;

import java.util.HashMap;
import java.util.List;

import org.appcelerator.kroll.KrollDict;
import org.appcelerator.kroll.KrollPropertyChange;
import org.appcelerator.kroll.KrollProxy;
import org.appcelerator.kroll.KrollProxyListener;
import org.appcelerator.kroll.annotations.Kroll;
import org.appcelerator.kroll.common.AsyncResult;
import org.appcelerator.kroll.common.Log;
import org.appcelerator.kroll.common.TiMessenger;
import org.appcelerator.titanium.TiApplication;
import org.appcelerator.titanium.TiBlob;
import org.appcelerator.titanium.TiC;
import org.appcelerator.titanium.TiDimension;
import org.appcelerator.titanium.TiPoint;
import org.appcelerator.titanium.proxy.TiViewProxy;
import org.appcelerator.titanium.util.TiConvert;
import org.appcelerator.titanium.view.TiDrawableReference;

import com.google.maps.android.data.kml.KmlLayer;

import android.graphics.Bitmap;
import android.os.Message;
import android.view.View;


@Kroll.proxy(creatableInModule=MapModule.class, propertyAccessors = {
	MapModule.PROPERTY_KML,
	MapModule.PROPERTY_JSON
})
public class DataLayerProxy extends KrollProxy implements KrollProxyListener
{
	private static final String TAG = "KrollProxy";
	
	private KmlLayer kmlLayer;


	public DataLayerProxy()
	{
		super();
	}

	@Override
	public void release() {
		super.release();
	}
	
	@Override
	public void processProperties(KrollDict props) {
	}
	
	@Override
	public void propertyChanged(String key, Object oldValue, Object newValue, KrollProxy proxy) {
	}

	@Override
	public void listenerAdded(String arg0, int arg1, KrollProxy arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void listenerRemoved(String arg0, int arg1, KrollProxy arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void propertiesChanged(List<KrollPropertyChange> arg0, KrollProxy arg1) {
		// TODO Auto-generated method stub
		
	}


}
