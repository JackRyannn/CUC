package com.digitalcuc;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
public class MapActivity extends Activity{
	 public MapView mapView = null;
	 public BaiduMap baiduMap = null;
	 public TextView tv1;
	 public TextView tv2;
	 public TextView tv3;
	 public TextView tv4;
	 public TextView tv5;
	 public TextView tv6;
	 public TextView tv7;
	 public TextView tv8;
	 public TextView tv9;
	 public TextView tv10;
	 public TextView tv11;
	 public TextView tv12;
	 public TextView tv13;
	 Button button1;
	 
	    public LocationClient locationClient = null;
	    BitmapDescriptor mCurrentMarker = null;
	    boolean isFirstLoc = true;
	    public BDLocationListener myListener = new BDLocationListener() {
	        @Override
	        public void onReceiveLocation(BDLocation location) {
	            if (location == null || mapView == null)
	                return;
			            StringBuffer sb = new StringBuffer(256);
						sb.append("time : ");
						sb.append(location.getTime());
						sb.append("\nerror code : ");
						sb.append(location.getLocType());
						sb.append("\nlatitude : ");
						sb.append(location.getLatitude());
						sb.append("\nlontitude : ");
						sb.append(location.getLongitude());
						sb.append("\nradius : ");
						sb.append(location.getRadius());
						if (location.getLocType() == BDLocation.TypeGpsLocation){
							sb.append("\nspeed : ");
							sb.append(location.getSpeed());
							sb.append("\nsatellite : ");
							sb.append(location.getSatelliteNumber());
						} else if (location.getLocType() == BDLocation.TypeNetWorkLocation){
							sb.append("\naddr : ");
							sb.append(location.getAddrStr());
						} 
						
						double jingdu = location.getLatitude();
						double weidu = location.getLongitude();
						double gaodu = location.getAltitude();
						double fangxiang = location.getDirection();
					
						 tv1.setText("���ȣ�"+jingdu);
						 tv2.setText("γ�ȣ�"+weidu);		
						 tv3.setText("�߶�:"+gaodu);
						 tv4.setText("����:"+fangxiang);
						 tv5.setText("��ַ:"+location.getAddrStr());
						 tv6.setText("ʡ:"+location.getProvince());
						 tv7.setText("��:"+location.getCity());
						 tv8.setText("��:"+location.getDistrict());
						 tv9.setText("��:"+location.getStreet());
						 tv10.setText("��:"+location.getFloor());






	             
	            MyLocationData locData = new MyLocationData.Builder()
	                    .accuracy(location.getRadius())
	                    .direction(100).latitude(location.getLatitude())
	                    .longitude(location.getLongitude()).build();
	            baiduMap.setMyLocationData(locData);    //���ö�λ���
	             
	            
	            if (isFirstLoc) {
	                isFirstLoc = false;
	                 
	                 
	                LatLng ll = new LatLng(location.getLatitude(),
	                        location.getLongitude());
	                MapStatusUpdate u = MapStatusUpdateFactory.newLatLngZoom(ll, 16);
	                baiduMap.animateMapStatus(u);
	            }
	        }
	    };
	 
	   
	 
	 
	 
	    @Override  
	    protected void onCreate(Bundle savedInstanceState) {  
	        super.onCreate(savedInstanceState);   
	        
	        //��ʹ��SDK�����֮ǰ��ʼ��context��Ϣ������ApplicationContext  
	        //ע��÷���Ҫ��setContentView����֮ǰʵ��  
	        SDKInitializer.initialize(getApplicationContext());  
	        setContentView(R.layout.activity_map);  
	        //��ȡ��ͼ�ؼ�����  
	        button1 = (Button)findViewById(R.id.button1);
	        mapView = (MapView) findViewById(R.id.bmapView);   
	        tv1 = (TextView)findViewById(R.id.textView1);
	        tv2 = (TextView)findViewById(R.id.textView2);
	        tv3 = (TextView)findViewById(R.id.textView3);
	        tv4 = (TextView)findViewById(R.id.textView4);
	        tv5 = (TextView)findViewById(R.id.textView5);
	        tv6 = (TextView)findViewById(R.id.textView6);
	        tv7 = (TextView)findViewById(R.id.textView7);
	        tv8 = (TextView)findViewById(R.id.textView8);
	        tv9 = (TextView)findViewById(R.id.textView9);
	        tv10 = (TextView)findViewById(R.id.textView10);
	        tv11 = (TextView)findViewById(R.id.textView11);
	        tv12 = (TextView)findViewById(R.id.textView12);
	        tv13 = (TextView)findViewById(R.id.textView13);
	        


	        baiduMap = mapView.getMap();
	        //������λͼ��
	        baiduMap.setMyLocationEnabled(true);    
	        locationClient = new LocationClient(getApplicationContext()); // ʵ��LocationClient��
	        locationClient.registerLocationListener(myListener); // ע�������
	        this.setLocationOption();   //��ȡ��λ����        
	        locationClient.start(); // ��ʼ��λ
	       
	        
	        
	         baiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL); // ����Ϊһ���ͼ
	 
