/**
 * Appcelerator Titanium Mobile
 * Copyright (c) 2013-2016 by Appcelerator, Inc. All Rights Reserved.
 * Licensed under the terms of the Apache Public License
 * Please see the LICENSE included with this distribution for details.
 */
package ti.map;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import android.util.Log;

import org.appcelerator.kroll.KrollDict;
import org.appcelerator.kroll.KrollPropertyChange;
import org.appcelerator.kroll.KrollProxy;
import org.appcelerator.kroll.KrollProxyListener;
import org.appcelerator.kroll.annotations.Kroll;
import org.appcelerator.titanium.TiApplication;
import org.xmlpull.v1.XmlPullParserException;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.maps.android.data.kml.KmlContainer;
import com.google.maps.android.data.kml.KmlLayer;
import com.google.maps.android.data.kml.KmlPlacemark;
import com.google.maps.android.data.kml.KmlPolygon;

@Kroll.proxy(creatableInModule = MapModule.class, propertyAccessors = { MapModule.PROPERTY_KML,
		MapModule.PROPERTY_MAP_VIEW })
public class KmlLayerProxy extends KrollProxy implements KrollProxyListener {
	private static final String TAG = "KmlLayerProxy";

	private KmlLayer kmlLayer = null;
	private GoogleMap map = null;

	public KmlLayerProxy() {
		super();
		setModelListener(this);
		Log.d(TAG,"kmlLayer CREATED ");
	}

	@Override
	public void release() {
		if (kmlLayer == null) {
			Log.d(TAG,"Layer  WAS NOT CREATED ");

		}
		super.release();
	}

	@Override
	public void processProperties(KrollDict props) {
		if (props.containsKey("mapView")) {
			ViewProxy proxy = (ViewProxy)props.get("mapView");
			TiUIMapView mapView = (TiUIMapView) proxy.peekView();
			map = mapView.getMap(); 
		}
		Log.i(TAG,"kmlLayer PROCESS PROPERTIES ");
		if (map != null) {
			if (!props.containsKey("kml")) {
				Log.d(TAG,"NO KML SET");
				return;
			}
			String kml = props.getString("kml");
			Log.d(TAG,"try to create layer " + kml);
			URI path;
			try {
				path = new URI(kml);
			} catch (URISyntaxException e) {
				Log.d(TAG,"Error URISyntaxException");
				e.printStackTrace();
				return;
			}
			File file = new File(path);
			if (file.exists()) {
				FileInputStream fileInputStream;
				try {
					fileInputStream = new FileInputStream(file);
					kmlLayer = new KmlLayer(map, fileInputStream, TiApplication.getInstance().getApplicationContext());
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					Log.d(TAG,"Error FileNotFoundException");
					e1.printStackTrace();
				} catch (XmlPullParserException e) {
					Log.d(TAG,"Error XmlPullParserException");
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					Log.d(TAG,"Error IOException");
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				Log.e(TAG,"NO FILE FOUND " + kml);
			}
		} else {
			Log.d(TAG,"Error map view is null");
		}
	}
	
	@Kroll.method
	public void addLayerToMap() {
		if (kmlLayer != null) {
			try {
				kmlLayer.addLayerToMap();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				Log.d(TAG,"Error IOException");
				e.printStackTrace();
			} catch (XmlPullParserException e) {
				Log.d(TAG,"Error XmlPullParserException");
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	@Kroll.method
	public void removeLayerFromMap() {
		if (kmlLayer != null) {
			kmlLayer.removeLayerFromMap();
		}
	}

	@Kroll.method
	public void moveCameraToKml() {
        //Retrieve the first container in the KML layer
        KmlContainer container = kmlLayer.getContainers().iterator().next();
        //Retrieve a nested container within the first container
        container = container.getContainers().iterator().next();
        //Retrieve the first placemark in the nested container
        KmlPlacemark placemark = container.getPlacemarks().iterator().next();
        //Retrieve a polygon object in a placemark
        KmlPolygon polygon = (KmlPolygon) placemark.getGeometry();
        //Create LatLngBounds of the outer coordinates of the polygon
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (LatLng latLng : polygon.getOuterBoundaryCoordinates()) {
            builder.include(latLng);
        }

		int width =  TiApplication.getInstance().getApplicationContext().getResources().getDisplayMetrics().widthPixels;
        int height =  TiApplication.getInstance().getApplicationContext().getResources().getDisplayMetrics().heightPixels;
        map.moveCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), width, height, 1));
    }

	@Override
	public void propertyChanged(String key, Object oldValue, Object newValue, KrollProxy proxy) {
		Log.i(TAG,"kmlLayer propertyChanged ");
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
		
		Log.d(TAG,"kmlLayer propertiesChanged ");

	}

}
