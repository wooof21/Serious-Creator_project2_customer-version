package upload;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tools.Exit;

import com.mkcomingc.BaseActivity;
import com.mkcomingc.R;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

/******************************************
 * 类描述： 相册管理�? 类名称：PhotoAlbumActivity
 * 
 * @version: 1.0
 * @author: why
 * @time: 2013-10-18 下午2:10:46
 *****************************************/
public class PhotoAlbumActivity extends BaseActivity {

    private GridView aibumGV;
    private List<PhotoAibum> aibumList = new ArrayList<PhotoAibum>();

    public static ArrayList<String> selectedDataList = new ArrayList<String>();
    private LinearLayout selectedImageLayout;
    private TextView okButton;
    private HorizontalScrollView scrollview;
    private HashMap<String, ImageView> hashMap = new HashMap<String, ImageView>();

    private static final String[] STORE_IMAGES = {
	    MediaStore.Images.Media.DISPLAY_NAME, 
	    MediaStore.Images.Media.DATA, MediaStore.Images.Media.LONGITUDE, 
	    MediaStore.Images.Media._ID, 
	    MediaStore.Images.Media.BUCKET_ID, 
	    MediaStore.Images.Media.BUCKET_DISPLAY_NAME 

    };

    /* (non-Javadoc)
     * @see com.mkcomingc.BaseActivity#onPause()
     */
    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
    }
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.photoselect_album_activity);
	Exit.getInstance().addActivity(this);
	aibumGV = (GridView) findViewById(R.id.gridView1);
	aibumList = getPhotoAlbum();
	aibumGV.setAdapter(new PhotoAibumAdapter(aibumList, this));
	aibumGV.setOnItemClickListener(aibumClickListener);
	selectedImageLayout = (LinearLayout) findViewById(R.id.selected_image_layout);
	okButton = (TextView) findViewById(R.id.ok_button);
	scrollview = (HorizontalScrollView) findViewById(R.id.scrollview);
	
	findViewById(R.id.title_back).setOnClickListener(new OnClickListener() {
	    
	    @Override
	    public void onClick(View v) {
		// TODO Auto-generated method stub
		selectedDataList.clear();
		setResult(1004);
		finish();
	    }
	});
	initSelectImage();

    }

    @Override
    protected void onResume() {
	initSelectImage();
	super.onResume();
    }

    private void initSelectImage() {
	selectedImageLayout.removeAllViews();
	if (PhotoAlbumActivity.selectedDataList.size() == 0)
	    return;
	System.out.println("size --->" + PhotoAlbumActivity.selectedDataList);
	for (final String path : selectedDataList) {
	    System.out.println("path --> " + path);
	    ImageView imageView = (ImageView) LayoutInflater.from(
		    PhotoAlbumActivity.this).inflate(
		    R.layout.photoselect_album_choose_item,
		    selectedImageLayout, false);
	    selectedImageLayout.addView(imageView);
	    hashMap.put(path, imageView);
	    ImageManager2.from(PhotoAlbumActivity.this).displayImage(imageView,
		    path, R.drawable.photoselect_bg, 400, 400);
	    imageView.setOnClickListener(new View.OnClickListener() {

		@Override
		public void onClick(View v) {
		    removePath(path);
		}
	    });
	}
	okButton.setText("���(" + PhotoAlbumActivity.selectedDataList.size()
		+ "/4)");
	okButton.setOnClickListener(new View.OnClickListener() {

	    @Override
	    public void onClick(View v) {

		Intent intent = new Intent();
		Bundle bundle = new Bundle();
		// intent.putArrayListExtra("dataList", dataList);
		bundle.putStringArrayList("dataList",
			PhotoAlbumActivity.selectedDataList);
		intent.putExtras(bundle);
		setResult(1000, intent);
		finish();

	    }
	});

    }

    private boolean removePath(String path) {
	if (hashMap.containsKey(path)) {
	    selectedImageLayout.removeView(hashMap.get(path));
	    hashMap.remove(path);
	    removeOneData(PhotoAlbumActivity.selectedDataList, path);
	    okButton.setText("���(" + PhotoAlbumActivity.selectedDataList.size()
		    + "/4)");
	    return true;
	} else {
	    return false;
	}
    }

    private void removeOneData(ArrayList<String> arrayList, String s) {
	for (int i = 0; i < arrayList.size(); i++) {
	    if (arrayList.get(i).equals(s)) {
		arrayList.remove(i);
		return;
	    }
	}
    }

    /**
     * ������¼�
     */
    OnItemClickListener aibumClickListener = new OnItemClickListener() {
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
		long id) {
	    Intent intent = new Intent(PhotoAlbumActivity.this,
		    AlbumActivity.class);
	    intent.putExtra("aibum", aibumList.get(position));
	    Bundle bundle = new Bundle();
	    // intent.putArrayListExtra("dataList", dataList);
	    bundle.putStringArrayList("dataList",
		    getIntentArrayList(selectedDataList));
	    intent.putExtras(bundle);
	    startActivityForResult(intent, 0);
	    // startActivity(intent);
	}
    };

    private ArrayList<String> getIntentArrayList(ArrayList<String> dataList) {

	ArrayList<String> tDataList = new ArrayList<String>();

	for (String s : dataList) {
	    if (!s.contains("default")) {
		tDataList.add(s);
	    }
	}
	return tDataList;
    }

    @Override
    protected void onDestroy() {
	//selectedDataList.clear();
	super.onDestroy();
    }

    /**
     * 方法描述：按相册获取图片信息
     * 
     * @author: why
     * @time: 2013-10-18 下午1:35:24
     */
    private List<PhotoAibum> getPhotoAlbum() {
	List<PhotoAibum> aibumList = new ArrayList<PhotoAibum>();
	Cursor cursor = MediaStore.Images.Media.query(getContentResolver(),
		MediaStore.Images.Media.EXTERNAL_CONTENT_URI, STORE_IMAGES);
	Map<String, PhotoAibum> countMap = new HashMap<String, PhotoAibum>();
	PhotoAibum pa = null;
	while (cursor.moveToNext()) {
	    String path = cursor.getString(1);
	    String id = cursor.getString(3);
	    String dir_id = cursor.getString(4);
	    String dir = cursor.getString(5);
	    Log.e("info", "id===" + id + "==dir_id==" + dir_id + "==dir=="
		    + dir + "==path=" + path);
	    if (!countMap.containsKey(dir_id)) {
		pa = new PhotoAibum();
		pa.setName(dir);
		pa.setBitmap(Integer.parseInt(id));
		pa.setCount("1");
		pa.getBitList().add(new PhotoItem(Integer.valueOf(id), path));
		countMap.put(dir_id, pa);
	    } else {
		pa = countMap.get(dir_id);
		pa.setCount(String.valueOf(Integer.parseInt(pa.getCount()) + 1));
		pa.getBitList().add(new PhotoItem(Integer.valueOf(id), path));
	    }
	}
	cursor.close();
	Iterable<String> it = countMap.keySet();
	for (String key : it) {
	    aibumList.add(countMap.get(key));
	}
	return aibumList;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

	if (requestCode == 0) {
	    if (resultCode == RESULT_OK) {
		Bundle bundle = data.getExtras();
		ArrayList<String> tDataList = (ArrayList<String>) bundle
			.getSerializable("dataList");
		if (tDataList != null) {
//		    if (tDataList.size() < 8) {
//			tDataList.add("camera_default");
//		    }
		    selectedDataList.clear();
		    selectedDataList.addAll(tDataList);
		    // gridImageAdapter.notifyDataSetChanged();
		}
	    }
	}

    }
}