	        // baiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE); //����Ϊ���ǵ�ͼ
	         baiduMap.setTrafficEnabled(true); //������ͨͼ
	         
		       //����Maker����  
	         LatLng point = new LatLng(39.916357,116.5626); //zhulou 
	         //����Markerͼ��  
	         BitmapDescriptor bitmap = BitmapDescriptorFactory  
		             .fromResource(R.drawable.maker);  
	         //����MarkerOption�������ڵ�ͼ�����Marker  
	         OverlayOptions option = new MarkerOptions()  
	             .position(point)  
	             .icon(bitmap);  
	         
	         //�ڵ�ͼ�����Marker������ʾ  
	         Marker marker = (Marker) (baiduMap.addOverlay(option));
	         Bundle bundle = new Bundle();
	         bundle.putInt("num", 1);
	         marker.setExtraInfo(bundle);

//	         marker.setIcon(bitmap1);
	         //����Maker����  
	          point = new LatLng(39.917666,116.562492);  //yijiao
	         //����Markerͼ��  
	          bitmap = BitmapDescriptorFactory  
	             .fromResource(R.drawable.maker);  
	         //����MarkerOption�������ڵ�ͼ�����Marker  
	          option = new MarkerOptions()  
	             .position(point)  
	             .icon(bitmap);  
	          
