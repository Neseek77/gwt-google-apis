/*
 * Copyright 2008 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.google.gwt.maps.client.overlay;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.maps.client.TestUtilities;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.geom.LatLngBounds;

/**
 * Tests the Overlay class.
 */
public class OverlayTest extends GWTTestCase {
  // length of time to wait for asynchronous test to complete.
  static final int ASYNC_DELAY_MSEC = 5000;

  private static native JavaScriptObject nativeCreateGeoXml(String url) /*-{
    return new $wnd.GGeoXml(url);
  }-*/;

  private static native JavaScriptObject nativeCreateGroundOverlay(String url,
      LatLngBounds bounds) /*-{
    return new $wnd.GGroundOverlay(url, bounds);
  }-*/;

  private static native JavaScriptObject nativeCreateMarker(LatLng p1) /*-{
    return new $wnd.GMarker(p1);
  }-*/;

  private static native JavaScriptObject nativeCreatePolygon(LatLng p1,
      LatLng p2, LatLng p3) /*-{
    return new $wnd.GPolygon([p1,p2,p3]);
  }-*/;

  private static native JavaScriptObject nativeCreatePolyline(LatLng p1,
      LatLng p2) /*-{
    return new $wnd.GPolyline([p1,p2]);
  }-*/;

  private static native JavaScriptObject nativeCreateTileLayerOverlay() /*-{
    return new $wnd.GTileLayerOverlay($wnd.G_NORMAL_MAP.getTileLayers()[0]);
  }-*/;

  private static native JavaScriptObject nativeCreateTrafficOverlay() /*-{
    return new $wnd.GTrafficOverlay();
  }-*/;

  @Override
  public String getModuleName() {
    return "com.google.gwt.maps.GoogleMapsTest";
  }

  /**
   * Runs before every test method.
   */
  @Override
  public void gwtSetUp() {
    TestUtilities.cleanDom();
  }

  public void testIsGeoXmlOverlay() {
    Overlay o = Overlay.createPeer(nativeCreateGeoXml("geoxml-test.html"));
    assertFalse("overlay instanceof Polyline", o instanceof Polyline);
    assertFalse("overlay instanceof Polygon", o instanceof Polygon);
    assertTrue("overlay instanceof GeoXmlOverlay", o instanceof GeoXmlOverlay);
  }

  public void testIsGroundOverlay() {
    Overlay o = Overlay.createPeer(nativeCreateGroundOverlay("spots.png",
        LatLngBounds.newInstance(LatLng.newInstance(0, 0), LatLng.newInstance(
            1, 1))));
    assertFalse("overlay instanceof Polyline", o instanceof Polyline);
    assertFalse("overlay instanceof Polygon", o instanceof Polygon);
    assertFalse("overlay instanceof GeoXmlOverlay", o instanceof GeoXmlOverlay);
    assertTrue("overlay instanceof GroundOverlay", o instanceof GroundOverlay);
  }

  public void testIsmarker() {
    Overlay o = Overlay.createPeer(nativeCreateMarker(LatLng.newInstance(0, 0)));
    assertTrue("overlay instanceof Marker", o instanceof Marker);
    assertFalse("overlay instanceof Polyline", o instanceof Polyline);
    assertFalse("overlay instanceof Polygon", o instanceof Polygon);
  }

  public void testIsPolygon() {
    Overlay o = Overlay.createPeer(nativeCreatePolygon(
        LatLng.newInstance(0, 0), LatLng.newInstance(1, 1), LatLng.newInstance(
            2, 0)));
    assertFalse("overlay instanceof Polyline", o instanceof Polyline);
    assertTrue("overlay instanceof Polygon", o instanceof Polygon);
  }

  public void testIsPolyline() {
    Overlay o = Overlay.createPeer(nativeCreatePolyline(
        LatLng.newInstance(0, 0), LatLng.newInstance(1, 1)));
    assertTrue("overlay instanceof Polyline", o instanceof Polyline);
    assertFalse("overlay instanceof Polygon", o instanceof Polygon);
  }

  public void testIsTileLayerOverlay() {
    Overlay o = Overlay.createPeer(nativeCreateTileLayerOverlay());
    assertTrue("overlay instanceof TileLayerOverlay",
        o instanceof TileLayerOverlay);
    assertFalse("overlay instanceof Polygon", o instanceof Polygon);
    assertFalse("overlay instanceof Polyline", o instanceof Polyline);
  }

  public void testIsTrafficOverlay() {
    Overlay o = Overlay.createPeer(nativeCreateTrafficOverlay());
    assertFalse("overlay instanceof TileLayerOverlay",
        o instanceof TileLayerOverlay);
    assertFalse("overlay instanceof Polygon", o instanceof Polygon);
    assertFalse("overlay instanceof Polyline", o instanceof Polyline);
    assertTrue("overlay instanceof TrafficLayer", o instanceof TrafficOverlay);
  }

  public void testOverlayZIndex() {
    LatLng atlanta = LatLng.newInstance(33.7814790, -84.3880580);
    double result1 = Overlay.getZIndex(atlanta.getLatitude());
    assertTrue("expected non-zero value", result1 != 0.0);
    double result2 = Overlay.getZIndex(atlanta.getLatitude() + 1);
    assertTrue("expected non-zero value", result2 != 0.0);
    assertTrue("expected result1 > result2 ", result1 > result2);
  }

}