	         	         //�ڵ�ͼ�����Marker������ʾ  
	          	Marker marker2 = (Marker) (baiduMap.addOverlay(option));
		         Bundle bundle2 = new Bundle();
		         bundle2.putInt("num", 2);
		         marker2.setExtraInfo(bundle2);
		          //����Maker����  
	          point = new LatLng(39.917345,116.56547);  //nancao
	         //����Markerͼ��  
	          bitmap = BitmapDescriptorFactory  
	             .fromResource(R.drawable.maker);  
	         //����MarkerOption�������ڵ�ͼ�����Marker  
	          option = new MarkerOptions()  
	             .position(point)  
	             .icon(bitmap);  
	         //�ڵ�ͼ�����Marker������ʾ  
	          Marker marker3 = (Marker) (baiduMap.addOverlay(option));
		         Bundle bundle3 = new Bundle();
		         bundle3.putInt("num", 3);
		         marker3.setExtraInfo(bundle3);
		          //����Maker����  
	          point = new LatLng(39.913938,116.562995);  //bangzijing
	         //����Markerͼ��  
	          bitmap = BitmapDescriptorFactory  
	             .fromResource(R.drawable.maker);  
	         //����MarkerOption�������ڵ�ͼ�����Marker  
	          option = new MarkerOptions()  
	             .position(point)  
	             .icon(bitmap);  
	         //�ڵ�ͼ�����Marker������ʾ  
	          Marker marker4 = (Marker) (baiduMap.addOverlay(option));
		         Bundle bundle4 = new Bundle();
		         bundle4.putInt("num", 4);
		         marker4.setExtraInfo(bundle4);
		           //����Maker����  
	          point = new LatLng(39.920534,116.564271);  //tushuguan
	         //����Markerͼ��  
	          bitmap = BitmapDescriptorFactory  
	             .fromResource(R.drawable.maker);  
	         //����MarkerOption�������ڵ�ͼ�����Marker  
	          option = new MarkerOptions()  
	             .position(point)  
	             .icon(bitmap);  
	         //�ڵ�ͼ�����Marker������ʾ  
	          Marker marker5 = (Marker) (baiduMap.addOverlay(option));
		         Bundle bundle5 = new Bundle();
		         bundle5.putInt("num", 5);
		         marker5.setExtraInfo(bundle5);
		              //����Maker����  
	          point = new LatLng(39.920132,116.562555);  //48
	         //����Markerͼ��  
	          bitmap = BitmapDescriptorFactory  
	             .fromResource(R.drawable.maker);  
	         //����MarkerOption�������ڵ�ͼ�����Marker  
	          option = new MarkerOptions()  
	             .position(point)  
	             .icon(bitmap);  
	         //�ڵ�ͼ�����Marker������ʾ  
	          Marker marker6 = (Marker) (baiduMap.addOverlay(option));
		         Bundle bundle6 = new Bundle();
		         bundle6.putInt("num", 6);
		         marker6.setExtraInfo(bundle6);
       //����Maker����  
	          point = new LatLng(39.915992,116.567128);  //1500
	         //����Markerͼ��  
	          bitmap = BitmapDescriptorFactory  
	             .fromResource(R.drawable.maker);  
	         //����MarkerOption�������ڵ�ͼ�����Marker  
	          option = new MarkerOptions()  
	             .position(point)  
	             .icon(bitmap);  
	         //�ڵ�ͼ�����Marker������ʾ  
	          Marker marker7 = (Marker) (baiduMap.addOverlay(option));
		         Bundle bundle7 = new Bundle();
		         bundle7.putInt("num", 7);
		         marker7.setExtraInfo(bundle7);
		         baiduMap.setOnMarkerClickListener(new OnMarkerClickListener()
		         {  
		        	    /** 
		        	    * ��ͼ Marker ���������¼������� 
		        	    * @param marker ������� marker 
		        	    */  
		        	    public boolean onMarkerClick(Marker marker){  
		                    int i = (int) marker.getExtraInfo().getInt("num");
		                    switch (i) {
		                    case 1:
								marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.maptxt_zhulou));
			        	    	Bundle bundle = new Bundle();
				       	         bundle.putInt("num", 11);
				       	         marker.setExtraInfo(bundle);	
								break;
		                    case 2:
								marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.maptxt_yijiao));
			        	    	 bundle = new Bundle();
				       	         bundle.putInt("num", 22);
				       	         marker.setExtraInfo(bundle);	
								break;
		                    case 3:
								marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.maptxt_nancao));
			        	    	 bundle = new Bundle();
				       	         bundle.putInt("num", 33);
				       	         marker.setExtraInfo(bundle);	
								break;
		                    case 4:
								marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.maptxt_bzj));
			        	    	 bundle = new Bundle();
				       	         bundle.putInt("num", 44);
				       	         marker.setExtraInfo(bundle);	
								break;
		                    case 5:
								marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.maptxt_lib));
			        	    	 bundle = new Bundle();
				       	         bundle.putInt("num", 55);
				       	         marker.setExtraInfo(bundle);	
								break;
		                    case 6:
								marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.maptxt_48jiao));
			        	    	 bundle = new Bundle();
				       	         bundle.putInt("num", 66);
				       	         marker.setExtraInfo(bundle);	
								break;
							case 7:
								marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.maptxt_zonghelou));
			        	    	 bundle = new Bundle();
				       	         bundle.putInt("num", 77);
				       	         marker.setExtraInfo(bundle);	
								break;
							case 11:
								marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.maker));
			        	    	 bundle = new Bundle();
				       	         bundle.putInt("num", 1);
				       	         marker.setExtraInfo(bundle);	
								break;
		                    case 22:
								marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.maker));
			        	    	 bundle = new Bundle();
				       	         bundle.putInt("num", 2);
				       	         marker.setExtraInfo(bundle);	
								break;
		                    case 33:
								marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.maker));
			        	    	 bundle = new Bundle();
				       	         bundle.putInt("num", 3);
				       	         marker.setExtraInfo(bundle);	
								break;
		                    case 44:
								marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.maker));
			        	    	 bundle = new Bundle();
				       	         bundle.putInt("num", 4);
				       	         marker.setExtraInfo(bundle);	
								break;
		                    case 55:
								marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.maker));
			        	    	 bundle = new Bundle();
				       	         bundle.putInt("num", 5);
				       	         marker.setExtraInfo(bundle);	
								break;
		                    case 66:
								marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.maker));
			        	    	 bundle = new Bundle();
				       	         bundle.putInt("num", 6);
				       	         marker.setExtraInfo(bundle);	
								break;
							case 77:
								marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.maker));
			        	    	 bundle = new Bundle();
				       	         bundle.putInt("num", 7);
				       	         marker.setExtraInfo(bundle);	
								break;

							default:
								break;
							}
		                    
		       	         return true;
		        	    }  
		        	});	         
	         
	        button1.setOnClickListener(new OnClickListener(){
				@Override 
				public void onClick(View v){
					locationClient.stop();
					setLocationOption();
			        locationClient.start(); // ��ʼ��λ
				}
			});
	        
	    }  
	    @Override  
	    protected void onDestroy() {  
	      //�˳�ʱ��ٶ�λ
	        locationClient.stop();
	        baiduMap.setMyLocationEnabled(false);
	        // TODO Auto-generated method stub
	        super.onDestroy();
	        mapView.onDestroy();
	        mapView = null;
	        //��activityִ��onDestroyʱִ��mMapView.onDestroy()��ʵ�ֵ�ͼ�������ڹ���  
	    }  
	    @Override  
	    protected void onResume() {  
	        super.onResume();  
	        //��activityִ��onResumeʱִ��mMapView. onResume ()��ʵ�ֵ�ͼ�������ڹ���  
	        mapView.onResume();  
	        }  
	    @Override  
	    protected void onPause() {  
	        super.onPause();  
	        //��activityִ��onPauseʱִ��mMapView. onPause ()��ʵ�ֵ�ͼ�������ڹ���  
	        mapView.onPause();  
	    
	        
	}
	    /**
	     * ���ö�λ����
	     */
	    private void setLocationOption() {
	        LocationClientOption option = new LocationClientOption();
	        option.setOpenGps(true); // ��GPS
	        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);// ���ö�λģʽ
	        option.setCoorType("bd09ll"); // ���صĶ�λ����ǰٶȾ�γ��,Ĭ��ֵgcj02
	        option.setScanSpan(5000); // ���÷���λ����ļ��ʱ��Ϊ5000ms
	        option.setIsNeedAddress(true); // ���صĶ�λ�����ַ��Ϣ
	        option.setNeedDeviceDirect(true); // ���صĶ�λ�����ֻ��ͷ�ķ���
	         
	        locationClient.setLocOption(option);
	    }
	    
	    
	    
	    
}
